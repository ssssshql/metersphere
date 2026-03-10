package io.metersphere.xpack.system.controller.sso;

import io.metersphere.sdk.exception.MSException;
import io.metersphere.sdk.util.CodingUtils;
import io.metersphere.sdk.util.JSON;
import io.metersphere.system.controller.handler.ResultHolder;
import io.metersphere.system.utils.SessionUtils;
import io.metersphere.xpack.system.service.sso.SSOService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/controller/sso/SSOController.class */
@RequestMapping({"/sso/callback"})
@RestController
public class SSOController {

    @Resource
    private SSOService ssoService;

    @GetMapping({"/we_com"})
    @Operation(summary = "获取企业微信登陆验证")
    public ResultHolder callbackWeCom(@RequestParam("code") String code) {
        return this.ssoService.exchangeWeComToken(code);
    }

    @GetMapping({"/ding_talk"})
    @Operation(summary = "获取钉钉登陆验证")
    public ResultHolder callbackDingTalk(@RequestParam("code") String authCode) {
        return this.ssoService.exchangeDingTalkToken(authCode);
    }

    @GetMapping({"/lark"})
    @Operation(summary = "获取飞书登陆验证")
    public ResultHolder callbackLark(@RequestParam("code") String authCode) {
        return this.ssoService.exchangeLarkToken(authCode);
    }

    @GetMapping({"/lark_suite"})
    @Operation(summary = "获取国际飞书登陆验证")
    public ResultHolder callbackLarkSuite(@RequestParam("code") String authCode) {
        return this.ssoService.exchangeLarkSuiteToken(authCode);
    }

    @GetMapping({"/oidc/{authId}"})
    public ModelAndView callback(@RequestParam("code") String code, @PathVariable("authId") String authId) throws Exception {
        ResultHolder resultHolder = this.ssoService.exchangeOidc(code, authId);
        Map bodyMap = JSON.parseMap(JSON.toJSONString(resultHolder.getData()));
        String url = "redirect:/#/?_token=" + CodingUtils.base64Encoding(SessionUtils.getSessionId()) + "&_csrf=" + String.valueOf(bodyMap.get("csrfToken")) + "&_pId=" + SessionUtils.getUser().getLastProjectId() + "&_orgId=" + SessionUtils.getUser().getLastOrganizationId();
        return new ModelAndView(url);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    @GetMapping({"/oauth2"})
    public ModelAndView callbackOauth(@RequestParam("code") String code, @RequestParam("state") String authId) throws MSException {
        ResultHolder resultHolder = this.ssoService.exchangeOauth2(code, authId);
        Map bodyMap = JSON.parseMap(JSON.toJSONString(resultHolder.getData()));
        return new ModelAndView("redirect:/#/?_token=" + CodingUtils.base64Encoding(SessionUtils.getSessionId()) + "&_csrf=" + String.valueOf(bodyMap.get("csrfToken")) + "&_pId=" + SessionUtils.getUser().getLastProjectId() + "&_orgId=" + SessionUtils.getUser().getLastOrganizationId());
    }

    @GetMapping({"/cas/{authId}"})
    public ModelAndView casCallback(@RequestParam("ticket") String ticket, @PathVariable("authId") String authId) {
        ResultHolder resultHolder = this.ssoService.exchangeCas(ticket, authId);
        Map bodyMap = JSON.parseMap(JSON.toJSONString(resultHolder.getData()));
        return new ModelAndView("redirect:/#/?_token=" + CodingUtils.base64Encoding(SessionUtils.getSessionId()) + "&_csrf=" + String.valueOf(bodyMap.get("csrfToken")) + "&_pId=" + SessionUtils.getUser().getLastProjectId() + "&_orgId=" + SessionUtils.getUser().getLastOrganizationId());
    }
}
