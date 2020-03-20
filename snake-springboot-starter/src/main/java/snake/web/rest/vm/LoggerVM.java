package snake.web.rest.vm;


import ch.qos.logback.classic.Logger;

/**
 * @author: sabri
 * @date: 2019/8/2 13:51
 * @description:
 */
public class LoggerVM {

    private String name;

    private String level;

    public LoggerVM() {
    }

    public LoggerVM(Logger logger) {
        this.name = logger.getName();
        this.level = logger.getEffectiveLevel().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return String.format("LoggerVM: [name=%s, level=%s]", name, level);
    }
}
