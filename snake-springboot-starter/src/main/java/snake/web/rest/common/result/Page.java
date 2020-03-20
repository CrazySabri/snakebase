package snake.web.rest.common.result;

import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author: sabri
 * @date: 2019/8/2 13:48
 * @description:
 */
public class Page<T> implements Serializable {

    private List<T> records = Collections.emptyList();

    private long total = 0;

    private long size = 10;

    private long current = 1;

    private String[] ascs;

    private String[] descs;

    private boolean optimizeCountSql = true;

    private boolean isSearchCount = true;

    public Page() {
    }

    public Page(long size, long current) {
        this(size, current, 0);
    }

    public Page(long size, long current, long total) {
        this.size = size;
        this.current = current;
        this.total = total;
    }

    public Page(long size, long current, boolean isSearchCount) {
        this(size, current, 0, isSearchCount);
    }



    public Page(long size, long current, long total, boolean isSearchCount) {
        if (current < 1) {
            this.current = 1;
        }
        this.size = size;
        this.current = current;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    public Page(long size, long current, long total, List<T> data) {
        this.size = size;
        this.current = current;
        this.total = total;
        this.records = data;
    }

    public boolean hasPrevious() {
        return this.current > 1;
    }

    public long getSize() {
        return this.size;
    }

    public long getTotal() {
        return this.total;
    }

    public List<T> getRecords() {
        return records;
    }

    public Page<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    public Page<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    public long getCurrent() {
        return this.current;
    }

    public Page<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    public String[] getAscs() {
        return ascs;
    }

    public Page<T> setAsc(String... ascs) {
        this.ascs = ascs;
        return this;
    }

    public Page<T> setAscs(List<String> ascs) {
        if (!CollectionUtils.isEmpty(ascs)) {
            this.ascs = ascs.toArray(new String[0]);
        }
        return this;
    }



    public String[] getDescs() {
        return descs;
    }

    public Page<T> setDesc(String... descs) {
        this.descs = descs;
        return this;
    }

    public Page<T> setDescs(List<String> descs) {
        if (!CollectionUtils.isEmpty(descs)) {
            this.descs = descs.toArray(new String[0]);
        }
        return this;
    }

    public long getPages() {
        if (getSize() == 0) {
            return 0L;
        }

        long pages = getTotal() / getSize();
        if (getTotal() % getSize() != 0) {
            pages++;
        }
        return pages;
    }

    public boolean hasNext() {
        return this.current < this.getPages();
    }

    public long offset() {
        return getCurrent() > 0 ? (getCurrent() - 1) * getSize() : 0;
    }

    public boolean isOptimizeCountSql() {
        return optimizeCountSql;
    }

    public Page<T> setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }

    public boolean isSearchCount() {
        return isSearchCount;
    }

    public Page<T> setSearchCount(boolean searchCount) {
        isSearchCount = searchCount;
        return this;
    }
}
