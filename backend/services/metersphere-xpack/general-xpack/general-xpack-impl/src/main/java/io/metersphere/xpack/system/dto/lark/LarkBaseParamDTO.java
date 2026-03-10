package io.metersphere.xpack.system.dto.lark;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Generated;

/* JADX INFO: compiled from: ba */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/dto/lark/LarkBaseParamDTO.class */
@Schema(description = "飞书基础信息")
public class LarkBaseParamDTO {

    @Schema(description = "应用密钥", requiredMode = Schema.RequiredMode.REQUIRED)
    private String app_secret;

    @Schema(description = "应用ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String app_id;

    @Generated
    public String getApp_secret() {
        return this.app_secret;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof LarkBaseParamDTO)) {
            return false;
        }
        LarkBaseParamDTO a2 = (LarkBaseParamDTO) a;
        if (!a2.canEqual(this)) {
            return false;
        }
        String app_id = getApp_id();
        String app_id2 = a2.getApp_id();
        if (app_id == null) {
            if (app_id2 != null) {
                return false;
            }
        } else if (!app_id.equals(app_id2)) {
            return false;
        }
        String app_secret = getApp_secret();
        String app_secret2 = a2.getApp_secret();
        return app_secret == null ? app_secret2 == null : app_secret.equals(app_secret2);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public int hashCode() {
        String app_id = getApp_id();
        int iHashCode = (1 * 59) + (app_id == null ? 43 : app_id.hashCode());
        String app_secret = getApp_secret();
        return (iHashCode * 59) + (app_secret == null ? 43 : app_secret.hashCode());
    }

    @Generated
    public void setApp_secret(String a) {
        this.app_secret = a;
    }

    @Generated
    public String toString() {
        return "LarkBaseParamDTO(app_id=" + getApp_id() + ", app_secret=" + getApp_secret() + ")";
    }

    @Generated
    public LarkBaseParamDTO() {
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof LarkBaseParamDTO;
    }

    @Generated
    public void setApp_id(String a) {
        this.app_id = a;
    }

    @Generated
    public String getApp_id() {
        return this.app_id;
    }
}
