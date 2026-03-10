package io.metersphere.xpack.system.controller.larksuite;

import io.metersphere.xpack.system.dto.lark.LarkEnableEditorRequest;
import io.metersphere.xpack.system.dto.lark.LarkInfoDTO;
import io.metersphere.xpack.system.service.larksuite.LarkSuiteLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/controller/larksuite/LarkSuiteLoginController.class */
@RequestMapping({"/lark_suite"})
@Tag(name = "国际版飞书")
@RestController
public class LarkSuiteLoginController {

    @Resource
    private LarkSuiteLoginService larkSuiteLoginService;

    @GetMapping({"/info"})
    @Operation(summary = "获取配置信息")
    public LarkInfoDTO login() {
        return this.larkSuiteLoginService.getInfo();
    }

    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ"})
    @GetMapping({"/info/with_detail"})
    @Operation(summary = "获取配置信息")
    public LarkInfoDTO getDetailInfo() {
        return this.larkSuiteLoginService.getDetailInfo();
    }

    @PostMapping({"/save"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ+UPDATE"})
    @Operation(summary = "保存国际飞书设置")
    public void save(@RequestBody LarkInfoDTO larkInfoDTO) {
        this.larkSuiteLoginService.save(larkInfoDTO);
    }

    @PostMapping({"/enable"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ+UPDATE"})
    @Operation(summary = "国际飞书开启关闭")
    public void switchEnable(@RequestBody LarkEnableEditorRequest editorRequest) {
        this.larkSuiteLoginService.switchEnable(editorRequest.isEnable());
    }

    @PostMapping({"/validate"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ+UPDATE"})
    @Operation(summary = "校验配置是否链接成功")
    public void validate(@RequestBody LarkInfoDTO larkInfoDTO) {
        this.larkSuiteLoginService.validate(larkInfoDTO);
    }

    @PostMapping({"/change/validate"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ+UPDATE"})
    @Operation(summary = "关闭校验状态")
    public void validate() {
        this.larkSuiteLoginService.changeValid(false);
    }
}
