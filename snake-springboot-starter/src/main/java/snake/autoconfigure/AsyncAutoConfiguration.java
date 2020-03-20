package snake.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import snake.utils.thread.ExceptionHandlingAsyncTaskExecutor;

import java.util.concurrent.Executor;

@ConditionalOnProperty(value = "snake.async.enabled")
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncAutoConfiguration implements AsyncConfigurer {

    private final Logger logger = LoggerFactory.getLogger(AsyncAutoConfiguration.class);

    private final int corePoolSize;

    private final int maxPoolSize;

    private final int queueCapacity;

    private final String applicationName;

    public AsyncAutoConfiguration(
            @Value("${spring.application.name}") String applicationName,
            @Value("${snake.async.corePoolSize}") int corePoolSize,
            @Value("${snake.async..maxPoolSize}") int maxPoolSize,
            @Value("${snake.async..queueCapacity}") int queueCapacity) {
        this.applicationName = applicationName;
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.queueCapacity = queueCapacity;
    }

    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        logger.debug("Creating Async Task Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(applicationName + "-Executor-");
        return new ExceptionHandlingAsyncTaskExecutor(executor);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }

}
