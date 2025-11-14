package cc.oofo.system.user.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cc.oofo.framework.core.service.BaseService;
import cc.oofo.system.user.dto.SysUserInfoDto;
import cc.oofo.system.user.entity.SysUser;
import cn.dev33.satoken.stp.StpUtil;

/**
 * 系统用户服务实现类
 * 
 * @author Sir丶雨轩
 * @since 2025/11/13
 */
@Service
public class SysUserService extends BaseService<SysUser> {

    /**
     * 获取登录用户信息
     * 
     * @return 用户信息
     */
    public SysUserInfoDto info() {
        // 获取当前登录用户
        SysUser user = getById(StpUtil.getLoginIdAsString());
        SysUserInfoDto userInfoDto = new SysUserInfoDto();
        BeanUtils.copyProperties(user, userInfoDto);
        return userInfoDto;
    }
}