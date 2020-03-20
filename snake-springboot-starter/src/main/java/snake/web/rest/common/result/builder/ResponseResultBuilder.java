package snake.web.rest.common.result.builder;

import com.alibaba.fastjson.JSON;
import org.springframework.http.MediaType;
import snake.web.rest.common.result.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: sabri
 * @date: 2019/8/2 13:47
 * @description:
 */
public abstract class ResponseResultBuilder {

    public static HttpServletResponse build(HttpServletResponse response, int status, boolean success,
                                            String code, String message) {
        Result<Void> result = new Result<>(success, code, message);

        response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
        response.setStatus(status);

        PrintWriter pw = null;

        try {
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (null != pw) {
            pw.println(JSON.toJSONString(result, false));
            pw.flush();
        }
        return response;
    }
}
