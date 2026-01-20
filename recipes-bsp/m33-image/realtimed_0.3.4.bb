#Copyright (c) 2024 F&S Elektronik Systeme GmbH
#SPDX-License-Identifier: BSD-3-Clause

SUMMARY = "F&S Realtime Daemon"
DESCRIPTION = "A M33 Image, that povides SRTM Services during runtime"
LICENSE = "CLOSED"

M33_MACHINE:mx8ulp-nxp-bsp ?= "fsimx8ulp"

SRC_URI = "${FUS_MIRROR}/${BPN}/${BPN}-${M33_MACHINE}_${PV}.bin"
SRC_URI[sha256sum] = "d5fa9bafb205202de05597d07358c1aa629bec042e87bfd9cafe7240307f8e56"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install[noexec] = "1"

do_deploy() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0644 ${WORKDIR}/${BPN}-${M33_MACHINE}_${PV}.bin ${DEPLOY_DIR_IMAGE}/${BPN}-${M33_MACHINE}_${PV}.bin
	install -m 0644 ${WORKDIR}/${BPN}-${M33_MACHINE}_${PV}.bin ${DEPLOY_DIR_IMAGE}/${BPN}.bin
}

addtask deploy after do_install before do_build

COMPATIBLE_MACHINE = "(mx8ulp-nxp-bsp)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
