package io.metersphere.plan.mapper;

import io.metersphere.plan.domain.TestPlanReportExtension;
import org.apache.ibatis.annotations.Param;

public interface TestPlanReportExtensionMapper {
    int insert(TestPlanReportExtension record);

    int insertSelective(TestPlanReportExtension record);

    TestPlanReportExtension selectByPrimaryKey(String id);

    TestPlanReportExtension selectByReportId(@Param("reportId") String reportId);

    int updateByPrimaryKeySelective(TestPlanReportExtension record);

    int updateByPrimaryKey(TestPlanReportExtension record);

    int deleteByReportId(@Param("reportId") String reportId);
}
