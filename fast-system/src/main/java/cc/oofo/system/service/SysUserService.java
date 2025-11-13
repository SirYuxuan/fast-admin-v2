package cc.oofo.system.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cc.oofo.framework.base.BaseService;
import cc.oofo.framework.utils.StpUtil;
import cc.oofo.system.entity.SysUser;
import cc.oofo.system.entity.dto.SysUserInfoDto;

/**
 * 系统用户服务类
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
        SysUser user = getById(StpUtil.getLoginId());
        SysUserInfoDto userInfoDto = new SysUserInfoDto();
        BeanUtils.copyProperties(user, userInfoDto);
        return userInfoDto;
    }
}
