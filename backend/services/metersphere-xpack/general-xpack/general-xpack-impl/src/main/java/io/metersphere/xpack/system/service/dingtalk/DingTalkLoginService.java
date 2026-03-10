package io.metersphere.xpack.system.service.dingtalk;

import io.metersphere.sdk.exception.MSException;
import io.metersphere.sdk.util.BeanUtils;
import io.metersphere.sdk.util.JSON;
import io.metersphere.system.domain.PlatformSource;
import io.metersphere.system.mapper.PlatformSourceMapper;
import io.metersphere.xpack.client.QrCodeClient;
import io.metersphere.xpack.project.service.ProjectVersionService;
import io.metersphere.xpack.system.dto.dingtalk.DingTalkCreator;
import io.metersphere.xpack.system.dto.dingtalk.DingTalkInfoDTO;
import io.metersphere.xpack.system.dto.dingtalk.DingTalkTokenParamDTO;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* JADX INFO: compiled from: w */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/dingtalk/DingTalkLoginService.class */
@Transactional(rollbackFor = {Exception.class})
@Service
public class DingTalkLoginService {

    @Resource
    private PlatformSourceMapper platformSourceMapper;
    private static final String KEY = "DING_TALK";
    private static final String TOKEN_URL = "https://api.dingtalk.com/v1.0/oauth2/accessToken";

    @Resource
    private QrCodeClient qrCodeClient;

    public void save(DingTalkInfoDTO a) {
        this.platformSourceMapper.deleteByPrimaryKey(KEY);
        DingTalkCreator dingTalkCreator = new DingTalkCreator();
        BeanUtils.copyBean(dingTalkCreator, a);
        PlatformSource platformSource = new PlatformSource();
        platformSource.setPlatform(KEY);
        platformSource.setConfig(JSON.toJSONString(dingTalkCreator).getBytes(StandardCharsets.UTF_8));
        platformSource.setEnable(a.getEnable());
        platformSource.setValid(a.getValid());
        this.platformSourceMapper.insert(platformSource);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public DingTalkInfoDTO getInfo() {
        PlatformSource platformSourceSelectByPrimaryKey = this.platformSourceMapper.selectByPrimaryKey(KEY);
        if (platformSourceSelectByPrimaryKey == null) {
            return new DingTalkInfoDTO();
        }
        DingTalkInfoDTO dingTalkInfoDTO = new DingTalkInfoDTO();
        DingTalkCreator dingTalkCreator = (DingTalkCreator) JSON.parseObject(new String(platformSourceSelectByPrimaryKey.getConfig(), StandardCharsets.UTF_8), DingTalkCreator.class);
        dingTalkCreator.setAppSecret(null);
        BeanUtils.copyBean(dingTalkInfoDTO, dingTalkCreator);
        dingTalkInfoDTO.setEnable(platformSourceSelectByPrimaryKey.getEnable());
        dingTalkInfoDTO.setValid(platformSourceSelectByPrimaryKey.getValid());
        return dingTalkInfoDTO;
    }

    public void switchEnable(boolean z) {
        PlatformSource platformSourceSelectByPrimaryKey = this.platformSourceMapper.selectByPrimaryKey(KEY);
        if (platformSourceSelectByPrimaryKey != null) {
            platformSourceSelectByPrimaryKey.setEnable(Boolean.valueOf(z));
            this.platformSourceMapper.updateByPrimaryKey(platformSourceSelectByPrimaryKey);
        }
    }

    public void validate(DingTalkInfoDTO a) {
        if (!StringUtils.isNotBlank(generateToken(a))) {
            return;
        }
        changeValid(true);
    }

    public void changeValid(Boolean a) {
        PlatformSource platformSourceSelectByPrimaryKey = this.platformSourceMapper.selectByPrimaryKey(KEY);
        if (platformSourceSelectByPrimaryKey != null) {
            platformSourceSelectByPrimaryKey.setValid(a);
            this.platformSourceMapper.updateByPrimaryKey(platformSourceSelectByPrimaryKey);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public String generateToken(DingTalkInfoDTO dingTalkInfoDTO) throws MSException {
        DingTalkInfoDTO detailInfo = dingTalkInfoDTO;
        if (StringUtils.isBlank(detailInfo.getAppSecret())) {
            detailInfo = getDetailInfo();
        }
        DingTalkInfoDTO dingTalkInfoDTO2 = detailInfo;
        String appKey = dingTalkInfoDTO2.getAppKey();
        String appSecret = dingTalkInfoDTO2.getAppSecret();
        DingTalkTokenParamDTO dingTalkTokenParamDTO = new DingTalkTokenParamDTO();
        dingTalkTokenParamDTO.setAppKey(appKey);
        dingTalkTokenParamDTO.setAppSecret(appSecret);
        Map map = JSON.parseMap(this.qrCodeClient.postExchange(TOKEN_URL, null, null, detailInfo, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON));
        if (!ObjectUtils.isNotEmpty(map.get(ProjectVersionService.ALLATORIxDEMO("t\u0018c\t~\u000et"))) || Integer.parseInt(map.get(ProjectVersionService.ALLATORIxDEMO("t\u0018c\t~\u000et")).toString()) == 0) {
            return map.get(ProjectVersionService.ALLATORIxDEMO("p\tr\u000fb\u0019E\u0005z\u000f\u007f")).toString();
        }
        throw new MSException("获取accessToken失败:" + String.valueOf(map.get(ProjectVersionService.ALLATORIxDEMO("\u000fc\u0018|\u0019v"))));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public DingTalkInfoDTO getDetailInfo() {
        PlatformSource platformSourceSelectByPrimaryKey = this.platformSourceMapper.selectByPrimaryKey(KEY);
        if (platformSourceSelectByPrimaryKey == null) {
            return new DingTalkInfoDTO();
        }
        DingTalkInfoDTO dingTalkInfoDTO = new DingTalkInfoDTO();
        BeanUtils.copyBean(dingTalkInfoDTO, (DingTalkCreator) JSON.parseObject(new String(platformSourceSelectByPrimaryKey.getConfig(), StandardCharsets.UTF_8), DingTalkCreator.class));
        dingTalkInfoDTO.setEnable(platformSourceSelectByPrimaryKey.getEnable());
        dingTalkInfoDTO.setValid(platformSourceSelectByPrimaryKey.getValid());
        return dingTalkInfoDTO;
    }
}
