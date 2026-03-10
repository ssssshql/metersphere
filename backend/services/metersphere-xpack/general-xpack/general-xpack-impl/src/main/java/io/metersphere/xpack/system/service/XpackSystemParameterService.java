package io.metersphere.xpack.system.service;

import io.metersphere.sdk.constants.HttpMethodConstants;
import io.metersphere.sdk.util.JSON;
import io.metersphere.sdk.util.Translator;
import io.metersphere.system.domain.SystemParameter;
import io.metersphere.system.domain.SystemParameterExample;
import io.metersphere.system.log.constants.OperationLogType;
import io.metersphere.system.log.dto.LogDTO;
import io.metersphere.system.mapper.SystemParameterMapper;
import io.metersphere.xpack.system.ldap.p000vo.LdapRequest;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/XpackSystemParameterService.class */
@Transactional(rollbackFor = {Exception.class})
@Service
public class XpackSystemParameterService {

    @Resource
    SystemParameterMapper systemParameterMapper;

    private /* synthetic */ List<SystemParameter> ALLATORIxDEMO(SystemParameter a) {
        SystemParameterExample systemParameterExample = new SystemParameterExample();
        systemParameterExample.createCriteria().andParamKeyEqualTo(a.getParamKey());
        return this.systemParameterMapper.selectByExample(systemParameterExample);
    }

    public LogDTO updateLog(SystemParameter a) {
        List<SystemParameter> listALLATORIxDEMO = ALLATORIxDEMO(a);
        LogDTO logDTO = new LogDTO(LdapRequest.ALLATORIxDEMO("\u001a@\u001aM\fT"), LdapRequest.ALLATORIxDEMO("\u001a@\u001aM\fT"), LdapRequest.ALLATORIxDEMO("\u001a@\u001aM\fT"), (String) null, OperationLogType.UPDATE.name(), LdapRequest.ALLATORIxDEMO("\u001a\\\u001dM\u0000W\u000eF\u001a@\u001aM\fT\u0016I\bK\bT\fM\fK\u0016[\bJ\fF\nV\u0007_\u0000^"), Translator.get(LdapRequest.ALLATORIxDEMO(":`:m,tgz&w/p.7+x:|\nv'\u007f ~")));
        logDTO.setPath(LdapRequest.ALLATORIxDEMO("fa9x*rfj0j=|$69x;x$|=|;6:x?|fx9pdz&w*l;k,w=4*v'\u007f ~"));
        logDTO.setMethod(HttpMethodConstants.POST.name());
        logDTO.setOriginalValue(JSON.toJSONBytes(listALLATORIxDEMO));
        return logDTO;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void editApiConcurrentConfigInfo(SystemParameter a) {
        SystemParameterExample systemParameterExample = new SystemParameterExample();
        systemParameterExample.createCriteria().andParamKeyEqualTo(a.getParamKey());
        if (this.systemParameterMapper.countByExample(systemParameterExample) <= 0) {
            this.systemParameterMapper.insert(a);
        } else {
            this.systemParameterMapper.updateByPrimaryKey(a);
        }
    }
}
