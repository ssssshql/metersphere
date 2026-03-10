package io.metersphere.xpack.config;

import io.metersphere.sdk.exception.MSException;
import io.metersphere.sdk.util.LogUtils;
import io.metersphere.sdk.util.Translator;
import io.metersphere.system.dto.sdk.LicenseDTO;
import io.metersphere.xpack.license.enums.LicenseEnum;
import io.metersphere.xpack.license.service.LicenseServiceImpl;
import io.metersphere.xpack.system.dto.request.AuthSourceRequest;
import io.metersphere.xpack.system.service.UserXpackServiceImpl;
import io.metersphere.xpack.system.service.lark.LarkLoginService;
import jakarta.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/* JADX INFO: compiled from: rb */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/config/LicenseInterceptor.class */
@Aspect
@Component
public class LicenseInterceptor {

    @Resource
    private UserXpackServiceImpl userXpackServiceImpl;

    @Resource
    private LicenseServiceImpl licenseServiceImpl;
    private final List<String> verifications = Arrays.asList(LarkLoginService.ALLATORIxDEMO("\r{\u0017s\u001f{\u000f\u007f"), AuthSourceRequest.ALLATORIxDEMO("\u0018M\u001de\u0010J\u001cG\nL"), LarkLoginService.ALLATORIxDEMO("}\u001en:o\u000fr(u\u000eh\u0018\u007f7s\bn"));

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
//    @Around("execution(* io.metersphere.xpack..*(..)) && (@annotation(org.springframework.web.bind.annotation.PostMapping) ||@annotation(org.springframework.web.bind.annotation.GetMapping)|| @annotation(org.springframework.web.bind.annotation.RequestMapping))")
//    public Object interceptor(ProceedingJoinPoint a) throws Throwable {
//        LicenseDTO licenseDTOValidate;
//        try {
//            if (!this.verifications.contains(a.getSignature().getMethod().getName()) && ((licenseDTOValidate = this.licenseServiceImpl.validate()) == null || !LicenseEnum.VALID.getName().equals(licenseDTOValidate.getStatus()))) {
//                throw new MSException(Translator.get(AuthSourceRequest.ALLATORIxDEMO("E\u0010J\u001cG\nL&_\u0018E\u0010M&E\u0010J\u001cG\nL&L\u000b[\u0016[")));
//            }
//            if (this.userXpackServiceImpl.checkValidateLicence(0) != 0) {
//                throw new MSException(LarkLoginService.ALLATORIxDEMO("N\u0013\u007f\t\u007f[{\t\u007f[n\u0014u[w\u001at\u0002:\u000ei\u001eh\b4"));
//            }
//            return a.proceed();
//        } catch (Throwable th) {
//            LogUtils.error(th.getMessage());
//            throw th;
//        }
//    }
}
