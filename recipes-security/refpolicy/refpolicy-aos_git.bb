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

BRANCH = "quota"
SRCREV = "3a8d5e405479fbd4696637736221b43e27a072f2"

SRC_URI = "git://github.com/MykolaSolyanko/refpolicy.git;branch=${BRANCH};protocol=https;name=refpolicy;destsuffix=refpolicy"

SRC_URI += " \
    file://customizable_types \
    file://setrans-mls.conf \
    file://setrans-mcs.conf \
"

POLICY_NAME = "aos"
POLICY_TYPE = "mcs"
POLICY_MLS_SENS = "0"
