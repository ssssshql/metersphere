package io.metersphere.xpack.system.dto.wecom;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Generated;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/dto/wecom/WeComInfoDTO.class */
@Schema(description = "企微信息")
public class WeComInfoDTO implements Serializable {

    @Schema(description = "应用ID")
    private String agentId;

    @Schema(description = "应用密钥")
    private String appSecret;

    @Schema(description = "企业ID")
    private String corpId;

    @Schema(description = "是否开启")
    private Boolean enable = false;

    @Schema(description = "是否可用")
    private Boolean valid = false;

    @Generated
    public Boolean getEnable() {
        return this.enable;
    }

    @Generated
    public WeComInfoDTO() {
    }

    @Generated
    public String getCorpId() {
        return this.corpId;
    }

    @Generated
    public String getAgentId() {
        return this.agentId;
    }

    @Generated
    public void setValid(Boolean a) {
        this.valid = a;
    }

    @Generated
    public String toString() {
        return "WeComInfoDTO(corpId=" + getCorpId() + ", agentId=" + getAgentId() + ", appSecret=" + getAppSecret() + ", enable=" + getEnable() + ", valid=" + getValid() + ")";
    }

    @Generated
    public void setEnable(Boolean a) {
        this.enable = a;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public int hashCode() {
        Boolean enable = getEnable();
        int iHashCode = (1 * 59) + (enable == null ? 43 : enable.hashCode());
        Boolean valid = getValid();
        int iHashCode2 = (iHashCode * 59) + (valid == null ? 43 : valid.hashCode());
        String corpId = getCorpId();
        int iHashCode3 = (iHashCode2 * 59) + (corpId == null ? 43 : corpId.hashCode());
        String agentId = getAgentId();
        int iHashCode4 = (iHashCode3 * 59) + (agentId == null ? 43 : agentId.hashCode());
        String appSecret = getAppSecret();
        return (iHashCode4 * 59) + (appSecret == null ? 43 : appSecret.hashCode());
    }

    @Generated
    public void setAgentId(String a) {
        this.agentId = a;
    }

    @Generated
    public void setAppSecret(String a) {
        this.appSecret = a;
    }

    @Generated
    public void setCorpId(String a) {
        this.corpId = a;
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof WeComInfoDTO;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof WeComInfoDTO)) {
            return false;
        }
        WeComInfoDTO weComInfoDTO = (WeComInfoDTO) a;
        if (!weComInfoDTO.canEqual(this)) {
            return false;
        }
        Boolean enable = getEnable();
        Boolean enable2 = weComInfoDTO.getEnable();
        if (enable == null) {
            if (enable2 != null) {
                return false;
            }
        } else if (!enable.equals(enable2)) {
            return false;
        }
        Boolean valid = getValid();
        Boolean valid2 = weComInfoDTO.getValid();
        if (valid == null) {
            if (valid2 != null) {
                return false;
            }
        } else if (!valid.equals(valid2)) {
            return false;
        }
        String corpId = getCorpId();
        String corpId2 = weComInfoDTO.getCorpId();
        if (corpId == null) {
            if (corpId2 != null) {
                return false;
            }
        } else if (!corpId.equals(corpId2)) {
            return false;
        }
        String agentId = getAgentId();
        String agentId2 = weComInfoDTO.getAgentId();
        if (agentId == null) {
            if (agentId2 != null) {
                return false;
            }
        } else if (!agentId.equals(agentId2)) {
            return false;
        }
        String appSecret = getAppSecret();
        Object a2 = weComInfoDTO.getAppSecret();
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
