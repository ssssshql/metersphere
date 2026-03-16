package io.metersphere.api.mapper;

import io.metersphere.api.domain.ApiScenarioEmailConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApiScenarioEmailConfigMapper {

    int deleteByPrimaryKey(String scenarioId);

    int deleteByScenarioIds(@Param("list") List<String> scenarioIds);

    int insert(ApiScenarioEmailConfig record);

    int insertSelective(ApiScenarioEmailConfig record);

    ApiScenarioEmailConfig selectByPrimaryKey(String scenarioId);

    int updateByPrimaryKeySelective(ApiScenarioEmailConfig record);

    int updateByPrimaryKey(ApiScenarioEmailConfig record);
}
