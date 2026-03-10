package io.metersphere.xpack.system.controller.platform;

import io.metersphere.system.dto.sdk.OptionDTO;
import io.metersphere.xpack.system.dto.PlatformSourceDTO;
import io.metersphere.xpack.system.service.platform.PlatformParamService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/controller/platform/PlatformParamController.class */
@RequestMapping({"/setting"})
@Tag(name = "获取三方登陆的配置信息")
@RestController
public class PlatformParamController {

    @Resource
    private PlatformParamService platformParamService;

    @GetMapping({"/get/platform/param"})
    public List<OptionDTO> getPlatformParam() {
        return this.platformParamService.getPlatformParam();
    }

    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ"})
    @GetMapping({"/get/platform/info"})
    public List<PlatformSourceDTO> getPlatformInfo() {
        return this.platformParamService.getPlatformInfo();
    }
}
