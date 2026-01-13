package io.metersphere.api.dto.scenario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class ApiScenarioEmailConfigDTO implements Serializable {

    @Schema(description = "场景ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{api_scenario_email_config.scenario_id.not_blank}")
    @Size(min = 1, max = 50, message = "{api_scenario_email_config.scenario_id.length_range}")
    private String scenarioId;

    @Schema(description = "邮件接收人，多个邮箱以逗号分隔")
    @Size(max = 2000, message = "{api_scenario_email_config.email_recipients.length_range}")
    private String emailRecipients;

    private static final long serialVersionUID = 1L;
}
