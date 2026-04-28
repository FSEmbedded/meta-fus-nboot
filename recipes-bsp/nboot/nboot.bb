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

SRCREV:fsimx93 = "88807d735f4cda604fc1d04a6f4b94475779bbc9"
SRCREV:fsimx8ulp = "ec4d60b7f721370a7504ae7939e6e7ba2af82003"
SRCREV:fsimx8mp = "76a1f712c128ef4b08a7bf05b46bc4b08633cc94"
SRCREV:fsimx8mm = "83adb1611edfbff224e8eae6f77272188b6f95df"
#SRCREV:fsimx8mn = ""

#NOTE: UPDATE PV with new releases
PV:fsimx93 = "2026.02.1"
PV:fsimx8ulp = "2026.04"
PV:fsimx8mp = "2025.12"
PV:fsimx8mm = "2026.03"
#PV:fsimx8mn = ""

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx8|mx93)"
