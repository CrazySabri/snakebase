package snake.security.jwt;

/**
 * @author: sabri
 * @date: 2019/8/2 13:43
 * @description:
 */
public class Token {

    protected String uid;

    protected String ip;

    protected boolean rememberMe;

    protected String token;

    protected String expires;

    public Token() {
        super();
    }

    public Token(String uid, String ip, boolean rememberMe, String token, String expires) {
        super();
        this.uid = uid;
        this.ip = ip;
        this.rememberMe = rememberMe;
        this.token = token;
        this.expires = expires;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRemenberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    @Override
    public String toString() {
        return String.format("Token = [uid=%s, ip=%s, rememberMe=%s, token=%s, expires=%s",
                uid, ip, rememberMe, token, expires);
    }
}
