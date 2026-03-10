package io.metersphere.xpack.system.service;

import io.metersphere.project.domain.Project;
import io.metersphere.project.domain.ProjectExample;
import io.metersphere.project.mapper.ProjectMapper;
import io.metersphere.sdk.constants.InternalUserRole;
import io.metersphere.sdk.constants.TemplateScene;
import io.metersphere.sdk.constants.TemplateScopeType;
import io.metersphere.sdk.exception.MSException;
import io.metersphere.sdk.util.BeanUtils;
import io.metersphere.sdk.util.Translator;
import io.metersphere.system.domain.*;
import io.metersphere.system.dto.OrganizationDTO;
import io.metersphere.system.dto.OrganizationSwitchRequest;
import io.metersphere.system.dto.sdk.OptionDTO;
import io.metersphere.system.dto.sdk.SessionUser;
import io.metersphere.system.dto.user.UserDTO;
import io.metersphere.system.mapper.BaseUserMapper;
import io.metersphere.system.mapper.ExtOrganizationMapper;
import io.metersphere.system.mapper.OrganizationMapper;
import io.metersphere.system.mapper.UserMapper;
import io.metersphere.system.mapper.UserRoleRelationMapper;
import io.metersphere.system.service.BaseStatusFlowSettingService;
import io.metersphere.system.service.BaseTemplateService;
import io.metersphere.system.service.UserLoginService;
import io.metersphere.system.uid.IDGenerator;
import io.metersphere.system.utils.SessionUtils;
import io.metersphere.xpack.project.service.ProjectVersionService;
import io.metersphere.xpack.system.service.lark.LarkLoginService;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/XpackSystemOrganizationService.class */
@Transactional(rollbackFor = {Exception.class})
@Service
public class XpackSystemOrganizationService {

    @Resource
    ExtOrganizationMapper extOrganizationMapper;

    @Resource
    BaseUserMapper baseUserMapper;

    @Resource
    UserRoleRelationMapper userRoleRelationMapper;

    @Resource
    BaseStatusFlowSettingService baseStatusFlowSettingService;

    @Resource
    ProjectMapper projectMapper;

    @Resource
    BaseTemplateService baseTemplateService;

    @Resource
    OrganizationMapper organizationMapper;

    @Resource
    UserLoginService userLoginService;

    @Resource
    UserMapper userMapper;

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void switchOrg(OrganizationSwitchRequest a, String a2) throws MSException {
        User user;
        if (StringUtils.equals(a2, a.getUserId())) {
            if (this.organizationMapper.selectByPrimaryKey(a.getOrganizationId()) == null) {
                throw new MSException(Translator.get(ProjectVersionService.ALLATORIxDEMO("\u0005c\rp\u0004x\u0010p\u001ex\u0005\u007f5\u007f\u0005e5t\u0012x\u0019e")));
            }
            UserDTO userDTO = this.userLoginService.getUserDTO(a2);
            User user2 = new User();
            boolean zIsSuperUser = this.baseUserMapper.isSuperUser(a2);
            userDTO.setLastOrganizationId(a.getOrganizationId());
            userDTO.setLastProjectId("");
            List<Project> listALLATORIxDEMO = ALLATORIxDEMO(a2, a.getOrganizationId());
            if (CollectionUtils.isNotEmpty(listALLATORIxDEMO)) {
                userDTO.setLastProjectId(listALLATORIxDEMO.get(0).getId());
                user = user2;
            } else {
                if (zIsSuperUser) {
                    ProjectExample projectExample = new ProjectExample();
                    projectExample.createCriteria().andOrganizationIdEqualTo(a.getOrganizationId());
                    List listSelectByExample = this.projectMapper.selectByExample(projectExample);
                    if (CollectionUtils.isNotEmpty(listSelectByExample)) {
                        userDTO.setLastProjectId(((Project) listSelectByExample.get(0)).getId());
                    }
                } else {
                    userDTO.setLastProjectId("");
                }
                user = user2;
            }
            BeanUtils.copyBean(user, userDTO);
            SessionUtils.putUser(SessionUser.fromUser(userDTO, SessionUtils.getSessionId()));
            this.userMapper.updateByPrimaryKeySelective(user2);
            return;
        }
        throw new MSException(Translator.get(LarkLoginService.ALLATORIxDEMO("\u0015u\u000fE\u001ao\u000fr\u0014h\u0012`\u001e~")));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private /* synthetic */ void ALLATORIxDEMO(String a) {
        TemplateScene[] templateSceneArrValues = TemplateScene.values();
        int length = templateSceneArrValues.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            switch (C00001.$SwitchMap$io$metersphere$sdk$constants$TemplateScene[templateSceneArrValues[i2].ordinal()]) {
                case 1:
                    this.baseTemplateService.initFunctionalDefaultTemplate(a, TemplateScopeType.ORGANIZATION);
                    break;
                case 2:
                    this.baseTemplateService.initBugDefaultTemplate(a, TemplateScopeType.ORGANIZATION);
                    this.baseStatusFlowSettingService.initBugDefaultStatusFlowSetting(a, TemplateScopeType.ORGANIZATION);
                    break;
                case 3:
                    this.baseTemplateService.initApiDefaultTemplate(a, TemplateScopeType.ORGANIZATION);
                    break;
                case 4:
                    this.baseTemplateService.initUiDefaultTemplate(a, TemplateScopeType.ORGANIZATION);
                    break;
                case 5:
                    this.baseTemplateService.initTestPlanDefaultTemplate(a, TemplateScopeType.ORGANIZATION);
                    break;
            }
            i2++;
            i = i2;
        }
    }

    private /* synthetic */ List<Project> ALLATORIxDEMO(String a, String a2) {
        ProjectExample projectExample = new ProjectExample();
        projectExample.createCriteria().andOrganizationIdEqualTo(a2);
        List<Project> listSelectByExample = this.projectMapper.selectByExample(projectExample);
        UserRoleRelationExample userRoleRelationExample = new UserRoleRelationExample();
        userRoleRelationExample.createCriteria().andUserIdEqualTo(a).andOrganizationIdEqualTo(a2);
        List<UserRoleRelation> listSelectByExample2 = this.userRoleRelationMapper.selectByExample(userRoleRelationExample);
        ArrayList arrayList = new ArrayList();
        listSelectByExample2.forEach(a3 -> {
            listSelectByExample.forEach(a4 -> {
                if (StringUtils.equals(a4.getId(), a3.getSourceId())) {
                    arrayList.add(a3);
                }
            });
        });
        return arrayList;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public List<OptionDTO> getSwitchOption(String a) {
        List<Organization> listSelectByExample;
        List arrayList = new ArrayList();
        if (!this.baseUserMapper.isSuperUser(a)) {
            List relatedOrganizationIds = this.extOrganizationMapper.getRelatedOrganizationIds(a);
            if (CollectionUtils.isNotEmpty(relatedOrganizationIds)) {
                OrganizationExample organizationExample = new OrganizationExample();
                organizationExample.createCriteria().andIdIn(relatedOrganizationIds);
                arrayList = this.organizationMapper.selectByExample(organizationExample);
            }
            listSelectByExample = arrayList;
        } else {
            listSelectByExample = this.organizationMapper.selectByExample(new OrganizationExample());
        }
        return listSelectByExample.stream().map(a2 -> {
            OptionDTO optionDTO = new OptionDTO();
            optionDTO.setId(a2.getId());
            optionDTO.setName(a2.getName());
            return optionDTO;
        }).toList();
    }

    /* JADX INFO: renamed from: io.metersphere.xpack.system.service.XpackSystemOrganizationService$1 */
    /* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/XpackSystemOrganizationService$1.class */
    static /* synthetic */ class C00001 {
        static final /* synthetic */ int[] $SwitchMap$io$metersphere$sdk$constants$TemplateScene = new int[TemplateScene.values().length];

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        static {
            try {
                $SwitchMap$io$metersphere$sdk$constants$TemplateScene[TemplateScene.FUNCTIONAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$metersphere$sdk$constants$TemplateScene[TemplateScene.BUG.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$io$metersphere$sdk$constants$TemplateScene[TemplateScene.API.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$io$metersphere$sdk$constants$TemplateScene[TemplateScene.UI.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$io$metersphere$sdk$constants$TemplateScene[TemplateScene.TEST_PLAN.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    private /* synthetic */ void ALLATORIxDEMO(OrganizationDTO a) throws MSException {
        OrganizationExample organizationExample = new OrganizationExample();
        OrganizationExample.Criteria criteriaCreateCriteria = organizationExample.createCriteria();
        criteriaCreateCriteria.andNameEqualTo(a.getName());
        if (StringUtils.isNotBlank(a.getId())) {
            criteriaCreateCriteria.andIdNotEqualTo(a.getId());
        }
        if (this.organizationMapper.countByExample(organizationExample) <= 0) {
        } else {
            throw new MSException(Translator.get(LarkLoginService.ALLATORIxDEMO("\u0014h\u001c{\u0015s\u0001{\u000fs\u0014t$t\u001aw\u001eE\u001av\t\u007f\u001a~\u0002E\u001eb\u0012i\u000fi")));
        }
    }

    public void createAdmin(String a, String a2, String a3) {
        UserRoleRelation userRoleRelation = new UserRoleRelation();
        userRoleRelation.setId(IDGenerator.nextStr());
        userRoleRelation.setUserId(a);
        userRoleRelation.setRoleId(InternalUserRole.ORG_ADMIN.getValue());
        userRoleRelation.setSourceId(a2);
        userRoleRelation.setCreateTime(Long.valueOf(System.currentTimeMillis()));
        userRoleRelation.setCreateUser(a3);
        userRoleRelation.setOrganizationId(a2);
        this.userRoleRelationMapper.insertSelective(userRoleRelation);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public OrganizationDTO add(OrganizationDTO a) throws MSException {
        ALLATORIxDEMO(a);
        a.setId(IDGenerator.nextStr());
        a.setCreateTime(Long.valueOf(System.currentTimeMillis()));
        a.setUpdateTime(Long.valueOf(System.currentTimeMillis()));
        this.organizationMapper.insertSelective(a);
        a.getUserIds().forEach(a2 -> {
            createAdmin(a2, a.getId(), a.getCreateUser());
        });
        ALLATORIxDEMO(a.getId());
        return a;
    }
}
