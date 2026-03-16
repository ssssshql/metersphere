package io.metersphere.sdk.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public abstract class BaseEmailConfig {
    @Schema(description = "场景ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String scenarioId;

    @Schema(description = "邮件接收人，多个邮箱以逗号分隔")
    private String emailRecipients;

    @Schema(description = "创建时间")
    private Long createTime;

    @Schema(description = "更新时间")
    private Long updateTime;

    @Schema(description = "创建人")
    private String createUser;

    @Schema(description = "更新人")
    private String updateUser;
}
