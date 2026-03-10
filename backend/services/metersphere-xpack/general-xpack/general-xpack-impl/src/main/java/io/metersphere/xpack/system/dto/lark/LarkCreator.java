package io.metersphere.xpack.system.dto.lark;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Generated;

/* JADX INFO: compiled from: la */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/dto/lark/LarkCreator.class */
public class LarkCreator implements Serializable {

    @Schema(description = "应用ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String agentId;

    @Schema(description = "应用密钥", requiredMode = Schema.RequiredMode.REQUIRED)
    private String appSecret;

    @Generated
    public String getAppSecret() {
        return this.appSecret;
    }

    @Generated
    public String getAgentId() {
        return this.agentId;
    }

    @Generated
    public void setAgentId(String a) {
        this.agentId = a;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public int hashCode() {
        String agentId = getAgentId();
        int iHashCode = (1 * 59) + (agentId == null ? 43 : agentId.hashCode());
        String appSecret = getAppSecret();
        return (iHashCode * 59) + (appSecret == null ? 43 : appSecret.hashCode());
    }

    @Generated
    public LarkCreator() {
    }

    @Generated
    public String toString() {
        return "LarkCreator(agentId=" + getAgentId() + ", appSecret=" + getAppSecret() + ")";
    }

    @Generated
    public void setAppSecret(String a) {
        this.appSecret = a;
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof LarkCreator;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof LarkCreator)) {
            return false;
        }
        LarkCreator a2 = (LarkCreator) a;
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
        String appSecret = getAppSecret();
        String appSecret2 = a2.getAppSecret();
        return appSecret == null ? appSecret2 == null : appSecret.equals(appSecret2);
    }
}
