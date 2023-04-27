DESCRIPTION = "AOS fierwall CNI plugin"

GO_IMPORT = "github.com/aoscloud/aos_cni_vlan"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

BRANCH = "vlan"
SRCREV = "37f2ecac92f367ecaa315efc70db71e6aed4d383"
SRC_URI = "git://github.com/MykolaSolyanko/aos_cni_vlan.git;branch=${BRANCH};protocol=https"

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
    install -m 755 ${B}/${GO_BUILD_BINDIR}/aos-vlan ${D}${localbindir}
}
