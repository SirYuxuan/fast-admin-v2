package cc.oofo.database.handlers;

import java.sql.Timestamp;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import cc.oofo.database.provider.UserInfoProvider;
import cc.oofo.utils.IdUtil;
import cn.dev33.satoken.spring.SpringMVCUtil;
import cn.dev33.satoken.stp.StpUtil;

/**
 * 实体元对象处理器
 * 
 * @author Sir丶雨轩
 * @since 2025/11/13
 */
@Component
public class EntityMetaObjectHandler implements MetaObjectHandler {

    @Autowired(required = false)
    private UserInfoProvider userInfoProvider;

    /**
     * 插入填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "id", String.class, IdUtil.generateId());
        this.strictInsertFill(metaObject, "createdAt", Timestamp.class, new Timestamp(System.currentTimeMillis()));
        this.strictInsertFill(metaObject, "createdBy", String.class, getNickName());
        this.strictInsertFill(metaObject, "createdId", String.class, getId());
        this.strictInsertFill(metaObject, "isDeleted", Boolean.class, false);
        this.strictInsertFill(metaObject, "updatedAt", Timestamp.class, new Timestamp(System.currentTimeMillis()));
        this.strictInsertFill(metaObject, "updatedBy", String.class, getNickName());
        this.strictInsertFill(metaObject, "updatedId", String.class, getId());

    }

    /**
     * 更新填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间
        this.strictInsertFill(metaObject, "updatedAt", Timestamp.class, new Timestamp(System.currentTimeMillis()));
        this.strictInsertFill(metaObject, "updatedBy", String.class, getNickName());
        this.strictInsertFill(metaObject, "updatedId", String.class, getId());
    }

    /**
     * 获取当前登录用户ID
     * 
     * @return 用户ID
     */
    public String getId() {
        if (SpringMVCUtil.isWeb() && StpUtil.isLogin()) {
            return StpUtil.getLoginIdAsString();
        }
        return "NOT_LOGIN";
    }

    /**
     * 获取当前登录用户昵称
     * 
     * @return 昵称
     */
    public String getNickName() {
        if (userInfoProvider != null && SpringMVCUtil.isWeb() && StpUtil.isLogin()) {
            return userInfoProvider.getNicknameById(StpUtil.getLoginIdAsString());
        }
        return "系统管理员";
    }

}
