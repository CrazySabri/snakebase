package snake.web.rest.common.result;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author: sabri
 * @date: 2019/8/2 14:30
 * @description: 分页对象适配器，将mybatis的转化为custom的
 */
public class PageResultAdapter {

    public static <T> Page<T> adapt(IPage<T> iPage) {
        return new Page<T>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords());
    }
}
