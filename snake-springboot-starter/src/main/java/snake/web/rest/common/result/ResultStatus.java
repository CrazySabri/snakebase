package snake.web.rest.common.result;

/**
 * @author: sabri
 * @date: 2019/8/2 13:49
 * @description:
 */
public enum ResultStatus {

    FORBIDDEN("100404", "禁止访问"),

    NOT_ACCESSABLE("100406", "请求拒绝"),

    REQUEST_TIMEOUT("100408", "请求超时");



    private final String code;

    private final String message;

    ResultStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }}
