package io.metersphere.xpack.system.service.larksuite;

import io.metersphere.sdk.exception.MSException;
import io.metersphere.sdk.util.BeanUtils;
import io.metersphere.sdk.util.JSON;
import io.metersphere.system.domain.PlatformSource;
import io.metersphere.system.mapper.PlatformSourceMapper;
import io.metersphere.xpack.client.QrCodeClient;
import io.metersphere.xpack.system.dto.lark.LarkBaseParamDTO;
import io.metersphere.xpack.system.dto.lark.LarkCreator;
import io.metersphere.xpack.system.dto.lark.LarkInfoDTO;
import io.metersphere.xpack.system.dto.request.AuthSourceRequest;
import io.metersphere.xpack.system.ldap.p000vo.LdapRequest;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* JADX INFO: compiled from: b */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/larksuite/LarkSuiteLoginService.class */
@Transactional(rollbackFor = {Exception.class})
@Service
public class LarkSuiteLoginService {
    private static final String KEY = "LARK_SUITE";

    @Resource
    private PlatformSourceMapper platformSourceMapper;

    @Resource
    private QrCodeClient qrCodeClient;
    private static final String TENANT_TOKEN_URL = "https://open.larksuite.com/open-apis/auth/v3/tenant_access_token/internal";

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    private /* synthetic */ String ALLATORIxDEMO(LarkInfoDTO larkInfoDTO) throws MSException {
        LarkInfoDTO detailInfo = larkInfoDTO;
        if (StringUtils.isBlank(detailInfo.getAppSecret())) {
            detailInfo = getDetailInfo();
        }
        LarkInfoDTO larkInfoDTO2 = detailInfo;
        String agentId = larkInfoDTO2.getAgentId();
        String appSecret = larkInfoDTO2.getAppSecret();
        LarkBaseParamDTO larkBaseParamDTO = new LarkBaseParamDTO();
        larkBaseParamDTO.setApp_id(agentId);
        larkBaseParamDTO.setApp_secret(appSecret);
        Map map = JSON.parseMap(this.qrCodeClient.postExchange(TENANT_TOKEN_URL, null, null, larkBaseParamDTO, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON));
        if (ObjectUtils.isNotEmpty(map.get(AuthSourceRequest.ALLATORIxDEMO("\u001aF\u001dL"))) && Integer.parseInt(map.get(LdapRequest.ALLATORIxDEMO("*v-|")).toString()) > 0) {
            throw new MSException("获取accessToken失败:" + String.valueOf(map.get(AuthSourceRequest.ALLATORIxDEMO("D\nN"))));
        }
        return map.get(LdapRequest.ALLATORIxDEMO("m,w(w=F(z*|:j\u0016m&r,w")).toString();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public LarkInfoDTO getInfo() {
        PlatformSource platformSourceSelectByPrimaryKey = this.platformSourceMapper.selectByPrimaryKey(KEY);
        if (platformSourceSelectByPrimaryKey == null) {
            return new LarkInfoDTO();
        }
        LarkInfoDTO larkInfoDTO = new LarkInfoDTO();
        LarkCreator larkCreator = (LarkCreator) JSON.parseObject(new String(platformSourceSelectByPrimaryKey.getConfig(), StandardCharsets.UTF_8), LarkCreator.class);
        larkCreator.setAppSecret(null);
        BeanUtils.copyBean(larkInfoDTO, larkCreator);
        larkInfoDTO.setEnable(platformSourceSelectByPrimaryKey.getEnable());
        larkInfoDTO.setValid(platformSourceSelectByPrimaryKey.getValid());
        return larkInfoDTO;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public LarkInfoDTO getDetailInfo() {
        PlatformSource platformSourceSelectByPrimaryKey = this.platformSourceMapper.selectByPrimaryKey(KEY);
        if (platformSourceSelectByPrimaryKey != null) {
            LarkInfoDTO larkInfoDTO = new LarkInfoDTO();
            BeanUtils.copyBean(larkInfoDTO, (LarkCreator) JSON.parseObject(new String(platformSourceSelectByPrimaryKey.getConfig(), StandardCharsets.UTF_8), LarkCreator.class));
            larkInfoDTO.setEnable(platformSourceSelectByPrimaryKey.getEnable());
            larkInfoDTO.setValid(platformSourceSelectByPrimaryKey.getValid());
            return larkInfoDTO;
        }
        return new LarkInfoDTO();
    }

    public void save(LarkInfoDTO a) {
        this.platformSourceMapper.deleteByPrimaryKey(KEY);
        LarkCreator larkCreator = new LarkCreator();
        BeanUtils.copyBean(larkCreator, a);
        PlatformSource platformSource = new PlatformSource();
        platformSource.setPlatform(KEY);
        platformSource.setConfig(JSON.toJSONString(larkCreator).getBytes(StandardCharsets.UTF_8));
        platformSource.setEnable(a.getEnable());
        platformSource.setValid(a.getValid());
        this.platformSourceMapper.insert(platformSource);
    }

    public void changeValid(Boolean a) {
        PlatformSource platformSourceSelectByPrimaryKey = this.platformSourceMapper.selectByPrimaryKey(KEY);
        if (platformSourceSelectByPrimaryKey == null) {
            return;
        }
        platformSourceSelectByPrimaryKey.setValid(a);
        this.platformSourceMapper.updateByPrimaryKey(platformSourceSelectByPrimaryKey);
    }

    public void validate(LarkInfoDTO a) {
        if (!StringUtils.isNotBlank(ALLATORIxDEMO(a))) {
            return;
        }
        changeValid(true);
    }

    public void switchEnable(boolean z) {
        PlatformSource platformSourceSelectByPrimaryKey = this.platformSourceMapper.selectByPrimaryKey(KEY);
        if (platformSourceSelectByPrimaryKey != null) {
            platformSourceSelectByPrimaryKey.setEnable(Boolean.valueOf(z));
            this.platformSourceMapper.updateByPrimaryKey(platformSourceSelectByPrimaryKey);
        }
    }
}
