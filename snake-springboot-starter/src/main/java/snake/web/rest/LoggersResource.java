package snake.web.rest;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import snake.web.rest.vm.LoggerVM;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: sabri
 * @date: 2019/8/2 13:51
 * @description:
 */
@RestController
@RequestMapping("/management")
public class LoggersResource {

    @GetMapping("/logs")
    public List<LoggerVM> getList() {
        LoggerContext context = (LoggerContext)LoggerFactory.getILoggerFactory();
        return context.getLoggerList()
                .stream()
                .map(LoggerVM::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/logs")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeLevel(@RequestBody LoggerVM logJson) {
        LoggerContext context = (LoggerContext)LoggerFactory.getILoggerFactory();
        context.getLogger(logJson.getName()).setLevel(Level.valueOf(logJson.getLevel()));
    }

}
