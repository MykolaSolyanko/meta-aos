DESCRIPTION = "AOS fierwall CNI plugin"

GO_IMPORT = "github.com/aoscloud/aos_cni_firewall"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

BRANCH = "main"
SRCREV = "4e89ddcb454c4051d47a2c58db0ddf72ed8a4830"
SRC_URI = "git://${GO_IMPORT}.git;branch=${BRANCH};protocol=https"

inherit go
inherit goarch

# embed version
GO_LDFLAGS += '-ldflags="-X github.com/containernetworking/plugins/pkg/utils/buildversion.BuildVersion=`git --git-dir=${S}/src/${GO_IMPORT}/.git describe --tags --always`"'

FILES_${PN} = "${libexecdir}/cni"

RDEPENDS_${PN} += "\
    iptables \
"

# WA to support go install for v 1.18

GO_LINKSHARED = ""

do_compile_prepend() {
    cd ${GOPATH}/src/${GO_IMPORT}/
}

do_install() {
    localbindir="${libexecdir}/cni"

    install -d ${D}${localbindir}
    install -m 755 ${B}/${GO_BUILD_BINDIR}/aos-firewall ${D}${localbindir}
}
