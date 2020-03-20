package snake.listener;

import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author: sabri
 * @date: 2019/8/2 13:58
 * @description:
 */
public class EnvironmentChangeListener implements
        ApplicationListener<EnvironmentChangeEvent> {

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        System.out.println("======================");
        System.out.println("Environment changed : " + event.getTimestamp());
        System.out.println("======================");
    }
}
