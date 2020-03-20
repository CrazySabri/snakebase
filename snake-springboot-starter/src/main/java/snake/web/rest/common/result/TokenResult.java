package snake.web.rest.common.result;

/**
 * @author: sabri
 * @date: 2019/8/2 13:49
 * @description:
 */
public class TokenResult<T> extends Result<T> {

    protected String token;

    protected Long expires;

    public TokenResult() {
        super();
    }

    public TokenResult(boolean success) {
        super(success);
    }

    public TokenResult(boolean success, String message) {
        super(success, message);
    }

    public TokenResult(boolean success, String token, Long expires) {
        super(success);
        this.setToken(token);
        this.setExpires(expires);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }
}
