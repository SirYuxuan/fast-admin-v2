package cc.oofo.system.role.controller;

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

import cc.oofo.framework.core.controller.BaseController;
import cc.oofo.framework.web.response.Ps;
import cc.oofo.framework.web.response.Rs;
import cc.oofo.system.role.entity.dto.SysRoleDto;
import cc.oofo.system.role.entity.dto.SysRoleSaveDto;
import cc.oofo.system.role.entity.dto.SysRoleSelectDto;
import cc.oofo.system.role.entity.query.SysRoleQuery;
import cc.oofo.system.role.service.SysRoleService;

/**
 * 系统角色控制器
 * 
 * @author Sir丶雨轩
 * @since 2025/11/17
 */
@RestController
@RequestMapping(path = "/system/role")
public class SysRoleController extends BaseController<SysRoleService> {

    /**
     * 获取角色列表
     * 
     * @return 角色列表
     */
    @GetMapping
    public Ps<SysRoleDto> list(SysRoleQuery query) {
        return Ps.ok(baseService.list(query), baseService.count(query));
    }

    /**
     * 获取角色下拉列表
     * 
     * @return 角色下拉列表
     */
    @GetMapping(path = "/select")
    public Rs<List<SysRoleSelectDto>> listRoleSelect() {
        return Rs.ok(baseService.listRoleSelect());
    }

    /**
     * 根据ID获取角色详情
     * 
     * @param id 角色ID
     * @return 角色详情
     */
    @GetMapping(path = "/{id}")
    public Rs<SysRoleDto> getById(@PathVariable String id) {
        return Rs.ok(baseService.getRoleById(id));
    }

    /**
     * 检查角色名称是否存在
     * 
     * @param id   角色ID（可选，编辑时传入）
     * @param name 角色名称
     * @return 是否存在
     */
    @GetMapping(path = "/nameExists")
    public Rs<Boolean> nameExists(@RequestParam(required = false) String id, @RequestParam String name) {
        return Rs.ok(baseService.nameExists(id, name));
    }

    /**
     * 添加角色
     * 
     * @param roleSaveDto 角色保存DTO
     * @return 操作结果
     */
    @PostMapping
    public Rs<Void> add(@RequestBody SysRoleSaveDto roleSaveDto) {
        baseService.addRole(roleSaveDto);
        return Rs.ok();
    }

    /**
     * 更新角色
     * 
     * @param roleSaveDto 角色保存DTO
     * @return 操作结果
     */
    @PutMapping
    public Rs<Void> update(@RequestBody SysRoleSaveDto roleSaveDto) {
        baseService.update(roleSaveDto);
        return Rs.ok();
    }

    /**
     * 删除角色
     * 
     * @param id 角色ID
     * @return 删除结果
     */
    @DeleteMapping(path = "/{id}")
    public Rs<Void> delete(@PathVariable String id) {
        baseService.deleteRole(id);
        return Rs.ok();
    }

}