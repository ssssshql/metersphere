package io.metersphere.xpack.system.controller;

import io.metersphere.system.dto.AuthSourceDTO;
import io.metersphere.xpack.system.service.AuthSourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/controller/AuthenticationController.class */
@RequestMapping({"/authentication"})
@Tag(name = "登录认证方式")
@RestController
public class AuthenticationController {

    @Resource
    private AuthSourceService authSourceService;

    @GetMapping({"/get-list"})
    @Operation(summary = "获取开启的认证类型列表(登录类型展示)")
    public List<String> getAuthSourceList() {
        return this.authSourceService.getAuthSourceList();
    }

    @GetMapping({"/get/by/type/{type}"})
    @Operation(summary = "系统设置-系统-系统参数-认证设置-详细信息")
    public AuthSourceDTO getByType(@PathVariable("type") String type) {
        return this.authSourceService.getAuthSourceByType(type);
    }
}
