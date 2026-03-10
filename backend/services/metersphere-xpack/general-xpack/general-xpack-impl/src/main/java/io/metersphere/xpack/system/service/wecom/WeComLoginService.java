package io.metersphere.xpack.system.service.wecom;

import io.metersphere.sdk.exception.MSException;
import io.metersphere.sdk.util.BeanUtils;
import io.metersphere.sdk.util.JSON;
import io.metersphere.system.domain.PlatformSource;
import io.metersphere.system.mapper.PlatformSourceMapper;
import io.metersphere.xpack.client.QrCodeClient;
import io.metersphere.xpack.system.dto.wecom.WeComCreator;
import io.metersphere.xpack.system.dto.wecom.WeComInfoDTO;
import io.metersphere.xpack.system.ldap.p000vo.LdapRequest;
import io.metersphere.xpack.system.service.lark.LarkLoginService;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* JADX INFO: compiled from: l */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/wecom/WeComLoginService.class */
@Transactional(rollbackFor = {Exception.class})
@Service
public class WeComLoginService {

    @Resource
    private PlatformSourceMapper platformSourceMapper;
    private static final String TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
    private static final String KEY = "WE_COM";

    @Resource
    private QrCodeClient qrCodeClient;

    public void switchEnable(boolean z) {
        PlatformSource platformSourceSelectByPrimaryKey = this.platformSourceMapper.selectByPrimaryKey(KEY);
        if (platformSourceSelectByPrimaryKey == null) {
            return;
        }
        platformSourceSelectByPrimaryKey.setEnable(Boolean.valueOf(z));
        this.platformSourceMapper.updateByPrimaryKey(platformSourceSelectByPrimaryKey);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public WeComInfoDTO getInfo() {
        PlatformSource platformSourceSelectByPrimaryKey = this.platformSourceMapper.selectByPrimaryKey(KEY);
        if (platformSourceSelectByPrimaryKey == null) {
            return new WeComInfoDTO();
        }
        WeComInfoDTO weComInfoDTO = new WeComInfoDTO();
        WeComCreator weComCreator = (WeComCreator) JSON.parseObject(new String(platformSourceSelectByPrimaryKey.getConfig(), StandardCharsets.UTF_8), WeComCreator.class);
        weComCreator.setAppSecret(null);
        BeanUtils.copyBean(weComInfoDTO, weComCreator);
        weComInfoDTO.setEnable(platformSourceSelectByPrimaryKey.getEnable());
        weComInfoDTO.setValid(platformSourceSelectByPrimaryKey.getValid());
        return weComInfoDTO;
    }

    public void changeValid(Boolean a) {
        PlatformSource platformSourceSelectByPrimaryKey = this.platformSourceMapper.selectByPrimaryKey(KEY);
        if (platformSourceSelectByPrimaryKey != null) {
            platformSourceSelectByPrimaryKey.setValid(a);
            this.platformSourceMapper.updateByPrimaryKey(platformSourceSelectByPrimaryKey);
        }
    }

    public void validate(WeComInfoDTO a) {
        if (!StringUtils.isNotBlank(ALLATORIxDEMO(a))) {
            return;
        }
        changeValid(true);
    }

    public void save(WeComInfoDTO a) {
        this.platformSourceMapper.deleteByPrimaryKey(KEY);
        WeComCreator weComCreator = new WeComCreator();
        BeanUtils.copyBean(weComCreator, a);
        PlatformSource platformSource = new PlatformSource();
        platformSource.setPlatform(KEY);
        platformSource.setConfig(JSON.toJSONString(weComCreator).getBytes(StandardCharsets.UTF_8));
        platformSource.setEnable(a.getEnable());
        platformSource.setValid(a.getValid());
        this.platformSourceMapper.insert(platformSource);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public WeComInfoDTO getDetailInfo() {
        PlatformSource platformSourceSelectByPrimaryKey = this.platformSourceMapper.selectByPrimaryKey(KEY);
        if (platformSourceSelectByPrimaryKey != null) {
            WeComInfoDTO weComInfoDTO = new WeComInfoDTO();
            BeanUtils.copyBean(weComInfoDTO, (WeComCreator) JSON.parseObject(new String(platformSourceSelectByPrimaryKey.getConfig(), StandardCharsets.UTF_8), WeComCreator.class));
            weComInfoDTO.setEnable(platformSourceSelectByPrimaryKey.getEnable());
            weComInfoDTO.setValid(platformSourceSelectByPrimaryKey.getValid());
            return weComInfoDTO;
        }
        return new WeComInfoDTO();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    private /* synthetic */ String ALLATORIxDEMO(WeComInfoDTO weComInfoDTO) throws MSException {
        WeComInfoDTO detailInfo = weComInfoDTO;
        if (StringUtils.isBlank(detailInfo.getAppSecret())) {
            detailInfo = getDetailInfo();
        }
        WeComInfoDTO weComInfoDTO2 = detailInfo;
        Map map = JSON.parseMap(this.qrCodeClient.get(String.format(TOKEN_URL, weComInfoDTO2.getCorpId(), weComInfoDTO2.getAppSecret())));
        if (!ObjectUtils.isNotEmpty(map.get(LarkLoginService.ALLATORIxDEMO("\u007f\th\u0018u\u001f\u007f"))) || Integer.parseInt(map.get(LdapRequest.ALLATORIxDEMO("|;k*v-|")).toString()) == 0) {
            return map.get(LdapRequest.ALLATORIxDEMO("(z*|:j\u0016m&r,w")).toString();
        }
        throw new MSException("获取accessToken失败:" + String.valueOf(map.get(LarkLoginService.ALLATORIxDEMO("\u001eh\tw\b}"))));
    }
}
