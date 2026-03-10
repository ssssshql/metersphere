package io.metersphere.xpack.system.service;

import io.metersphere.sdk.exception.MSException;
import io.metersphere.sdk.util.Translator;
import io.metersphere.system.dto.pool.TestResourceNodeDTO;
import io.metersphere.system.service.TestResourcePoolValidateService;
import io.metersphere.xpack.license.util.LicenseValidate;
import io.metersphere.xpack.system.ldap.p000vo.LdapRequest;
import io.metersphere.xpack.system.service.lark.LarkLoginService;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/TestResourcePoolValidateServiceImpl.class */
@Service
public class TestResourcePoolValidateServiceImpl implements TestResourcePoolValidateService {
    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public void validateNodeList(List<TestResourceNodeDTO> list) throws MSException {
        if (LicenseValidate.validate() || !CollectionUtils.isNotEmpty(list) || list.size() <= 1) {
            if (CollectionUtils.isEmpty(list)) {
                throw new MSException(Translator.get(LdapRequest.ALLATORIxDEMO("m,j=F;|:v<k*|\u0016i&v%7'v-|\u0016t<j=F!x?|\u0016v'|")));
            }
            return;
        }
        throw new MSException(Translator.get(LarkLoginService.ALLATORIxDEMO("n\u001ei\u000fE\t\u007f\bu\u000eh\u0018\u007f$j\u0014u\u00174\u0015u\u001f\u007f$w\u000ei\u000fE\u0019\u007f$u\u0015\u007f")));
    }
}
