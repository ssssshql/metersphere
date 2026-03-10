package io.metersphere.xpack.system.dto.dingtalk;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Generated;

/* JADX INFO: compiled from: va */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/dto/dingtalk/DingTalkCreator.class */
public class DingTalkCreator implements Serializable {

    @Schema(description = "应用key", requiredMode = Schema.RequiredMode.REQUIRED)
    private String appKey;

    @Schema(description = "应用ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String agentId;

    @Schema(description = "应用密钥", requiredMode = Schema.RequiredMode.REQUIRED)
    private String appSecret;

    @Generated
    public String getAppSecret() {
        return this.appSecret;
    }

    @Generated
    public String getAppKey() {
        return this.appKey;
    }

    @Generated
    public String getAgentId() {
        return this.agentId;
    }

    @Generated
    public String toString() {
        return "DingTalkCreator(agentId=" + getAgentId() + ", appKey=" + getAppKey() + ", appSecret=" + getAppSecret() + ")";
    }

    @Generated
    public DingTalkCreator() {
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public int hashCode() {
        String agentId = getAgentId();
        int iHashCode = (1 * 59) + (agentId == null ? 43 : agentId.hashCode());
        String appKey = getAppKey();
        int iHashCode2 = (iHashCode * 59) + (appKey == null ? 43 : appKey.hashCode());
        String appSecret = getAppSecret();
        return (iHashCode2 * 59) + (appSecret == null ? 43 : appSecret.hashCode());
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof DingTalkCreator;
    }

    @Generated
    public void setAgentId(String a) {
        this.agentId = a;
    }

    @Generated
    public void setAppSecret(String a) {
        this.appSecret = a;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof DingTalkCreator)) {
            return false;
        }
        DingTalkCreator a2 = (DingTalkCreator) a;
        if (!a2.canEqual(this)) {
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
    public void setAppKey(String a) {
        this.appKey = a;
    }
}
