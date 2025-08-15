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

include nboot.inc

# Use this to build from local source
#SRC_URI = "git://${DL_DIR}/u-boot-fus;branch=fsimx93;protocol=file"
SRC_URI = "git://github.com/FSEmbedded/u-boot-fus;branch=master;protocol=https"
SRCREV = "97a04cb7185ad3426979507344b30619828581bb"

#NOTE: UPDATE PV with new releases
PV = "2025.08"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx8|mx93)"

