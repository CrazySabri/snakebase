package snake.autoconfigure;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import snake.config.fastjson.DataMaskingFilter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: sabri
 * @date: 2019/8/2 13:42
 * @description:
 */
@ConditionalOnClass(JSON.class)
@Configuration
public class FastJsonAutoConfiguration {

    @Resource
    private Environment env;

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);

        fastConverter.setSupportedMediaTypes(supportedMediaTypes);

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // fastjson建议关闭【后续版本自动关闭？】
        fastJsonConfig.getParserConfig().setAutoTypeSupport(false);

        if (env.acceptsProfiles("dev")) {
            // ?
            fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        }

        DataMaskingFilter filter = new DataMaskingFilter();
        fastJsonConfig.setSerializeFilters(filter);
        fastConverter.setFastJsonConfig(fastJsonConfig);

        return new HttpMessageConverters(fastConverter);
    }
}
