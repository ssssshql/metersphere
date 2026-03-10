package io.metersphere.xpack.system.controller.dingtalk;

import io.metersphere.xpack.system.dto.dingtalk.DingTalkEnableEditorRequest;
import io.metersphere.xpack.system.dto.dingtalk.DingTalkInfoDTO;
import io.metersphere.xpack.system.service.dingtalk.DingTalkLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/controller/dingtalk/DingTalkLoginController.class */
@RequestMapping({"/ding_talk"})
@Tag(name = "钉钉")
@RestController
public class DingTalkLoginController {

    @Resource
    private DingTalkLoginService dingTalkLoginService;

    @GetMapping({"/info"})
    @Operation(summary = "获取配置信息")
    public DingTalkInfoDTO login() {
        return this.dingTalkLoginService.getInfo();
    }

    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ"})
    @GetMapping({"/info/with_detail"})
    @Operation(summary = "获取配置信息")
    public DingTalkInfoDTO getDetailInfo() {
        return this.dingTalkLoginService.getDetailInfo();
    }

    @PostMapping({"/save"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ+UPDATE"})
    @Operation(summary = "保存钉钉设置")
    public void save(@RequestBody DingTalkInfoDTO dingTalkInfoDTO) {
        this.dingTalkLoginService.save(dingTalkInfoDTO);
    }

    @PostMapping({"/enable"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ+UPDATE"})
    @Operation(summary = "钉钉开启关闭")
    public void switchEnable(@RequestBody DingTalkEnableEditorRequest editorRequest) {
        this.dingTalkLoginService.switchEnable(editorRequest.isEnable());
    }

    @PostMapping({"/validate"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ+UPDATE"})
    @Operation(summary = "校验配置是否链接成功")
    public void validate(@RequestBody DingTalkInfoDTO dingTalkInfoDTO) {
        this.dingTalkLoginService.validate(dingTalkInfoDTO);
    }

    @PostMapping({"/change/validate"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ+UPDATE"})
    @Operation(summary = "关闭校验状态")
    public void validate() {
        this.dingTalkLoginService.changeValid(false);
    }
}
