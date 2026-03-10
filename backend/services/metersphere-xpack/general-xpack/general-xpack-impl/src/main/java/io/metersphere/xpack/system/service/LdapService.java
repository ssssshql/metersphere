package io.metersphere.xpack.system.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.metersphere.project.domain.Project;
import io.metersphere.project.domain.ProjectExample;
import io.metersphere.project.mapper.ProjectMapper;
import io.metersphere.sdk.constants.UserSource;
import io.metersphere.sdk.exception.MSException;
import io.metersphere.sdk.util.BeanUtils;
import io.metersphere.sdk.util.EncryptUtils;
import io.metersphere.sdk.util.JSON;
import io.metersphere.sdk.util.LogUtils;
import io.metersphere.sdk.util.Translator;
import io.metersphere.system.controller.handler.ResultHolder;
import io.metersphere.system.domain.*;
import io.metersphere.system.dto.sdk.LoginRequest;
import io.metersphere.system.mapper.AuthSourceMapper;
import io.metersphere.system.mapper.UserMapper;
import io.metersphere.system.mapper.UserRoleRelationMapper;
import io.metersphere.system.service.SimpleUserService;
import io.metersphere.system.service.UserLoginService;
import io.metersphere.xpack.system.dto.LdapDTO;
import io.metersphere.xpack.system.ldap.SSLLdapContextSource;
import io.metersphere.xpack.system.ldap.p000vo.LdapLoginRequest;
import io.metersphere.xpack.system.ldap.p000vo.LdapRequest;
import io.metersphere.xpack.system.service.lark.LarkLoginService;
import io.metersphere.xpack.system.service.sso.SSOService;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.naming.ldap.LdapContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.realm.ldap.LdapUtils;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.InvalidNameException;
import org.springframework.ldap.InvalidSearchFilterException;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.core.support.DefaultDirObjectFactory;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.query.SearchScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* JADX INFO: compiled from: u */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/LdapService.class */
@Transactional(rollbackFor = {Exception.class})
@Service
public class LdapService {

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private UserLoginService userLoginService;

    @Resource
    private AuthSourceMapper authSourceMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleRelationMapper userRoleRelationMapper;

    @Resource
    private SimpleUserService userService;

    @Resource
    private UserXpackServiceImpl userXpackServiceImpl;

    @Resource
    private SSOService ssoService;

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private /* synthetic */ boolean ALLATORIxDEMO(String a, String a2, LdapTemplate a3) throws AuthenticationException {
        LdapContext context = null;
        try {
            context = (LdapContext) a3.getContextSource().getContext(a, a2);
            LdapUtils.closeContext(context);
            return true;
        } catch (Throwable th) {
            LdapUtils.closeContext(context);
            throw th;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void updateUser(User a) throws MSException {
        if (StringUtils.isNotBlank(a.getEmail())) {
            UserExample userExample = new UserExample();
            UserExample.Criteria criteriaCreateCriteria = userExample.createCriteria();
            criteriaCreateCriteria.andEmailEqualTo(a.getEmail());
            criteriaCreateCriteria.andIdNotEqualTo(a.getId());
            if (this.userMapper.countByExample(userExample) > 0) {
                throw new MSException(Translator.get(LarkLoginService.ALLATORIxDEMO("o\b\u007f\tE\u001ew\u001as\u0017E\u001av\t\u007f\u001a~\u0002E\u001eb\u0012i\u000fi")));
            }
        }
        a.setPassword((String) null);
        a.setUpdateTime(Long.valueOf(System.currentTimeMillis()));
        User userSelectByPrimaryKey = this.userMapper.selectByPrimaryKey(a.getId());
        if (a.getLastOrganizationId() != null && !StringUtils.equals(a.getLastOrganizationId(), userSelectByPrimaryKey.getLastOrganizationId())) {
            List<Project> listALLATORIxDEMO = ALLATORIxDEMO(a.getId(), a.getLastOrganizationId());
            if (listALLATORIxDEMO.size() <= 0) {
                a.setLastProjectId("");
            } else if (!listALLATORIxDEMO.stream().anyMatch(a2 -> {
                return StringUtils.equals(a2.getId(), a.getLastProjectId());
            })) {
                a.setLastProjectId(listALLATORIxDEMO.get(0).getId());
            }
        }
        this.userMapper.updateByPrimaryKeySelective(a);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.ldap.AuthenticationException */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public DirContextOperations testLogin(LdapLoginRequest a) throws MSException {
        String password = a.getPassword();
        try {
            LdapTemplate ldapTemplateALLATORIxDEMO = ALLATORIxDEMO(a);
            DirContextOperations contextMapper = getContextMapper(a, ldapTemplateALLATORIxDEMO);
            ALLATORIxDEMO(String.valueOf(contextMapper.getDn()), password, ldapTemplateALLATORIxDEMO);
            getMappingAttr(LdapRequest.ALLATORIxDEMO("'x$|"), contextMapper, a);
            return contextMapper;
        } catch (AuthenticationException e) {
            LogUtils.error(e.getMessage(), e);
            throw new MSException(Translator.get(LarkLoginService.ALLATORIxDEMO("{\u000en\u0013\u007f\u0015n\u0012y\u001an\u0012u\u0015E\u001d{\u0012v\u001e~")));
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public ResultHolder login(LoginRequest a) throws MSException {
        AuthSourceExample authSourceExample = new AuthSourceExample();
        authSourceExample.createCriteria().andTypeEqualTo(LarkLoginService.ALLATORIxDEMO("7^:J")).andEnableEqualTo(true);
        List listSelectByExampleWithBLOBs = this.authSourceMapper.selectByExampleWithBLOBs(authSourceExample);
        if (!CollectionUtils.isEmpty(listSelectByExampleWithBLOBs)) {
            String str = new String(((AuthSource) listSelectByExampleWithBLOBs.get(0)).getConfiguration(), StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            LdapLoginRequest ldapLoginRequest = new LdapLoginRequest();
            try {
                BeanUtils.copyBean(ldapLoginRequest, (LdapDTO) objectMapper.readValue(str, LdapDTO.class));
                ldapLoginRequest.setPassword(a.getPassword());
                ldapLoginRequest.setUsername(a.getUsername());
                ldapLoginRequest.setAuthenticate(a.getAuthenticate());
                DirContextOperations dirContextOperationsTestLogin = testLogin(ldapLoginRequest);
                return this.ssoService.login(getNotRequiredMappingAttr(LarkLoginService.ALLATORIxDEMO("\u007f\u0016{\u0012v"), dirContextOperationsTestLogin, ldapLoginRequest.getLdapUserMapping()), getMappingAttr(LdapRequest.ALLATORIxDEMO("<j,k'x$|"), dirContextOperationsTestLogin, ldapLoginRequest), getMappingAttr(LarkLoginService.ALLATORIxDEMO("\u0015{\u0016\u007f"), dirContextOperationsTestLogin, ldapLoginRequest), getNotRequiredMappingAttr(LdapRequest.ALLATORIxDEMO("i!v'|"), dirContextOperationsTestLogin, ldapLoginRequest.getLdapUserMapping()), UserSource.LDAP.name());
            } catch (JsonProcessingException e) {
                throw new RuntimeException((Throwable) e);
            }
        }
        throw new MSException(Translator.get(LdapRequest.ALLATORIxDEMO("u-x9F(l=q,w=p*x=p&w\u0016w&m\u0016|'x+u,}")));
    }

    private /* synthetic */ LdapTemplate ALLATORIxDEMO(LdapLoginRequest a) {
        LdapRequest ldapRequest = new LdapRequest();
        BeanUtils.copyBean(ldapRequest, a);
        return ALLATORIxDEMO(ldapRequest);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.ldap.AuthenticationException */
    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    private /* synthetic */ LdapTemplate ALLATORIxDEMO(LdapRequest a) throws MSException {
        LdapContextSource ldapContextSource;
        LdapContextSource ldapContextSource2;
        String string = EncryptUtils.aesDecrypt(a.getLdapPassword()).toString();
        if (StringUtils.startsWithIgnoreCase(a.getLdapUrl(), LdapRequest.ALLATORIxDEMO("%}(i:#f6"))) {
            ldapContextSource = new SSLLdapContextSource();
            ldapContextSource2 = ldapContextSource;
        } else {
            ldapContextSource = new LdapContextSource();
            ldapContextSource2 = ldapContextSource;
        }
        ldapContextSource.setUrl(a.getLdapUrl());
        LdapContextSource ldapContextSource3 = ldapContextSource2;
        ldapContextSource2.setUserDn(a.getLdapDn());
        ldapContextSource3.setPassword(string);
        ldapContextSource3.setDirObjectFactory(DefaultDirObjectFactory.class);
        ldapContextSource3.afterPropertiesSet();
        LdapTemplate ldapTemplate = new LdapTemplate(ldapContextSource2);
        ldapTemplate.setIgnorePartialResultException(true);
        Hashtable hashtable = new Hashtable();
        hashtable.put(LarkLoginService.ALLATORIxDEMO("y\u0014wUi\u000etUp\u0015~\u00124\u0017~\u001ajUy\u0014t\u0015\u007f\u0018nUn\u0012w\u001eu\u000en"), LdapRequest.ALLATORIxDEMO("z)y)"));
        hashtable.put(LarkLoginService.ALLATORIxDEMO("\u0018u\u00164\bo\u00154\u0011t\u001fsUv\u001f{\u000b4\t\u007f\u001a~Un\u0012w\u001eu\u000en"), LdapRequest.ALLATORIxDEMO("z)y)"));
        ldapContextSource2.setBaseEnvironmentProperties(hashtable);
        ldapTemplate.setDefaultSearchScope(SearchScope.SUBTREE.getId());
        try {
            ALLATORIxDEMO(a.getLdapDn(), string, ldapTemplate);
            return ldapTemplate;
        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
            throw new MSException(Translator.get(LdapRequest.ALLATORIxDEMO("u-x9F*v'w,z=F/x u")));
        }
    }

    /* JADX INFO: compiled from: u */
    /* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/LdapService$MsContextMapper.class */
    private static class MsContextMapper extends AbstractContextMapper<DirContextOperations> {
        /* JADX INFO: renamed from: doMapFromContext, reason: merged with bridge method [inline-methods] */
        public DirContextOperations doMapFromContext(DirContextOperations a) {
            return a;
        }

        private /* synthetic */ MsContextMapper() {
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public void testConnect(LdapRequest a) throws MSException {
        ALLATORIxDEMO(a);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public String getNotRequiredMappingAttr(String a, DirContextOperations a2, String a3) {
        String str = (String) ((Map) JSON.parseObject(a3, Map.class)).get(a);
        if (!StringUtils.isNotBlank(str)) {
            return str;
        }
        return a2.getStringAttribute(str);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public DirContextOperations getContextMapper(LdapLoginRequest a, LdapTemplate a2) throws MSException {
        String ldapUserFilter = a.getLdapUserFilter();
        String[] strArrSplit = a.getLdapUserOu().split(LarkLoginService.ALLATORIxDEMO("'f"));
        List list = null;
        int length = strArrSplit.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            try {
                List listSearch = a2.search(LdapQueryBuilder.query().base(strArrSplit[i2].trim()).filter(ldapUserFilter, new Object[]{a.getUsername()}), new MsContextMapper());
                list = listSearch;
                if (listSearch.size() != 1) {
                    i2++;
                    i = i2;
                } else {
                    return (DirContextOperations) list.get(0);
                }
            } catch (InvalidSearchFilterException e) {
                LogUtils.error(e.getMessage(), e);
                throw new MSException(Translator.get(LarkLoginService.ALLATORIxDEMO("v\u0014}\u0012t$|\u001as\u0017E\u001ds\u0017n\u001eh$\u007f\th\u0014h")));
            } catch (NameNotFoundException | InvalidNameException e2) {
                LogUtils.error(e2.getMessage(), e2);
                throw new MSException(Translator.get(LdapRequest.ALLATORIxDEMO("u&~ w\u0016\u007f(p%F&l\u0016|;k&k")));
            }
        }
        if (list.size() != 1) {
            throw new MSException(Translator.get(LdapRequest.ALLATORIxDEMO("<j,k\u0016w&m\u0016\u007f&l'}\u0016v;F'v=F<w h<|")));
        }
        return (DirContextOperations) list.get(0);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public String getMappingAttr(String a, DirContextOperations a2, LdapLoginRequest a3) throws MSException {
        String str = (String) ((Map) JSON.parseObject(a3.getLdapUserMapping(), Map.class)).get(a);
        if (StringUtils.isBlank(str)) {
            throw new MSException(Translator.get(LarkLoginService.ALLATORIxDEMO("\u0018r\u001ey\u0010E\u0017~\u001aj$w\u001aj\u000bs\u0015}")) + " " + a);
        }
        String a4 = a2.getStringAttribute(str);
        if (!StringUtils.isBlank(a4)) {
            return a4;
        }
        throw new MSException(Translator.get(LdapRequest.ALLATORIxDEMO("u-x9F$x9i w.F?x%l,F'l%u")) + " " + str);
    }

    private /* synthetic */ List<Project> ALLATORIxDEMO(String a, String a2) {
        ProjectExample projectExample = new ProjectExample();
        projectExample.createCriteria().andOrganizationIdEqualTo(a2);
        List<Project> listSelectByExample = this.projectMapper.selectByExample(projectExample);
        UserRoleRelationExample userRoleRelationExample = new UserRoleRelationExample();
        userRoleRelationExample.createCriteria().andUserIdEqualTo(a);
        List<UserRoleRelation> listSelectByExample2 = this.userRoleRelationMapper.selectByExample(userRoleRelationExample);
        ArrayList arrayList = new ArrayList();
        listSelectByExample2.forEach(a3 -> {
            listSelectByExample.forEach(a4 -> {
                if (!StringUtils.equals(a3.getSourceId(), a4.getId()) || arrayList.contains(a3)) {
                    return;
                }
                arrayList.add(a3);
            });
        });
        return arrayList;
    }
}
