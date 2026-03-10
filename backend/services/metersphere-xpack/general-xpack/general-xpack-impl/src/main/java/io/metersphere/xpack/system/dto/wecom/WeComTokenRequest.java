package io.metersphere.xpack.system.dto.wecom;

import java.io.Serializable;
import lombok.Generated;

/* JADX INFO: compiled from: g */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/dto/wecom/WeComTokenRequest.class */
public class WeComTokenRequest implements Serializable {
    private String code;
    private String state;

    @Generated
    public String getState() {
        return this.state;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof WeComTokenRequest)) {
            return false;
        }
        WeComTokenRequest a2 = (WeComTokenRequest) a;
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
        String state = getState();
        String state2 = a2.getState();
        return state == null ? state2 == null : state.equals(state2);
    }

    @Generated
    public WeComTokenRequest() {
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof WeComTokenRequest;
    }

    @Generated
    public String toString() {
        return "WeComTokenRequest(code=" + getCode() + ", state=" + getState() + ")";
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public int hashCode() {
        String code = getCode();
        int iHashCode = (1 * 59) + (code == null ? 43 : code.hashCode());
        String state = getState();
        return (iHashCode * 59) + (state == null ? 43 : state.hashCode());
    }

    @Generated
    public void setCode(String a) {
        this.code = a;
    }

    @Generated
    public void setState(String a) {
        this.state = a;
    }

    @Generated
    public String getCode() {
        return this.code;
    }
}
