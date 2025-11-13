package cc.oofo.system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cc.oofo.framework.base.BaseController;
import cc.oofo.framework.base.resp.Rs;
import cc.oofo.system.entity.dto.SysMenuDto;
import cc.oofo.system.entity.dto.SysMenuSaveDto;
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
     * 检查菜单名称是否存在
     * 
     * @param id   菜单ID（可选，编辑时传入）
     * @param name 菜单名称
     * @return 是否存在
     */
    @GetMapping(path = "/nameExists")
    public Rs<Boolean> nameExists(@RequestParam(required = false) String id, @RequestParam String name) {
        return Rs.ok(baseService.nameExists(id, name));
    }

    /**
     * 检查菜单路径是否存在
     * 
     * @param id   菜单ID（可选，编辑时传入）
     * @param path 菜单路径
     * @return 是否存在
     */
    @GetMapping(path = "/pathExists")
    public Rs<Boolean> pathExists(@RequestParam(required = false) String id, @RequestParam String path) {
        return Rs.ok(baseService.pathExists(id, path));
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
     * 添加菜单
     * 
     * @param menuSaveDto 菜单保存DTO
     * @return 操作结果
     */
    @PostMapping
    public Rs<Void> add(@RequestBody SysMenuSaveDto menuSaveDto) {
        baseService.add(menuSaveDto);
        return Rs.ok();
    }

    /**
     * 更新菜单
     * 
     * @param menuSaveDto 菜单保存DTO
     * @return 操作结果
     */
    @PutMapping(path = "/{id}")
    public Rs<Void> update(@PathVariable String id, @RequestBody SysMenuSaveDto menuSaveDto) {
        menuSaveDto.setId(id);
        baseService.update(menuSaveDto);
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