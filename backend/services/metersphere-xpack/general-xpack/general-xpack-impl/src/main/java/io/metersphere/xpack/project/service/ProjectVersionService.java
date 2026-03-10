package io.metersphere.xpack.project.service;

import io.metersphere.project.domain.ProjectApplication;
import io.metersphere.project.domain.ProjectVersion;
import io.metersphere.project.domain.ProjectVersionExample;
import io.metersphere.project.mapper.ProjectVersionMapper;
import io.metersphere.project.request.ProjectApplicationRequest;
import io.metersphere.project.service.ProjectApplicationService;
import io.metersphere.sdk.constants.ProjectApplicationType;
import io.metersphere.sdk.exception.MSException;
import io.metersphere.sdk.util.BeanUtils;
import io.metersphere.sdk.util.Translator;
import io.metersphere.system.mapper.BaseUserMapper;
import io.metersphere.system.uid.IDGenerator;
import io.metersphere.xpack.project.dto.ProjectVersionDTO;
import io.metersphere.xpack.project.dto.ProjectVersionOptionDTO;
import io.metersphere.xpack.project.dto.request.ProjectVersionRequest;
import io.metersphere.xpack.project.invoker.ProjectVersionServiceInvoker;
import io.metersphere.xpack.project.mapper.ExtProjectVersionMapper;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* JADX INFO: compiled from: qa */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/project/service/ProjectVersionService.class */
@Transactional(rollbackFor = {Exception.class})
@Service
public class ProjectVersionService {

    @Resource
    private BaseUserMapper baseUserMapper;

    @Resource
    private ProjectApplicationService projectApplicationService;
    private static final String VERSION_ENABLE = "TRUE";
    private static final String VERSION_CLOSED = "closed";
    private static final String VERSION_OPEN = "open";
    private static final String VERSION_DISABLE = "FALSE";
    private final ProjectVersionServiceInvoker versionServiceInvoker;

    @Resource
    private ExtProjectVersionMapper extProjectVersionMapper;

    @Resource
    private ProjectVersionMapper projectVersionMapper;

    public static String ALLATORIxDEMO(String a) {
        int i = (2 << 3) ^ 1;
        int i2 = ((3 ^ 5) << 4) ^ (5 << 1);
        String str = a;
        int length = str.length();
        char[] cArr = new char[length];
        int i3 = length - 1;
        int i4 = i3;
        int i5 = i3;
        while (i5 >= 0) {
            int i6 = i4;
            int i7 = i4 - 1;
            cArr[i6] = (char) (str.charAt(i6) ^ i);
            if (i7 < 0) {
                break;
            }
            i4 = i7 - 1;
            cArr[i7] = (char) (str.charAt(i7) ^ i2);
            i5 = i4;
        }
        return new String(cArr);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public List<ProjectVersionOptionDTO> getOption(String a) {
        ArrayList arrayList = new ArrayList();
        ProjectVersionExample projectVersionExample = new ProjectVersionExample();
        projectVersionExample.createCriteria().andProjectIdEqualTo(a);
        List<ProjectVersion> listSelectByExample = this.projectVersionMapper.selectByExample(projectVersionExample);
        if (CollectionUtils.isEmpty(listSelectByExample)) {
            return new ArrayList();
        }
        listSelectByExample.forEach(a2 -> {
            arrayList.add(ProjectVersionOptionDTO.builder().m0id(a2.getId()).name(a2.getName()).latest(a2.getLatest()).enable(Boolean.valueOf(StringUtils.equals(VERSION_OPEN, a2.getStatus()))).build());
        });
        return arrayList;
    }

    @Autowired
    public ProjectVersionService(ProjectVersionServiceInvoker a) {
        this.versionServiceInvoker = a;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public boolean getVersionEnable(String a) {
        ProjectApplicationRequest projectApplicationRequest = new ProjectApplicationRequest();
        projectApplicationRequest.setProjectId(a);
        Map map = this.projectApplicationService.get(projectApplicationRequest, List.of(ProjectApplicationType.VERSION.VERSION_ENABLE.name()));
        if (map == null || !map.containsKey(ProjectApplicationType.VERSION.VERSION_ENABLE.name())) {
            return false;
        }
        return StringUtils.equals((String) map.get(ProjectApplicationType.VERSION.VERSION_ENABLE.name()), VERSION_ENABLE);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public void delete(String a) throws MSException {
        if (!this.versionServiceInvoker.invokeCheckExist(a)) {
            ProjectVersion projectVersionSelectByPrimaryKey = this.projectVersionMapper.selectByPrimaryKey(a);
            if (projectVersionSelectByPrimaryKey != null) {
                if (projectVersionSelectByPrimaryKey.getLatest().booleanValue()) {
                    throw new MSException(Translator.get(ALLATORIxDEMO("\u001ac\u0005{\u000fr\u001e?\u001ct\u0018b\u0003~\u0004?\u0006p\u001et\u0019eD\u007f\u0005N\u000et\u0006t\u001et")));
                }
                this.projectVersionMapper.deleteByPrimaryKey(a);
                return;
            }
            throw new MSException(Translator.get(ALLATORIxDEMO("a\u0018~\u0000t\teDg\u000fc\u0019x\u0005\u007fD\u007f\u0005e5t\u0012x\u0019e")));
        }
        throw new MSException(Translator.get(ALLATORIxDEMO("\u001ac\u0005{\u000fr\u001e?\u001ct\u0018b\u0003~\u0004?\u0018t\u0019~\u001fc\tt5t\u0012x\u0019e")));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void switchEnable(String a) throws MSException {
        ProjectVersionExample projectVersionExample = new ProjectVersionExample();
        projectVersionExample.createCriteria().andProjectIdEqualTo(a);
        boolean versionEnable = getVersionEnable(a);
        if (this.projectVersionMapper.countByExample(projectVersionExample) > 1 && versionEnable) {
            throw new MSException(Translator.get(ALLATORIxDEMO("\u001ac\u0005{\u000fr\u001e?\u001ct\u0018b\u0003~\u0004?\u0005\u007f\u0006h")));
        }
        ProjectApplication projectApplication = new ProjectApplication();
        projectApplication.setProjectId(a);
        projectApplication.setType(ProjectApplicationType.VERSION.VERSION_ENABLE.name());
        projectApplication.setTypeValue(versionEnable ? VERSION_DISABLE : VERSION_ENABLE);
        this.projectApplicationService.update(projectApplication, "");
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public List<ProjectVersionDTO> list(ProjectVersionRequest a) {
        List<ProjectVersionDTO> list = this.extProjectVersionMapper.list(a);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList();
        }
        Map map = (Map) this.baseUserMapper.selectUserOptionByIds(list.stream().map((v0) -> {
            return v0.getCreateUser();
        }).toList()).stream().collect(Collectors.toMap((v0) -> {
            return v0.getId();
        }, (v0) -> {
            return v0.getName();
        }));
        list.forEach(a2 -> {
            a2.setCreateUser((String) map.get(a2.getCreateUser()));
        });
        return list;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public ProjectVersion add(ProjectVersionDTO a) throws MSException {
        ProjectVersionExample projectVersionExample = new ProjectVersionExample();
        projectVersionExample.createCriteria().andProjectIdEqualTo(a.getProjectId()).andNameEqualTo(a.getName());
        if (this.projectVersionMapper.countByExample(projectVersionExample) > 0) {
            throw new MSException(Translator.get(ALLATORIxDEMO("a\u0018~\u0000t\teDg\u000fc\u0019x\u0005\u007fDt\u0012x\u0019e")));
        }
        a.setId(IDGenerator.nextStr());
        a.setCreateTime(Long.valueOf(System.currentTimeMillis()));
        ProjectVersion projectVersion = new ProjectVersion();
        BeanUtils.copyBean(projectVersion, a);
        projectVersion.setStatus(a.getStatus().booleanValue() ? VERSION_OPEN : VERSION_CLOSED);
        this.projectVersionMapper.insert(projectVersion);
        if (a.getLatest().booleanValue()) {
            switchLatest(a.getId(), a.getProjectId(), false);
        }
        return projectVersion;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void switchLatest(String a, String a2, boolean z) throws MSException {
        String projectId;
        ProjectVersion projectVersion = new ProjectVersion();
        projectVersion.setLatest(true);
        if (z) {
            ProjectVersion projectVersionSelectByPrimaryKey = this.projectVersionMapper.selectByPrimaryKey(a);
            if (projectVersionSelectByPrimaryKey == null) {
                throw new MSException(Translator.get(ALLATORIxDEMO("a\u0018~\u0000t\teDg\u000fc\u0019x\u0005\u007fD\u007f\u0005e5t\u0012x\u0019e")));
            }
            projectVersion.setId(a);
            this.projectVersionMapper.updateByPrimaryKeySelective(projectVersion);
            projectId = projectVersionSelectByPrimaryKey.getProjectId();
        } else {
            projectId = a2;
        }
        ProjectVersionExample projectVersionExample = new ProjectVersionExample();
        projectVersionExample.createCriteria().andProjectIdEqualTo(projectId).andIdNotEqualTo(a);
        projectVersion.setId((String) null);
        projectVersion.setLatest(false);
        this.projectVersionMapper.updateByExampleSelective(projectVersion, projectVersionExample);
        this.versionServiceInvoker.invokeSetLatest(a);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public void update(ProjectVersionDTO a) throws MSException {
        ProjectVersionExample projectVersionExample = new ProjectVersionExample();
        projectVersionExample.createCriteria().andIdNotEqualTo(a.getId()).andProjectIdEqualTo(a.getProjectId()).andNameEqualTo(a.getName());
        if (this.projectVersionMapper.countByExample(projectVersionExample) <= 0) {
            ProjectVersion projectVersion = new ProjectVersion();
            BeanUtils.copyBean(projectVersion, a);
            projectVersion.setLatest((Boolean) null);
            projectVersion.setStatus((String) null);
            projectVersion.setCreateTime((Long) null);
            projectVersion.setCreateUser((String) null);
            this.projectVersionMapper.updateByPrimaryKeySelective(projectVersion);
            return;
        }
        throw new MSException(Translator.get(ALLATORIxDEMO("a\u0018~\u0000t\teDg\u000fc\u0019x\u0005\u007fDt\u0012x\u0019e")));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void switchStatus(String a) throws MSException {
        ProjectVersionService projectVersionService;
        ProjectVersion projectVersionSelectByPrimaryKey = this.projectVersionMapper.selectByPrimaryKey(a);
        if (projectVersionSelectByPrimaryKey != null) {
            if (!StringUtils.equals(projectVersionSelectByPrimaryKey.getStatus(), VERSION_OPEN)) {
                projectVersionSelectByPrimaryKey.setStatus(VERSION_OPEN);
                projectVersionService = this;
            } else {
                projectVersionService = this;
                projectVersionSelectByPrimaryKey.setStatus(VERSION_CLOSED);
            }
            projectVersionService.projectVersionMapper.updateByPrimaryKeySelective(projectVersionSelectByPrimaryKey);
            return;
        }
        throw new MSException(Translator.get(ALLATORIxDEMO("a\u0018~\u0000t\teDg\u000fc\u0019x\u0005\u007fD\u007f\u0005e5t\u0012x\u0019e")));
    }
}
