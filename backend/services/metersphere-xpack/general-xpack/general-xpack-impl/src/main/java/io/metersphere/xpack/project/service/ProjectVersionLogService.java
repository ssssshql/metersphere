package io.metersphere.xpack.project.service;

import io.metersphere.project.domain.Project;
import io.metersphere.project.domain.ProjectVersion;
import io.metersphere.project.mapper.ProjectMapper;
import io.metersphere.project.mapper.ProjectVersionMapper;
import io.metersphere.sdk.constants.HttpMethodConstants;
import io.metersphere.sdk.util.JSON;
import io.metersphere.system.log.constants.OperationLogType;
import io.metersphere.system.log.dto.LogDTO;
import io.metersphere.xpack.project.dto.ProjectVersionDTO;
import io.metersphere.xpack.system.dto.request.AuthSourceRequest;
import io.metersphere.xpack.system.ldap.p000vo.LdapRequest;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* JADX INFO: compiled from: ca */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/project/service/ProjectVersionLogService.class */
@Transactional(rollbackFor = {Exception.class})
@Service
public class ProjectVersionLogService {
    private static final String PRE_URI = "/project/version";

    @Resource
    private ProjectVersionMapper projectVersionMapper;

    @Resource
    private ProjectMapper projectMapper;

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public LogDTO changeStatusLog(String a) {
        ProjectVersion projectVersionSelectByPrimaryKey = this.projectVersionMapper.selectByPrimaryKey(a);
        if (projectVersionSelectByPrimaryKey != null) {
            Project projectALLATORIxDEMO = ALLATORIxDEMO(projectVersionSelectByPrimaryKey.getProjectId());
            LogDTO logDTO = new LogDTO(projectALLATORIxDEMO.getId(), projectALLATORIxDEMO.getOrganizationId(), AuthSourceRequest.ALLATORIxDEMO("*p*}<d"), (String) null, OperationLogType.UPDATE.name(), LdapRequest.ALLATORIxDEMO("I\u001bV\u0003\\\nM\u0016T\bW\b^\fT\fW\u001dF\u0019\\\u001bT\u0000J\u001aP\u0006W\u0016O\fK\u001aP\u0006W"), projectVersionSelectByPrimaryKey.getName());
            logDTO.setPath(AuthSourceRequest.ALLATORIxDEMO("VY\u000bF\u0013L\u001a]V_\u001c[\n@\u0016GVZ\u000e@\rJ\u0011\u0006\n]\u0018]\fZ"));
            logDTO.setMethod(HttpMethodConstants.GET.name());
            logDTO.setOriginalValue(JSON.toJSONBytes(projectVersionSelectByPrimaryKey));
            projectVersionSelectByPrimaryKey.setStatus(StringUtils.equals(projectVersionSelectByPrimaryKey.getName(), "open") ? "closed" : "open");
            logDTO.setModifiedValue(JSON.toJSONBytes(projectVersionSelectByPrimaryKey));
            return logDTO;
        }
        return null;
    }

    public LogDTO addLog(ProjectVersionDTO a) {
        Project projectALLATORIxDEMO = ALLATORIxDEMO(a.getProjectId());
        LogDTO logDTO = new LogDTO(projectALLATORIxDEMO.getId(), projectALLATORIxDEMO.getOrganizationId(), LdapRequest.ALLATORIxDEMO("\u001a@\u001aM\fT"), (String) null, OperationLogType.ADD.name(), AuthSourceRequest.ALLATORIxDEMO("y+f3l:}&d8g8n<d<g-v)l+d0z*`6g&\u007f<{*`6g"), a.getName());
        logDTO.setPath(LdapRequest.ALLATORIxDEMO("fi;v#|*mfo,k:p&wfx-}"));
        logDTO.setMethod(HttpMethodConstants.POST.name());
        logDTO.setOriginalValue(JSON.toJSONBytes(a.getName()));
        return logDTO;
    }

    private /* synthetic */ Project ALLATORIxDEMO(String a) {
        return this.projectMapper.selectByPrimaryKey(a);
    }

    public LogDTO updateLog(ProjectVersionDTO a) {
        Project projectALLATORIxDEMO = ALLATORIxDEMO(a.getProjectId());
        LogDTO logDTO = new LogDTO(projectALLATORIxDEMO.getId(), projectALLATORIxDEMO.getOrganizationId(), AuthSourceRequest.ALLATORIxDEMO("*p*}<d"), (String) null, OperationLogType.UPDATE.name(), LdapRequest.ALLATORIxDEMO("I\u001bV\u0003\\\nM\u0016T\bW\b^\fT\fW\u001dF\u0019\\\u001bT\u0000J\u001aP\u0006W\u0016O\fK\u001aP\u0006W"), a.getName());
        logDTO.setPath(AuthSourceRequest.ALLATORIxDEMO("\u0006\t[\u0016C\u001cJ\r\u0006\u000fL\u000bZ\u0010F\u0017\u0006\fY\u001dH\rL"));
        logDTO.setMethod(HttpMethodConstants.POST.name());
        logDTO.setOriginalValue(JSON.toJSONBytes(this.projectVersionMapper.selectByPrimaryKey(a.getId()).getName()));
        logDTO.setModifiedValue(JSON.toJSONBytes(a.getName()));
        return logDTO;
    }

    public LogDTO deleteLog(String a) {
        ProjectVersion projectVersionSelectByPrimaryKey = this.projectVersionMapper.selectByPrimaryKey(a);
        if (projectVersionSelectByPrimaryKey != null) {
            Project projectALLATORIxDEMO = ALLATORIxDEMO(projectVersionSelectByPrimaryKey.getProjectId());
            LogDTO logDTO = new LogDTO(projectALLATORIxDEMO.getId(), projectALLATORIxDEMO.getOrganizationId(), LdapRequest.ALLATORIxDEMO("\u001a@\u001aM\fT"), (String) null, OperationLogType.DELETE.name(), AuthSourceRequest.ALLATORIxDEMO("y+f3l:}&d8g8n<d<g-v)l+d0z*`6g&\u007f<{*`6g"), projectVersionSelectByPrimaryKey.getName());
            logDTO.setPath(LdapRequest.ALLATORIxDEMO("69k&s,z=6?|;j v'6-|%|=|"));
            logDTO.setMethod(HttpMethodConstants.GET.name());
            logDTO.setOriginalValue(JSON.toJSONBytes(projectVersionSelectByPrimaryKey));
            return null;
        }
        return null;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public LogDTO changeLatestLog(String a) {
        ProjectVersion projectVersionSelectByPrimaryKey = this.projectVersionMapper.selectByPrimaryKey(a);
        if (projectVersionSelectByPrimaryKey != null) {
            Project projectALLATORIxDEMO = ALLATORIxDEMO(projectVersionSelectByPrimaryKey.getProjectId());
            LogDTO logDTO = new LogDTO(projectALLATORIxDEMO.getId(), projectALLATORIxDEMO.getOrganizationId(), LdapRequest.ALLATORIxDEMO("\u001a@\u001aM\fT"), (String) null, OperationLogType.UPDATE.name(), AuthSourceRequest.ALLATORIxDEMO("y+f3l:}&d8g8n<d<g-v)l+d0z*`6g&\u007f<{*`6g"), projectVersionSelectByPrimaryKey.getName());
            logDTO.setPath(LdapRequest.ALLATORIxDEMO("fi;v#|*mfo,k:p&wfj>p=z!6%x=|:m"));
            logDTO.setMethod(HttpMethodConstants.GET.name());
            logDTO.setOriginalValue(JSON.toJSONBytes(projectVersionSelectByPrimaryKey));
            projectVersionSelectByPrimaryKey.setLatest(Boolean.valueOf(!projectVersionSelectByPrimaryKey.getLatest().booleanValue()));
            logDTO.setModifiedValue(JSON.toJSONBytes(projectVersionSelectByPrimaryKey));
            return logDTO;
        }
        return null;
    }
}
