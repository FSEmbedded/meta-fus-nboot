# Copyright (C) 2024 F&S Elektronik Systeme GmbH
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "F&S Realtime Daemon"
DESCRIPTION = "A M33 Image, that povides SRTM Services during runtime"
LICENSE = "CLOSED"

M33_MACHINE:mx8ulp-nxp-bsp ?= "fsimx8ulp"

SRC_URI = "${FUS_MIRROR}/${BPN}/${BPN}-${M33_MACHINE}_${PV}.bin"
SRC_URI[sha256sum] = "3c4d01e4ddc469e4a3527cd2fd72aea6ffd761fe43e84dc009424e3e68bd0789"

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
