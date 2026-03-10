package io.metersphere.xpack.system.dto.dingtalk;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Generated;

/* JADX INFO: compiled from: ta */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/dto/dingtalk/DingTalkBaseParamDTO.class */
@Schema(description = "钉钉基础信息")
public class DingTalkBaseParamDTO {

    @Schema(description = "应用key", requiredMode = Schema.RequiredMode.REQUIRED)
    private String appKey;

    @Schema(description = "应用密钥", requiredMode = Schema.RequiredMode.REQUIRED)
    private String appSecret;

    @Generated
    public String getAppSecret() {
        return this.appSecret;
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof DingTalkBaseParamDTO;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public int hashCode() {
        String appKey = getAppKey();
        int iHashCode = (1 * 59) + (appKey == null ? 43 : appKey.hashCode());
        String appSecret = getAppSecret();
        return (iHashCode * 59) + (appSecret == null ? 43 : appSecret.hashCode());
    }

    @Generated
    public String toString() {
        return "DingTalkBaseParamDTO(appKey=" + getAppKey() + ", appSecret=" + getAppSecret() + ")";
    }

    @Generated
    public void setAppSecret(String a) {
        this.appSecret = a;
    }

    @Generated
    public void setAppKey(String a) {
        this.appKey = a;
    }

    @Generated
    public DingTalkBaseParamDTO() {
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof DingTalkBaseParamDTO)) {
            return false;
        }
        DingTalkBaseParamDTO a2 = (DingTalkBaseParamDTO) a;
        if (!a2.canEqual(this)) {
            return false;
        }
        String appKey = getAppKey();
        String appKey2 = a2.getAppKey();
        if (appKey == null) {
            if (appKey2 != null) {
                return false;
            }
        } else if (!appKey.equals(appKey2)) {
            return false;
        }
        String appSecret = getAppSecret();
        String appSecret2 = a2.getAppSecret();
        return appSecret == null ? appSecret2 == null : appSecret.equals(appSecret2);
    }

    @Generated
    public String getAppKey() {
        return this.appKey;
    }
}
