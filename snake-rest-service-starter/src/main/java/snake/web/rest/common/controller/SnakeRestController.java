package snake.web.rest.common.controller;

import snake.web.rest.common.result.PageResult;
import snake.web.rest.common.result.PageResultAdapter;
import snake.web.rest.common.result.Result;
import snake.web.rest.common.result.builder.ResultBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * CRUD 基础方法控制器
 *
 * @param <E> Entity Class 实体类型
 * @param <T> Service Class 服务类型
 */
public abstract class SnakeRestController<E, T extends IService<E>> {

    protected abstract T getService();

    /**
     * 分页排序子句，默认只支持倒序，复杂排序请重写page方法
     * @see #page
     * @return
     */
    protected String getPageOrderByCaluse() {
        return "id";
    }

    /**
     * 分页查询，支持基本条件筛选
     * @param current   当前页码
     * @param size      每页数据量
     * @param entity    筛选条件
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageResult<List<E>> page(
            @RequestParam(defaultValue = "1", required = false) int current,
            @RequestParam(defaultValue = "10", required = false) int size,
            E entity,
            HttpServletRequest request,
            HttpServletResponse response) {
        return ResultBuilder.build(
                PageResultAdapter.adapt(
                        getService().page(
                                new Page<E>(current, size),
                                new QueryWrapper<>(entity).orderByDesc(this.getPageOrderByCaluse())
                        )
                )
        );
    }

    /**
     * 查询单个，根据 ID 查询
     * @param id    ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<E> getById(@PathVariable long id) {
        return ResultBuilder.build(getService().getById(id));
    }

    /**
     * 查询单个，支持基本条件筛选
     * @param entity    筛选条件
     * @return
     */
    @RequestMapping(value = "/one", method = RequestMethod.GET)
    public Result<E> getOne(E entity) {
        return ResultBuilder.build(getService().getOne(new QueryWrapper<>(entity)));
    }

    /**
     * 查询总数，支持基本条件筛选
     * @param entity    筛选条件
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Result<Integer> count(E entity) {
        return ResultBuilder.build(getService().count(new QueryWrapper<>(entity)));
    }

    /**
     * 新建
     * @param entity    数据实体
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<E> create(@RequestBody E entity) {
        return ResultBuilder.build(getService().save(entity), entity);
    }

    /**
     * 根据 ID 更新
     * @param entity    数据实体
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody E entity) {
        return ResultBuilder.build(getService().updateById(entity));
    }

    /**
     * 保存，新建或者更新，@TableId 注解存在更新记录，否插入一条记录
     * @param entity    数据实体
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<E> createOrUpdate(@RequestBody E entity) {
        return ResultBuilder.build(getService().saveOrUpdate(entity), entity);
    }

    /**
     * 根据 ID 逻辑删除
     * @param id    ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result<Void> remove(@PathVariable long id) {
        return ResultBuilder.build(getService().removeById(id));
    }

    /**
     * 根据 ID 强制删除
     * @param id    ID
     * @return
     */
    @RequestMapping(value = "/{id}/force", method = RequestMethod.DELETE)
    public Result<Void> delete(@PathVariable long id) {
        return ResultBuilder.build(getService().removeById(id));
    }

}
