package io.metersphere.xpack.system.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Generated;

/* JADX INFO: compiled from: fa */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/dto/request/AuthSourceStatusRequest.class */
public class AuthSourceStatusRequest implements Serializable {
    private static final long serialVersionUID = 1;

    @Schema(description = "是否禁用")
    private Boolean enable;

    /* JADX INFO: renamed from: id */
    @Schema(description = "id")
    private String f4id;

    @Generated
    public Boolean getEnable() {
        return this.enable;
    }

    @Generated
    public void setId(String a) {
        this.f4id = a;
    }

    @Generated
    public String toString() {
        return "AuthSourceStatusRequest(enable=" + getEnable() + ", id=" + getId() + ")";
    }

    @Generated
    public void setEnable(Boolean a) {
        this.enable = a;
    }

    @Generated
    public AuthSourceStatusRequest() {
    }

    @Generated
    public String getId() {
        return this.f4id;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public int hashCode() {
        Boolean enable = getEnable();
        int iHashCode = (1 * 59) + (enable == null ? 43 : enable.hashCode());
        String id = getId();
        return (iHashCode * 59) + (id == null ? 43 : id.hashCode());
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof AuthSourceStatusRequest)) {
            return false;
        }
        AuthSourceStatusRequest a2 = (AuthSourceStatusRequest) a;
        if (!a2.canEqual(this)) {
            return false;
        }
        Boolean enable = getEnable();
        Boolean enable2 = a2.getEnable();
        if (enable == null) {
            if (enable2 != null) {
                return false;
            }
        } else if (!enable.equals(enable2)) {
            return false;
        }
        String id = getId();
        String id2 = a2.getId();
        return id == null ? id2 == null : id.equals(id2);
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof AuthSourceStatusRequest;
    }
}
