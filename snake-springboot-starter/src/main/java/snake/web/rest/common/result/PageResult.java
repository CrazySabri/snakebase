package snake.web.rest.common.result;

import java.util.List;

/**
 * @author: sabri
 * @date: 2019/8/2 13:48
 * @description:
 */
public class PageResult<T> extends Result<T> {

    protected long current = 1;

    protected long size = 10;

    protected long pages;

    protected long total;

    public PageResult() {
        super();
    }

    public PageResult(boolean success, long current, long size, long pages, long total, T data) {
        super();
        this.success = success;
        this.current = current;
        this.size = size;
        this.pages = pages;
        this.total = total;
        this.data = data;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return String.format("PageResult [current=%s, size=%s, total=%s," +
                " pages=%s, success=%s, code=%s, message=%s, data=%s]",
                current, size, total, pages, success, code, message, data);
    }

    public static <E> PageResult<List<E>> ok(Page<E> page) {
        return new PageResult<>(true, page.getCurrent(),
                page.getSize(), page.getTotal(), page.getPages(), page.getRecords());
    }
}
