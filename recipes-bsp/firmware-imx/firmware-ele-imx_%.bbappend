do_deploy:append () {
	install -d ${DEPLOYDIR}/Firmware
	install -d ${DEPLOYDIR}/Firmware/${PN}


    install -m 0644 ${S}/${SECO_FIRMWARE_NAME}  ${DEPLOYDIR}/Firmware/${PN}
}

