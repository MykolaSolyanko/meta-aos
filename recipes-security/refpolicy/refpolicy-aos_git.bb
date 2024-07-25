SUMMARY = "SELinux Aos policy based on targeted policy"
DESCRIPTION = "\
This is the Aos modification for targeted variant of the \
SELinux reference policy. Most service domains are locked \
down. Users and admins will login in with unconfined_t \
domain, so they have the same access to the system as if \
SELinux was not enabled. \
"

include recipes-security/refpolicy/refpolicy_common.inc

PV = "2_20220106+git${SRCPV}"

BRANCH = "container"
# SRCREV = "${AUTOREV}"
SRCREV = "4228e46a08a96a4f4432f7ddb34f75c5e013179a"

SRC_URI = "git://github.com/MykolaSolyanko/refpolicy.git;branch=${BRANCH};protocol=https;name=refpolicy;destsuffix=refpolicy"

SRC_URI += " \
    file://customizable_types \
    file://setrans-mls.conf \
    file://setrans-mcs.conf \
"

POLICY_NAME = "aos"
POLICY_TYPE = "mcs"
POLICY_MLS_SENS = "0"
