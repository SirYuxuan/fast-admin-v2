package cc.oofo.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.oofo.auth.entity.dto.LoginDto;
import cc.oofo.auth.entity.dto.LoginRsDto;
import cc.oofo.auth.service.AuthService;
import cc.oofo.framework.base.BaseController;
import cc.oofo.framework.base.resp.Rs;
import cn.dev33.satoken.stp.StpUtil;

/**
 * 鉴权控制器
 *
 * @author Sir丶雨轩
 * @since 2025/11/13
 */
@RestController
@RequestMapping(path = "/auth")
public class AuthController extends BaseController<AuthService> {

    /**
     * 登录
     * 
     * @param loginDto 登录信息
     * @return 结果
     */
    @PostMapping(path = "/login")
    public Rs<LoginRsDto> login(@RequestBody LoginDto loginDto) {
        LoginRsDto loginResult = new LoginRsDto();
        loginResult.setAccessToken(baseService.login(loginDto));
        return Rs.ok(loginResult);
    }

    /**
     * 登出
     * 
     * @return 结果
     */
    @PostMapping(path = "/logout")
    public Rs<Void> logout() {
        StpUtil.logout();
        return Rs.ok();
    }
}
