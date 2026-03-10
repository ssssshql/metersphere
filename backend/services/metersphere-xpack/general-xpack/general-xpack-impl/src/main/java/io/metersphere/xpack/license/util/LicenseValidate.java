package io.metersphere.xpack.license.util;

import io.metersphere.sdk.util.CommonBeanFactory;
import io.metersphere.xpack.license.enums.LicenseEnum;
import io.metersphere.xpack.license.service.LicenseServiceImpl;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: za */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/license/util/LicenseValidate.class */
public class LicenseValidate {
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static boolean validate() {
        LicenseServiceImpl licenseServiceImpl = (LicenseServiceImpl) CommonBeanFactory.getBean(LicenseServiceImpl.class);
        if (licenseServiceImpl == null) {
            return false;
        }
        return StringUtils.equalsIgnoreCase(licenseServiceImpl.validate().getStatus(), LicenseEnum.VALID.getName());
    }
}
