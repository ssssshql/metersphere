package io.metersphere.xpack.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Generated;

/* JADX INFO: compiled from: wa */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/project/dto/ProjectVersionOptionDTO.class */
public class ProjectVersionOptionDTO {

    @Schema(description = "是否启用")
    private Boolean enable;

    @Schema(description = "版本名称")
    private String name;

    @Schema(description = "是否最新版本")
    private Boolean latest;

    /* JADX INFO: renamed from: id */
    @Schema(description = "版本ID")
    private String f1id;

    @Generated
    public Boolean getEnable() {
        return this.enable;
    }

    @Generated
    public String getId() {
        return this.f1id;
    }

    /* JADX INFO: compiled from: wa */
    /* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/project/dto/ProjectVersionOptionDTO$ProjectVersionOptionDTOBuilder.class */
    @Generated
    public static class ProjectVersionOptionDTOBuilder {

        /* JADX INFO: renamed from: id */
        @Generated
        private String f2id;

        @Generated
        private Boolean enable;

        @Generated
        private String name;

        @Generated
        private Boolean latest;

        @Generated
        public ProjectVersionOptionDTOBuilder enable(Boolean a) {
            this.enable = a;
            return this;
        }

        @Generated
        public ProjectVersionOptionDTO build() {
            return new ProjectVersionOptionDTO(this.f2id, this.name, this.latest, this.enable);
        }

        @Generated
        public ProjectVersionOptionDTOBuilder latest(Boolean a) {
            this.latest = a;
            return this;
        }

        @Generated
        /* JADX INFO: renamed from: id */
        public ProjectVersionOptionDTOBuilder m0id(String a) {
            this.f2id = a;
            return this;
        }

        @Generated
        ProjectVersionOptionDTOBuilder() {
        }

        @Generated
        public String toString() {
            return "ProjectVersionOptionDTO.ProjectVersionOptionDTOBuilder(id=" + this.f2id + ", name=" + this.name + ", latest=" + this.latest + ", enable=" + this.enable + ")";
        }

        @Generated
        public ProjectVersionOptionDTOBuilder name(String a) {
            this.name = a;
            return this;
        }
    }

    @Generated
    ProjectVersionOptionDTO(String a, String a2, Boolean a3, Boolean a4) {
        this.f1id = a;
        this.name = a2;
        this.latest = a3;
        this.enable = a4;
    }

    @Generated
    public static ProjectVersionOptionDTOBuilder builder() {
        return new ProjectVersionOptionDTOBuilder();
    }

    @Generated
    public String getName() {
        return this.name;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof ProjectVersionOptionDTO)) {
            return false;
        }
        ProjectVersionOptionDTO projectVersionOptionDTO = (ProjectVersionOptionDTO) a;
        if (!projectVersionOptionDTO.canEqual(this)) {
            return false;
        }
        Boolean latest = getLatest();
        Boolean latest2 = projectVersionOptionDTO.getLatest();
        if (latest == null) {
            if (latest2 != null) {
                return false;
            }
        } else if (!latest.equals(latest2)) {
            return false;
        }
        Boolean enable = getEnable();
        Boolean enable2 = projectVersionOptionDTO.getEnable();
        if (enable == null) {
            if (enable2 != null) {
                return false;
            }
        } else if (!enable.equals(enable2)) {
            return false;
        }
        String id = getId();
        String id2 = projectVersionOptionDTO.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String name = getName();
        Object a2 = projectVersionOptionDTO.getName();
        return name == null ? a2 == null : name.equals(a2);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public int hashCode() {
        Boolean latest = getLatest();
        int iHashCode = (1 * 59) + (latest == null ? 43 : latest.hashCode());
        Boolean enable = getEnable();
        int iHashCode2 = (iHashCode * 59) + (enable == null ? 43 : enable.hashCode());
        String id = getId();
        int iHashCode3 = (iHashCode2 * 59) + (id == null ? 43 : id.hashCode());
        String name = getName();
        return (iHashCode3 * 59) + (name == null ? 43 : name.hashCode());
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof ProjectVersionOptionDTO;
    }

    @Generated
    public Boolean getLatest() {
        return this.latest;
    }

    @Generated
    public void setEnable(Boolean a) {
        this.enable = a;
    }

    @Generated
    public void setId(String a) {
        this.f1id = a;
    }

    @Generated
    public void setName(String a) {
        this.name = a;
    }

    @Generated
    public void setLatest(Boolean a) {
        this.latest = a;
    }

    @Generated
    public String toString() {
        return "ProjectVersionOptionDTO(id=" + getId() + ", name=" + getName() + ", latest=" + getLatest() + ", enable=" + getEnable() + ")";
    }
}
