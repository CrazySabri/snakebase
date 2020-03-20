package snake.annotations;

import snake.validators.DateTimeVolidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: sabri
 * @date: 2019/10/26 15:15
 * @description:
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateTimeVolidator.class)
public @interface DateTime {

    String message() default "日期格式错误 --> yyyy-mm-dd";

    String format() default "yyyy-mm-dd";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
