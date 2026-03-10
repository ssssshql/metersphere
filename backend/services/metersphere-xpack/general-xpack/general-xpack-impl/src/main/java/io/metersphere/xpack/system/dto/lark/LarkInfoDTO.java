package io.metersphere.xpack.system.dto.lark;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Generated;

/* JADX INFO: compiled from: oa */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/dto/lark/LarkInfoDTO.class */
@Schema(description = "飞书")
public class LarkInfoDTO implements Serializable {

    @Schema(description = "是否开启")
    private Boolean enable = false;

    @Schema(description = "是否可用")
    private Boolean valid = false;

    @Schema(description = "应用密钥")
    private String appSecret;

    @Schema(description = "应用ID")
    private String agentId;

    @Generated
    public Boolean getValid() {
        return this.valid;
    }

    @Generated
    public String getAgentId() {
        return this.agentId;
    }

    @Generated
    public String getAppSecret() {
        return this.appSecret;
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof LarkInfoDTO;
    }

    @Generated
    public void setEnable(Boolean a) {
        this.enable = a;
    }

    @Generated
    public void setAppSecret(String a) {
        this.appSecret = a;
    }

    @Generated
    public void setValid(Boolean a) {
        this.valid = a;
    }

    @Generated
    public String toString() {
        return "LarkInfoDTO(agentId=" + getAgentId() + ", appSecret=" + getAppSecret() + ", enable=" + getEnable() + ", valid=" + getValid() + ")";
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
        String appSecret = getAppSecret();
        return (iHashCode3 * 59) + (appSecret == null ? 43 : appSecret.hashCode());
    }

    @Generated
    public void setAgentId(String a) {
        this.agentId = a;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof LarkInfoDTO)) {
            return false;
        }
        LarkInfoDTO larkInfoDTO = (LarkInfoDTO) a;
        if (!larkInfoDTO.canEqual(this)) {
            return false;
        }
        Boolean enable = getEnable();
        Boolean enable2 = larkInfoDTO.getEnable();
        if (enable == null) {
            if (enable2 != null) {
                return false;
            }
        } else if (!enable.equals(enable2)) {
            return false;
        }
        Boolean valid = getValid();
        Boolean valid2 = larkInfoDTO.getValid();
        if (valid == null) {
            if (valid2 != null) {
                return false;
            }
        } else if (!valid.equals(valid2)) {
            return false;
        }
        String agentId = getAgentId();
        String agentId2 = larkInfoDTO.getAgentId();
        if (agentId == null) {
            if (agentId2 != null) {
                return false;
            }
        } else if (!agentId.equals(agentId2)) {
            return false;
        }
        String appSecret = getAppSecret();
        Object a2 = larkInfoDTO.getAppSecret();
        return appSecret == null ? a2 == null : appSecret.equals(a2);
    }

    @Generated
    public LarkInfoDTO() {
    }

    @Generated
    public Boolean getEnable() {
        return this.enable;
    }
}
