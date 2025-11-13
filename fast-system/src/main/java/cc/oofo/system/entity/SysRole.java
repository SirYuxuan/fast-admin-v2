package cc.oofo.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import cc.oofo.framework.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色实体类
 * 
 * @author Sir丶雨轩
 * @since 2025/11/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class SysRole extends BaseEntity<SysRole> {

    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色编码
     */
    private String code;
    /**
     * 角色备注
     */
    private String remark;
    /**
     * 是否启用
     */
    private Boolean isEnabled;

}
