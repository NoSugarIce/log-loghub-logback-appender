### 使用方法
在logback的配置文件xml中加入

```
    <!-- 阿里云日志服务输出 -->
    <appender name="ALI_LOG" class="com.dessert.log.logback.appender.LoghubLogbackAppender">
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>INFO</level>
        </filter>

        <projectName>${ali.log.projectName}</projectName>
        <logstore>${ali.log.logstore}</logstore>
        <topic>${ali.log.topic}</topic>
        <endpoint>${ali.log.endpoint}</endpoint>
        <accessKeyId>${aliyun.access.key.id}</accessKeyId>
        <accessKey>${aliyun.access.key.secret}</accessKey>

        <layout class="ch.qos.logback.classic.PatternLayout">
            <pattern>${LOG_PATTERN}</pattern>
        </layout>
    </appender>
    
    
    <root level="DEBUG">
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="ALI_LOG"/>
    </root>    
    
```