package io.metersphere.xpack.system.controller;

import io.metersphere.sdk.exception.MSException;
import io.metersphere.sdk.util.BeanUtils;
import io.metersphere.system.dto.OrganizationDTO;
import io.metersphere.system.dto.OrganizationSwitchRequest;
import io.metersphere.system.dto.request.OrganizationEditRequest;
import io.metersphere.system.dto.sdk.OptionDTO;
import io.metersphere.system.log.annotation.Log;
import io.metersphere.system.log.constants.OperationLogType;
import io.metersphere.system.utils.SessionUtils;
import io.metersphere.validation.groups.Created;
import io.metersphere.xpack.system.service.XpackSystemOrganizationLogService;
import io.metersphere.xpack.system.service.XpackSystemOrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/controller/XpackSystemOrganizationController.class */
@RequestMapping({"/system/organization"})
@Tag(name = "系统设置-系统-组织与项目-组织")
@RestController
public class XpackSystemOrganizationController {

    @Resource
    private XpackSystemOrganizationService systemOrganizationService;

    @PostMapping({"/add"})
    @Log(type = OperationLogType.ADD, expression = "#msClass.addLog(#organizationEditRequest)", msClass = {XpackSystemOrganizationLogService.class})
    @RequiresPermissions({"SYSTEM_ORGANIZATION_PROJECT:READ+ADD"})
    @Operation(summary = "系统设置-系统-组织与项目-组织-添加组织")
    public OrganizationDTO add(@Validated({Created.class}) @RequestBody OrganizationEditRequest organizationEditRequest) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        BeanUtils.copyBean(organizationDTO, organizationEditRequest);
        organizationDTO.setCreateUser(SessionUtils.getUserId());
        organizationDTO.setUpdateUser(SessionUtils.getUserId());
        return this.systemOrganizationService.add(organizationDTO);
    }

    @GetMapping({"/switch-option"})
    @Operation(summary = "个人中心-获取切换组织下拉选项")
    public List<OptionDTO> getSwitchOption() {
        return this.systemOrganizationService.getSwitchOption(SessionUtils.getUserId());
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    @PostMapping({"/switch"})
    @Operation(summary = "个人中心-切换组织")
    public void switchOrg(@RequestBody OrganizationSwitchRequest request) throws MSException {
        this.systemOrganizationService.switchOrg(request, SessionUtils.getUserId());
    }
}
