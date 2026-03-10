package io.metersphere.xpack.system.service;

import io.metersphere.sdk.constants.HttpMethodConstants;
import io.metersphere.sdk.util.JSON;
import io.metersphere.system.domain.UserExample;
import io.metersphere.system.dto.request.OrganizationEditRequest;
import io.metersphere.system.log.constants.OperationLogType;
import io.metersphere.system.log.dto.LogDTO;
import io.metersphere.system.mapper.UserMapper;
import io.metersphere.system.utils.SessionUtils;
import io.metersphere.xpack.project.service.ProjectVersionService;
import io.metersphere.xpack.system.dto.request.AuthSourceRequest;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/XpackSystemOrganizationLogService.class */
@Transactional(rollbackFor = {Exception.class})
@Service
public class XpackSystemOrganizationLogService {

    @Resource
    private UserMapper userMapper;
    private static final String PRE_URI = "/system/organization";

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public LogDTO addLog(OrganizationEditRequest a) {
        LogDTO logDTO = new LogDTO(AuthSourceRequest.ALLATORIxDEMO("*p*}<d"), ProjectVersionService.ALLATORIxDEMO("9H9E/\\"), (String) null, (String) null, OperationLogType.ADD.name(), AuthSourceRequest.ALLATORIxDEMO("z<}-`7n&z z-l4v6{>h7`#h-`6g"), a.getName());
        logDTO.setPath(ProjectVersionService.ALLATORIxDEMO("Eb\u0013b\u001et\u0007>\u0005c\rp\u0004x\u0010p\u001ex\u0005\u007fEp\u000eu"));
        logDTO.setMethod(HttpMethodConstants.POST.name());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(CollectionUtils.isEmpty(a.getUserIds()) ? List.of(SessionUtils.getUserId()) : a.getUserIds());
        logDTO.setModifiedValue(JSON.toJSONBytes(this.userMapper.selectByExample(userExample)));
        return logDTO;
    }
}
