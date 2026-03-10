package io.metersphere.xpack.system.service;

import io.metersphere.sdk.util.JSON;
import io.metersphere.system.domain.AuthSource;
import io.metersphere.system.log.constants.OperationLogType;
import io.metersphere.system.log.dto.LogDTO;
import io.metersphere.system.mapper.AuthSourceMapper;
import io.metersphere.xpack.system.dto.request.AuthSourceRequest;
import io.metersphere.xpack.system.ldap.p000vo.LdapRequest;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/AuthSourceLogService.class */
@Transactional(rollbackFor = {Exception.class})
@Service
public class AuthSourceLogService {

    @Resource
    private AuthSourceMapper authSourceMapper;

    public LogDTO updateLog(AuthSourceRequest a) {
        AuthSource authSourceSelectByPrimaryKey = this.authSourceMapper.selectByPrimaryKey(a.getId());
        LogDTO logDTO = null;
        if (authSourceSelectByPrimaryKey != null) {
            LogDTO logDTO2 = new LogDTO(AuthSourceRequest.ALLATORIxDEMO("*p*}<d"), LdapRequest.ALLATORIxDEMO("\u001a@\u001aM\fT"), a.getId(), (String) null, OperationLogType.UPDATE.name(), AuthSourceRequest.ALLATORIxDEMO("*l-}0g>v*p*}<d&y8{8d<}<{&h,}1v:f7o0n"), a.getName());
            logDTO = logDTO2;
            logDTO2.setOriginalValue(JSON.toJSONBytes(authSourceSelectByPrimaryKey));
        }
        return logDTO;
    }

    public LogDTO addLog(AuthSourceRequest a) {
        LogDTO logDTO = new LogDTO(LdapRequest.ALLATORIxDEMO("\u001a@\u001aM\fT"), AuthSourceRequest.ALLATORIxDEMO("*p*}<d"), a.getId(), (String) null, OperationLogType.ADD.name(), LdapRequest.ALLATORIxDEMO("\u001a\\\u001dM\u0000W\u000eF\u001a@\u001aM\fT\u0016I\bK\bT\fM\fK\u0016X\u001cM\u0001F\nV\u0007_\u0000^"), a.getName());
        logDTO.setModifiedValue(JSON.toJSONBytes(a));
        return logDTO;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public LogDTO deleteLog(String a) {
        AuthSource authSourceSelectByPrimaryKey = this.authSourceMapper.selectByPrimaryKey(a);
        if (authSourceSelectByPrimaryKey == null) {
            return null;
        }
        LogDTO logDTO = new LogDTO(AuthSourceRequest.ALLATORIxDEMO("*p*}<d"), LdapRequest.ALLATORIxDEMO("\u001a@\u001aM\fT"), authSourceSelectByPrimaryKey.getId(), (String) null, OperationLogType.DELETE.name(), AuthSourceRequest.ALLATORIxDEMO("*l-}0g>v*p*}<d&y8{8d<}<{&h,}1v:f7o0n"), authSourceSelectByPrimaryKey.getName());
        logDTO.setOriginalValue(JSON.toJSONBytes(authSourceSelectByPrimaryKey));
        return logDTO;
    }

    public LogDTO updateLog(String a) {
        AuthSource authSourceSelectByPrimaryKey = this.authSourceMapper.selectByPrimaryKey(a);
        LogDTO logDTO = null;
        if (authSourceSelectByPrimaryKey != null) {
            LogDTO logDTO2 = new LogDTO(LdapRequest.ALLATORIxDEMO("\u001a@\u001aM\fT"), AuthSourceRequest.ALLATORIxDEMO("*p*}<d"), a, (String) null, OperationLogType.UPDATE.name(), LdapRequest.ALLATORIxDEMO("\u001a\\\u001dM\u0000W\u000eF\u001a@\u001aM\fT\u0016I\bK\bT\fM\fK\u0016X\u001cM\u0001F\nV\u0007_\u0000^"), authSourceSelectByPrimaryKey.getName());
            logDTO = logDTO2;
            logDTO2.setOriginalValue(JSON.toJSONBytes(authSourceSelectByPrimaryKey));
        }
        return logDTO;
    }
}
