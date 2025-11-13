package cc.oofo.system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.oofo.framework.base.BaseController;
import cc.oofo.framework.base.resp.Rs;
import cc.oofo.system.entity.dto.SysMenuDto;
import cc.oofo.system.service.SysMenuService;

/**
 * 系统菜单控制器
 * 
 * @author Sir丶雨轩
 * @since 2025/11/13
 */
@RestController
@RequestMapping(path = "/sysMenu")
public class SysMenuController extends BaseController<SysMenuService> {

    /**
     * 获取当前用户的菜单树
     * 
     * @return 菜单树结果
     */
    @GetMapping(path = "/userMenu")
    public Rs<List<SysMenuDto>> userMenu() {
        return Rs.ok(baseService.userMenu());
    }

    /**
     * 删除菜单
     * 
     * @param id 菜单ID
     * @return 删除结果
     */
    @DeleteMapping(path = "/{id}")
    public Rs<Void> del(@PathVariable String id) {
        baseService.del(id);
        return Rs.ok();
    }

    /**
     * 获取所有菜单树
     * 
     * @return 菜单树结果
     */
    @GetMapping(path = "/list")
    public Rs<List<SysMenuDto>> list() {
        return Rs.ok(baseService.buildMenuTree(baseService.list(), true));
    }
}