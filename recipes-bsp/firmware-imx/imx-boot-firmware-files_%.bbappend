deploy_for_mx8() {
    # Cadence HDMI
    install -m 0644 ${S}/firmware/hdmi/cadence/hdmitxfw.bin ${DEPLOYDIR}/Firmware/${PN}
    install -m 0644 ${S}/firmware/hdmi/cadence/hdmirxfw.bin ${DEPLOYDIR}/Firmware/${PN}
    install -m 0644 ${S}/firmware/hdmi/cadence/dpfw.bin ${DEPLOYDIR}/Firmware/${PN}
}

deploy_for_mx8m() {
    # Synopsys DDR
    for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
        install -m 0644 ${S}/firmware/ddr/synopsys/${ddr_firmware} ${DEPLOYDIR}/Firmware/${PN}
    done

    # Cadence DP and HDMI
    install -m 0644 ${S}/firmware/hdmi/cadence/signed_dp_imx8m.bin ${DEPLOYDIR}/Firmware/${PN}
    install -m 0644 ${S}/firmware/hdmi/cadence/signed_hdmi_imx8m.bin ${DEPLOYDIR}/Firmware/${PN}
}


deploy_for_mx9() {
    # Synopsys DDR
    for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
        install -m 0644 ${S}/firmware/ddr/synopsys/${ddr_firmware} ${DEPLOYDIR}/Firmware/${PN}
    done
}

do_deploy:prepend () {
	install -d ${DEPLOYDIR}/Firmware
	install -d ${DEPLOYDIR}/Firmware/${PN}
}

