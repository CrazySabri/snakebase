package snake.web.rest.common.result.builder;

import snake.web.rest.common.result.Page;
import snake.web.rest.common.result.PageResult;
import snake.web.rest.common.result.Result;

import java.util.List;

/**
 * @author: sabri
 * @date: 2019/8/2 13:48
 * @description:
 */
public class ResultBuilder {

    /**
     * 构建执行结果
     * @param success
     * @return
     */
    public static Result<Void> build(boolean success) {
        return new Result<>(success);
    }

    /**
     * 构建错误结果
     * @param code
     * @param message
     * @return
     */
    public static Result<Void> build(String code, String message) {
        return new Result<>(false, code, message);
    }

    /**
     * 构建响应结果信息
     * @param success
     * @param message
     * @return
     */
    public static Result<Void> build(boolean success, String message) {
        return new Result<>(success, message);
    }

    /**
     * 构建响应结果信息，包含响应代码，可以使成功或者错误信息
     * @param success
     * @param code
     * @param message
     * @return
     */
    public static Result<Void> build(boolean success, String code, String message) {
        return new Result<>(success, code, message);
    }

    /**
     * 构建数据结果
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> build(T data) {
        return new Result<>(true, data);
    }

    /**
     * 构建数据列表型结果集
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<List<T>> build(List<T> data) {
        return new Result<>(true,data);
    }

    /**
     * 构建数据结果
     * @param success
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> build(boolean success, T data) {
        return new Result<>(success, data);
    }

    /**
     * 构建分页结果
     * @param page
     * @param <T>
     * @return
     */
    public static <T> PageResult<List<T>> build(Page<T> page) {
        return build(true, page);
    }

    /**
     * 构建分业结果
     * @param current
     * @param size
     * @param total
     * @param data
     * @param <T>
     * @return
     */
    public static <T> PageResult<List<T>> build(long current, long size, long total, List<T> data) {
        return build(true, new Page<>(current, size, total, data));
    }

    /**
     * 构建分页结果
     * @param success
     * @param page
     * @param <T>
     * @return
     */
    public static <T> PageResult<List<T>> build(boolean success, Page<T> page) {
        return new PageResult<>(success, page.getCurrent(), page.getSize(), page.getTotal(), page
        .getPages(), page.getRecords());
    }

    public static <T> Result<T> build(boolean success, String message, T data) {
        return new Result<>(success, message, data);
    }




}
