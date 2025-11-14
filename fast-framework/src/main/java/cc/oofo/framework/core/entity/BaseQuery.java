package cc.oofo.framework.core.entity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

@Data
public class BaseQuery<T> {

    /**
     * 分页 页码
     */
    private long page;
    /**
     * 分页 每页大小
     */
    private long size;

    private final QueryWrapper<T> queryWrapper = new QueryWrapper<>();

    /**
     * 获取MyBatis的分页对象
     *
     * @return 分页对象
     */
    public Page<T> getPage() {
        return new Page<T>(page, size);
    }

}
