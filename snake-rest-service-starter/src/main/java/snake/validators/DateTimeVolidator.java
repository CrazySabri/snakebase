package snake.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import snake.annotations.DateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author: sabri
 * @date: 2019/10/26 15:23
 * @description:
 */
public class DateTimeVolidator implements ConstraintValidator<DateTime, String> {

    private final static Logger LOGGER = LoggerFactory.getLogger(DateTimeVolidator.class);

    private DateTime dateTime;

    @Override
    public void initialize(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 基本情况可通过前置基本校验完成（NonNull, NotBlank, NotEmpty）等
        if (null == value) {
            return true;
        }

        String format = dateTime.format();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            dateFormat.parse(value);
        } catch (ParseException e) {
            LOGGER.error("[DateTimeVolidator] parse error: value = {}", value);
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
