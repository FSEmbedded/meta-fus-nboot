# Copyright (C) 2024 F&S Elektronik Systeme GmbH
# Released under the MIT license (see COPYING.MIT for the terms)

DEPENDS:append = " python3 dtc-native bison-native lzop-native xxd-native"

EXTRA_OEMAKE:append = " -C ${S}"
NBOOT_MAKE_TARGET ??= "nboot"

inherit deploy
inherit use-imx-security-controller-firmware

EXTRA_OEMAKE:append  = ' CROSS_COMPILE=${TARGET_PREFIX} V=1'
EXTRA_OEMAKE:append  = ' CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS} ${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', ' -fuse-ld=bfd ', '', d)} ${DEBUG_PREFIX_MAP}"'
EXTRA_OEMAKE:append = ' HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}"'
EXTRA_OEMAKE:append = ' STAGING_INCDIR=${STAGING_INCDIR_NATIVE} STAGING_LIBDIR=${STAGING_LIBDIR_NATIVE}'

NEED_ATF ?= 'false'
NEED_OPTEE ?= 'false'
NEED_M33_IMAGE = "${@bb.utils.contains('MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS', 'm33-image', 'true', 'false', d)}"
NEED_UPOWER = "${@bb.utils.contains('IMX_EXTRA_FIRMWARE', 'firmware-upower', 'true', 'false', d)}"
NEED_OPTEE:mx8m-nxp-bsp = "${@bb.utils.contains('MACHINE_FEATURES', 'optee', 'true', 'false', d)}"
NEED_ATF:mx8m-nxp-bsp = 'true'

do_configure() {
	for config in ${UBOOT_CONFIG}; do
		oe_runmake O=${B}/${config} ${config}_defconfig
	done
}

do_compile[depends] += " \
    ${@' '.join('%s:do_deploy' % r for r in '${FUS_EXTRA_FIRMWARE}'.split() )} \
    imx-atf:do_deploy \
	${@bb.utils.contains('MACHINE_FEATURES', 'optee', 'optee-os:do_deploy', '', d)} \
    ${@bb.utils.contains('MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS', 'm33-image', 'm33-image:do_deploy', '', d)} \
"

do_compile:prepend() {
	install -d ${S}/board/F+S/NXP-Firmware/${MACHINE}

	for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
		cp "${DEPLOY_DIR_IMAGE}/Firmware/${IMX_BOOT_FIRMWARE}/${ddr_firmware}" "${S}/board/F+S/NXP-Firmware/${MACHINE}/${ddr_firmware}"
	done

	if [ -n "${SECO_FIRMWARE_NAME}" ]; then
		cp "${DEPLOY_DIR_IMAGE}/Firmware/${FIRMWARE_ELE_IMX}/${SECO_FIRMWARE_NAME}" "${S}/board/F+S/NXP-Firmware/${MACHINE}/${SECO_FIRMWARE_NAME}"
	fi

	if ${NEED_M33_IMAGE}; then
		cp "${DEPLOY_DIR_IMAGE}/m33_image.bin" "${S}/board/F+S/${MACHINE}/m33_image.bin"
	fi

	if ${NEED_UPOWER}; then
		cp "${DEPLOY_DIR_IMAGE}/imx-boot-tools/upower.bin" "${S}/board/F+S/NXP-Firmware/${MACHINE}/upower.bin"
	fi

	if ${NEED_ATF}; then
		cp ${DEPLOY_DIR_IMAGE}/bl31-${ATF_PLATFORM}.bin ${S}/board/F+S/NXP-Firmware/${MACHINE}/bl31.bin
		if ${NEED_OPTEE}; then
			cp ${DEPLOY_DIR_IMAGE}/bl31-${ATF_PLATFORM}.bin-optee ${S}/board/F+S/NXP-Firmware/${MACHINE}/bl31-optee.bin
		fi
	fi

	if ${NEED_OPTEE}; then
		cp ${DEPLOY_DIR_IMAGE}/tee.bin ${S}/board/F+S/NXP-Firmware/${MACHINE}/bl32.bin
	fi

	#Set SCMVERSION
	echo -n -F+S+g 1>${S}/.scmversion
	echo -n -F+S+g 1>${B}/.scmversion
	git -C ${S} rev-parse --short HEAD 1>> ${S}/.scmversion
	git -C ${S} rev-parse --short HEAD 1>> ${B}/.scmversion
}

do_compile() {
	for config in ${UBOOT_CONFIG}; do
		oe_runmake O=${B}/${config} ${NBOOT_MAKE_TARGET}
	done
}

do_install[noexec] = "1"

addtask deploy after do_compile

do_deploy[depends] += " u-boot-fus:do_deploy"
do_deploy() {
	install -d ${DEPLOY_DIR_IMAGE}/Firmware
	install -d ${DEPLOY_DIR_IMAGE}/Firmware/NXP-Firmware/
	install -d ${DEPLOY_DIR_IMAGE}/Firmware/NXP-Firmware/${MACHINE}

	install -m 0644 ${S}/board/F+S/NXP-Firmware/${MACHINE}/* ${DEPLOY_DIR_IMAGE}/Firmware/NXP-Firmware/${MACHINE}
	cd ${DEPLOY_DIR_IMAGE}/Firmware/ && ln -sf nboot-${UBOOT_CONFIG_BASENAME}-${PV}.fs ./nboot.fs; cd -

	for config in ${UBOOT_CONFIG}; do
		install -m 0644 ${B}/${config}/nboot-${UBOOT_CONFIG_BASENAME}-${PV}.fs ${DEPLOY_DIR_IMAGE}/Firmware/nboot-${config}-${PV}.fs
		cat ${DEPLOY_DIR_IMAGE}/Firmware/nboot.fs ${DEPLOY_DIR_IMAGE}/Firmware/uboot-${config}.fs > ${DEPLOY_DIR_IMAGE}/Firmware/flash_${config}.fs
	done
}
