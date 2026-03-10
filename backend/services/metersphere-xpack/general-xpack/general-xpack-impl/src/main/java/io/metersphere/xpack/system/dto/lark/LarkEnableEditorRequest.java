package io.metersphere.xpack.system.dto.lark;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Generated;

/* JADX INFO: compiled from: ja */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/dto/lark/LarkEnableEditorRequest.class */
@Schema(description = "开启状态切换器")
public class LarkEnableEditorRequest implements Serializable {

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean enable;

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Generated
    public int hashCode() {
        return (1 * 59) + (isEnable() ? 79 : 97);
    }

    @Generated
    public LarkEnableEditorRequest() {
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof LarkEnableEditorRequest)) {
            return false;
        }
        LarkEnableEditorRequest a2 = (LarkEnableEditorRequest) a;
        return a2.canEqual(this) && isEnable() == a2.isEnable();
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof LarkEnableEditorRequest;
    }

    @Generated
    public void setEnable(boolean z) {
        this.enable = z;
    }

    @Generated
    public String toString() {
        return "LarkEnableEditorRequest(enable=" + isEnable() + ")";
    }

    @Generated
    public boolean isEnable() {
        return this.enable;
    }
}
