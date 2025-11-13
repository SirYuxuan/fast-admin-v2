package cc.oofo.system.entity.consts;

import java.util.Objects;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 系统用户状态枚举
 * 
 * @author Sir丶雨轩
 * @since 2025/11/13
 */
public enum SysUserStatus {

    /**
     * 正常
     */
    NORMAL(0, "正常"),
    /**
     * 冻结
     */
    FROZEN(1, "冻结"),
    /**
     * 锁定
     */
    LOCKING(2, "锁定");

    @EnumValue
    private final Integer value;
    private final String name;

    SysUserStatus(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer value() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Integer status) {
        return Objects.equals(status, this.value);
    }

}
