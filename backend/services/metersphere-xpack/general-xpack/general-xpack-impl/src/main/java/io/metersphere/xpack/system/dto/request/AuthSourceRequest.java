package io.metersphere.xpack.system.dto.request;

import io.metersphere.validation.groups.Created;
import io.metersphere.validation.groups.Updated;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Generated;

/* JADX INFO: compiled from: ea */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/dto/request/AuthSourceRequest.class */
public class AuthSourceRequest implements Serializable {
    private static final long serialVersionUID = 1;

    @Schema(description = "描述")
    private String description;

    /* JADX INFO: renamed from: id */
    @Size(min = 1, max = 50, message = "{auth_source.id.length_range}", groups = {Created.class, Updated.class})
    @Schema(description = "认证源ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String f3id;

    @NotBlank(message = "{authsource_name_is_null}")
    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "{authsource_configuration_is_null}", groups = {Created.class})
    @Schema(description = "认证源配置", requiredMode = Schema.RequiredMode.REQUIRED)
    private String configuration;

    @NotBlank(message = "{authsource_type_is_null}")
    @Schema(description = "类型")
    private String type;

    public static String ALLATORIxDEMO(String a) {
        int i = (5 << 3) ^ 1;
        int i2 = ((3 ^ 5) << 4) ^ (2 ^ 5);
        int i3 = ((2 ^ 5) << 4) ^ ((2 << 2) ^ 1);
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
    public String getConfiguration() {
        return this.configuration;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof AuthSourceRequest)) {
            return false;
        }
        AuthSourceRequest authSourceRequest = (AuthSourceRequest) a;
        if (!authSourceRequest.canEqual(this)) {
            return false;
        }
        String id = getId();
        String id2 = authSourceRequest.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String description = getDescription();
        String description2 = authSourceRequest.getDescription();
        if (description == null) {
            if (description2 != null) {
                return false;
            }
        } else if (!description.equals(description2)) {
            return false;
        }
        String name = getName();
        String name2 = authSourceRequest.getName();
        if (name == null) {
            if (name2 != null) {
                return false;
            }
        } else if (!name.equals(name2)) {
            return false;
        }
        String type = getType();
        String type2 = authSourceRequest.getType();
        if (type == null) {
            if (type2 != null) {
                return false;
            }
        } else if (!type.equals(type2)) {
            return false;
        }
        String configuration = getConfiguration();
        Object a2 = authSourceRequest.getConfiguration();
        return configuration == null ? a2 == null : configuration.equals(a2);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public int hashCode() {
        String id = getId();
        int iHashCode = (1 * 59) + (id == null ? 43 : id.hashCode());
        String description = getDescription();
        int iHashCode2 = (iHashCode * 59) + (description == null ? 43 : description.hashCode());
        String name = getName();
        int iHashCode3 = (iHashCode2 * 59) + (name == null ? 43 : name.hashCode());
        String type = getType();
        int iHashCode4 = (iHashCode3 * 59) + (type == null ? 43 : type.hashCode());
        String configuration = getConfiguration();
        return (iHashCode4 * 59) + (configuration == null ? 43 : configuration.hashCode());
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof AuthSourceRequest;
    }

    @Generated
    public String getDescription() {
        return this.description;
    }

    @Generated
    public void setType(String a) {
        this.type = a;
    }

    @Generated
    public void setId(String a) {
        this.f3id = a;
    }

    @Generated
    public AuthSourceRequest() {
    }

    @Generated
    public void setConfiguration(String a) {
        this.configuration = a;
    }

    @Generated
    public String toString() {
        return "AuthSourceRequest(id=" + getId() + ", description=" + getDescription() + ", name=" + getName() + ", type=" + getType() + ", configuration=" + getConfiguration() + ")";
    }

    @Generated
    public void setDescription(String a) {
        this.description = a;
    }

    @Generated
    public void setName(String a) {
        this.name = a;
    }

    @Generated
    public String getId() {
        return this.f3id;
    }

    @Generated
    public String getType() {
        return this.type;
    }

    @Generated
    public String getName() {
        return this.name;
    }
}
