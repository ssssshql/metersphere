package io.metersphere.xpack.system.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.metersphere.sdk.exception.MSException;
import io.metersphere.system.domain.AuthSource;
import io.metersphere.system.dto.AuthSourceDTO;
import io.metersphere.system.dto.sdk.BasePageRequest;
import io.metersphere.system.log.annotation.Log;
import io.metersphere.system.log.constants.OperationLogType;
import io.metersphere.system.utils.PageUtils;
import io.metersphere.system.utils.Pager;
import io.metersphere.xpack.system.dto.request.AuthSourceRequest;
import io.metersphere.xpack.system.dto.request.AuthSourceStatusRequest;
import io.metersphere.xpack.system.ldap.p000vo.LdapLoginRequest;
import io.metersphere.xpack.system.ldap.p000vo.LdapRequest;
import io.metersphere.xpack.system.service.AuthSourceLogService;
import io.metersphere.xpack.system.service.AuthSourceService;
import io.metersphere.xpack.system.service.LdapService;
import io.swagger.v3.oas.annotations.Operation;
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

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/controller/AuthSourceController.class */
@RequestMapping({"/system/authsource"})
@Tag(name = "系统设置-系统-系统参数-认证设置")
@RestController
public class AuthSourceController {

    @Resource
    private AuthSourceService authSourceService;

    @Resource
    private LdapService ldapService;

    @PostMapping({"/list"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_AUTH:READ"})
    @Operation(summary = "系统设置-系统-系统参数-认证设置-列表查询")
    public Pager<List<AuthSource>> list(@Validated @RequestBody BasePageRequest request) {
        Page<Object> page = PageHelper.startPage(request.getCurrent(), request.getPageSize(), StringUtils.isNotBlank(request.getSortString()) ? request.getSortString() : "create_time desc");
        return PageUtils.setPageInfo(page, this.authSourceService.list(request));
    }

    @PostMapping({"/add"})
    @Log(type = OperationLogType.ADD, expression = "#msClass.addLog(#authSource)", msClass = {AuthSourceLogService.class})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_AUTH:READ+ADD"})
    @Operation(summary = "系统设置-系统-系统参数-认证设置-新增")
    public AuthSource add(@Validated @RequestBody AuthSourceRequest authSource) {
        return this.authSourceService.addAuthSource(authSource);
    }

    @PostMapping({"/update"})
    @Log(type = OperationLogType.UPDATE, expression = "#msClass.updateLog(#authSource)", msClass = {AuthSourceLogService.class})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_AUTH:READ+UPDATE"})
    @Operation(summary = "系统设置-系统-系统参数-认证设置-更新")
    public AuthSourceRequest update(@Validated @RequestBody AuthSourceRequest authSource) {
        return this.authSourceService.updateAuthSource(authSource);
    }

    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_AUTH:READ"})
    @GetMapping({"/get/{id}"})
    @Operation(summary = "系统设置-系统-系统参数-认证设置-详细信息")
    public AuthSourceDTO get(@PathVariable("id") String id) {
        return this.authSourceService.getAuthSource(id);
    }

    @Log(type = OperationLogType.DELETE, expression = "#msClass.deleteLog(#id)", msClass = {AuthSourceLogService.class})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_AUTH:READ+DELETE"})
    @Operation(summary = "系统设置-系统-系统参数-认证设置-删除")
    @GetMapping({"/delete/{id}"})
    public void delete(@PathVariable("id") String id) {
        this.authSourceService.deleteAuthSource(id);
    }

    @PostMapping({"/update/status"})
    @Log(type = OperationLogType.UPDATE, expression = "#msClass.updateLog(#request.getId())", msClass = {AuthSourceLogService.class})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_AUTH:READ+UPDATE"})
    @Operation(summary = "系统设置-系统-系统参数-认证设置-更新状态")
    public AuthSource updateStatus(@Validated @RequestBody AuthSourceStatusRequest request) {
        return this.authSourceService.updateStatus(request.getId(), request.getEnable());
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    @PostMapping({"/ldap/test-connect"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_AUTH:READ+UPDATE"})
    @Operation(summary = "系统设置-系统-系统参数-认证设置-ldap测试连接")
    public void ldapTestConnect(@Validated @RequestBody LdapRequest request) throws MSException {
        this.ldapService.testConnect(request);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    @PostMapping({"/ldap/test-login"})
    @RequiresPermissions({"SYSTEM_PARAMETER_SETTING_AUTH:READ+UPDATE"})
    @Operation(summary = "系统设置-系统-系统参数-认证设置-ldap测试登录")
    public void testLogin(@RequestBody LdapLoginRequest request) throws MSException {
        this.ldapService.testLogin(request);
    }
}
