package snake.web.rest.common.result.builder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import snake.web.rest.common.result.Page;
import snake.web.rest.common.result.PageResult;
import snake.web.rest.common.result.Result;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

/**
 * @author: sabri
 * @date: 2019/8/2 13:47
 * @description:
 */
public class ResponseEntityBuilder {

    public static <T extends Result<T>> ResponseEntity<T> build(T result) {
        return new ResponseEntity<>(result, OK);
    }

    public static <T> ResponseEntity<Result<T>> build(boolean success) {
        return new ResponseEntity<>(new Result<>(success), OK);
    }

    public static ResponseEntity<Result<Integer>> build(int count) {
        return new ResponseEntity<>(new Result<>(true, count), OK);
    }

    public static <T> ResponseEntity<Result<List<T>>> build(List<T> list) {
        return new ResponseEntity<>(new Result<>(true, list), OK);
    }

    public static ResponseEntity<Result<Void>> build(boolean success, String message) {
        return new ResponseEntity<>(new Result<>(success, message), OK);
    }

    public static <T> ResponseEntity<Result<T>> build(boolean success, T data) {
        return new ResponseEntity<>(new Result<>(success, data), OK);
    }

    public static <T> ResponseEntity<PageResult<List<T>>> build(Page<T> page) {
        return build(page, true, OK);
    }

    public static <T> ResponseEntity<PageResult<List<T>>> build(long current, long size, long total ,List<T> data) {
        return build(new Page<>(current, size, total ,data), true, OK);
    }

    public static <T> ResponseEntity<PageResult<List<T>>> build(Page<T> page, boolean success, HttpStatus status) {
        PageResult<List<T>> result = new PageResult<>();
        result.setSuccess(success);
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        result.setTotal(page.getTotal());
        result.setPages(page.getPages());
        result.setData(page.getRecords());

        ResponseEntity<PageResult<List<T>>> entity = new ResponseEntity<>(result, status);
        return entity;
    }

}
