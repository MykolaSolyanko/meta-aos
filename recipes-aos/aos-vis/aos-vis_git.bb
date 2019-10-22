DESCRIPTION = "AOS VIS"

LICENSE = "CLOSED"

GO_IMPORT = "gitpct.epam.com/epmd-aepr/aos_vis"

SRCREV = "${AUTOREV}"
SRC_URI = "\
    git://git@${GO_IMPORT}.git;protocol=ssh \
"

inherit go

PLUGINS ?= "storageadapter telemetryemulatoradapter renesassimulatoradapter"

S = "${WORKDIR}/git"

GO_INSTALL = "${GO_IMPORT}"

# embed version
GO_LDFLAGS += '-ldflags "-X main.GitSummary=`git --git-dir=${S}/src/${GO_IMPORT}/.git describe --tags --always`"'

DEPENDS += "\
    github.com-godbus-dbus \
    github.com-gorilla-websocket \
    github.com-sirupsen-logrus \
    gitpct.epam.com-epmd-aepr-aos-servicemanager \
"

RDEPENDS_${PN} += "\
    ca-certificates \
    openssl \
"

FILES_${PN} += "${libdir}/aos/vis_plugins/*"

GOLIB="${B}/lib"

do_compile_append() {
    rm -rf ${GOLIB} ${B}/pkg
    mkdir ${GOLIB}

    for PLUGIN in ${PLUGINS}
    do
        cd ${B}/src/${GO_IMPORT}/plugins/${PLUGIN}
        ${GO} build ${GO_LINKSHARED} ${GOBUILDFLAGS} -buildmode=plugin -o ${GOLIB}/${PLUGIN}.so
    done
}

do_install_append() {
    for PLUGIN in ${PLUGINS}
    do
        install -d ${D}${libdir}/aos/vis_plugins
        install -m644 ${GOLIB}/${PLUGIN}.so ${D}${libdir}/aos/vis_plugins
    done
}
