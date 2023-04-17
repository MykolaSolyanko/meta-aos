DESCRIPTION = "AOS Message Proxy"

GO_IMPORT = "github.com/MykolaSolyanko/aos_message_proxy"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

BRANCH = "rewrite_select"
SRCREV = "2c50acc1935b4ba5283ddc8387deef449b732955"

SRC_URI = "git://${GO_IMPORT}.git;branch=${BRANCH};protocol=https"

SRC_URI += " \
    file://aos_messageproxy.cfg \
    file://aos-message-proxy.service \
    file://aos-target.conf \
"
FILES_${PN} += " \
    ${sysconfdir} \
    ${systemd_system_unitdir} \
"

inherit go goarch

DEPENDS = "xen-tools"
RDEPENDS_${PN} = "xen-tools-libxenvchan"

# embed version
GO_LDFLAGS += '-ldflags="-X main.GitSummary=`git --git-dir=${S}/src/${GO_IMPORT}/.git describe --tags --always`"'

# WA to support go install for v 1.18

GO_LINKSHARED = ""

do_compile_prepend() {
    cd ${GOPATH}/src/${GO_IMPORT}/
}

do_install_append() {
    install -d ${D}${sysconfdir}/aos
    install -m 0644 ${WORKDIR}/aos_messageproxy.cfg ${D}${sysconfdir}/aos

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/aos-message-proxy.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/systemd/system/aos.target.d
    install -m 0644 ${WORKDIR}/aos-target.conf ${D}${sysconfdir}/systemd/system/aos.target.d/${PN}.conf
}
