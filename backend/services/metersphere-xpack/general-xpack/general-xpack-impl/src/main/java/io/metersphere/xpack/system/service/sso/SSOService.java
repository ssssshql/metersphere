package io.metersphere.xpack.system.service.sso;

import com.fasterxml.jackson.core.type.TypeReference;
import io.metersphere.project.domain.Project;
import io.metersphere.project.domain.ProjectExample;
import io.metersphere.project.mapper.ProjectMapper;
import io.metersphere.sdk.constants.HttpMethodConstants;
import io.metersphere.sdk.constants.InternalUserRole;
import io.metersphere.sdk.constants.UserSource;
import io.metersphere.sdk.exception.MSException;
import io.metersphere.sdk.util.BeanUtils;
import io.metersphere.sdk.util.CodingUtils;
import io.metersphere.sdk.util.EncryptUtils;
import io.metersphere.sdk.util.JSON;
import io.metersphere.sdk.util.LogUtils;
import io.metersphere.sdk.util.RsaKey;
import io.metersphere.sdk.util.RsaUtils;
import io.metersphere.sdk.util.Translator;
import io.metersphere.system.controller.handler.ResultHolder;
import io.metersphere.system.domain.Organization;
import io.metersphere.system.domain.OrganizationExample;
import io.metersphere.system.domain.PlatformSource;
import io.metersphere.system.domain.User;
import io.metersphere.system.domain.UserRoleRelation;
import io.metersphere.system.dto.AuthSourceDTO;
import io.metersphere.system.dto.builder.LogDTOBuilder;
import io.metersphere.system.dto.sdk.LoginRequest;
import io.metersphere.system.dto.user.UserCreateInfo;
import io.metersphere.system.dto.user.UserDTO;
import io.metersphere.system.log.constants.OperationLogType;
import io.metersphere.system.log.service.OperationLogService;
import io.metersphere.system.mapper.BaseUserMapper;
import io.metersphere.system.mapper.OrganizationMapper;
import io.metersphere.system.mapper.PlatformSourceMapper;
import io.metersphere.system.mapper.UserMapper;
import io.metersphere.system.mapper.UserRoleRelationMapper;
import io.metersphere.system.service.UserLoginService;
import io.metersphere.system.uid.IDGenerator;
import io.metersphere.xpack.client.QrCodeClient;
import io.metersphere.xpack.project.service.ProjectVersionService;
import io.metersphere.xpack.system.dto.dingtalk.DingTalkCreator;
import io.metersphere.xpack.system.dto.dingtalk.DingTalkInfoDTO;
import io.metersphere.xpack.system.dto.dingtalk.DingTalkTokenParamDTO;
import io.metersphere.xpack.system.dto.lark.LarkBaseParamDTO;
import io.metersphere.xpack.system.dto.lark.LarkCreator;
import io.metersphere.xpack.system.dto.lark.LarkInfoDTO;
import io.metersphere.xpack.system.dto.lark.LarkTokenParamDTO;
import io.metersphere.xpack.system.dto.request.AuthSourceRequest;
import io.metersphere.xpack.system.dto.wecom.WeComCreator;
import io.metersphere.xpack.system.dto.wecom.WeComInfoDTO;
import io.metersphere.xpack.system.service.AuthSourceService;
import io.metersphere.xpack.system.service.IDEncryptUtils;
import io.metersphere.xpack.system.service.UserXpackServiceImpl;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

/* JADX INFO: compiled from: x */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/sso/SSOService.class */
@Transactional(rollbackFor = {Exception.class})
@Service
public class SSOService {
    private static final String LARK = "LARK";
    private static final String WE_COM_USERINFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=%s&userid=%s";

    @Resource
    private UserMapper userMapper;

    @Resource
    private QrCodeClient qrCodeClient;

    @Resource
    private UserLoginService userLoginService;

    @Resource
    private OperationLogService operationLogService;

    @Resource
    private UserXpackServiceImpl userXpackService;
    private static final String DING_USER_INFO = "https://api.dingtalk.com/v1.0/contact/users/me";
    private static final String LARK_APP_TOKEN_URL = "https://open.feishu.cn/open-apis/auth/v3/app_access_token/internal";
    private static final String LARK_SUITE_USER_TOKEN_URL = "https://open.larksuite.com/open-apis/authen/v1/oidc/access_token";
    private static final String DING = "DING_TALK";
    private static final String LARK_SUITE = "LARK_SUITE";
    private static final String WE_COM_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
    private static final String LARK_USER_TOKEN_URL = "https://open.feishu.cn/open-apis/authen/v1/oidc/access_token";

    @Resource
    private OrganizationMapper organizationMapper;
    private static final String LARK_USER_INFO_URL = "https://open.feishu.cn/open-apis/authen/v1/user_info";
    private static final String LARK_SUITE_USER_INFO_URL = "https://open.larksuite.com/open-apis/authen/v1/user_info";
    private static final String LARK_SUITE_APP_TOKEN_URL = "https://open.larksuite.com/open-apis/auth/v3/app_access_token/internal";
    private static final String WE_COM_USERID_URL = "https://qyapi.weixin.qq.com/cgi-bin/auth/getuserinfo?access_token=%s&code=%s";

    @Resource
    private AuthSourceService authSourceService;

    @Resource
    private ProjectMapper projectMapper;
    private static final String DING_USER_TOKEN_URL = "https://api.dingtalk.com/v1.0/oauth2/userAccessToken";
    private static final String WE_COM = "WE_COM";

    @Resource
    private BaseUserMapper baseUserMapper;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @Resource
    private PlatformSourceMapper platformSourceMapper;

    /* JADX INFO: renamed from: L */
    private /* synthetic */ void m2L(String a, String a2) {
        User user = new User();
        user.setId(a2);
        user.setEmail(a);
        user.setUpdateUser(a2);
        user.setUpdateTime(Long.valueOf(System.currentTimeMillis()));
        user.setCreateUser((String) null);
        user.setUpdateTime((Long) null);
        this.userMapper.updateByPrimaryKeySelective(user);
    }

    private /* synthetic */ void ALLATORIxDEMO(String a, String a2) {
        User user = new User();
        user.setId(a2);
        user.setEmail(a);
        user.setCftToken(IDEncryptUtils.aesEncrypt(user.getId()));
        user.setDeleted(false);
        user.setEnable(true);
        user.setPassword(CodingUtils.md5(a));
        user.setUpdateUser(a2);
        user.setUpdateTime(Long.valueOf(System.currentTimeMillis()));
        user.setCreateUser((String) null);
        user.setUpdateTime((Long) null);
        this.userMapper.updateByPrimaryKeySelective(user);
        User userSelectByPrimaryKey = this.userMapper.selectByPrimaryKey(a2);
        Organization organizationALLATORIxDEMO = ALLATORIxDEMO();
        ALLATORIxDEMO(organizationALLATORIxDEMO, ALLATORIxDEMO(organizationALLATORIxDEMO), userSelectByPrimaryKey);
        ALLATORIxDEMO(userSelectByPrimaryKey);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public ResultHolder exchangeWeComToken(String a) throws MSException {
        String strALLATORIxDEMO = ALLATORIxDEMO(getWeComInfo(WE_COM));
        Map map = JSON.parseMap(this.qrCodeClient.get(String.format(WE_COM_USERID_URL, strALLATORIxDEMO, a)));
        if (ObjectUtils.isNotEmpty(map.get(ProjectVersionService.ALLATORIxDEMO("t\u0018c\t~\u000et"))) && Integer.parseInt(map.get(AuthSourceRequest.ALLATORIxDEMO("L\u000b[\u001aF\u001dL")).toString()) != 0) {
            throw new MSException("获取USERID失败:" + String.valueOf(map.get(ProjectVersionService.ALLATORIxDEMO("\u000fc\u0018|\u0019v"))));
        }
        if (map.containsKey(AuthSourceRequest.ALLATORIxDEMO("\fZ\u001c[\u0010M")) || !map.containsKey(ProjectVersionService.ALLATORIxDEMO("\u0005a\u000f\u007f\u0003u"))) {
            String a2 = map.get(ProjectVersionService.ALLATORIxDEMO("\u001fb\u000fc\u0003u")).toString();
            Map map2 = JSON.parseMap(this.qrCodeClient.get(String.format(WE_COM_USERINFO_URL, strALLATORIxDEMO, a2)));
            if (!ObjectUtils.isNotEmpty(map2.get(AuthSourceRequest.ALLATORIxDEMO("L\u000b[\u001aF\u001dL"))) || Integer.parseInt(map2.get(ProjectVersionService.ALLATORIxDEMO("t\u0018c\t~\u000et")).toString()) == 0) {
                String string = ObjectUtils.isNotEmpty(map2.get(ProjectVersionService.ALLATORIxDEMO("\u0007~\bx\u0006t"))) ? map2.get(AuthSourceRequest.ALLATORIxDEMO("\u0014F\u001b@\u0015L")).toString() : "";
                return login(ObjectUtils.isNotEmpty(map2.get(AuthSourceRequest.ALLATORIxDEMO("L\u0014H\u0010E"))) ? map2.get(ProjectVersionService.ALLATORIxDEMO("t\u0007p\u0003}")).toString() : ObjectUtils.isNotEmpty(map2.get(AuthSourceRequest.ALLATORIxDEMO("\u001b@\u0003v\u0014H\u0010E"))) ? map2.get(ProjectVersionService.ALLATORIxDEMO("\bx\u0010N\u0007p\u0003}")).toString() : "", a2, map2.get(ProjectVersionService.ALLATORIxDEMO("\u0004p\u0007t")).toString(), string, UserSource.QR_CODE.name());
            }
            throw new MSException("获取用户详情失败:" + String.valueOf(map2.get(AuthSourceRequest.ALLATORIxDEMO("\u001c[\u000bD\nN"))));
        }
        throw new MSException(AuthSourceRequest.ALLATORIxDEMO("弪剤畑戞霧伨乣戹吡Ｅ秸歋瘂彼撴併"));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: org.apache.shiro.authc.AuthenticationException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: org.apache.shiro.authc.DisabledAccountException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: org.apache.shiro.authc.ExcessiveAttemptsException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: org.apache.shiro.authc.ExpiredCredentialsException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: org.apache.shiro.authc.LockedAccountException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: org.apache.shiro.authz.UnauthorizedException */
    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public ResultHolder login(String a, String a2, String a3, String a4, String a5) throws AuthenticationException, UnauthorizedException, ExpiredCredentialsException, DisabledAccountException, MSException, ExcessiveAttemptsException, LockedAccountException {
        UserDTO userDTO;
        boolean z = false;
        try {
            UserDTO userDTOSelectById = this.baseUserMapper.selectById(a2);
            if (userDTOSelectById != null && BooleanUtils.isTrue(userDTOSelectById.getDeleted())) {
                this.userXpackService.GWHowToChangeUser(List.of(a2), true, a2);
            }
            if (!StringUtils.isNotBlank(a)) {
                a = a2 + "@metersphere.io";
                UserDTO userDTOByEmail = this.userLoginService.getUserDTOByEmail(a, new String[0]);
                userDTO = userDTOByEmail;
                if (userDTOByEmail == null) {
                    userDTO = this.userLoginService.getUserDTO(a2);
                }
            } else {
                userDTO = this.userLoginService.getUserDTOByEmail(a, new String[0]);
                if (userDTOSelectById != null && userDTO == null) {
                    UserDTO userDTO2 = new UserDTO();
                    userDTO = userDTO2;
                    BeanUtils.copyBean(userDTO2, userDTOSelectById);
                    userDTO.setEmail(a);
                    z = true;
                }
            }
            if (userDTO != null) {
                UserDTO userDTO3 = userDTO;
                a2 = userDTO3.getId();
                if (!userDTO3.getEnable().booleanValue()) {
                    throw new MSException(AuthSourceRequest.ALLATORIxDEMO("\\\nL\u000b\t\u0010ZYM\u0010Z\u0018K\u0015L\u001d\b"));
                }
                LogUtils.info(a2);
                UserDTO userDTO4 = userDTO;
                LogUtils.info(userDTO4.getDeleted());
                if (userDTO4.getDeleted().booleanValue()) {
                    if (this.userXpackService.checkValidateLicence(1) != 0) {
                        throw new MSException(ProjectVersionService.ALLATORIxDEMO("?b\u000fcJe\u0005~J|\u000b\u007f\u00130"));
                    }
                    LogUtils.info(AuthSourceRequest.ALLATORIxDEMO("\u000bL\u001aF\u000fL\u000b\t\fZ\u001c["));
                    ALLATORIxDEMO(a, a2);
                }
                if (z) {
                    m2L(a, a2);
                }
            } else {
                UserCreateInfo userCreateInfo = new UserCreateInfo();
                userCreateInfo.setId(a2);
                userCreateInfo.setEmail(a);
                userCreateInfo.setName(a3);
                userCreateInfo.setPhone(a4);
                LogUtils.info(ProjectVersionService.ALLATORIxDEMO("r\u0018t\u000be?b\u000fc"));
                LogUtils.info(a);
                LogUtils.info(a3);
                LogUtils.info(a2);
                ALLATORIxDEMO(userCreateInfo, a5);
            }
            LogUtils.info(ProjectVersionService.ALLATORIxDEMO("\u0006~\rx\u00041\u001fb\u000fc"));
            LogUtils.info(a);
            LogUtils.info(a3);
            LogUtils.info(a2);
            LoginRequest loginRequest = new LoginRequest();
            try {
                RsaKey rsaKey = RsaUtils.getRsaKey();
                loginRequest.setAuthenticate(a5);
                loginRequest.setUsername(RsaUtils.publicEncrypt(a2, rsaKey.getPublicKey()));
                loginRequest.setPassword(CodingUtils.md5(a));
                ResultHolder resultHolderLogin = this.userLoginService.login(loginRequest);
                resultHolderLogin.setMessage(BooleanUtils.toStringTrueFalse(this.userLoginService.checkWhetherChangePasswordOrNot(loginRequest)));
                SecurityUtils.getSubject().getSession().setAttribute(ProjectVersionService.ALLATORIxDEMO("\u000bd\u001ey\u000f\u007f\u001ex\tp\u001et"), a5);
                return resultHolderLogin;
            } catch (Exception e) {
                LogUtils.error(AuthSourceRequest.ALLATORIxDEMO("E\u0016N\u0010GYL\u000b[\u0016[C\t"), e);
                throw new MSException("login error: " + e.getMessage());
            }
        } catch (AuthenticationException e2) {
            throw new AuthenticationException(e2.getMessage());
        } catch (UnauthorizedException e4) {
            throw new UnauthorizedException(Translator.get(AuthSourceRequest.ALLATORIxDEMO("\u0017F\rv\u0018\\\rA\u0016[\u0010S\u001cM")) + e4.getMessage());
        }
    }

    private /* synthetic */ void ALLATORIxDEMO(Organization a, Project a2, User a3) {
        HashMap<String,String> map = new HashMap();
        map.put(InternalUserRole.MEMBER.getValue(), ProjectVersionService.ALLATORIxDEMO("\u0019h\u0019e\u000f|"));
        map.put(InternalUserRole.ORG_MEMBER.getValue(), a.getId());
        map.put(InternalUserRole.PROJECT_MEMBER.getValue(), a2.getId());
        HashMap<String,String> map2 = new HashMap();
        map2.put(InternalUserRole.MEMBER.getValue(), AuthSourceRequest.ALLATORIxDEMO("\nP\n]\u001cD"));
        map2.put(InternalUserRole.ORG_MEMBER.getValue(), a.getId());
        map2.put(InternalUserRole.PROJECT_MEMBER.getValue(), a.getId());
        SqlSession sqlSessionOpenSession = this.sqlSessionFactory.openSession(ExecutorType.BATCH);
        UserRoleRelationMapper userRoleRelationMapper = (UserRoleRelationMapper) sqlSessionOpenSession.getMapper(UserRoleRelationMapper.class);
        map.forEach((a4, a5) -> {
            UserRoleRelation userRoleRelation = new UserRoleRelation();
            userRoleRelation.setId(IDGenerator.nextStr());
            userRoleRelation.setUserId(a3.getId());
            userRoleRelation.setRoleId(a4);
            userRoleRelation.setSourceId(a5);
            userRoleRelation.setCreateTime(Long.valueOf(System.currentTimeMillis()));
            userRoleRelation.setCreateUser(a3.getCreateUser());
            userRoleRelation.setOrganizationId((String) map2.get(a4));
            userRoleRelationMapper.insert(userRoleRelation);
        });
        sqlSessionOpenSession.flushStatements();
        SqlSessionUtils.closeSqlSession(sqlSessionOpenSession, this.sqlSessionFactory);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public ResultHolder exchangeLarkToken(String a) {
        Map map = (Map) JSON.parseMap(this.qrCodeClient.exchange(LARK_USER_INFO_URL, "Bearer " + generateLarkUserToken(a, LARK_USER_TOKEN_URL, generateLarkAppToken(getLarkInfo(LARK), LARK_APP_TOKEN_URL, LARK)), AuthSourceRequest.ALLATORIxDEMO("h\f]\u0011F\u000b@\u0003H\r@\u0016G"), MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON)).get(ProjectVersionService.ALLATORIxDEMO("\u000ep\u001ep"));
        return login(ObjectUtils.isNotEmpty(map.get(AuthSourceRequest.ALLATORIxDEMO("L\u0014H\u0010E"))) ? map.get(ProjectVersionService.ALLATORIxDEMO("t\u0007p\u0003}")).toString() : ObjectUtils.isNotEmpty(map.get(AuthSourceRequest.ALLATORIxDEMO("\u001cG\rL\u000bY\u000b@\nL&L\u0014H\u0010E"))) ? map.get(ProjectVersionService.ALLATORIxDEMO("\u000f\u007f\u001et\u0018a\u0018x\u0019t5t\u0007p\u0003}")).toString() : "", map.get(AuthSourceRequest.ALLATORIxDEMO("\fG\u0010F\u0017v\u0010M")).toString(), map.get(ProjectVersionService.ALLATORIxDEMO("\u0004p\u0007t")).toString(), ObjectUtils.isNotEmpty(map.get(AuthSourceRequest.ALLATORIxDEMO("\u0014F\u001b@\u0015L"))) ? map.get(ProjectVersionService.ALLATORIxDEMO("\u0007~\bx\u0006t")).toString() : "", UserSource.QR_CODE.name());
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public LarkInfoDTO getLarkInfo(String a) {
        PlatformSource platformSourceSelectByPrimaryKey = this.platformSourceMapper.selectByPrimaryKey(a);
        if (platformSourceSelectByPrimaryKey != null) {
            LarkInfoDTO larkInfoDTO = new LarkInfoDTO();
            BeanUtils.copyBean(larkInfoDTO, (LarkCreator) JSON.parseObject(new String(platformSourceSelectByPrimaryKey.getConfig(), StandardCharsets.UTF_8), LarkCreator.class));
            larkInfoDTO.setEnable(platformSourceSelectByPrimaryKey.getEnable());
            larkInfoDTO.setValid(platformSourceSelectByPrimaryKey.getValid());
            return larkInfoDTO;
        }
        return new LarkInfoDTO();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public String generateLarkUserToken(String a, String a2, String a3) throws MSException {
        LarkTokenParamDTO larkTokenParamDTO = new LarkTokenParamDTO();
        larkTokenParamDTO.setCode(a);
        larkTokenParamDTO.setGrant_type(ProjectVersionService.ALLATORIxDEMO("\u000bd\u001ey\u0005c\u0003k\u000be\u0003~\u0004N\t~\u000et"));
        Map map = JSON.parseMap(this.qrCodeClient.postExchange(a2, "Bearer " + a3, AuthSourceRequest.ALLATORIxDEMO("h\f]\u0011F\u000b@\u0003H\r@\u0016G"), larkTokenParamDTO, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON));
        if (!ObjectUtils.isNotEmpty(map.get(ProjectVersionService.ALLATORIxDEMO("\t~\u000et"))) || Integer.parseInt(map.get(AuthSourceRequest.ALLATORIxDEMO("\u001aF\u001dL")).toString()) <= 0) {
            return ((Map) map.get(AuthSourceRequest.ALLATORIxDEMO("\u001dH\rH"))).get(ProjectVersionService.ALLATORIxDEMO("\u000br\tt\u0019b5e\u0005z\u000f\u007f")).toString();
        }
        throw new MSException("获取user_access_token失败:" + String.valueOf(map.get(ProjectVersionService.ALLATORIxDEMO("|\u0019v"))));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public ResultHolder exchangeDingTalkToken(String a) {
        Map map = JSON.parseMap(this.qrCodeClient.exchange(DING_USER_INFO, generateDingUserToken(getDingInfo(DING), a), ProjectVersionService.ALLATORIxDEMO("iGp\tbGu\u0003\u007f\re\u000b}\u0001<\u000br\tt\u0019bGe\u0005z\u000f\u007f"), MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON));
        return login(ObjectUtils.isNotEmpty(map.get(AuthSourceRequest.ALLATORIxDEMO("L\u0014H\u0010E"))) ? map.get(ProjectVersionService.ALLATORIxDEMO("t\u0007p\u0003}")).toString() : "", map.get(AuthSourceRequest.ALLATORIxDEMO("\\\u0017@\u0016G0M")).toString(), map.get(ProjectVersionService.ALLATORIxDEMO("\u0004x\tz")).toString(), ObjectUtils.isNotEmpty(map.get(AuthSourceRequest.ALLATORIxDEMO("\u0014F\u001b@\u0015L"))) ? map.get(ProjectVersionService.ALLATORIxDEMO("\u0007~\bx\u0006t")).toString() : "", UserSource.QR_CODE.name());
    }

    /* JADX INFO: renamed from: ALLATORIxDEMO, reason: collision with other method in class */
    private /* synthetic */ RestTemplate m7ALLATORIxDEMO() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(HttpClients.custom().setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create().setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create().setSslContext(SSLContexts.custom().loadTrustMaterial((KeyStore) null, (x509CertificateArr, str) -> {
            return true;
        }).build()).setHostnameVerifier(NoopHostnameVerifier.INSTANCE).build()).build()).build()));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    private /* synthetic */ String ALLATORIxDEMO(WeComInfoDTO weComInfoDTO) throws MSException {
        WeComInfoDTO weComInfo = weComInfoDTO;
        if (ObjectUtils.isEmpty(weComInfo)) {
            weComInfo = getWeComInfo(WE_COM);
        }
        WeComInfoDTO weComInfoDTO2 = weComInfo;
        Map map = JSON.parseMap(this.qrCodeClient.get(String.format(WE_COM_TOKEN_URL, weComInfoDTO2.getCorpId(), weComInfoDTO2.getAppSecret())));
        if (!ObjectUtils.isNotEmpty(map.get(AuthSourceRequest.ALLATORIxDEMO("L\u000b[\u001aF\u001dL"))) || Integer.parseInt(map.get(ProjectVersionService.ALLATORIxDEMO("t\u0018c\t~\u000et")).toString()) == 0) {
            return map.get(ProjectVersionService.ALLATORIxDEMO("\u000br\tt\u0019b5e\u0005z\u000f\u007f")).toString();
        }
        throw new MSException("获取accessToken失败:" + String.valueOf(map.get(AuthSourceRequest.ALLATORIxDEMO("\u001c[\u000bD\nN"))));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public ResultHolder exchangeCas(String a, String a2) {
        Map map = (Map) JSON.parseObject(this.authSourceService.getAuthSource(a2).getConfiguration(), Map.class);
        String a3 = StringUtils.substringBetween(this.qrCodeClient.exchangeString(((String) map.get(ProjectVersionService.ALLATORIxDEMO("g\u000b}\u0003u\u000be\u000fD\u0018}"))) + "?service=" + ((String) map.get(ProjectVersionService.ALLATORIxDEMO("c\u000fu\u0003c\u000fr\u001eD\u0018}"))).replace(AuthSourceRequest.ALLATORIxDEMO("\r\u0002H\f]\u0011`\u001dT"), a2) + "&ticket=" + a, null, null, MediaType.TEXT_XML, MediaType.TEXT_XML), AuthSourceRequest.ALLATORIxDEMO("EJ\u0018ZC\\\nL\u000b\u0017"), ProjectVersionService.ALLATORIxDEMO("-Er\u000bbPd\u0019t\u0018/"));
        return login(StringUtils.contains(a3, AuthSourceRequest.ALLATORIxDEMO("i")) ? a3 : null, a3, a3, "", UserSource.CAS.name());
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public ResultHolder exchangeOauth2(String a, String a2) throws MSException {
        AuthSourceDTO authSource = this.authSourceService.getAuthSource(a2);
        String a3 = ALLATORIxDEMO(a, (Map<String, String>) JSON.parseObject(authSource.getConfiguration(), new TypeReference<HashMap<String, String>>() { // from class: io.metersphere.xpack.system.service.sso.SSOService.1
        }));
        try {
            Map map = (Map) JSON.parseObject(authSource.getConfiguration(), new TypeReference<HashMap<String, String>>() { // from class: io.metersphere.xpack.system.service.sso.SSOService.2
            });
            Map map2 = (Map) JSON.parseObject(this.qrCodeClient.exchange((String) map.get(ProjectVersionService.ALLATORIxDEMO("d\u0019t\u0018X\u0004w\u0005D\u0018}")), "Bearer " + a3, AuthSourceRequest.ALLATORIxDEMO("h\f]\u0011F\u000b@\u0003H\r@\u0016G"), MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON), new TypeReference<HashMap<String, Object>>() { // from class: io.metersphere.xpack.system.service.sso.SSOService.3
            });
            Map<String, String> mapALLATORIxDEMO = ALLATORIxDEMO((String) map.get(AuthSourceRequest.ALLATORIxDEMO("D\u0018Y\t@\u0017N")));
            return login((String) map2.get(mapALLATORIxDEMO.get(ProjectVersionService.ALLATORIxDEMO("t\u0007p\u0003}"))), (String) map2.get(mapALLATORIxDEMO.get(ProjectVersionService.ALLATORIxDEMO("\u001fb\u000fc\u0003u"))), (String) map2.get(mapALLATORIxDEMO.get(AuthSourceRequest.ALLATORIxDEMO("\fZ\u001c[\u0017H\u0014L"))), "", UserSource.OAUTH2.name());
        } catch (Exception e) {
            throw new MSException(ProjectVersionService.ALLATORIxDEMO("菝叇畂戦例恾奛贴"));
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private /* synthetic */ String ALLATORIxDEMO(String a, Map<String, String> map) throws MSException {
        String a2 = map.get(ProjectVersionService.ALLATORIxDEMO("\u001e~\u0001t\u0004D\u0018}")) + "?client_id=" + map.get(AuthSourceRequest.ALLATORIxDEMO("\u001aE\u0010L\u0017]0M")) + "&client_secret=" + map.get(ProjectVersionService.ALLATORIxDEMO("\u0019t\tc\u000fe")) + "&redirect_uri=" + map.get(AuthSourceRequest.ALLATORIxDEMO("[\u001cM\u0010[\u001cJ\r|\u000bE")) + "&code=" + a + "&grant_type=authorization_code";
        try {
            String str = (String) ((Map) JSON.parseObject(this.qrCodeClient.postExchange(a2, "Basic " + EncryptUtils.base64Encoding(map.get(ProjectVersionService.ALLATORIxDEMO("\t}\u0003t\u0004e#u")) + ":" + map.get(AuthSourceRequest.ALLATORIxDEMO("\nL\u001a[\u001c]"))), ProjectVersionService.ALLATORIxDEMO("P\u001fe\u0002~\u0018x\u0010p\u001ex\u0005\u007f"), HttpEntity.EMPTY, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON), new TypeReference<HashMap<String, String>>() { // from class: io.metersphere.xpack.system.service.sso.SSOService.5
            })).get(ProjectVersionService.ALLATORIxDEMO("\u000br\tt\u0019b5e\u0005z\u000f\u007f"));
            if (!StringUtils.isBlank(str)) {
                return str;
            }
            throw new MSException(AuthSourceRequest.ALLATORIxDEMO("菎叿\u0018J\u001aL\nZ&]\u0016B\u001cG奈贌"));
        } catch (Exception e) {
            throw new MSException(AuthSourceRequest.ALLATORIxDEMO("菎叿\u0018J\u001aL\nZ&]\u0016B\u001cG奈贌"));
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public ResultHolder exchangeLarkSuiteToken(String a) {
        Map map = (Map) JSON.parseMap(this.qrCodeClient.exchange(LARK_SUITE_USER_INFO_URL, "Bearer " + generateLarkUserToken(a, LARK_SUITE_USER_TOKEN_URL, generateLarkAppToken(getLarkInfo(LARK_SUITE), LARK_SUITE_APP_TOKEN_URL, LARK)), AuthSourceRequest.ALLATORIxDEMO("h\f]\u0011F\u000b@\u0003H\r@\u0016G"), MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON)).get(ProjectVersionService.ALLATORIxDEMO("\u000ep\u001ep"));
        return login(ObjectUtils.isNotEmpty(map.get(AuthSourceRequest.ALLATORIxDEMO("L\u0014H\u0010E"))) ? map.get(ProjectVersionService.ALLATORIxDEMO("t\u0007p\u0003}")).toString() : ObjectUtils.isNotEmpty(map.get(AuthSourceRequest.ALLATORIxDEMO("\u001cG\rL\u000bY\u000b@\nL&L\u0014H\u0010E"))) ? map.get(ProjectVersionService.ALLATORIxDEMO("\u000f\u007f\u001et\u0018a\u0018x\u0019t5t\u0007p\u0003}")).toString() : "", map.get(AuthSourceRequest.ALLATORIxDEMO("\fG\u0010F\u0017v\u0010M")).toString(), map.get(ProjectVersionService.ALLATORIxDEMO("\u0004p\u0007t")).toString(), ObjectUtils.isNotEmpty(map.get(AuthSourceRequest.ALLATORIxDEMO("\u0014F\u001b@\u0015L"))) ? map.get(ProjectVersionService.ALLATORIxDEMO("\u0007~\bx\u0006t")).toString() : "", UserSource.QR_CODE.name());
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public String generateDingUserToken(DingTalkInfoDTO dingTalkInfoDTO, String a) throws MSException {
        DingTalkInfoDTO dingInfo = dingTalkInfoDTO;
        if (ObjectUtils.isEmpty(dingInfo)) {
            dingInfo = getDingInfo(DING);
        }
        DingTalkInfoDTO dingTalkInfoDTO2 = dingInfo;
        String appKey = dingTalkInfoDTO2.getAppKey();
        String appSecret = dingTalkInfoDTO2.getAppSecret();
        DingTalkTokenParamDTO dingTalkTokenParamDTO = new DingTalkTokenParamDTO();
        dingTalkTokenParamDTO.setClientId(appKey);
        dingTalkTokenParamDTO.setClientSecret(appSecret);
        dingTalkTokenParamDTO.setCode(a);
        dingTalkTokenParamDTO.setGrantType(AuthSourceRequest.ALLATORIxDEMO("\u0018\\\rA\u0016[\u0010S\u0018]\u0010F\u0017v\u001aF\u001dL"));
        Map map = JSON.parseMap(this.qrCodeClient.postExchange(DING_USER_TOKEN_URL, null, null, dingTalkTokenParamDTO, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON));
        if (ObjectUtils.isNotEmpty(map.get(ProjectVersionService.ALLATORIxDEMO("t\u0018c\t~\u000et"))) && Integer.parseInt(map.get(AuthSourceRequest.ALLATORIxDEMO("L\u000b[\u001aF\u001dL")).toString()) != 0) {
            throw new MSException("获取accessToken失败:" + String.valueOf(map.get(ProjectVersionService.ALLATORIxDEMO("\u000fc\u0018|\u0019v"))));
        }
        return map.get(AuthSourceRequest.ALLATORIxDEMO("H\u001aJ\u001cZ\n}\u0016B\u001cG")).toString();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public String generateLarkAppToken(LarkInfoDTO larkInfoDTO, String a, String a2) throws MSException {
        LarkInfoDTO larkInfo = larkInfoDTO;
        if (ObjectUtils.isEmpty(larkInfo)) {
            larkInfo = getLarkInfo(a2);
        }
        LarkInfoDTO larkInfoDTO2 = larkInfo;
        String agentId = larkInfoDTO2.getAgentId();
        String a3 = larkInfoDTO2.getAppSecret();
        LarkBaseParamDTO larkBaseParamDTO = new LarkBaseParamDTO();
        larkBaseParamDTO.setApp_id(agentId);
        larkBaseParamDTO.setApp_secret(a3);
        Map map = JSON.parseMap(this.qrCodeClient.postExchange(a, null, null, larkBaseParamDTO, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON));
        if (!ObjectUtils.isNotEmpty(map.get(ProjectVersionService.ALLATORIxDEMO("\t~\u000et"))) || Integer.parseInt(map.get(AuthSourceRequest.ALLATORIxDEMO("\u001aF\u001dL")).toString()) <= 0) {
            return map.get(AuthSourceRequest.ALLATORIxDEMO("\u0018Y\tv\u0018J\u001aL\nZ&]\u0016B\u001cG")).toString();
        }
        throw new MSException("获取appAccessToken失败:" + String.valueOf(map.get(ProjectVersionService.ALLATORIxDEMO("|\u0019v"))));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private /* synthetic */ Map<String, String> ALLATORIxDEMO(String a) throws MSException {
        try {
            Map<String, String> map = (Map) JSON.parseObject(a, new TypeReference<HashMap<String, String>>() { // from class: io.metersphere.xpack.system.service.sso.SSOService.4
            });
            if (StringUtils.isBlank(map.get(ProjectVersionService.ALLATORIxDEMO("\u001fb\u000fc\u0003u")))) {
                throw new MSException(Translator.get(AuthSourceRequest.ALLATORIxDEMO("\u0016H\f]\u0011v\u0014H\tY\u0010G\u001ev\u000fH\u0015\\\u001cv\u0017\\\u0015E")) + ": userid");
            }
            if (StringUtils.isBlank(map.get(ProjectVersionService.ALLATORIxDEMO("\u001fb\u000fc\u0004p\u0007t")))) {
                throw new MSException(Translator.get(AuthSourceRequest.ALLATORIxDEMO("\u0016H\f]\u0011v\u0014H\tY\u0010G\u001ev\u000fH\u0015\\\u001cv\u0017\\\u0015E")) + ": username");
            }
            if (!StringUtils.isBlank(map.get(ProjectVersionService.ALLATORIxDEMO("t\u0007p\u0003}")))) {
                return map;
            }
            throw new MSException(Translator.get(AuthSourceRequest.ALLATORIxDEMO("\u0016H\f]\u0011v\u0014H\tY\u0010G\u001ev\u000fH\u0015\\\u001cv\u0017\\\u0015E")) + ": email");
        } catch (Exception unused) {
            throw new MSException(Translator.get(AuthSourceRequest.ALLATORIxDEMO("\u0016H\f]\u0011v\u0014H\tY\u0010G\u001ev\u001aF\u0017O\u0010N&L\u000b[\u0016[")));
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    private /* synthetic */ void ALLATORIxDEMO(UserCreateInfo a, String a2) throws MSException {
        if (this.userXpackService.checkValidateLicence(1) != 0) {
            throw new MSException(AuthSourceRequest.ALLATORIxDEMO(",Z\u001c[Y]\u0016FYD\u0018G\u0000\b"));
        }
        User user = new User();
        BeanUtils.copyBean(user, a);
        user.setCftToken(IDEncryptUtils.aesEncrypt(user.getId()));
        user.setCreateUser(ProjectVersionService.ALLATORIxDEMO("p\u000e|\u0003\u007f"));
        user.setCreateTime(Long.valueOf(System.currentTimeMillis()));
        user.setUpdateUser(AuthSourceRequest.ALLATORIxDEMO("H\u001dD\u0010G"));
        user.setUpdateTime(Long.valueOf(System.currentTimeMillis()));
        user.setPassword(CodingUtils.md5(user.getEmail()));
        user.setSource(a2);
        user.setDeleted(false);
        this.userMapper.insertSelective(user);
        Organization organizationALLATORIxDEMO = ALLATORIxDEMO();
        ALLATORIxDEMO(organizationALLATORIxDEMO, ALLATORIxDEMO(organizationALLATORIxDEMO), user);
        ALLATORIxDEMO(user);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public ResultHolder exchangeOidc(String a, String a2) throws Exception {
        Map map = (Map) JSON.parseObject(this.authSourceService.getAuthSource(a2).getConfiguration(), Map.class);
        String a3 = ALLATORIxDEMO(a, map, a2);
        String a4 = this.qrCodeClient.exchange((String) map.get(AuthSourceRequest.ALLATORIxDEMO("\\\nL\u000b`\u0017O\u0016|\u000bE")), "Bearer " + a3, ProjectVersionService.ALLATORIxDEMO("P\u001fe\u0002~\u0018x\u0010p\u001ex\u0005\u007f"), MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
        Map map2 = (Map) JSON.parseObject(a4, Map.class);
        String strSubstring = StringUtils.substring((String) map2.get(AuthSourceRequest.ALLATORIxDEMO("Z\fK")), 0, 50);
        if (!StringUtils.isBlank(strSubstring)) {
            return login((String) map2.get(ProjectVersionService.ALLATORIxDEMO("t\u0007p\u0003}")), strSubstring, (String) map2.get(AuthSourceRequest.ALLATORIxDEMO("\u0017H\u0014L")), "", UserSource.OIDC.name());
        }
        throw new MSException("获取用户信息失败:" + a4);
    }

    private /* synthetic */ Project ALLATORIxDEMO(Organization a) {
        ProjectExample projectExample = new ProjectExample();
        projectExample.setOrderByClause(ProjectVersionService.ALLATORIxDEMO("r\u0018t\u000be\u000fN\u001ex\u0007tJP9R"));
        projectExample.createCriteria().andOrganizationIdEqualTo(a.getId()).andEnableEqualTo(true).andDeletedEqualTo(false);
        return (Project) this.projectMapper.selectByExample(projectExample).get(0);
    }

    private /* synthetic */ Organization ALLATORIxDEMO() {
        OrganizationExample organizationExample = new OrganizationExample();
        organizationExample.setOrderByClause(AuthSourceRequest.ALLATORIxDEMO("J\u000bL\u0018]\u001cv\r@\u0014LYh*j"));
        organizationExample.createCriteria().andEnableEqualTo(true).andDeletedEqualTo(false);
        return (Organization) this.organizationMapper.selectByExample(organizationExample).get(0);
    }

    public DingTalkInfoDTO getDingInfo(String a) {
        PlatformSource platformSourceSelectByPrimaryKey = this.platformSourceMapper.selectByPrimaryKey(a);
        DingTalkInfoDTO dingTalkInfoDTO = new DingTalkInfoDTO();
        BeanUtils.copyBean(dingTalkInfoDTO, (DingTalkCreator) JSON.parseObject(new String(platformSourceSelectByPrimaryKey.getConfig(), StandardCharsets.UTF_8), DingTalkCreator.class));
        dingTalkInfoDTO.setEnable(platformSourceSelectByPrimaryKey.getEnable());
        dingTalkInfoDTO.setValid(platformSourceSelectByPrimaryKey.getValid());
        return dingTalkInfoDTO;
    }

    public WeComInfoDTO getWeComInfo(String a) {
        PlatformSource platformSourceSelectByPrimaryKey = this.platformSourceMapper.selectByPrimaryKey(a);
        WeComInfoDTO weComInfoDTO = new WeComInfoDTO();
        BeanUtils.copyBean(weComInfoDTO, (WeComCreator) JSON.parseObject(new String(platformSourceSelectByPrimaryKey.getConfig(), StandardCharsets.UTF_8), WeComCreator.class));
        weComInfoDTO.setEnable(platformSourceSelectByPrimaryKey.getEnable());
        weComInfoDTO.setValid(platformSourceSelectByPrimaryKey.getValid());
        return weComInfoDTO;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    private /* synthetic */ String ALLATORIxDEMO(String a, Map a2, String a3) throws Exception {
        String str = (String) a2.get(ProjectVersionService.ALLATORIxDEMO("\u001e~\u0001t\u0004D\u0018}"));
        RestTemplate restTemplateM7ALLATORIxDEMO = m7ALLATORIxDEMO();
        HttpHeaders httpHeaders = new HttpHeaders();
        LinkedMultiValueMap linkedMultiValueMap = new LinkedMultiValueMap();
        linkedMultiValueMap.add(AuthSourceRequest.ALLATORIxDEMO("\u001aF\u001dL"), a);
        linkedMultiValueMap.add(ProjectVersionService.ALLATORIxDEMO("r\u0006x\u000f\u007f\u001eN\u0003u"), a2.get(AuthSourceRequest.ALLATORIxDEMO("\u001aE\u0010L\u0017]0M")));
        linkedMultiValueMap.add(ProjectVersionService.ALLATORIxDEMO("r\u0006x\u000f\u007f\u001eN\u0019t\tc\u000fe"), a2.get(AuthSourceRequest.ALLATORIxDEMO("\nL\u001a[\u001c]")));
        linkedMultiValueMap.add(ProjectVersionService.ALLATORIxDEMO("\rc\u000b\u007f\u001eN\u001eh\u001at"), AuthSourceRequest.ALLATORIxDEMO("\u0018\\\rA\u0016[\u0010S\u0018]\u0010F\u0017v\u001aF\u001dL"));
        linkedMultiValueMap.add(ProjectVersionService.ALLATORIxDEMO("\u0018t\u000ex\u0018t\te5d\u0018x"), a2.get(AuthSourceRequest.ALLATORIxDEMO("[\u001cM\u0010[\u001cJ\r|\u000bE")).toString().replace(ProjectVersionService.ALLATORIxDEMO("5\u0011p\u001fe\u0002X\u000el"), a3));
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String a4 = (String) restTemplateM7ALLATORIxDEMO.postForEntity(str, new HttpEntity(linkedMultiValueMap, httpHeaders), String.class, new Object[0]).getBody();
        String str2 = (String) ((Map) JSON.parseObject(a4, Map.class)).get(AuthSourceRequest.ALLATORIxDEMO("\u0018J\u001aL\nZ&]\u0016B\u001cG"));
        if (!StringUtils.isBlank(str2)) {
            return str2;
        }
        throw new MSException("获取access_token失败:" + a4);
    }

    private /* synthetic */ void ALLATORIxDEMO(User a) {
        this.operationLogService.add(LogDTOBuilder.builder().projectId(ProjectVersionService.ALLATORIxDEMO("9H9E/\\")).organizationId(AuthSourceRequest.ALLATORIxDEMO("*p*}<d")).type(OperationLogType.QRCODE.name()).module(ProjectVersionService.ALLATORIxDEMO("9T>E#_-N9H9E/\\5D9T8N9X$V&T")).method(HttpMethodConstants.POST.name()).path(AuthSourceRequest.ALLATORIxDEMO("VZ\nFVJ\u0018E\u0015K\u0018J\u0012\u0006\u000eL&J\u0016D")).sourceId(a.getId()).content(a.getName() + "(" + a.getEmail() + ")").originalValue(JSON.toJSONBytes(a)).createUser(a.getCreateUser()).build().getLogDTO());
    }
}
