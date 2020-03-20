package snake.config.fastjson;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: sabri
 * @date: 2019/8/2 13:43
 * @description: 数据脱敏过滤器
 */
public class DataMaskingFilter implements PropertyPreFilter {

    private Set<String> excludes = new HashSet<>();

    public DataMaskingFilter() {
        super();
        excludes.add("password");
        excludes.add("secret");
    }

    public DataMaskingFilter(String... properties) {
        super();
        for (String p : properties) {
            if (null != p) {
               excludes.add(p);
            }
        }
    }

    @Override
    public boolean apply(JSONSerializer serializer, Object object, String name) {
        if (null == object || excludes.size() == 0) {
            return true;
        }

        if (excludes.contains(name)) {
            return false;
        }
        return true;
    }
}
