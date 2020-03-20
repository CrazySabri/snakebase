package snake.web.rest.common.result;

import java.util.Objects;

/**
 * @author: sabri
 * @date: 2019/8/2 13:49
 * @description:
 */
public class Result<T> {

    protected boolean success;

    protected String code;

    protected String message;


    protected T data;

    public Result() {
        super();
    }

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public Result(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("Result [success=%s, code=%s, message=%s, data=%s]",
                success, code, message, data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result)) return false;
        Result<?> result = (Result<?>) o;
        return success == result.success &&
                code.equals(result.code) &&
                message.equals(result.message) &&
                data.equals(result.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, code, message, data);
    }
}
