package snake.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import snake.web.rest.common.result.Result;
import snake.web.rest.common.result.builder.ResultBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: sabri
 * @date: 2019/10/26 15:38
 * @description: 全局统一异常处理【filter除外】
 */ 
@RestController
@Slf4j
public class OnlineGlobalException {

    /**
     * 方法参数校验异常处理
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Result<Void> handleValidationException(HttpServletRequest request, ConstraintViolationException ex) {
        log.error("异常：uri = {}, ex = {}", request.getRequestURI(), ex);
        String collect = ex.getConstraintViolations().stream()
                .filter(Objects::nonNull)
                .map(cv -> cv == null?"null":cv.getPropertyPath()+": " + cv.getMessage())
                .collect(Collectors.joining(","));
        log.info("请求参数异常 {}", collect);
        return ResultBuilder.build(false, String.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getMessage());
    }

    public Result<Void> methodArgumentValidationHandler(HttpServletRequest request, MethodArgumentNotValidException ex) {
        log.error("异常：uri = {}, ex = {}", request.getRequestURI(), ex);
        
        return ResultBuilder.build(false, String.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getMessage());
    }


}
