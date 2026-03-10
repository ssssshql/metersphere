package io.metersphere.xpack.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Generated;

/* JADX INFO: compiled from: ia */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/dto/LdapDTO.class */
public class LdapDTO implements Serializable {

    @JsonProperty("ou")
    private String ldapUserOu;

    @JsonProperty("password")
    private String ldapPassword;

    @JsonProperty("filter")
    private String ldapUserFilter;

    @JsonProperty("dn")
    private String ldapDn;

    @JsonProperty("mapping")
    private String ldapUserMapping;
    private static final long serialVersionUID = 1;

    @JsonProperty("url")
    private String ldapUrl;

    @Generated
    public String getLdapDn() {
        return this.ldapDn;
    }

    @Generated
    public String getLdapPassword() {
        return this.ldapPassword;
    }

    @Generated
    public String getLdapUserOu() {
        return this.ldapUserOu;
    }

    @Generated
    public void setLdapPassword(String a) {
        this.ldapPassword = a;
    }

    @Generated
    public LdapDTO() {
    }

    @Generated
    public void setLdapUserMapping(String a) {
        this.ldapUserMapping = a;
    }

    @Generated
    public void setLdapDn(String a) {
        this.ldapDn = a;
    }

    @Generated
    public void setLdapUserOu(String a) {
        this.ldapUserOu = a;
    }

    @Generated
    public void setLdapUrl(String a) {
        this.ldapUrl = a;
    }

    @Generated
    public void setLdapUserFilter(String a) {
        this.ldapUserFilter = a;
    }

    @Generated
    public String toString() {
        return "LdapDTO(ldapUrl=" + getLdapUrl() + ", ldapDn=" + getLdapDn() + ", ldapPassword=" + getLdapPassword() + ", ldapUserFilter=" + getLdapUserFilter() + ", ldapUserOu=" + getLdapUserOu() + ", ldapUserMapping=" + getLdapUserMapping() + ")";
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
        return a instanceof LdapDTO;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof LdapDTO)) {
            return false;
        }
        LdapDTO ldapDTO = (LdapDTO) a;
        if (!ldapDTO.canEqual(this)) {
            return false;
        }
        String ldapUrl = getLdapUrl();
        String ldapUrl2 = ldapDTO.getLdapUrl();
        if (ldapUrl == null) {
            if (ldapUrl2 != null) {
                return false;
            }
        } else if (!ldapUrl.equals(ldapUrl2)) {
            return false;
        }
        String ldapDn = getLdapDn();
        String ldapDn2 = ldapDTO.getLdapDn();
        if (ldapDn == null) {
            if (ldapDn2 != null) {
                return false;
            }
        } else if (!ldapDn.equals(ldapDn2)) {
            return false;
        }
        String ldapPassword = getLdapPassword();
        String ldapPassword2 = ldapDTO.getLdapPassword();
        if (ldapPassword == null) {
            if (ldapPassword2 != null) {
                return false;
            }
        } else if (!ldapPassword.equals(ldapPassword2)) {
            return false;
        }
        String ldapUserFilter = getLdapUserFilter();
        String ldapUserFilter2 = ldapDTO.getLdapUserFilter();
        if (ldapUserFilter == null) {
            if (ldapUserFilter2 != null) {
                return false;
            }
        } else if (!ldapUserFilter.equals(ldapUserFilter2)) {
            return false;
        }
        String ldapUserOu = getLdapUserOu();
        String ldapUserOu2 = ldapDTO.getLdapUserOu();
        if (ldapUserOu == null) {
            if (ldapUserOu2 != null) {
                return false;
            }
        } else if (!ldapUserOu.equals(ldapUserOu2)) {
            return false;
        }
        String ldapUserMapping = getLdapUserMapping();
        Object a2 = ldapDTO.getLdapUserMapping();
        return ldapUserMapping == null ? a2 == null : ldapUserMapping.equals(a2);
    }

    @Generated
    public String getLdapUserFilter() {
        return this.ldapUserFilter;
    }

    @Generated
    public String getLdapUserMapping() {
        return this.ldapUserMapping;
    }

    @Generated
    public String getLdapUrl() {
        return this.ldapUrl;
    }
}
