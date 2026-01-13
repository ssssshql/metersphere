package io.metersphere.system.service;


import io.metersphere.api.domain.ApiScenarioEmailConfig;
import io.metersphere.api.mapper.ApiScenarioEmailConfigMapper;
import io.metersphere.project.domain.Project;
import io.metersphere.sdk.util.LogUtils;
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

import java.util.List;
import java.util.Locale;
import java.util.Map;

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
                    && !StringUtils.equals(event, NoticeConstants.Event.SCENARIO_EXECUTE_FAKE_ERROR)) {
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

            // 构建邮件标题
            String scenarioName = getStringValue(paramMap, "name");
            String executionTime = formatTime(paramMap.get("startTime"));
            String subject = String.format("【MS】%s_%s_场景测试报告", scenarioName, executionTime);

            // 构建邮件正文
            String content = buildEmailContent(paramMap);

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
    private String buildEmailContent(Map<String, Object> paramMap) {
        String scenarioName = getStringValue(paramMap, "name");
        String reportStatus = getStringValue(paramMap, "reportStatus");
        boolean isSuccess = "成功".equals(reportStatus) || "SUCCESS".equalsIgnoreCase(getStringValue(paramMap, "lastReportStatus"));
        String statusColor = isSuccess ? "#00C261" : "#ED0303";
        String reportUrl = getStringValue(paramMap, "reportUrl");

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width,initial-scale=1.0'></head>");
        html.append("<body style='margin:0;padding:20px;font-family:Arial,sans-serif;font-size:14px;color:#333;'>");

        // 标题
        html.append("<h2 style='margin:0 0 20px 0;color:#783887;font-size:18px;'>").append(scenarioName).append("</h2>");

        // 信息列表
        html.append("<table cellpadding='0' cellspacing='0' border='0' style='line-height:1.8;'>");
        appendInfoRow(html, "环境", getStringValue(paramMap, "environment"));
        appendInfoRow(html, "执行人", getStringValue(paramMap, "OPERATOR"));
        html.append("<tr><td style='color:#666;padding-right:16px;'>测试结果</td><td style='font-weight:bold;color:").append(statusColor).append(";'>").append(reportStatus).append("</td></tr>");
        appendInfoRow(html, "测试开始时间", formatTime(paramMap.get("startTime")));
        appendInfoRow(html, "测试结束时间", formatTime(paramMap.get("endTime")));
        appendInfoRow(html, "接口总数", getStringValue(paramMap, "stepTotal"));
        appendInfoRow(html, "成功接口数", getStringValue(paramMap, "successCount"));
        appendInfoRow(html, "失败接口数", getStringValue(paramMap, "errorCount"));
        appendInfoRow(html, "成功率", getStringValue(paramMap, "requestPassRate") + "%");
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

}
