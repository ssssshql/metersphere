package io.metersphere.xpack.system.service.lark;

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
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* JADX INFO: compiled from: p */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/lark/LarkLoginService.class */
@Transactional(rollbackFor = {Exception.class})
@Service
public class LarkLoginService {

    @Resource
    private QrCodeClient qrCodeClient;
    private static final String KEY = "LARK";

    @Resource
    private PlatformSourceMapper platformSourceMapper;
    private static final String TENANT_TOKEN_URL = "https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal";

    public static String ALLATORIxDEMO(String a) {
        int i = (5 << 3) ^ (2 ^ 5);
        int i2 = (3 << 3) ^ 2;
        int i3 = ((2 ^ 5) << 4) ^ ((2 << 2) ^ 3);
        String str = a;
        int length = str.length();
        char[] cArr = new char[length];
        int i4 = length - 1;
        int i5 = i4;
        int i6 = i4;
        while (i6 >= 0) {
            int i7 = i5;
            int i8 = i5 - 1;
            cArr[i7] = (char) (str.charAt(i7) ^ i2);
            if (i8 < 0) {
                break;
            }
            i5 = i8 - 1;
            cArr[i8] = (char) (str.charAt(i8) ^ i3);
            i6 = i5;
        }
        return new String(cArr);
    }

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
        if (ObjectUtils.isNotEmpty(map.get(ALLATORIxDEMO("\u0018u\u001f\u007f"))) && Integer.parseInt(map.get(AuthSourceRequest.ALLATORIxDEMO("\u001aF\u001dL")).toString()) > 0) {
            throw new MSException("获取accessToken失败:" + String.valueOf(map.get(ALLATORIxDEMO("w\b}"))));
        }
        return map.get(AuthSourceRequest.ALLATORIxDEMO("]\u001cG\u0018G\rv\u0018J\u001aL\nZ&]\u0016B\u001cG")).toString();
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
