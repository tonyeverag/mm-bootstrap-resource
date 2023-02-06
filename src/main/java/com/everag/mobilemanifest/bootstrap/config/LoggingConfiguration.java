package com.everag.mobilemanifest.bootstrap.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.ThresholdFilter;
import io.logz.logback.LogzioLogbackAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class LoggingConfiguration {
    private final Logger log = LoggerFactory.getLogger(LoggingConfiguration.class);

    private LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DairyProperties dairyProperties;

    @PostConstruct
    private void init() {
        addLogzioAppender();
    }

    public void addLogzioAppender() {
        if (dairyProperties.getLogging().getLogstash().isEnabled()) {
            log.info("Initializing Logzio Appender");
            LogzioLogbackAppender logzioAppender = new LogzioLogbackAppender();
            logzioAppender.setName("LOGZIO");
            logzioAppender.setLogzioUrl(dairyProperties.getLogging().getLogstash().getHost());
            logzioAppender.setContext(context);
            logzioAppender.setLogzioType(dairyProperties.getLogging().getLogstash().getLogType());
            logzioAppender.setToken(dairyProperties.getLogging().getLogstash().getToken());
            logzioAppender.setAddHostname(true);
            ThresholdFilter thresholdFilter = new ThresholdFilter();
            thresholdFilter.setLevel(Level.INFO.toString());
            logzioAppender.addFilter(thresholdFilter);
            PatternLayoutEncoder patternEncoder = new PatternLayoutEncoder();
            patternEncoder.setPattern("%d{yy/MM/dd HH:mm:ss} {%t} %p %c{2}: %m");

            logzioAppender.start();

            context.getLogger("ROOT").addAppender(logzioAppender);
        }
    }
}
