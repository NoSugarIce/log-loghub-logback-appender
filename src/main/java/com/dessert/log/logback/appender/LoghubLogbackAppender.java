package com.dessert.log.logback.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.aliyun.openservices.log.common.LogItem;
import com.aliyun.openservices.log.producer.LogProducer;
import com.aliyun.openservices.log.producer.ProducerConfig;
import com.aliyun.openservices.log.producer.ProjectConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dingjingyang@foxmail.com(dingjingyang)
 * @date 2017/10/11
 */
public class LoghubLogbackAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private Layout<ILoggingEvent> layout;

    private String logstore;
    private ProducerConfig config = new ProducerConfig();
    private ProjectConfig projectConfig = new ProjectConfig();
    private LogProducer producer;
    private String topic;
    private SimpleDateFormat formatter;

    @Override
    protected void append(ILoggingEvent event) {
        List<LogItem> logItems = new ArrayList<>();
        LogItem item = new LogItem();
        logItems.add(item);
        item.SetTime((int) (event.getTimeStamp() / 1000));
        item.PushBack("time", formatter.format(new Date(event.getTimeStamp())));
        item.PushBack("level", event.getLevel().toString());
        item.PushBack("thread", event.getThreadName());
        item.PushBack("message", this.layout.doLayout(event));
        producer.send(projectConfig.projectName, logstore, topic, null, logItems);
    }

    @Override
    public void start() {
        super.start();
        final String timeFormat = "yyyy-MM-dd'T'HH:mm:ssZ";
        formatter = new SimpleDateFormat(timeFormat);
        producer = new LogProducer(config);
        producer.setProjectConfig(projectConfig);
    }

    @Override
    public void stop() {
        super.stop();
        if (producer != null) {
            producer.flush();
            producer.close();
        }
    }


    public Layout<ILoggingEvent> getLayout() {
        return this.layout;
    }

    public void setLayout(Layout<ILoggingEvent> layout) {
        this.layout = layout;
    }

    public String getProjectName() {
        return projectConfig.projectName;
    }

    public void setProjectName(String projectName) {
        projectConfig.projectName = projectName;
    }

    public String getLogstore() {
        return logstore;
    }

    public void setLogstore(String logstore) {
        this.logstore = logstore;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getEndpoint() {
        return projectConfig.endpoint;
    }

    public void setEndpoint(String endpoint) {
        projectConfig.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return projectConfig.accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        projectConfig.accessKeyId = accessKeyId;
    }

    public String getAccessKey() {
        return projectConfig.accessKey;
    }

    public void setAccessKey(String accessKey) {
        projectConfig.accessKey = accessKey;
    }

}
