package io.metersphere.plan.domain;

import lombok.Data;

@Data
public class TestPlanReportExtension {
    private String id;
    private String reportId;
    private Long deployTime;
    private String deployVersion;
}
