# Copyright (C) 2024 F&S Elektronik Systeme GmbH
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "NBOOT bootloader for F&S boards and modules"

PROVIDES += "nboot"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

FIRMWARE_ELE_IMX = "firmware-ele-imx"
FIRMWARE_UPOWER = "firmware-upower"
IMX_BOOT_FIRMWARE = "imx-boot-firmware-files"

FUS_EXTRA_FIRMWARE:mx9-generic-bsp = " ${FIRMWARE_ELE_IMX} ${IMX_BOOT_FIRMWARE}"
FUS_EXTRA_FIRMWARE:mx8ulp-generic-bsp = " ${FIRMWARE_ELE_IMX} ${IMX_BOOT_FIRMWARE} ${FIRMWARE_UPOWER}"
FUS_EXTRA_FIRMWARE:mx8m-generic-bsp = " ${IMX_BOOT_FIRMWARE}"

include nboot.inc

# Use this to build from local source
#SRC_URI = "git://${DL_DIR}/u-boot-fus;branch=master;protocol=file"
SRC_URI = "git://github.com/FSEmbedded/u-boot-fus;branch=${SRCBRANCH};protocol=https"

SRCBRANCH = "master"

#NOTE: UPDATE PV with new releases
PV:fsimx95 = "2026.09.1"
SRCREV:fsimx95 = "53f0694fe7564371e994a84122516c77300a94d1"
SRCBRANCH = "uboot-fus-2026.04"

PV:fsimx93 = "2026.09"
SRCREV:fsimx93 = "49ecaa46ba6ebd356d28f5632f505c80a2c8dd3b"

PV:fsimx8ulp = "2026.08"
SRCREV:fsimx8ulp = "7178c688eaf3b7e58b7fcb58ba787733b49083d2"

PV:fsimx8mp = "2026.07"
SRCREV:fsimx8mp = "49bd2eeef5f1038eaa155ac0109b6a1d63d15f6c"

PV:fsimx8mm = "2026.08"
SRCREV:fsimx8mm = "de5732e850366f3291fab3493cfd6e93c1596eab"

#SRCREV:fsimx8mn = ""
#PV:fsimx8mn = ""

B = "${WORKDIR}/build"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx8|mx93|mx95)"
