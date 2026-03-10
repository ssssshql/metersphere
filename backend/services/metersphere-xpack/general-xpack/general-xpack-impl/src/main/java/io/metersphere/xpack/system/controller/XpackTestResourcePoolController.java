package io.metersphere.xpack.system.controller;

import io.metersphere.sdk.exception.MSException;
import io.metersphere.sdk.util.BeanUtils;
import io.metersphere.system.domain.TestResourcePool;
import io.metersphere.system.dto.pool.TestResourcePoolDTO;
import io.metersphere.system.dto.pool.TestResourcePoolRequest;
import io.metersphere.system.log.annotation.Log;
import io.metersphere.system.log.constants.OperationLogType;
import io.metersphere.system.utils.SessionUtils;
import io.metersphere.validation.groups.Created;
import io.metersphere.xpack.system.service.XpackTestResourcePoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/controller/XpackTestResourcePoolController.class */
@RequestMapping({"/test/resource/pool"})
@Tag(name = "系统设置-系统-资源池")
@RestController
public class XpackTestResourcePoolController {

    @Resource
    private XpackTestResourcePoolService xpackTestResourcePoolService;

    @PostMapping({"/add"})
    @Log(type = OperationLogType.ADD, expression = "#msClass.addLog(#request)", msClass = {XpackTestResourcePoolService.class})
    @RequiresPermissions({"SYSTEM_TEST_RESOURCE_POOL:READ+ADD"})
    @Operation(summary = "系统设置-系统-资源池-添加资源池")
    public TestResourcePool addTestResourcePool(@Validated({Created.class}) @RequestBody TestResourcePoolRequest request) {
        String userId = SessionUtils.getUserId();
        TestResourcePoolDTO testResourcePool = new TestResourcePoolDTO();
        BeanUtils.copyBean(testResourcePool, request);
        testResourcePool.setCreateUser(userId);
        testResourcePool.setCreateTime(Long.valueOf(System.currentTimeMillis()));
        return this.xpackTestResourcePoolService.addTestResourcePool(testResourcePool);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    @Log(type = OperationLogType.DELETE, expression = "#msClass.deleteLog(#testResourcePoolId)", msClass = {XpackTestResourcePoolService.class})
    @RequiresPermissions({"SYSTEM_TEST_RESOURCE_POOL:READ+DELETE"})
    @Operation(summary = "系统设置-系统-资源池-删除资源池")
    @GetMapping({"/delete/{poolId}"})
    public void deleteTestResourcePool(@PathVariable("poolId") String testResourcePoolId) throws MSException {
        this.xpackTestResourcePoolService.deleteTestResourcePool(testResourcePoolId);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    @PostMapping({"/set/enable/{poolId}"})
    @Log(type = OperationLogType.UPDATE, expression = "#msClass.updateLog(#testResourcePoolId)", msClass = {XpackTestResourcePoolService.class})
    @RequiresPermissions({"SYSTEM_TEST_RESOURCE_POOL:READ+UPDATE"})
    @Operation(summary = "系统设置-系统-资源池-资源池禁用")
    public void unableTestResourcePool(@PathVariable("poolId") String testResourcePoolId) throws MSException {
        this.xpackTestResourcePoolService.unableTestResourcePool(testResourcePoolId);
    }
}
