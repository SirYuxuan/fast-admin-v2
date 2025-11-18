package cc.oofo.system.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.oofo.framework.core.controller.BaseController;
import cc.oofo.framework.web.response.Ps;
import cc.oofo.framework.web.response.Rs;
import cc.oofo.system.user.dto.SysUserInfoDto;
import cc.oofo.system.user.entity.dto.SysUserDto;
import cc.oofo.system.user.entity.query.SysUserQuery;
import cc.oofo.system.user.service.SysUserService;

/**
 * 系统用户控制器
 * 
 * @author Sir丶雨轩
 * @since 2025/11/13
 */
@RestController
@RequestMapping(path = "/system/user")
public class SysUserController extends BaseController<SysUserService> {

    /**
     * 用户列表
     * 
     * @param query 查询参数
     * @return 结果
     */
    @GetMapping
    public Ps<SysUserDto> list(SysUserQuery query) {
        return Ps.ok(baseService.listUsers(query), baseService.countUsers(query));
    }

    /**
     * 添加用户
     * 
     * @param sysUserDto 用户信息
     * @return 结果
     */
    @PostMapping
    public Rs<Void> add(@RequestBody SysUserDto sysUserDto) {
        baseService.add(sysUserDto);
        return Rs.ok();
    }

    /**
     * 修改用户
     * 
     * @param sysUserDto 用户信息
     * @return 结果
     */
    @PutMapping
    public Rs<Void> edit(@RequestBody SysUserDto sysUserDto) {
        baseService.edit(sysUserDto);
        return Rs.ok();
    }

    /**
     * 删除用户
     * 
     * @param id 用户ID
     * @return 结果
     */
    @PostMapping("/{id}")
    public Rs<Void> del(@PathVariable String id) {
        baseService.del(id);
        return Rs.ok();
    }

    /**
     * 获取用户信息
     * 
     * @return 结果
     */
    @GetMapping(path = "/info")
    public Rs<SysUserInfoDto> info() {
        return Rs.ok(baseService.info());
    }

}