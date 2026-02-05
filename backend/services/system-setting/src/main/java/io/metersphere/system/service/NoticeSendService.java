package io.metersphere.system.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.metersphere.api.domain.ApiScenarioEmailConfig;
import io.metersphere.api.domain.ApiScenarioReport;
import io.metersphere.api.mapper.ApiScenarioEmailConfigMapper;
import io.metersphere.api.mapper.ApiScenarioReportMapper;
import io.metersphere.plan.domain.TestPlanReportApiScenario;
import io.metersphere.plan.domain.TestPlanReportExtension;
import io.metersphere.plan.mapper.TestPlanReportApiScenarioMapper;
import io.metersphere.plan.mapper.TestPlanReportExtensionMapper;
import io.metersphere.project.domain.Project;
import io.metersphere.sdk.domain.Environment;
import io.metersphere.sdk.domain.EnvironmentBlob;
import io.metersphere.sdk.mapper.EnvironmentBlobMapper;
import io.metersphere.sdk.mapper.EnvironmentMapper;
import io.metersphere.sdk.util.LogUtils;
import io.metersphere.system.mapper.BaseProjectMapper;
import io.metersphere.system.notice.MessageDetail;
import io.metersphere.system.notice.NoticeModel;
import io.metersphere.system.notice.constants.NoticeConstants;
import io.metersphere.system.notice.sender.AbstractNoticeSender;
import io.metersphere.system.notice.sender.impl.*;
import io.metersphere.system.notice.utils.MessageTemplateUtils;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.springframework.ai.model.ModelOptionsUtils.OBJECT_MAPPER;

@Component
public class NoticeSendService {
    @Resource
    private MailNoticeSender mailNoticeSender;
    @Resource
    private WeComNoticeSender weComNoticeSender;
    @Resource
    private DingCustomNoticeSender dingCustomNoticeSender;
    @Resource
    private DingEnterPriseNoticeSender dingEnterPriseNoticeSender;
    @Resource
    private LarkNoticeSender larkNoticeSender;
    @Resource
    private InSiteNoticeSender inSiteNoticeSender;
    @Resource
    private WebhookNoticeSender webhookNoticeSender;
    @Resource
    private MessageDetailService messageDetailService;
    @Resource
    private ApiScenarioEmailConfigMapper apiScenarioEmailConfigMapper;
    @Resource
    private EnvironmentMapper environmentMapper;
    @Resource
    private ApiScenarioReportMapper apiScenarioReportMapper;
    @Resource
    private BaseProjectMapper baseProjectMapper;
    @Resource
    private EnvironmentBlobMapper environmentBlobMapper;
    @Resource
    private TestPlanReportExtensionMapper testPlanReportExtensionMapper;
    @Resource
    private TestPlanReportApiScenarioMapper testPlanReportApiScenarioMapper;

    private AbstractNoticeSender getNoticeSender(MessageDetail messageDetail) {
        AbstractNoticeSender noticeSender;
        switch (messageDetail.getType()) {
            case NoticeConstants.Type.MAIL -> noticeSender = mailNoticeSender;
            case NoticeConstants.Type.WE_COM -> noticeSender = weComNoticeSender;
            case NoticeConstants.Type.DING_TALK -> noticeSender = getDingSender(messageDetail.getDingType());
            case NoticeConstants.Type.LARK -> noticeSender = larkNoticeSender;
            case NoticeConstants.Type.CUSTOM -> noticeSender = webhookNoticeSender;
            default -> noticeSender = inSiteNoticeSender;
        }
        return noticeSender;
    }

    private AbstractNoticeSender getDingSender(String dingType) {
        if (StringUtils.equalsIgnoreCase(dingType, NoticeConstants.DingType.ENTERPRISE)) {
            return dingEnterPriseNoticeSender;
        }else{
            return dingCustomNoticeSender;
        }
    }

    /**
     * 在线操作发送通知
     */
    @Async("threadPoolTaskExecutor")
    public void send(String taskType, NoticeModel noticeModel) {
        setLanguage(noticeModel);
        try {
            String projectId = (String) noticeModel.getParamMap().get("projectId");
            List<MessageDetail> messageDetails = messageDetailService.searchMessageByTypeAndProjectId(taskType, projectId);
            // 异步发送通知
            messageDetails.stream()
                    .filter(messageDetail -> StringUtils.equals(messageDetail.getEvent(), noticeModel.getEvent()))
                    .forEach(messageDetail -> {
                        MessageDetail m = SerializationUtils.clone(messageDetail);
                        NoticeModel n = SerializationUtils.clone(noticeModel);
                        try {
                            this.getNoticeSender(m).send(m, n);
                        } catch (Exception e) {
                            LogUtils.error(e);
                        }
                    });

        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
        }
    }

    private static void setLanguage(NoticeModel noticeModel) {
        String language = (String) noticeModel.getParamMap().get("Language");
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        if (StringUtils.containsIgnoreCase(language,"US")) {
            locale = Locale.US;
        } else if (StringUtils.containsIgnoreCase(language,"TW")){
            locale = Locale.TAIWAN;
        }
        LocaleContextHolder.setLocale(locale);
    }

    public void setLanguage(String language) {
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        if (StringUtils.containsIgnoreCase(language,"US")) {
            locale = Locale.US;
        } else if (StringUtils.containsIgnoreCase(language,"TW")){
            locale = Locale.TAIWAN;
        }
        LocaleContextHolder.setLocale(locale);
    }

    /**
     * jenkins 和定时任务触发的发送
     */
    @Async("threadPoolTaskExecutor")
    public void sendJenkins(String triggerMode, NoticeModel noticeModel) {
        // api和定时任务调用不排除自己
        noticeModel.setExcludeSelf(false);
        try {
            List<MessageDetail> messageDetails;

            if (StringUtils.equals(triggerMode, NoticeConstants.Mode.SCHEDULE)) {
                messageDetails = messageDetailService.searchMessageByTestId(noticeModel.getTestId());
            } else {
                String projectId = (String) noticeModel.getParamMap().get("projectId");
                messageDetails = messageDetailService.searchMessageByTypeAndProjectId(triggerMode, projectId);
            }

            // 异步发送通知
            messageDetails.stream()
                    .filter(messageDetail -> StringUtils.equals(messageDetail.getEvent(), noticeModel.getEvent()))
                    .forEach(messageDetail -> {
                        MessageDetail m = SerializationUtils.clone(messageDetail);
                        NoticeModel n = SerializationUtils.clone(noticeModel);
                        try {
                            this.getNoticeSender(m).send(m, n);
                        } catch (Exception e) {
                            LogUtils.error(e);
                        }
                    });

        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
        }
    }

    /**
     * 后台触发的发送，没有session
     */
    @Async("threadPoolTaskExecutor")
    public void send(Project project, String taskType, NoticeModel noticeModel) {
        setLanguage(noticeModel);
        try {
            List<MessageDetail> messageDetails = messageDetailService.searchMessageByTypeAndProjectId(taskType, project.getId());
            // 异步发送通知
            messageDetails.stream()
                    .filter(messageDetail -> StringUtils.equals(messageDetail.getEvent(), noticeModel.getEvent()))
                    .forEach(messageDetail -> {
                        MessageDetail m = SerializationUtils.clone(messageDetail);
                        NoticeModel n = SerializationUtils.clone(noticeModel);
                        try {
                            this.getNoticeSender(m).send(m, n);
                        } catch (Exception e) {
                            LogUtils.error(e);
                        }
                    });

            // 直接获取场景的邮件配置信息，发送邮件
            sendScenarioReportEmail(noticeModel);

        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
        }
    }

    /**
     * 其他类型
     */
    @Async("threadPoolTaskExecutor")
    public void sendOther(String taskType, NoticeModel noticeModel, List<String> users, boolean excludeSelf) {
        //如果在线需要排除自己，也需要选定当前环境选择的语言
        if (excludeSelf) {
            setLanguage(noticeModel);
        }
        // 定时任务调用不排除自己
        noticeModel.setExcludeSelf(excludeSelf);
        try {
            String projectId = (String) noticeModel.getParamMap().get("projectId");
            List<MessageDetail> messageDetails = messageDetailService.searchMessageByTypeAndProjectId(taskType, projectId)
                    .stream()
                    .filter(messageDetail -> StringUtils.equals(messageDetail.getEvent(), noticeModel.getEvent()))
                    .toList();
            if (CollectionUtils.isEmpty(messageDetails)) {
                NoticeModel n = SerializationUtils.clone(noticeModel);
                MessageDetail m = buildMessageTails(taskType, noticeModel, users, projectId);
                inSiteNoticeSender.send(m, n);
            } else {
                // 异步发送通知
                messageDetails.stream()
                        .forEach(messageDetail -> {
                            MessageDetail m = SerializationUtils.clone(messageDetail);
                            if (CollectionUtils.isNotEmpty(users)) {
                                m.getReceiverIds().addAll(users);
                            }
                            NoticeModel n = SerializationUtils.clone(noticeModel);
                            try {
                                this.getNoticeSender(m).send(m, n);
                            } catch (Exception e) {
                                LogUtils.error(e);
                            }
                        });
            }

        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
        }
    }

    private static MessageDetail buildMessageTails(String taskType, NoticeModel noticeModel, List<String> users, String projectId) {
        MessageDetail m = new MessageDetail();
        Map<String, String> defaultTemplateTitleMap = MessageTemplateUtils.getDefaultTemplateSubjectMap();
        String defaultSubject = defaultTemplateTitleMap.get(taskType + "_" + noticeModel.getEvent());
        Map<String, String> defaultTemplateMap = MessageTemplateUtils.getDefaultTemplateMap();
        String defaultTemplate = defaultTemplateMap.get(taskType + "_" + noticeModel.getEvent());
        m.setReceiverIds(users);
        m.setProjectId(projectId);
        m.setEvent(noticeModel.getEvent());
        m.setTaskType(taskType);
        m.setTemplate(defaultTemplate);
        m.setSubject(defaultSubject);
        m.setType(NoticeConstants.Type.IN_SITE);
        return m;
    }

    /**
     * 发送场景测试报告邮件
     */
    private void sendScenarioReportEmail(NoticeModel noticeModel) {
        try {
            // 只有场景执行成功或误报时才发送邮件
            String event = noticeModel.getEvent();
            if (!StringUtils.equals(event, NoticeConstants.Event.SCENARIO_EXECUTE_SUCCESSFUL)
                    && !StringUtils.equals(event, NoticeConstants.Event.SCENARIO_EXECUTE_FAKE_ERROR)
                    && !StringUtils.equals(event, NoticeConstants.Event.SCENARIO_EXECUTE_FAILED)) {
                return;
            }

            Map<String, Object> paramMap = noticeModel.getParamMap();
            if (paramMap == null || paramMap.get("id") == null) {
                return;
            }
            String scenarioId = paramMap.get("id").toString();
            ApiScenarioEmailConfig emailConfig = apiScenarioEmailConfigMapper.selectByPrimaryKey(scenarioId);
            if (emailConfig == null || StringUtils.isBlank(emailConfig.getEmailRecipients())) {
                return;
            }

            // 解析邮件接收人
            String[] recipients = emailConfig.getEmailRecipients().split(",");
            if (recipients.length == 0) {
                return;
            }

            // 测试报告
            ApiScenarioReport lastReport = apiScenarioReportMapper.selectByPrimaryKey(getStringValue(paramMap, "lastReportId"));
            // 项目
            Project project = baseProjectMapper.selectByPrimaryKey(lastReport.getProjectId());

            // 构建邮件标题
            String scenarioName = getStringValue(paramMap, "name");
            String executionTime = formatTime(lastReport.getStartTime());
            String subject = String.format("【MS】%s-%s_%s_场景测试报告", project.getName(), scenarioName, executionTime);

            // 构建邮件正文
            String content = buildEmailContent(paramMap, lastReport, project);

            // 发送邮件
            mailNoticeSender.sendWithNoSign(subject, content, recipients, null);
            LogUtils.info("场景测试报告邮件发送成功，场景ID: {}, 收件人: {}", scenarioId, emailConfig.getEmailRecipients());
        } catch (Exception e) {
            LogUtils.error("发送场景测试报告邮件失败", e);
        }
    }

    /**
     * 构建邮件正文内容 - 简约风格
     */
    private String buildEmailContent(Map<String, Object> paramMap, ApiScenarioReport lastReport, Project project) {
        String scenarioName = getStringValue(paramMap, "name");
        boolean isSuccess = "SUCCESS".equalsIgnoreCase(getStringValue(paramMap, "lastReportStatus"));
        String statusColor = isSuccess ? "#00C261" : "#ED0303";
        String reportUrl = getStringValue(paramMap, "reportUrl");
        Environment environment = environmentMapper.selectByPrimaryKey(getStringValue(paramMap, "environmentId"));

        // 环境url
        String environmentUrl = "-";
        EnvironmentBlob environmentBlob = environmentBlobMapper.selectByPrimaryKey(environment.getId());
        if (environmentBlob != null) {
            String url = getHttpConfigFirstUrl(new String(environmentBlob.getConfig()));
            environmentUrl = url!=null?url:"-";
        }

        String reportStatus = lastReport.getStatus().equals("SUCCESS") ? "成功" : "失败";

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width,initial-scale=1.0'></head>");
        html.append("<body style='margin:0;padding:20px;font-family:Arial,sans-serif;font-size:14px;color:#333;'>");

        // 标题
        html.append("<h2 style='margin:0 0 20px 0;color:#783887;font-size:18px;'>").append(scenarioName).append("</h2>");

        // 信息列表
        html.append("<table cellpadding='0' cellspacing='0' border='0' style='line-height:1.8;'>");

        appendInfoRow(html, "项目名称", project.getName());
        appendInfoRow(html, "环境URL", environmentUrl);

        if(paramMap.containsKey("plan_report_api_scenario_id")){
            TestPlanReportApiScenario planReportApiScenario = testPlanReportApiScenarioMapper.selectByPrimaryKey(getStringValue(paramMap, "plan_report_api_scenario_id"));
            TestPlanReportExtension extension = testPlanReportExtensionMapper.selectByReportId(planReportApiScenario.getTestPlanReportId());
            if(extension!=null){
                appendInfoRow(html, "版本号", extension.getDeployVersion());
                appendInfoRow(html, "部署时间", formatTime(extension.getDeployTime()));
            }
        }

//        appendInfoRow(html, "环境", environment!=null? environment.getName():"未知环境");
        appendInfoRow(html, "执行人", getStringValue(paramMap, "OPERATOR"));
        html.append("<tr><td style='color:#666;padding-right:16px;'>测试结果</td><td style='font-weight:bold;color:").append(statusColor).append(";'>").append(reportStatus).append("</td></tr>");
        appendInfoRow(html, "测试开始时间", formatTime(lastReport.getStartTime()));
        appendInfoRow(html, "测试结束时间", formatTime(lastReport.getEndTime()));
        appendInfoRow(html, "接口总数", getStringValue(paramMap, "stepTotal"));
        appendInfoRow(html, "成功接口数", String.valueOf(lastReport.getSuccessCount()));
        appendInfoRow(html, "失败接口数", String.valueOf(lastReport.getErrorCount()));
        appendInfoRow(html, "成功率", lastReport.getRequestPassRate() + "%");
        html.append("</table>");

        // 报告链接
        if (StringUtils.isNotBlank(reportUrl)) {
            html.append("<p style='margin:20px 0 0 0;'>");
            html.append("<span style='color:#666;'>测试报告链接：</span>");
            html.append("<a href='").append(reportUrl).append("' style='color:#783887;'>").append(reportUrl).append("</a>");
            html.append("</p>");
        }

        html.append("</body></html>");
        return html.toString();
    }

    private void appendInfoRow(StringBuilder html, String label, String value) {
        html.append("<tr>");
        html.append("<td style='color:#666;padding-right:16px;'>").append(label).append("</td>");
        html.append("<td>").append(value != null && !value.isEmpty() ? value : "-").append("</td>");
        html.append("</tr>");
    }

    private String getStringValue(Map<String, Object> paramMap, String key) {
        Object value = paramMap.get(key);
        return value != null ? value.toString() : "";
    }

    private String formatTime(Object timestamp) {
        if (timestamp == null) {
            return "-";
        }
        try {
            long time = Long.parseLong(timestamp.toString());
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(new java.util.Date(time));
        } catch (Exception e) {
            return "-";
        }
    }

    /**
     * 从JSON文本中安全获取httpConfig第一个元素的url值
     * @param jsonText JSON字符串
     * @return url值（如果不存在/异常则返回null）
     */
    public static String getHttpConfigFirstUrl(String jsonText) {
        // 容错1：入参为空
        if (jsonText == null || jsonText.trim().isEmpty()) {
            return null;
        }

        try {
            // 解析JSON为JsonNode（树形结构，方便逐层获取）
            JsonNode rootNode = OBJECT_MAPPER.readTree(jsonText);

            // 容错2：httpConfig字段不存在 或 不是数组
            JsonNode httpConfigNode = rootNode.get("httpConfig");
            if (httpConfigNode == null || !httpConfigNode.isArray()) {
                return null;
            }

            // 容错3：httpConfig数组为空
            if (httpConfigNode.size() == 0) {
                return null;
            }

            // 获取第一个元素
            JsonNode firstHttpConfig = httpConfigNode.get(0);

            // 容错4：第一个元素没有url字段 或 url值为空
            JsonNode urlNode = firstHttpConfig.get("url");
            if (urlNode == null || urlNode.isNull() || urlNode.asText().trim().isEmpty()) {
                return null;
            }

            // 返回url的字符串值
            return urlNode.asText();

        } catch (JsonProcessingException e) {
            // 容错5：JSON格式错误导致解析失败
            System.err.println("JSON解析异常：" + e.getMessage());
            return null;
        } catch (Exception e) {
            // 兜底：其他未知异常
            System.err.println("获取url异常：" + e.getMessage());
            return null;
        }
    }

}
