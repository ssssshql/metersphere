package io.metersphere.plan.service;

import io.metersphere.plan.domain.TestPlanEmailConfig;
import io.metersphere.plan.dto.TestPlanEmailConfigDTO;
import io.metersphere.plan.mapper.TestPlanEmailConfigMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class TestPlanEmailConfigService {

    @Resource
    private TestPlanEmailConfigMapper testPlanEmailConfigMapper;

    /**
     * 获取测试计划邮件接收人配置
     */
    public TestPlanEmailConfigDTO getEmailConfig(String testPlanId) {
        TestPlanEmailConfig config = testPlanEmailConfigMapper.selectByPrimaryKey(testPlanId);
        TestPlanEmailConfigDTO dto = new TestPlanEmailConfigDTO();
        dto.setTestPlanId(testPlanId);
        if (config != null) {
            dto.setEmailRecipients(config.getEmailRecipients());
        } else {
            dto.setEmailRecipients("");
        }
        return dto;
    }

    /**
     * 保存测试计划邮件接收人配置
     */
    public void saveEmailConfig(TestPlanEmailConfigDTO request, String userId) {
        TestPlanEmailConfig existConfig = testPlanEmailConfigMapper.selectByPrimaryKey(request.getTestPlanId());
        long currentTime = System.currentTimeMillis();
        
        if (existConfig == null) {
            // 新增
            TestPlanEmailConfig config = new TestPlanEmailConfig();
            config.setTestPlanId(request.getTestPlanId());
            config.setEmailRecipients(request.getEmailRecipients());
            config.setCreateTime(currentTime);
            config.setUpdateTime(currentTime);
            config.setCreateUser(userId);
            config.setUpdateUser(userId);
            testPlanEmailConfigMapper.insert(config);
        } else {
            // 更新
            existConfig.setEmailRecipients(request.getEmailRecipients());
            existConfig.setUpdateTime(currentTime);
            existConfig.setUpdateUser(userId);
            testPlanEmailConfigMapper.updateByPrimaryKeySelective(existConfig);
        }
    }

    /**
     * 根据测试计划ID获取邮件接收人
     */
    public String getEmailRecipients(String testPlanId) {
        TestPlanEmailConfig config = testPlanEmailConfigMapper.selectByPrimaryKey(testPlanId);
        return config != null ? config.getEmailRecipients() : null;
    }
}
