package snake.web.rest.common.result;

/**
 * @author: sabri
 * @date: 2019/8/2 13:48
 * @description:
 */
public class ErrorResult extends Result<Void> {

    private boolean error = true;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
