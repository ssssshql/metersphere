package io.metersphere.api.service.scenario;

import io.metersphere.api.domain.ApiScenarioEmailConfig;
import io.metersphere.api.dto.scenario.ApiScenarioEmailConfigDTO;
import io.metersphere.api.mapper.ApiScenarioEmailConfigMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ApiScenarioEmailConfigService {

    @Resource
    private ApiScenarioEmailConfigMapper apiScenarioEmailConfigMapper;

    /**
     * 获取场景邮件接收人配置
     */
    public ApiScenarioEmailConfigDTO getEmailConfig(String scenarioId) {
        ApiScenarioEmailConfig config = apiScenarioEmailConfigMapper.selectByPrimaryKey(scenarioId);
        ApiScenarioEmailConfigDTO dto = new ApiScenarioEmailConfigDTO();
        dto.setScenarioId(scenarioId);
        if (config != null) {
            dto.setEmailRecipients(config.getEmailRecipients());
        } else {
            dto.setEmailRecipients("");
        }
        return dto;
    }

    /**
     * 保存场景邮件接收人配置
     */
    public void saveEmailConfig(ApiScenarioEmailConfigDTO request, String userId) {
        ApiScenarioEmailConfig existConfig = apiScenarioEmailConfigMapper.selectByPrimaryKey(request.getScenarioId());
        long currentTime = System.currentTimeMillis();
        
        if (existConfig == null) {
            // 新增
            ApiScenarioEmailConfig config = new ApiScenarioEmailConfig();
            config.setScenarioId(request.getScenarioId());
            config.setEmailRecipients(request.getEmailRecipients());
            config.setCreateTime(currentTime);
            config.setUpdateTime(currentTime);
            config.setCreateUser(userId);
            config.setUpdateUser(userId);
            apiScenarioEmailConfigMapper.insert(config);
        } else {
            // 更新
            existConfig.setEmailRecipients(request.getEmailRecipients());
            existConfig.setUpdateTime(currentTime);
            existConfig.setUpdateUser(userId);
            apiScenarioEmailConfigMapper.updateByPrimaryKeySelective(existConfig);
        }
    }

    /**
     * 根据场景ID获取邮件接收人
     */
    public String getEmailRecipients(String scenarioId) {
        ApiScenarioEmailConfig config = apiScenarioEmailConfigMapper.selectByPrimaryKey(scenarioId);
        return config != null ? config.getEmailRecipients() : null;
    }
}
