package io.metersphere.xpack.system.controller.wecom;

import io.metersphere.xpack.system.dto.wecom.WeComEnableEditorRequest;
import io.metersphere.xpack.system.dto.wecom.WeComInfoDTO;
import io.metersphere.xpack.system.service.wecom.WeComLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/controller/wecom/WeComLoginController.class */
@RequestMapping({"/we_com"})
@Tag(name = "企业微信")
@RestController
public class WeComLoginController {

    @Resource
    private WeComLoginService weComLoginService;

    @GetMapping({"/info"})
    @Operation(summary = "获取配置信息")
    public WeComInfoDTO login() {
        return this.weComLoginService.getInfo();
    }

    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ"})
    @GetMapping({"/info/with_detail"})
    @Operation(summary = "获取详细配置信息")
    public WeComInfoDTO getDetailInfo() {
        return this.weComLoginService.getDetailInfo();
    }

    @PostMapping({"/save"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ+UPDATE"})
    @Operation(summary = "保存企业微信设置")
    public void save(@RequestBody WeComInfoDTO weComInfoDTO) {
        this.weComLoginService.save(weComInfoDTO);
    }

    @PostMapping({"/enable"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ+UPDATE"})
    @Operation(summary = "企业微信开启关闭")
    public void switchEnable(@RequestBody WeComEnableEditorRequest editorRequest) {
        this.weComLoginService.switchEnable(editorRequest.isEnable());
    }

    @PostMapping({"/validate"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ+UPDATE"})
    @Operation(summary = "校验配置是否链接成功")
    public void validate(@RequestBody WeComInfoDTO weComInfoDTO) {
        this.weComLoginService.validate(weComInfoDTO);
    }

    @PostMapping({"/change/validate"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ+UPDATE"})
    @Operation(summary = "关闭校验状态")
    public void validate() {
        this.weComLoginService.changeValid(false);
    }
}
