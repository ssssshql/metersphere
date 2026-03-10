package io.metersphere.xpack.system.controller.lark;

import io.metersphere.xpack.system.dto.lark.LarkEnableEditorRequest;
import io.metersphere.xpack.system.dto.lark.LarkInfoDTO;
import io.metersphere.xpack.system.service.lark.LarkLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/controller/lark/LarkLoginController.class */
@RequestMapping({"/lark"})
@Tag(name = "飞书")
@RestController
public class LarkLoginController {

    @Resource
    private LarkLoginService larkLoginService;

    @GetMapping({"/info"})
    @Operation(summary = "获取配置信息")
    public LarkInfoDTO login() {
        return this.larkLoginService.getInfo();
    }

    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ"})
    @GetMapping({"/info/with_detail"})
    @Operation(summary = "获取配置信息")
    public LarkInfoDTO getDetailInfo() {
        return this.larkLoginService.getDetailInfo();
    }

    @PostMapping({"/save"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ+UPDATE"})
    @Operation(summary = "保存飞书设置")
    public void save(@RequestBody LarkInfoDTO larkInfoDTO) {
        this.larkLoginService.save(larkInfoDTO);
    }

    @PostMapping({"/enable"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ+UPDATE"})
    @Operation(summary = "飞书开启关闭")
    public void switchEnable(@RequestBody LarkEnableEditorRequest editorRequest) {
        this.larkLoginService.switchEnable(editorRequest.isEnable());
    }

    @PostMapping({"/validate"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ+UPDATE"})
    @Operation(summary = "校验配置是否链接成功")
    public void validate(@RequestBody LarkInfoDTO larkInfoDTO) {
        this.larkLoginService.validate(larkInfoDTO);
    }

    @PostMapping({"/change/validate"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_QRCODE:READ+UPDATE"})
    @Operation(summary = "关闭校验状态")
    public void validate() {
        this.larkLoginService.changeValid(false);
    }
}
