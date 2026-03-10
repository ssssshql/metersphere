package io.metersphere.xpack.system.controller;

import io.metersphere.system.domain.SystemParameter;
import io.metersphere.system.log.annotation.Log;
import io.metersphere.system.log.constants.OperationLogType;
import io.metersphere.xpack.system.service.XpackSystemParameterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/controller/XpackSystemParameterController.class */
@RequestMapping({"/xpack/system/parameter"})
@Tag(name = "系统设置-系统参数-基础设置")
@RestController
public class XpackSystemParameterController {

    @Resource
    private XpackSystemParameterService xpackSystemParameterService;

    @PostMapping({"/save/api-concurrent-config"})
    @Log(type = OperationLogType.UPDATE, expression = "#msClass.updateLog(#systemParameter)", msClass = {XpackSystemParameterService.class})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_BASE:READ+UPDATE"})
    @Operation(summary = "系统设置-系统-系统参数-单接口任务并发数-保存")
    public void editApiConcurrentConfigInfo(@Validated @RequestBody SystemParameter systemParameter) {
        this.xpackSystemParameterService.editApiConcurrentConfigInfo(systemParameter);
    }
}
