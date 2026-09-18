SUMMARY = "F&S System Manager Firmware"
DESCRIPTION = "\
    The System Manager (SM) is a firmware that runs on a Cortex-M processor on \
    NXPs i.MX9 processors. The Cortex-M is the boot core, runs the boot ROM \
    which loads the SM (and other boot code), and then branches to the SM. The \
    SM then configures some aspects of the hardware such as isolation mechanisms \
    and then starts other cores in the system. After starting these cores, it \
    enters a service mode where it provides access to clocking, power, sensor, \
    and pin control via a client RPC API based on ARM's System Control and \
    Management Interface (SCMI)."
HOMEPAGE = "https://github.com/FSEmbedded/fus-sm"
SECTION = "firmware"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=f2a70813bc08547f509361c08b718861"

SRC_URI = "${FUS_SYSTEM_MANAGER_SRC};branch=${SRCBRANCH}"
FUS_SYSTEM_MANAGER_SRC ?= "git://github.com/FSEmbedded/fus-sm.git;protocol=https"
SRCBRANCH = "master"
SRCREV = "66dfffe94b7f586a3480d73b6d364c354bab5d59"

require imx-system-manager.inc


M33_MACHINE:mx95-generic-bsp ?= "fsimx95"
SYSTEM_MANAGER_FIRMWARE_BASENAME = "m33_image"
PACKAGECONFIG ??= "m2"

do_deploy() {
    install -m 0644 \
        ${B}/build/${SYSTEM_MANAGER_CONFIG}/${SYSTEM_MANAGER_FIRMWARE_BASENAME}.bin \
        ${DEPLOYDIR}/${BPN}-${M33_MACHINE}_${PV}.bin
    install -m 0644 \
        ${B}/build/${SYSTEM_MANAGER_CONFIG}/${SYSTEM_MANAGER_FIRMWARE_BASENAME}.bin \
        ${DEPLOY_DIR_IMAGE}/${BPN}-${MACHINE}.bin
}

