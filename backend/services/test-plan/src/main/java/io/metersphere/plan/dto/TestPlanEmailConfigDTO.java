package io.metersphere.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class TestPlanEmailConfigDTO implements Serializable {

    @Schema(description = "测试计划ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{test_plan_email_config.test_plan_id.not_blank}")
    @Size(min = 1, max = 50, message = "{test_plan_email_config.test_plan_id.length_range}")
    private String testPlanId;

    @Schema(description = "邮件接收人，多个邮箱以逗号分隔")
    @Size(max = 2000, message = "{test_plan_email_config.email_recipients.length_range}")
    private String emailRecipients;

    private static final long serialVersionUID = 1L;
}
