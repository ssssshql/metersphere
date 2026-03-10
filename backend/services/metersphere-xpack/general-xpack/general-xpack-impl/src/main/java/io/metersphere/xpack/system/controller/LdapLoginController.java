package io.metersphere.xpack.system.controller;

import io.metersphere.system.controller.handler.ResultHolder;
import io.metersphere.system.dto.sdk.LoginRequest;
import io.metersphere.xpack.system.service.LdapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/controller/LdapLoginController.class */
@RequestMapping({"/ldap"})
@Tag(name = "首页登录")
@RestController
public class LdapLoginController {

    @Resource
    private LdapService ldapService;

    @PostMapping({"/login"})
    @Operation(summary = "ldap登录")
    public ResultHolder login(@RequestBody LoginRequest request) {
        return this.ldapService.login(request);
    }
}
