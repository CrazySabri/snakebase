package snake.aop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * @author: sabri
 * @date: 2019/8/2 13:40
 * @description:
 */
@Aspect
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);


    private final Environment env;

    public LoggingAspect(Environment env) {
        this.env = env;
    }

    /**
     * Pointcut that matcher all Respositoties, Services and RestControllers
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a pointcut;
    }

    @Pointcut("within(snake.web.rest..*)")
    public void applicationPackagePointcut() {
    }

    @AfterThrowing(pointcut = "springBeanPointcut() && applicationPackagePointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (env.acceptsProfiles("dev")) {
            LOGGER.error("Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    e.getCause() != null ? e.getCause() : "NULL",
                    e.getMessage(),
                    e
                    );
        } else {
            LOGGER.error("Exception in {}.{}() with cause = \'{}\'",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    e.getCause() != null ? e.getCause() : "NULL"
            );
        }
    }

    @Around("springBeanPointcut() && applicationPackagePointcut()")
    public void logAround(ProceedingJoinPoint point) throws Throwable{
        boolean flag = LOGGER.isDebugEnabled();
        if (flag) {
            LOGGER.debug("Enter : {}.{}() with arguments[s] = {}",
                    point.getSignature().getDeclaringTypeName(),
                    point.getSignature().getName(), Arrays.toString(point.getArgs()));
        }
        try {
            Object result = point.proceed();
            if (flag) {
                LOGGER.debug("Exit : {}.{}() with result = {}",
                        point.getSignature().getDeclaringTypeName(),
                        point.getSignature().getName(),
                        result);
            }

        } catch (IllegalArgumentException e) {
            LOGGER.error("Illegal argument : {} in {}.{}()",
                    Arrays.toString(point.getArgs()),
                    point.getSignature().getDeclaringTypeName(),
                    point.getSignature().getName());
            throw e;
        }
    }}
