package io.metersphere.xpack.project.dto.request;

import io.metersphere.system.dto.sdk.BasePageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Generated;

/* JADX INFO: compiled from: ka */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/project/dto/request/ProjectVersionRequest.class */
public class ProjectVersionRequest extends BasePageRequest {

    @NotBlank(message = "{project.id.not_blank}")
    @Size(min = 1, max = 50, message = "{project.id.length_range}")
    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String projectId;

    @Generated
    public ProjectVersionRequest() {
    }

    @Generated
    public String toString() {
        return "ProjectVersionRequest(projectId=" + getProjectId() + ")";
    }

    @Generated
    public void setProjectId(String a) {
        this.projectId = a;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Generated
    public int hashCode() {
        String projectId = getProjectId();
        return (1 * 59) + (projectId == null ? 43 : projectId.hashCode());
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Generated
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }
        if (!(a instanceof ProjectVersionRequest)) {
            return false;
        }
        ProjectVersionRequest a2 = (ProjectVersionRequest) a;
        if (!a2.canEqual(this)) {
            return false;
        }
        String projectId = getProjectId();
        String projectId2 = a2.getProjectId();
        return projectId == null ? projectId2 == null : projectId.equals(projectId2);
    }

    @Generated
    protected boolean canEqual(Object a) {
        return a instanceof ProjectVersionRequest;
    }

    @Generated
    public String getProjectId() {
        return this.projectId;
    }
}
