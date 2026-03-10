package io.metersphere.xpack.system.controller;

import io.metersphere.sdk.exception.MSException;
import io.metersphere.system.log.annotation.Log;
import io.metersphere.system.log.constants.OperationLogType;
import io.metersphere.xpack.system.dto.DisplayDTO;
import io.metersphere.xpack.system.service.DisplayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/controller/DisplayController.class */
@RequestMapping({"/display"})
@Tag(name = "系统设置-界面设置")
@RestController
public class DisplayController {

    @Resource
    private DisplayService displayService;

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    @PostMapping(value = {"/save"}, consumes = {"multipart/form-data"})
    @Log(type = OperationLogType.UPDATE, expression = "#msClass.updateLog(#request, #files)", msClass = {DisplayService.class})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_DISPLAY:READ+UPDATE"})
    @Operation(summary = "系统设置-界面设置-保存界面设置信息")
    public void save(@RequestPart("request") List<DisplayDTO> request, @RequestPart(value = "files", required = false) List<MultipartFile> files) throws MSException {
        this.displayService.save(request, files);
    }

    @GetMapping({"/info"})
    @Operation(summary = "系统设置-界面设置-保获取界面设置信息")
    public List<DisplayDTO> uiInfo() {
        return this.displayService.uiInfo("ui");
    }
}
