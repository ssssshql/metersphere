package io.metersphere.xpack.project.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.metersphere.project.domain.ProjectVersion;
import io.metersphere.sdk.exception.MSException;
import io.metersphere.system.log.annotation.Log;
import io.metersphere.system.log.constants.OperationLogType;
import io.metersphere.system.utils.PageUtils;
import io.metersphere.system.utils.Pager;
import io.metersphere.system.utils.SessionUtils;
import io.metersphere.validation.groups.Created;
import io.metersphere.validation.groups.Updated;
import io.metersphere.xpack.project.dto.ProjectVersionDTO;
import io.metersphere.xpack.project.dto.ProjectVersionOptionDTO;
import io.metersphere.xpack.project.dto.request.ProjectVersionRequest;
import io.metersphere.xpack.project.service.ProjectVersionLogService;
import io.metersphere.xpack.project.service.ProjectVersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/project/controller/ProjectVersionController.class */
@RequestMapping({"/project/version"})
@Tag(name = "项目管理-版本管理")
@RestController
public class ProjectVersionController {

    @Resource
    private ProjectVersionService projectVersionService;

    @PostMapping({"/list"})
    @RequiresPermissions({"PROJECT_VERSION:READ"})
    @Operation(summary = "项目管理-版本管理-列表查询")
    public Pager<List<ProjectVersionDTO>> list(@Validated @RequestBody ProjectVersionRequest request) {
        Page<Object> page = PageHelper.startPage(request.getCurrent(), request.getPageSize(), StringUtils.isNotBlank(request.getSortString()) ? request.getSortString() : "create_time desc");
        return PageUtils.setPageInfo(page, this.projectVersionService.list(request));
    }

    @PostMapping({"/add"})
    @Log(type = OperationLogType.ADD, expression = "#msClass.addLog(#dto)", msClass = {ProjectVersionLogService.class})
    @RequiresPermissions({"PROJECT_VERSION:READ+ADD"})
    @Operation(summary = "项目管理-版本管理-新增版本")
    public ProjectVersion add(@Validated({Created.class}) @RequestBody ProjectVersionDTO dto) {
        dto.setCreateUser(SessionUtils.getUserId());
        return this.projectVersionService.add(dto);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    @PostMapping({"/update"})
    @Log(type = OperationLogType.UPDATE, expression = "#msClass.updateLog(#dto)", msClass = {ProjectVersionLogService.class})
    @RequiresPermissions({"PROJECT_VERSION:READ+UPDATE"})
    @Operation(summary = "项目管理-版本管理-修改版本")
    public void update(@Validated({Updated.class}) @RequestBody ProjectVersionDTO dto) throws MSException {
        this.projectVersionService.update(dto);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    @Log(type = OperationLogType.DELETE, expression = "#msClass.deleteLog(#id)", msClass = {ProjectVersionLogService.class})
    @RequiresPermissions({"PROJECT_VERSION:READ+DELETE"})
    @Operation(summary = "项目管理-版本管理-删除版本")
    @Parameter(name = "id", description = "版本ID", schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED))
    @GetMapping({"/delete/{id}"})
    public void delete(@PathVariable String id) throws MSException {
        this.projectVersionService.delete(id);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    @Log(type = OperationLogType.UPDATE, expression = "#msClass.changeStatusLog(#id)", msClass = {ProjectVersionLogService.class})
    @RequiresPermissions({"PROJECT_VERSION:READ+UPDATE"})
    @Operation(summary = "项目管理-版本管理-切换版本状态")
    @Parameter(name = "id", description = "版本ID", schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED))
    @GetMapping({"/switch/status/{id}"})
    public void switchStatus(@PathVariable String id) throws MSException {
        this.projectVersionService.switchStatus(id);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    @Log(type = OperationLogType.UPDATE, expression = "#msClass.changeLatestLog(#id)", msClass = {ProjectVersionLogService.class})
    @RequiresPermissions({"PROJECT_VERSION:READ+UPDATE"})
    @Operation(summary = "项目管理-版本管理-切换最新版本")
    @Parameter(name = "id", description = "版本ID", schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED))
    @GetMapping({"/switch/latest/{id}"})
    public void switchLatest(@PathVariable String id) throws MSException {
        this.projectVersionService.switchLatest(id, null, true);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    @RequiresPermissions({"PROJECT_VERSION:READ+UPDATE"})
    @Operation(summary = "项目管理-版本管理-启用关闭版本")
    @Parameter(name = "id", description = "项目ID", schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED))
    @GetMapping({"/switch/enable/{id}"})
    public void switchEnable(@PathVariable String id) throws MSException {
        this.projectVersionService.switchEnable(id);
    }

    @RequiresPermissions({"PROJECT_VERSION:READ"})
    @Operation(summary = "项目管理-版本管理-查看版本启用状态")
    @Parameter(name = "id", description = "项目ID", schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED))
    @GetMapping({"/enable/{id}"})
    public boolean enable(@PathVariable String id) {
        return this.projectVersionService.getVersionEnable(id);
    }

    @Parameter(name = "id", description = "项目ID", schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED))
    @GetMapping({"/option/{id}"})
    @Operation(summary = "项目管理-版本管理-获取版本下拉选项")
    public List<ProjectVersionOptionDTO> getOption(@PathVariable String id) {
        return this.projectVersionService.getOption(id);
    }
}
