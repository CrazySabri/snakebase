package snake.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import snake.aop.logging.LoggingAspect;

/**
 * @author: sabri
 * @date: 2019/8/2 13:42
 * @description:
 */
@ConditionalOnProperty(value = "snake.logging-aspect.enabled")
@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspectAutoConfiguration.class);

    @Bean
    public LoggingAspect loggingAspect(Environment env) {
        LOGGER.debug("Creating logging Aspect");
        return new LoggingAspect(env);
    }

}
