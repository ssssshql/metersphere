package io.metersphere.plan.mapper;

import io.metersphere.plan.domain.TestPlanEmailConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestPlanEmailConfigMapper {

    int deleteByPrimaryKey(String testPlanId);

    int deleteByTestPlanIds(@Param("list") List<String> testPlanIds);

    int insert(TestPlanEmailConfig record);

    int insertSelective(TestPlanEmailConfig record);

    TestPlanEmailConfig selectByPrimaryKey(String testPlanId);

    int updateByPrimaryKeySelective(TestPlanEmailConfig record);

    int updateByPrimaryKey(TestPlanEmailConfig record);
}
