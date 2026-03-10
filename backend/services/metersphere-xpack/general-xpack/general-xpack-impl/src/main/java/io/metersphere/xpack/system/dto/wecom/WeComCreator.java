package io.metersphere.xpack.system.dto.wecom;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Generated;

/* JADX INFO: compiled from: sa */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/dto/wecom/WeComCreator.class */
public class WeComCreator implements Serializable {

    @Schema(description = "应用ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String agentId;

    @Schema(description = "应用密钥", requiredMode = Schema.RequiredMode.REQUIRED)
    private String appSecret;

    @Schema(description = "企业id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String corpId;

    @Generated
    public String getAppSecret() {
        return this.appSecret;
    }

    @Generated
    public String getAgentId() {
        return this.agentId;
    }

    @Generated
    public String getCorpId() {
        return this.corpId;
    }

    @Generated
    public void setAppSecret(String a) {
        this.appSecret = a;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public int hashCode() {
        String corpId = getCorpId();
        int iHashCode = (1 * 59) + (corpId == null ? 43 : corpId.hashCode());
        String agentId = getAgentId();
        int iHashCode2 = (iHashCode * 59) + (agentId == null ? 43 : agentId.hashCode());
        String appSecret = getAppSecret();
        return (iHashCode2 * 59) + (appSecret == null ? 43 : appSecret.hashCode());
    }

    @Generated
    public String toString() {
        return "WeComCreator(corpId=" + getCorpId() + ", agentId=" + getAgentId() + ", appSecret=" + getAppSecret() + ")";
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof WeComCreator)) {
            return false;
        }
        WeComCreator a2 = (WeComCreator) a;
        if (!a2.canEqual(this)) {
            return false;
        }
        String corpId = getCorpId();
        String corpId2 = a2.getCorpId();
        if (corpId == null) {
            if (corpId2 != null) {
                return false;
            }
        } else if (!corpId.equals(corpId2)) {
            return false;
        }
        String agentId = getAgentId();
        String agentId2 = a2.getAgentId();
        if (agentId == null) {
            if (agentId2 != null) {
                return false;
            }
        } else if (!agentId.equals(agentId2)) {
            return false;
        }
        String appSecret = getAppSecret();
        String appSecret2 = a2.getAppSecret();
        return appSecret == null ? appSecret2 == null : appSecret.equals(appSecret2);
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof WeComCreator;
    }

    @Generated
    public void setCorpId(String a) {
        this.corpId = a;
    }

    @Generated
    public WeComCreator() {
    }

    @Generated
    public void setAgentId(String a) {
        this.agentId = a;
    }
}
