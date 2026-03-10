package io.metersphere.xpack.system.dto.dingtalk;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Generated;

/* JADX INFO: compiled from: ga */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/dto/dingtalk/DingTalkTokenParamDTO.class */
@Schema(description = "钉钉")
public class DingTalkTokenParamDTO extends DingTalkBaseParamDTO {

    @Schema(description = "code")
    public String code;

    @Schema(description = "grantType")
    public String grantType;

    @Schema(description = "expireIn")
    public Long expireIn;

    @Schema(description = "clientSecret")
    public String clientSecret;

    @Schema(description = "accessToken")
    public String accessToken;

    @Schema(description = "clientId")
    public String clientId;

    @Generated
    public String getCode() {
        return this.code;
    }

    @Generated
    public Long getExpireIn() {
        return this.expireIn;
    }

    @Generated
    public void setCode(String a) {
        this.code = a;
    }

    @Override // io.metersphere.xpack.system.dto.dingtalk.DingTalkBaseParamDTO
    @Generated
    public String toString() {
        return "DingTalkTokenParamDTO(accessToken=" + getAccessToken() + ", expireIn=" + getExpireIn() + ", clientId=" + getClientId() + ", clientSecret=" + getClientSecret() + ", code=" + getCode() + ", grantType=" + getGrantType() + ")";
    }

    @Generated
    public void setExpireIn(Long a) {
        this.expireIn = a;
    }

    @Generated
    public void setClientSecret(String a) {
        this.clientSecret = a;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // io.metersphere.xpack.system.dto.dingtalk.DingTalkBaseParamDTO
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof DingTalkTokenParamDTO)) {
            return false;
        }
        DingTalkTokenParamDTO dingTalkTokenParamDTO = (DingTalkTokenParamDTO) a;
        if (!dingTalkTokenParamDTO.canEqual(this) || !super.equals(a)) {
            return false;
        }
        Long expireIn = getExpireIn();
        Long expireIn2 = dingTalkTokenParamDTO.getExpireIn();
        if (expireIn == null) {
            if (expireIn2 != null) {
                return false;
            }
        } else if (!expireIn.equals(expireIn2)) {
            return false;
        }
        String accessToken = getAccessToken();
        String accessToken2 = dingTalkTokenParamDTO.getAccessToken();
        if (accessToken == null) {
            if (accessToken2 != null) {
                return false;
            }
        } else if (!accessToken.equals(accessToken2)) {
            return false;
        }
        String clientId = getClientId();
        String clientId2 = dingTalkTokenParamDTO.getClientId();
        if (clientId == null) {
            if (clientId2 != null) {
                return false;
            }
        } else if (!clientId.equals(clientId2)) {
            return false;
        }
        String clientSecret = getClientSecret();
        String clientSecret2 = dingTalkTokenParamDTO.getClientSecret();
        if (clientSecret == null) {
            if (clientSecret2 != null) {
                return false;
            }
        } else if (!clientSecret.equals(clientSecret2)) {
            return false;
        }
        String code = getCode();
        String code2 = dingTalkTokenParamDTO.getCode();
        if (code == null) {
            if (code2 != null) {
                return false;
            }
        } else if (!code.equals(code2)) {
            return false;
        }
        String grantType = getGrantType();
        String grantType2 = dingTalkTokenParamDTO.getGrantType();
        return grantType == null ? grantType2 == null : grantType.equals(grantType2);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // io.metersphere.xpack.system.dto.dingtalk.DingTalkBaseParamDTO
    @Generated
    public int hashCode() {
        int iHashCode = super.hashCode();
        Long expireIn = getExpireIn();
        int iHashCode2 = (iHashCode * 59) + (expireIn == null ? 43 : expireIn.hashCode());
        String accessToken = getAccessToken();
        int iHashCode3 = (iHashCode2 * 59) + (accessToken == null ? 43 : accessToken.hashCode());
        String clientId = getClientId();
        int iHashCode4 = (iHashCode3 * 59) + (clientId == null ? 43 : clientId.hashCode());
        String clientSecret = getClientSecret();
        int iHashCode5 = (iHashCode4 * 59) + (clientSecret == null ? 43 : clientSecret.hashCode());
        String code = getCode();
        int iHashCode6 = (iHashCode5 * 59) + (code == null ? 43 : code.hashCode());
        String grantType = getGrantType();
        return (iHashCode6 * 59) + (grantType == null ? 43 : grantType.hashCode());
    }

    @Generated
    public DingTalkTokenParamDTO() {
    }

    @Generated
    public void setGrantType(String a) {
        this.grantType = a;
    }

    @Override // io.metersphere.xpack.system.dto.dingtalk.DingTalkBaseParamDTO
    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof DingTalkTokenParamDTO;
    }

    @Generated
    public void setAccessToken(String a) {
        this.accessToken = a;
    }

    @Generated
    public void setClientId(String a) {
        this.clientId = a;
    }

    @Generated
    public String getClientId() {
        return this.clientId;
    }

    @Generated
    public String getAccessToken() {
        return this.accessToken;
    }

    @Generated
    public String getGrantType() {
        return this.grantType;
    }

    @Generated
    public String getClientSecret() {
        return this.clientSecret;
    }
}
