package io.metersphere.xpack.system.service;

import io.metersphere.project.domain.ProjectExample;
import io.metersphere.project.mapper.ProjectMapper;
import io.metersphere.sdk.constants.InternalUserRole;
import io.metersphere.sdk.constants.UserSource;
import io.metersphere.sdk.util.BeanUtils;
import io.metersphere.sdk.util.CodingUtils;
import io.metersphere.sdk.util.JSON;
import io.metersphere.sdk.util.RsaUtils;
import io.metersphere.system.domain.OrganizationExample;
import io.metersphere.system.domain.User;
import io.metersphere.system.domain.UserExample;
import io.metersphere.system.domain.UserInvite;
import io.metersphere.system.domain.UserRoleRelation;
import io.metersphere.system.dto.request.UserRegisterRequest;
import io.metersphere.system.dto.sdk.LicenseDTO;
import io.metersphere.system.dto.user.UserCreateInfo;
import io.metersphere.system.dto.user.request.UserBatchCreateRequest;
import io.metersphere.system.mapper.OrganizationMapper;
import io.metersphere.system.mapper.UserMapper;
import io.metersphere.system.mapper.UserRoleMapper;
import io.metersphere.system.mapper.UserRoleRelationMapper;
import io.metersphere.system.service.UserXpackService;
import io.metersphere.system.uid.IDGenerator;
import io.metersphere.system.utils.SessionUtils;
import io.metersphere.xpack.license.service.LicenseServiceImpl;
import io.metersphere.xpack.project.service.ProjectVersionService;
import io.metersphere.xpack.system.ldap.p000vo.LdapRequest;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/UserXpackServiceImpl.class */
@Transactional(rollbackFor = {Exception.class})
@Service
public class UserXpackServiceImpl implements UserXpackService {

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @Resource
    private LicenseServiceImpl licenseService;

    @Resource
    private OrganizationMapper organizationMapper;

    @Resource
    private UserRoleRelationMapper userRoleRelationMapper;

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    private /* synthetic */ UserRoleRelation ALLATORIxDEMO(String a, String a2) {
        UserRoleRelation userRoleRelation = new UserRoleRelation();
        userRoleRelation.setId(IDGenerator.nextStr());
        userRoleRelation.setUserId(a);
        userRoleRelation.setRoleId(InternalUserRole.MEMBER.getValue());
        userRoleRelation.setSourceId(LdapRequest.ALLATORIxDEMO(":`:m,t"));
        userRoleRelation.setOrganizationId(ProjectVersionService.ALLATORIxDEMO("\u0019h\u0019e\u000f|"));
        userRoleRelation.setCreateTime(Long.valueOf(System.currentTimeMillis()));
        userRoleRelation.setCreateUser(a2);
        return userRoleRelation;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public int GWHowToChangeUser(List<String> list, boolean z, String a) {
        int iCheckUserEnableValidate = 0;
        if (z) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdIn(list).andEnableEqualTo(false);
            iCheckUserEnableValidate = checkUserEnableValidate(this.userMapper.selectByExample(userExample).size());
        }
        if (iCheckUserEnableValidate == 0) {
            UserExample userExample2 = new UserExample();
            userExample2.createCriteria().andIdIn(list).andEnableEqualTo(Boolean.valueOf(!z));
            this.userMapper.selectByExample(userExample2).forEach(a2 -> {
                UserXpackServiceImpl userXpackServiceImpl;
                User user = new User();
                user.setId(a2.getId());
                user.setEnable(Boolean.valueOf(z));
                user.setUpdateUser(a);
                user.setUpdateTime(Long.valueOf(System.currentTimeMillis()));
                if (!BooleanUtils.isTrue(Boolean.valueOf(z))) {
                    user.setCftToken(IDEncryptUtils.aesEncrypt(user.getUpdateTime()));
                    userXpackServiceImpl = this;
                } else {
                    userXpackServiceImpl = this;
                    user.setCftToken(IDEncryptUtils.aesEncrypt(user.getId()));
                }
                userXpackServiceImpl.userMapper.updateByPrimaryKeySelective(user);
                if (BooleanUtils.isFalse(Boolean.valueOf(z))) {
                    SessionUtils.kickOutUser(a2.getId());
                }
            });
            return 0;
        }
        return iCheckUserEnableValidate;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public int GWHowToAddUser(@Valid UserBatchCreateRequest userCreateDTO, String source, String a) {
        int iCheckValidateLicence = checkValidateLicence(userCreateDTO.getUserInfoList().size());
        if (iCheckValidateLicence != 0) {
            return iCheckValidateLicence;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        Iterator it = userCreateDTO.getUserInfoList().iterator();
        while (it.hasNext()) {
            UserCreateInfo userCreateInfo = (UserCreateInfo) it.next();
            userCreateInfo.setId(IDGenerator.nextStr());
            User user = new User();
            BeanUtils.copyBean(user, userCreateInfo);
            user.setCreateUser(a);
            user.setCftToken(IDEncryptUtils.aesEncrypt(user.getId()));
            user.setCreateTime(Long.valueOf(jCurrentTimeMillis));
            user.setUpdateUser(a);
            user.setUpdateTime(Long.valueOf(jCurrentTimeMillis));
            user.setPassword(CodingUtils.md5(user.getEmail()));
            user.setSource(source);
            user.setDeleted(false);
            this.userMapper.insertSelective(user);
            arrayList.add(user);
        }
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        ArrayList arrayList2 = new ArrayList();
        for (String source2 : userCreateDTO.getUserRoleIdList()) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                User user2 = (User) it2.next();
                UserRoleRelation userRoleRelation = new UserRoleRelation();
                userRoleRelation.setId(IDGenerator.nextStr());
                userRoleRelation.setUserId(user2.getId());
                userRoleRelation.setRoleId(source2);
                userRoleRelation.setSourceId(ProjectVersionService.ALLATORIxDEMO("\u0019h\u0019e\u000f|"));
                user2.setCreateTime(Long.valueOf(jCurrentTimeMillis2));
                userRoleRelation.setCreateUser(a);
                userRoleRelation.setOrganizationId(LdapRequest.ALLATORIxDEMO(":`:m,t"));
                userRoleRelation.setCreateTime(Long.valueOf(jCurrentTimeMillis));
                arrayList2.add(userRoleRelation);
            }
        }
        SqlSession sqlSessionOpenSession = this.sqlSessionFactory.openSession(ExecutorType.BATCH);
        UserRoleRelationMapper userRoleRelationMapper = sqlSessionOpenSession.getMapper(UserRoleRelationMapper.class);
        int i = 0;
        Iterator it3 = arrayList2.iterator();
        while (it3.hasNext()) {
            i++;
            userRoleRelationMapper.insert((UserRoleRelation) it3.next());
            if (i % 50 == 0) {
                sqlSessionOpenSession.flushStatements();
            }
        }
        sqlSessionOpenSession.flushStatements();
        SqlSessionUtils.closeSqlSession(sqlSessionOpenSession, this.sqlSessionFactory);
        return 0;
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public int GWHowToAddUser(@Valid UserRegisterRequest request, UserInvite a) throws Exception {
        List<UserRoleRelation> arrayList;
        int iCheckValidateLicence = checkValidateLicence(1);
        if (iCheckValidateLicence != 0) {
            return iCheckValidateLicence;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        User user = new User();
        user.setId(IDGenerator.nextStr());
        user.setEmail(a.getEmail());
        user.setPassword(CodingUtils.md5(RsaUtils.privateDecrypt(request.getPassword(), RsaUtils.getRsaKey().getPrivateKey())));
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setCreateUser(a.getInviteUser());
        user.setUpdateUser(a.getInviteUser());
        user.setCreateTime(jCurrentTimeMillis);
        user.setUpdateTime(jCurrentTimeMillis);
        user.setSource(UserSource.LOCAL.name());
        user.setDeleted(false);
        user.setCftToken(IDEncryptUtils.aesEncrypt(user.getId()));
        this.userMapper.insertSelective(user);
        String organizationId = a.getOrganizationId();
        String projectId = a.getProjectId();
        if (organizationId == null) {
            organizationId = ProjectVersionService.ALLATORIxDEMO("\u0019h\u0019e\u000f|");
        }
        if (projectId == null) {
            projectId = LdapRequest.ALLATORIxDEMO(":`:m,t");
        }
        List<UserRoleRelation> arrayList2 = new ArrayList<>();
        List<String> listALLATORIxDEMO = ALLATORIxDEMO(JSON.parseArray(a.getRoles(), String.class));
        if (!CollectionUtils.isEmpty(listALLATORIxDEMO)) {
            if (StringUtils.equalsIgnoreCase(organizationId, ProjectVersionService.ALLATORIxDEMO("\u0019h\u0019e\u000f|")) && StringUtils.equalsIgnoreCase(projectId, LdapRequest.ALLATORIxDEMO(":`:m,t"))) {
                Iterator<String> it = listALLATORIxDEMO.iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    UserRoleRelation userRoleRelation = new UserRoleRelation();
                    userRoleRelation.setId(IDGenerator.nextStr());
                    userRoleRelation.setUserId(user.getId());
                    userRoleRelation.setRoleId(next);
                    userRoleRelation.setSourceId(ProjectVersionService.ALLATORIxDEMO("\u0019h\u0019e\u000f|"));
                    userRoleRelation.setOrganizationId(LdapRequest.ALLATORIxDEMO(":`:m,t"));
                    userRoleRelation.setCreateTime(Long.valueOf(jCurrentTimeMillis));
                    userRoleRelation.setCreateUser(user.getCreateUser());
                    arrayList2.add(userRoleRelation);
                }
            } else if (StringUtils.equalsIgnoreCase(projectId, ProjectVersionService.ALLATORIxDEMO("\u0019h\u0019e\u000f|"))) {
                arrayList2.addFirst(ALLATORIxDEMO(user.getId(), user.getCreateUser()));
                OrganizationExample organizationExample = new OrganizationExample();
                organizationExample.createCriteria().andIdEqualTo(organizationId).andDeletedEqualTo(false);
                if (this.organizationMapper.countByExample(organizationExample) > 0) {
                    Iterator<String> it2 = listALLATORIxDEMO.iterator();
                    while (it2.hasNext()) {
                        String next2 = it2.next();
                        UserRoleRelation userRoleRelation2 = new UserRoleRelation();
                        userRoleRelation2.setId(IDGenerator.nextStr());
                        userRoleRelation2.setUserId(user.getId());
                        userRoleRelation2.setRoleId(next2);
                        userRoleRelation2.setSourceId(organizationId);
                        userRoleRelation2.setOrganizationId(organizationId);
                        userRoleRelation2.setCreateTime(jCurrentTimeMillis);
                        userRoleRelation2.setCreateUser(user.getCreateUser());
                        arrayList2.add(userRoleRelation2);
                    }
                }
            } else {
                arrayList2.addFirst(ALLATORIxDEMO(user.getId(), user.getCreateUser()));
                OrganizationExample organizationExample2 = new OrganizationExample();
                organizationExample2.createCriteria().andIdEqualTo(organizationId).andDeletedEqualTo(false);
                if (this.organizationMapper.countByExample(organizationExample2) > 0) {
                    UserRoleRelation userRoleRelation3 = new UserRoleRelation();
                    userRoleRelation3.setId(IDGenerator.nextStr());
                    userRoleRelation3.setUserId(user.getId());
                    userRoleRelation3.setRoleId(InternalUserRole.ORG_MEMBER.getValue());
                    userRoleRelation3.setSourceId(organizationId);
                    userRoleRelation3.setCreateTime(System.currentTimeMillis());
                    userRoleRelation3.setCreateUser(user.getCreateUser());
                    userRoleRelation3.setOrganizationId(organizationId);
                    arrayList2.add(userRoleRelation3);
                    ProjectExample projectExample = new ProjectExample();
                    projectExample.createCriteria().andIdEqualTo(projectId).andDeletedEqualTo(false);
                    if (this.projectMapper.countByExample(projectExample) > 0) {
                        Iterator<String> it3 = listALLATORIxDEMO.iterator();
                        while (it3.hasNext()) {
                            String next3 = it3.next();
                            UserRoleRelation userRoleRelation4 = new UserRoleRelation();
                            userRoleRelation4.setId(IDGenerator.nextStr());
                            userRoleRelation4.setUserId(user.getId());
                            userRoleRelation4.setRoleId(next3);
                            userRoleRelation4.setSourceId(projectId);
                            userRoleRelation4.setOrganizationId(organizationId);
                            userRoleRelation4.setCreateTime(jCurrentTimeMillis);
                            userRoleRelation4.setCreateUser(user.getCreateUser());
                            arrayList2.add(userRoleRelation4);
                        }
                    }
                }
            }
            arrayList = arrayList2;
        } else {
            UserRoleRelation userRoleRelation5 = new UserRoleRelation();
            arrayList = arrayList2;
            userRoleRelation5.setId(IDGenerator.nextStr());
            userRoleRelation5.setUserId(user.getId());
            userRoleRelation5.setRoleId(InternalUserRole.MEMBER.getValue());
            userRoleRelation5.setSourceId(ProjectVersionService.ALLATORIxDEMO("\u0019h\u0019e\u000f|"));
            userRoleRelation5.setOrganizationId(LdapRequest.ALLATORIxDEMO(":`:m,t"));
            userRoleRelation5.setCreateTime(Long.valueOf(System.currentTimeMillis()));
            userRoleRelation5.setCreateUser(user.getCreateUser());
            arrayList.add(userRoleRelation5);
        }
        if (!CollectionUtils.isEmpty(arrayList)) {
            this.userRoleRelationMapper.batchInsert(arrayList2);
            return 0;
        }
        return 0;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private /* synthetic */ List<String> ALLATORIxDEMO(List<String> list) {
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            if (this.userRoleMapper.selectByPrimaryKey(str) != null) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public int checkUserEnableValidate(int a) {
        int count = 30;
        LicenseDTO licenseDTOValidate = this.licenseService.validate();
        boolean z = false;
        if (licenseDTOValidate != null && licenseDTOValidate.getLicense() != null && !StringUtils.equalsIgnoreCase(licenseDTOValidate.getStatus(), LdapRequest.ALLATORIxDEMO("\\;k&k"))) {
            z = true;
            if (licenseDTOValidate.getLicense().getCount() > 30) {
                count = licenseDTOValidate.getLicense().getCount();
            }
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria().andDeletedEqualTo(false).andEnableEqualTo(true);
        if (this.userMapper.countByExample(userExample) + ((long) a) > count) {
            if (z) {
                return count;
            }
            return -1;
        }
        return 0;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public int GWHowToDeleteUser(List<String> list, String a) {
        SqlSession sqlSessionOpenSession = this.sqlSessionFactory.openSession(ExecutorType.BATCH);
        UserMapper userMapper = (UserMapper) sqlSessionOpenSession.getMapper(UserMapper.class);
        int i = 0;
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (String str : list) {
            User user = new User();
            user.setId(str);
            user.setDeleted(true);
            user.setUpdateTime(jCurrentTimeMillis);
            user.setUpdateUser(a);
            i++;
            user.setEmail(str);
            user.setCftToken(str);
            userMapper.updateByPrimaryKeySelective(user);
            if (i % 50 == 0) {
                sqlSessionOpenSession.flushStatements();
            }
        }
        sqlSessionOpenSession.flushStatements();
        SqlSessionUtils.closeSqlSession(sqlSessionOpenSession, this.sqlSessionFactory);
        return i;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public int checkValidateLicence(int a) {
        return 0;
    }
}
