package io.metersphere.xpack.project.dto;

import io.metersphere.validation.groups.Created;
import io.metersphere.validation.groups.Updated;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Generated;

/* JADX INFO: compiled from: xa */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/project/dto/ProjectVersionDTO.class */
public class ProjectVersionDTO implements Serializable {

    @NotBlank(message = "{project_version.name.not_blank}", groups = {Created.class, Updated.class})
    @Size(min = 1, max = 255, message = "{project_version.name.length_range}", groups = {Created.class, Updated.class})
    @Schema(description = "版本名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "状态")
    private Boolean status;

    @Schema(description = "发布时间")
    private Long publishTime;

    @NotBlank(message = "{project_version.project_id.not_blank}", groups = {Created.class, Updated.class})
    @Size(min = 1, max = 50, message = "{project_version.project_id.length_range}", groups = {Created.class, Updated.class})
    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String projectId;

    @Schema(description = "描述")
    private String description;

    @NotNull(message = "{project_version.latest.not_blank}", groups = {Created.class})
    @Schema(description = "是否是最新版", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean latest;

    /* JADX INFO: renamed from: id */
    @NotBlank(message = "{project_version.id.not_blank}", groups = {Updated.class})
    @Size(min = 1, max = 50, message = "{project_version.id.length_range}", groups = {Created.class, Updated.class})
    @Schema(description = "版本ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String f0id;

    @Schema(description = "创建时间")
    private Long createTime;

    @Schema(description = "创建人")
    private String createUser;
    private static final long serialVersionUID = 1;

    @Generated
    public Long getCreateTime() {
        return this.createTime;
    }

    @Generated
    public Long getPublishTime() {
        return this.publishTime;
    }

    @Generated
    public Boolean getStatus() {
        return this.status;
    }

    @Generated
    public String getCreateUser() {
        return this.createUser;
    }

    @Generated
    public String getDescription() {
        return this.description;
    }

    @Generated
    public void setDescription(String a) {
        this.description = a;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof ProjectVersionDTO)) {
            return false;
        }
        ProjectVersionDTO projectVersionDTO = (ProjectVersionDTO) a;
        if (!projectVersionDTO.canEqual(this)) {
            return false;
        }
        Boolean status = getStatus();
        Boolean status2 = projectVersionDTO.getStatus();
        if (status == null) {
            if (status2 != null) {
                return false;
            }
        } else if (!status.equals(status2)) {
            return false;
        }
        Boolean latest = getLatest();
        Boolean latest2 = projectVersionDTO.getLatest();
        if (latest == null) {
            if (latest2 != null) {
                return false;
            }
        } else if (!latest.equals(latest2)) {
            return false;
        }
        Long publishTime = getPublishTime();
        Long publishTime2 = projectVersionDTO.getPublishTime();
        if (publishTime == null) {
            if (publishTime2 != null) {
                return false;
            }
        } else if (!publishTime.equals(publishTime2)) {
            return false;
        }
        Long createTime = getCreateTime();
        Long createTime2 = projectVersionDTO.getCreateTime();
        if (createTime == null) {
            if (createTime2 != null) {
                return false;
            }
        } else if (!createTime.equals(createTime2)) {
            return false;
        }
        String id = getId();
        String id2 = projectVersionDTO.getId();
        if (id == null) {
            if (id2 != null) {
                return false;
            }
        } else if (!id.equals(id2)) {
            return false;
        }
        String projectId = getProjectId();
        String projectId2 = projectVersionDTO.getProjectId();
        if (projectId == null) {
            if (projectId2 != null) {
                return false;
            }
        } else if (!projectId.equals(projectId2)) {
            return false;
        }
        String name = getName();
        String name2 = projectVersionDTO.getName();
        if (name == null) {
            if (name2 != null) {
                return false;
            }
        } else if (!name.equals(name2)) {
            return false;
        }
        String createUser = getCreateUser();
        String createUser2 = projectVersionDTO.getCreateUser();
        if (createUser == null) {
            if (createUser2 != null) {
                return false;
            }
        } else if (!createUser.equals(createUser2)) {
            return false;
        }
        String description = getDescription();
        Object a2 = projectVersionDTO.getDescription();
        return description == null ? a2 == null : description.equals(a2);
    }

    @Generated
    public void setName(String a) {
        this.name = a;
    }

    @Generated
    public void setId(String a) {
        this.f0id = a;
    }

    @Generated
    public void setProjectId(String a) {
        this.projectId = a;
    }

    @Generated
    public void setLatest(Boolean a) {
        this.latest = a;
    }

    @Generated
    public ProjectVersionDTO() {
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public int hashCode() {
        Boolean status = getStatus();
        int iHashCode = (1 * 59) + (status == null ? 43 : status.hashCode());
        Boolean latest = getLatest();
        int iHashCode2 = (iHashCode * 59) + (latest == null ? 43 : latest.hashCode());
        Long publishTime = getPublishTime();
        int iHashCode3 = (iHashCode2 * 59) + (publishTime == null ? 43 : publishTime.hashCode());
        Long createTime = getCreateTime();
        int iHashCode4 = (iHashCode3 * 59) + (createTime == null ? 43 : createTime.hashCode());
        String id = getId();
        int iHashCode5 = (iHashCode4 * 59) + (id == null ? 43 : id.hashCode());
        String projectId = getProjectId();
        int iHashCode6 = (iHashCode5 * 59) + (projectId == null ? 43 : projectId.hashCode());
        String name = getName();
        int iHashCode7 = (iHashCode6 * 59) + (name == null ? 43 : name.hashCode());
        String createUser = getCreateUser();
        int iHashCode8 = (iHashCode7 * 59) + (createUser == null ? 43 : createUser.hashCode());
        String description = getDescription();
        return (iHashCode8 * 59) + (description == null ? 43 : description.hashCode());
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof ProjectVersionDTO;
    }

    @Generated
    public void setPublishTime(Long a) {
        this.publishTime = a;
    }

    @Generated
    public void setCreateTime(Long a) {
        this.createTime = a;
    }

    @Generated
    public String toString() {
        return "ProjectVersionDTO(id=" + getId() + ", projectId=" + getProjectId() + ", name=" + getName() + ", status=" + getStatus() + ", latest=" + getLatest() + ", publishTime=" + getPublishTime() + ", createTime=" + getCreateTime() + ", createUser=" + getCreateUser() + ", description=" + getDescription() + ")";
    }

    @Generated
    public void setStatus(Boolean a) {
        this.status = a;
    }

    @Generated
    public void setCreateUser(String a) {
        this.createUser = a;
    }

    @Generated
    public Boolean getLatest() {
        return this.latest;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public String getId() {
        return this.f0id;
    }

    @Generated
    public String getProjectId() {
        return this.projectId;
    }
}
