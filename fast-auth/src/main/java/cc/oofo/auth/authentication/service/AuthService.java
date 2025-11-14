package cc.oofo.auth.authentication.service;

import org.springframework.stereotype.Service;

import cc.oofo.auth.authentication.dto.LoginDto;
import cc.oofo.framework.core.service.BaseService;
import cc.oofo.framework.exception.BizException;
import cc.oofo.framework.security.auth.StpUtil;
import cc.oofo.system.user.entity.SysUser;
import cc.oofo.system.user.entity.enums.SysUserStatus;
import cc.oofo.utils.PasswordUtil;

/**
 * 鉴权服务
 *
 * @author Sir丶雨轩
 * @since 2025/11/13
 */
@Service
public class AuthService extends BaseService<SysUser> {

    /**
     * 用户登录
     * 
     * @param loginDto 登录参数
     * @return token
     */
    public String login(LoginDto loginDto) {
        SysUser sysUser = query().eq("username", loginDto.getUsername()).one();
        if (sysUser == null) {
            throw new BizException("用户名或密码错误");
        }
        if (!PasswordUtil.verify(loginDto.getPassword(), sysUser.getPassword())) {
            throw new BizException("用户名或密码错误");
        }
        // 判断用户状态
        if (sysUser.getStatus() != SysUserStatus.NORMAL) {
            throw new BizException("您的账户已经被" + sysUser.getStatus().getName() + ", 请联系系统管理员.");
        }
        StpUtil.login(sysUser);
        return StpUtil.getTokenValue();
    }
}