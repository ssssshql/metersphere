package io.metersphere.xpack.system.dto.lark;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Generated;

/* JADX INFO: compiled from: ha */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/dto/lark/LarkTokenParamDTO.class */
@Schema(description = "飞书")
public class LarkTokenParamDTO {

    @Schema(description = "grantType")
    public String grant_type;

    @Schema(description = "code")
    public String code;

    @Generated
    public String getGrant_type() {
        return this.grant_type;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof LarkTokenParamDTO)) {
            return false;
        }
        LarkTokenParamDTO a2 = (LarkTokenParamDTO) a;
        if (!a2.canEqual(this)) {
            return false;
        }
        String code = getCode();
        String code2 = a2.getCode();
        if (code == null) {
            if (code2 != null) {
                return false;
            }
        } else if (!code.equals(code2)) {
            return false;
        }
        String grant_type = getGrant_type();
        String grant_type2 = a2.getGrant_type();
        return grant_type == null ? grant_type2 == null : grant_type.equals(grant_type2);
    }

    @Generated
    public void setGrant_type(String a) {
        this.grant_type = a;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public int hashCode() {
        String code = getCode();
        int iHashCode = (1 * 59) + (code == null ? 43 : code.hashCode());
        String grant_type = getGrant_type();
        return (iHashCode * 59) + (grant_type == null ? 43 : grant_type.hashCode());
    }

    @Generated
    public LarkTokenParamDTO() {
    }

    @Generated
    public String toString() {
        return "LarkTokenParamDTO(code=" + getCode() + ", grant_type=" + getGrant_type() + ")";
    }

    @Generated
    public void setCode(String a) {
        this.code = a;
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof LarkTokenParamDTO;
    }

    @Generated
    public String getCode() {
        return this.code;
    }
}
