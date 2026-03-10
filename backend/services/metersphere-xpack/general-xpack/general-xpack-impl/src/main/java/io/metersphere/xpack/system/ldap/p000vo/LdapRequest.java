package io.metersphere.xpack.system.ldap.p000vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Generated;

/* JADX INFO: compiled from: c */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/ldap/vo/LdapRequest.class */
public class LdapRequest implements Serializable {

    @NotBlank(message = "{ldap_dn_is_null}")
    @Schema(description = "LDAP绑定DN", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ldapDn;

    @NotBlank(message = "{ldap_url_is_null}")
    @Schema(description = "LDAP地址", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ldapUrl;

    @NotBlank(message = "{ldap_password_is_null}")
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ldapPassword;
    private static final long serialVersionUID = 1;

    public static String ALLATORIxDEMO(String a) {
        int i = (3 << 3) ^ 1;
        int i2 = ((2 ^ 5) << 4) ^ ((3 << 2) ^ 1);
        int i3 = (4 << 4) ^ ((2 << 2) ^ 1);
        String str = a;
        int length = str.length();
        char[] cArr = new char[length];
        int i4 = length - 1;
        int i5 = i4;
        int i6 = i4;
        while (i6 >= 0) {
            int i7 = i5;
            int i8 = i5 - 1;
            cArr[i7] = (char) (str.charAt(i7) ^ i);
            if (i8 < 0) {
                break;
            }
            i5 = i8 - 1;
            cArr[i8] = (char) (str.charAt(i8) ^ i3);
            i6 = i5;
        }
        return new String(cArr);
    }

    @Generated
    public String getLdapUrl() {
        return this.ldapUrl;
    }

    @Generated
    public void setLdapDn(String a) {
        this.ldapDn = a;
    }

    @Generated
    public void setLdapUrl(String a) {
        this.ldapUrl = a;
    }

    @Generated
    public void setLdapPassword(String a) {
        this.ldapPassword = a;
    }

    @Generated
    public LdapRequest() {
    }

    @Generated
    public String toString() {
        return "LdapRequest(ldapUrl=" + getLdapUrl() + ", ldapDn=" + getLdapDn() + ", ldapPassword=" + getLdapPassword() + ")";
    }

    @Generated
    public String getLdapPassword() {
        return this.ldapPassword;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public int hashCode() {
        String ldapUrl = getLdapUrl();
        int iHashCode = (1 * 59) + (ldapUrl == null ? 43 : ldapUrl.hashCode());
        String ldapDn = getLdapDn();
        int iHashCode2 = (iHashCode * 59) + (ldapDn == null ? 43 : ldapDn.hashCode());
        String ldapPassword = getLdapPassword();
        return (iHashCode2 * 59) + (ldapPassword == null ? 43 : ldapPassword.hashCode());
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof LdapRequest)) {
            return false;
        }
        LdapRequest a2 = (LdapRequest) a;
        if (!a2.canEqual(this)) {
            return false;
        }
        String ldapUrl = getLdapUrl();
        String ldapUrl2 = a2.getLdapUrl();
        if (ldapUrl == null) {
            if (ldapUrl2 != null) {
                return false;
            }
        } else if (!ldapUrl.equals(ldapUrl2)) {
            return false;
        }
        String ldapDn = getLdapDn();
        String ldapDn2 = a2.getLdapDn();
        if (ldapDn == null) {
            if (ldapDn2 != null) {
                return false;
            }
        } else if (!ldapDn.equals(ldapDn2)) {
            return false;
        }
        String ldapPassword = getLdapPassword();
        String ldapPassword2 = a2.getLdapPassword();
        return ldapPassword == null ? ldapPassword2 == null : ldapPassword.equals(ldapPassword2);
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof LdapRequest;
    }

    @Generated
    public String getLdapDn() {
        return this.ldapDn;
    }
}
