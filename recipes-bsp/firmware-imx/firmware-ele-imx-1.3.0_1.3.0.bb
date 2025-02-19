require recipes-bsp/firmware-imx/firmware-ele-imx_1.3.0.bb

SRC_URI = "${FSL_MIRROR}/${BPN}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true"
S = "${WORKDIR}/${BPN}-${IMX_SRCREV_ABBREV}"

do_deploy () {
	install -d ${DEPLOYDIR}/Firmware
	install -d ${DEPLOYDIR}/Firmware/${PN}
    # Deploy the related firmware to be package by imx-boot
    install -m 0644 ${S}/${SECO_FIRMWARE_NAME}  ${DEPLOYDIR}/Firmware/${PN}
}
