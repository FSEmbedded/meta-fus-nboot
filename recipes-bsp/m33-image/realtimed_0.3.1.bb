#Copyright (c) 2024 F&S Elektronik Systeme GmbH
#SPDX-License-Identifier: BSD-3-Clause

SUMMARY = "F&S Realtime Daemon"
DESCRIPTION = "A M33 Image, that povides SRTM Services during runtime"
LICENSE = "CLOSED"

M33_MACHINE:mx8ulp-nxp-bsp ?= "fsimx8ulp"

SRC_URI = "${FUS_MIRROR}/${BPN}/${BPN}-${M33_MACHINE}_${PV}.bin"
SRC_URI[sha256sum] = "8bb4ca53d91bbd59067b3f745f040db7351d8ec363b5f5f983a39a621907f493"

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
