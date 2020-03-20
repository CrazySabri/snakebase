package snake.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: sabri
 * @date: 2019/8/2 13:45
 * @description:
 */
public class IPUtils {

    public static String getRemoteAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        boolean flag = ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);

        if (flag) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (flag) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (flag) {
            ip = request.getRemoteAddr();
        }

        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "localhost";
        }

        String[] ips = ip.split(",");
        if (ips.length > 1) {
            ip = ips[0];
        }
        return ip;
    }
}
