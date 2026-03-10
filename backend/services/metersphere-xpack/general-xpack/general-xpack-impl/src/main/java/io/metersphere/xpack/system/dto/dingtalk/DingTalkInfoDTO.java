package io.metersphere.xpack.system.dto.dingtalk;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Generated;

/* JADX INFO: compiled from: da */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/dto/dingtalk/DingTalkInfoDTO.class */
@Schema(description = "钉钉")
public class DingTalkInfoDTO implements Serializable {

    @Schema(description = "应用key", requiredMode = Schema.RequiredMode.REQUIRED)
    private String appKey;

    @Schema(description = "应用ID")
    private String agentId;

    @Schema(description = "应用密钥")
    private String appSecret;

    @Schema(description = "是否开启")
    private Boolean enable = false;

    @Schema(description = "是否可用")
    private Boolean valid = false;

    @Generated
    public Boolean getEnable() {
        return this.enable;
    }

    @Generated
    public DingTalkInfoDTO() {
    }

    @Generated
    public String getAgentId() {
        return this.agentId;
    }

    @Generated
    public String getAppKey() {
        return this.appKey;
    }

    @Generated
    public String toString() {
        return "DingTalkInfoDTO(agentId=" + getAgentId() + ", appKey=" + getAppKey() + ", appSecret=" + getAppSecret() + ", enable=" + getEnable() + ", valid=" + getValid() + ")";
    }

    @Generated
    public void setAgentId(String a) {
        this.agentId = a;
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof DingTalkInfoDTO;
    }

    @Generated
    public void setAppKey(String a) {
        this.appKey = a;
    }

    @Generated
    public void setAppSecret(String a) {
        this.appSecret = a;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public int hashCode() {
        Boolean enable = getEnable();
        int iHashCode = (1 * 59) + (enable == null ? 43 : enable.hashCode());
        Boolean valid = getValid();
        int iHashCode2 = (iHashCode * 59) + (valid == null ? 43 : valid.hashCode());
        String agentId = getAgentId();
        int iHashCode3 = (iHashCode2 * 59) + (agentId == null ? 43 : agentId.hashCode());
        String appKey = getAppKey();
        int iHashCode4 = (iHashCode3 * 59) + (appKey == null ? 43 : appKey.hashCode());
        String appSecret = getAppSecret();
        return (iHashCode4 * 59) + (appSecret == null ? 43 : appSecret.hashCode());
    }

    @Generated
    public void setValid(Boolean a) {
        this.valid = a;
    }

    @Generated
    public void setEnable(Boolean a) {
        this.enable = a;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof DingTalkInfoDTO)) {
            return false;
        }
        DingTalkInfoDTO dingTalkInfoDTO = (DingTalkInfoDTO) a;
        if (!dingTalkInfoDTO.canEqual(this)) {
            return false;
        }
        Boolean enable = getEnable();
        Boolean enable2 = dingTalkInfoDTO.getEnable();
        if (enable == null) {
            if (enable2 != null) {
                return false;
            }
        } else if (!enable.equals(enable2)) {
            return false;
        }
        Boolean valid = getValid();
        Boolean valid2 = dingTalkInfoDTO.getValid();
        if (valid == null) {
            if (valid2 != null) {
                return false;
            }
        } else if (!valid.equals(valid2)) {
            return false;
        }
        String agentId = getAgentId();
        String agentId2 = dingTalkInfoDTO.getAgentId();
        if (agentId == null) {
            if (agentId2 != null) {
                return false;
            }
        } else if (!agentId.equals(agentId2)) {
            return false;
        }
        String appKey = getAppKey();
        String appKey2 = dingTalkInfoDTO.getAppKey();
        if (appKey == null) {
            if (appKey2 != null) {
                return false;
            }
        } else if (!appKey.equals(appKey2)) {
            return false;
        }
        String appSecret = getAppSecret();
        Object a2 = dingTalkInfoDTO.getAppSecret();
        return appSecret == null ? a2 == null : appSecret.equals(a2);
    }

    @Generated
    public Boolean getValid() {
        return this.valid;
    }

    @Generated
    public String getAppSecret() {
        return this.appSecret;
    }
}
