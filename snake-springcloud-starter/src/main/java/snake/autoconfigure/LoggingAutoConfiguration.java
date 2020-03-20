package snake.autoconfigure;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.boolex.OnMarkerEvaluator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.filter.EvaluatorFilter;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.FilterReply;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.Iterator;

/**
 * @author: sabri
 * @date: 2019/8/2 13:58
 * @description:
 */
@ConditionalOnProperty(value = "snake.logstash.enabled")
@Configuration
@RefreshScope
public class LoggingAutoConfiguration {

    private static final String LOGSTASH_APPENDER_NAME = "LOGSTASH";

    private static final String ASYNC_LOGSTASH_APPENDER_NAME = "ASYNC_LOGSTASH";

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAutoConfiguration.class);

    private LoggerContext context = (LoggerContext)LoggerFactory.getILoggerFactory();

    private String appName;

    private String serverPort;

    private String version;

    @Value("${snake.logstash.host}")
    private String logstashHost;

    @Value("${snake.logstash.post}")
    private int logstashPort;

    @Value("${snake.logstash.queueSize}")
    private int logstashQueueSize;


    public LoggingAutoConfiguration(@Value("${spring.application.name}") String appName,
                                    @Value("${server.port}") String serverPort,
                                    @Value("${info.project.version}") String version) {
        this.appName = appName;
        this.serverPort = serverPort;
        this.version = version;
        addLogstashAppender(context);
        addContextListener(context);
        setMetricsMarkerLogbackFilter(context);
    }

    // config a log filter to remove "metrics" logs from all appenders except "Logstash" appender;
    private void setMetricsMarkerLogbackFilter(LoggerContext context) {
        LOGGER.info("Filtering Metrics logs from all appender except {} appender", LOGSTASH_APPENDER_NAME);
        OnMarkerEvaluator onMarkerFilterEvaluator = new OnMarkerEvaluator();
        onMarkerFilterEvaluator.setContext(context);
        onMarkerFilterEvaluator.addMarker("metrics");
        onMarkerFilterEvaluator.start();

        EvaluatorFilter<ILoggingEvent> metricsFilter = new EvaluatorFilter<>();
        metricsFilter.setContext(context);
        metricsFilter.setEvaluator(onMarkerFilterEvaluator);
        metricsFilter.setOnMatch(FilterReply.DENY);
        metricsFilter.start();

        for (ch.qos.logback.classic.Logger logger : context.getLoggerList()) {
            for (Iterator<Appender<ILoggingEvent>> it = logger.iteratorForAppenders(); it.hasNext();) {
                Appender<ILoggingEvent> appender = it.next();
                if (!appender.getName().equals(LOGSTASH_APPENDER_NAME)) {
                    appender.setContext(context);
                    appender.addFilter(metricsFilter);
                    appender.start();
                }
            }
        }


    }

    private void addLogstashAppender(LoggerContext context) {
        LOGGER.info("Initializing Logstash Logging");

        LogstashTcpSocketAppender logstashAppender = new LogstashTcpSocketAppender();
        logstashAppender.setContext(context);
        logstashAppender.setName(LOGSTASH_APPENDER_NAME);

        String optionalFields = "";
        String customFields = "{\"app_name\":\"" + appName + "\",\"app_port\":\"" + serverPort +
                "\"," + optionalFields + "\"version\":\"" + version + "\"}";

        LogstashEncoder logstashEncoder = new LogstashEncoder();
        logstashEncoder.setCustomFields(customFields);
        logstashAppender.addDestinations(new InetSocketAddress(logstashHost, logstashPort));

        ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
        throwableConverter.setRootCauseFirst(true);
        logstashEncoder.setThrowableConverter(throwableConverter);
        // 为什么2次
        logstashEncoder.setCustomFields(customFields);
        logstashAppender.setEncoder(logstashEncoder);
        logstashAppender.start();

        AsyncAppender asyncLogstashAppender = new AsyncAppender();
        asyncLogstashAppender.setContext(context);
        asyncLogstashAppender.setName(ASYNC_LOGSTASH_APPENDER_NAME);
        asyncLogstashAppender.setQueueSize(logstashQueueSize);
        asyncLogstashAppender.addAppender(logstashAppender);
        asyncLogstashAppender.start();

        context.getLogger("ROOT").addAppender(asyncLogstashAppender);



    }

    private void addContextListener(LoggerContext context) {
        LogbackLoggerContextListener loggerContextListener = new LogbackLoggerContextListener();
        loggerContextListener.setContext(context);
        context.addListener(loggerContextListener);
    }





    class LogbackLoggerContextListener extends ContextAwareBase implements LoggerContextListener {

        @Override
        public boolean isResetResistant() {
            return true;
        }

        @Override
        public void onStart(LoggerContext context) {
            addLogstashAppender(context);
        }

        @Override
        public void onReset(LoggerContext context) {
            addLogstashAppender(context);
        }

        @Override
        public void onStop(LoggerContext context) {

        }

        @Override
        public void onLevelChange(ch.qos.logback.classic.Logger logger, Level level) {

        }
    }
}
