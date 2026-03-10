package io.metersphere.xpack.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Generated;

/* JADX INFO: compiled from: ra */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/dto/PlatformSourceDTO.class */
@Schema(description = "企微信息")
public class PlatformSourceDTO implements Serializable {

    @Schema(description = "是否配置", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean hasConfig;

    @Schema(description = "是否校验", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean valid;

    @Schema(description = "是否开启")
    private Boolean enable;

    @Schema(description = "平台名称（国际飞书:LARK_SUITE，飞书:LARK，钉钉:DING_TALK，企业微信:WE_COM）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String platform;

    @Generated
    public Boolean getValid() {
        return this.valid;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof PlatformSourceDTO)) {
            return false;
        }
        PlatformSourceDTO platformSourceDTO = (PlatformSourceDTO) a;
        if (!platformSourceDTO.canEqual(this)) {
            return false;
        }
        Boolean enable = getEnable();
        Boolean enable2 = platformSourceDTO.getEnable();
        if (enable == null) {
            if (enable2 != null) {
                return false;
            }
        } else if (!enable.equals(enable2)) {
            return false;
        }
        Boolean valid = getValid();
        Boolean valid2 = platformSourceDTO.getValid();
        if (valid == null) {
            if (valid2 != null) {
                return false;
            }
        } else if (!valid.equals(valid2)) {
            return false;
        }
        Boolean hasConfig = getHasConfig();
        Boolean hasConfig2 = platformSourceDTO.getHasConfig();
        if (hasConfig == null) {
            if (hasConfig2 != null) {
                return false;
            }
        } else if (!hasConfig.equals(hasConfig2)) {
            return false;
        }
        String platform = getPlatform();
        Object a2 = platformSourceDTO.getPlatform();
        return platform == null ? a2 == null : platform.equals(a2);
    }

    @Generated
    public PlatformSourceDTO() {
    }

    @Generated
    public void setValid(Boolean a) {
        this.valid = a;
    }

    @Generated
    public void setPlatform(String a) {
        this.platform = a;
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof PlatformSourceDTO;
    }

    @Generated
    public String toString() {
        return "PlatformSourceDTO(platform=" + getPlatform() + ", enable=" + getEnable() + ", valid=" + getValid() + ", hasConfig=" + getHasConfig() + ")";
    }

    @Generated
    public void setHasConfig(Boolean a) {
        this.hasConfig = a;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public int hashCode() {
        Boolean enable = getEnable();
        int iHashCode = (1 * 59) + (enable == null ? 43 : enable.hashCode());
        Boolean valid = getValid();
        int iHashCode2 = (iHashCode * 59) + (valid == null ? 43 : valid.hashCode());
        Boolean hasConfig = getHasConfig();
        int iHashCode3 = (iHashCode2 * 59) + (hasConfig == null ? 43 : hasConfig.hashCode());
        String platform = getPlatform();
        return (iHashCode3 * 59) + (platform == null ? 43 : platform.hashCode());
    }

    @Generated
    public void setEnable(Boolean a) {
        this.enable = a;
    }

    @Generated
    public String getPlatform() {
        return this.platform;
    }

    @Generated
    public Boolean getEnable() {
        return this.enable;
    }

    @Generated
    public Boolean getHasConfig() {
        return this.hasConfig;
    }
}
