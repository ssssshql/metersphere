package io.metersphere.xpack.system.ldap.p000vo;

import io.metersphere.system.dto.sdk.LoginRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Generated;

/* JADX INFO: compiled from: r */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/ldap/vo/LdapLoginRequest.class */
public class LdapLoginRequest extends LoginRequest implements Serializable {

    @NotBlank(message = "{ldap_dn_is_null}")
    @Schema(description = "LDAP绑定DN", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ldapDn;

    @NotBlank(message = "{ldap_url_is_null}")
    @Schema(description = "LDAP地址", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ldapUrl;

    @NotBlank(message = "{ldap_ou_is_null}")
    @Schema(description = "用户OU", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ldapUserOu;

    @NotBlank(message = "{ldap_user_mapping_is_null}")
    @Schema(description = "LDAP属性映射", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ldapUserMapping;

    @NotBlank(message = "{ldap_user_filter_is_null}")
    @Schema(description = "用户过滤器", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ldapUserFilter;
    private static final long serialVersionUID = 1;

    @NotBlank(message = "{ldap_password_is_null}")
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ldapPassword;

    @Generated
    public String getLdapUrl() {
        return this.ldapUrl;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public int hashCode() {
        String ldapUrl = getLdapUrl();
        int iHashCode = (1 * 59) + (ldapUrl == null ? 43 : ldapUrl.hashCode());
        String ldapDn = getLdapDn();
        int iHashCode2 = (iHashCode * 59) + (ldapDn == null ? 43 : ldapDn.hashCode());
        String ldapPassword = getLdapPassword();
        int iHashCode3 = (iHashCode2 * 59) + (ldapPassword == null ? 43 : ldapPassword.hashCode());
        String ldapUserFilter = getLdapUserFilter();
        int iHashCode4 = (iHashCode3 * 59) + (ldapUserFilter == null ? 43 : ldapUserFilter.hashCode());
        String ldapUserOu = getLdapUserOu();
        int iHashCode5 = (iHashCode4 * 59) + (ldapUserOu == null ? 43 : ldapUserOu.hashCode());
        String ldapUserMapping = getLdapUserMapping();
        return (iHashCode5 * 59) + (ldapUserMapping == null ? 43 : ldapUserMapping.hashCode());
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof LdapLoginRequest;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof LdapLoginRequest)) {
            return false;
        }
        LdapLoginRequest ldapLoginRequest = (LdapLoginRequest) a;
        if (!ldapLoginRequest.canEqual(this)) {
            return false;
        }
        String ldapUrl = getLdapUrl();
        String ldapUrl2 = ldapLoginRequest.getLdapUrl();
        if (ldapUrl == null) {
            if (ldapUrl2 != null) {
                return false;
            }
        } else if (!ldapUrl.equals(ldapUrl2)) {
            return false;
        }
        String ldapDn = getLdapDn();
        String ldapDn2 = ldapLoginRequest.getLdapDn();
        if (ldapDn == null) {
            if (ldapDn2 != null) {
                return false;
            }
        } else if (!ldapDn.equals(ldapDn2)) {
            return false;
        }
        String ldapPassword = getLdapPassword();
        String ldapPassword2 = ldapLoginRequest.getLdapPassword();
        if (ldapPassword == null) {
            if (ldapPassword2 != null) {
                return false;
            }
        } else if (!ldapPassword.equals(ldapPassword2)) {
            return false;
        }
        String ldapUserFilter = getLdapUserFilter();
        String ldapUserFilter2 = ldapLoginRequest.getLdapUserFilter();
        if (ldapUserFilter == null) {
            if (ldapUserFilter2 != null) {
                return false;
            }
        } else if (!ldapUserFilter.equals(ldapUserFilter2)) {
            return false;
        }
        String ldapUserOu = getLdapUserOu();
        String ldapUserOu2 = ldapLoginRequest.getLdapUserOu();
        if (ldapUserOu == null) {
            if (ldapUserOu2 != null) {
                return false;
            }
        } else if (!ldapUserOu.equals(ldapUserOu2)) {
            return false;
        }
        String ldapUserMapping = getLdapUserMapping();
        Object a2 = ldapLoginRequest.getLdapUserMapping();
        return ldapUserMapping == null ? a2 == null : ldapUserMapping.equals(a2);
    }

    @Generated
    public String getLdapDn() {
        return this.ldapDn;
    }

    @Generated
    public String getLdapUserMapping() {
        return this.ldapUserMapping;
    }

    @Generated
    public String getLdapUserOu() {
        return this.ldapUserOu;
    }

    @Generated
    public String getLdapUserFilter() {
        return this.ldapUserFilter;
    }

    @Generated
    public void setLdapUserOu(String a) {
        this.ldapUserOu = a;
    }

    @Generated
    public void setLdapDn(String a) {
        this.ldapDn = a;
    }

    @Generated
    public String toString() {
        return "LdapLoginRequest(ldapUrl=" + getLdapUrl() + ", ldapDn=" + getLdapDn() + ", ldapPassword=" + getLdapPassword() + ", ldapUserFilter=" + getLdapUserFilter() + ", ldapUserOu=" + getLdapUserOu() + ", ldapUserMapping=" + getLdapUserMapping() + ")";
    }

    @Generated
    public LdapLoginRequest() {
    }

    @Generated
    public void setLdapUserFilter(String a) {
        this.ldapUserFilter = a;
    }

    @Generated
    public void setLdapUserMapping(String a) {
        this.ldapUserMapping = a;
    }

    @Generated
    public void setLdapPassword(String a) {
        this.ldapPassword = a;
    }

    @Generated
    public void setLdapUrl(String a) {
        this.ldapUrl = a;
    }

    @Generated
    public String getLdapPassword() {
        return this.ldapPassword;
    }
}
