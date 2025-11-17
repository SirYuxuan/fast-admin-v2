package cc.oofo.system.role.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cc.oofo.framework.core.service.BaseService;
import cc.oofo.framework.exception.BizException;
import cc.oofo.system.permission.entity.SysRolesMenus;
import cc.oofo.system.role.entity.SysRole;
import cc.oofo.system.role.entity.dto.SysRoleDto;
import cc.oofo.system.role.entity.dto.SysRoleSaveDto;
import cc.oofo.system.role.entity.query.SysRoleQuery;
import cc.oofo.system.role.mapper.SysRoleMapper;

/**
 * 系统角色服务类
 * 
 * @author Sir丶雨轩
 * @since 2025/11/17
 */
@Service
@Transactional
public class SysRoleService extends BaseService<SysRole> {

    /**
     * 获取角色列表
     * 
     * @return 角色列表
     */
    public List<SysRoleDto> list(SysRoleQuery query) {
        return ((SysRoleMapper) baseMapper).listRoleWithPermissions(query);
    }

    /**
     * 统计角色数量
     * 
     * @param query 查询条件
     * @return 角色数量
     */
    public Long count(SysRoleQuery query) {
        return ((SysRoleMapper) baseMapper).countRoleWithPermissions(query);
    }

    /**
     * 添加角色
     * 
     * @param roleSaveDto 角色保存DTO
     */
    public void addRole(SysRoleSaveDto roleSaveDto) {

        if (roleSaveDto == null) {
            throw new BizException("角色信息不能为空");
        }

        SysRole role = new SysRole();
        BeanUtils.copyProperties(roleSaveDto, role);
        role.setIsEnabled(roleSaveDto.getStatus() != null && roleSaveDto.getStatus() == 1);

        // 生成角色编码
        if (StringUtils.hasText(roleSaveDto.getName())) {
            role.setCode(generateRoleCode(roleSaveDto.getName()));
        }

        save(role);

        // 处理权限关联
        if (roleSaveDto.getPermissions() != null && !roleSaveDto.getPermissions().isEmpty()) {
            // 这里需要根据实际的权限关联表来实现
            roleSaveDto.getPermissions().forEach(perm -> {
                SysRolesMenus rolesMenus = new SysRolesMenus();
                rolesMenus.setRoleId(role.getId());
                rolesMenus.setMenuId(perm);
                // 保存关联
                rolesMenus.insert();
            });
        }
    }

    /**
     * 更新角色
     * 
     * @param roleSaveDto 角色保存DTO
     */
    public void update(SysRoleSaveDto roleSaveDto) {
        if (roleSaveDto == null || !StringUtils.hasText(roleSaveDto.getId())) {
            throw new BizException("角色ID不能为空");
        }

        SysRole role = getById(roleSaveDto.getId());
        if (role == null) {
            throw new BizException("角色不存在");
        }

        BeanUtils.copyProperties(roleSaveDto, role);
        role.setIsEnabled(roleSaveDto.getStatus() != null && roleSaveDto.getStatus() == 1);

        updateById(role);

        if (roleSaveDto.getPermissions() != null) {
            // 删除原始权限
            LambdaQueryWrapper<SysRolesMenus> wrapper = Wrappers.lambdaQuery(SysRolesMenus.class)
                    .eq(SysRolesMenus::getRoleId, role.getId());
            new SysRolesMenus().delete(wrapper);
            // 添加新的权限关联
            roleSaveDto.getPermissions().forEach(perm -> {
                SysRolesMenus rolesMenus = new SysRolesMenus();
                rolesMenus.setRoleId(role.getId());
                rolesMenus.setMenuId(perm);
                // 保存关联
                rolesMenus.insert();
            });
        }
    }

    /**
     * 删除角色
     * 
     * @param id 角色ID
     */
    public void deleteRole(String id) {
        removeById(id);
        LambdaQueryWrapper<SysRolesMenus> wrapper = Wrappers.lambdaQuery(SysRolesMenus.class)
                .eq(SysRolesMenus::getRoleId, id);
        new SysRolesMenus().delete(wrapper);
    }

    /**
     * 检查角色名称是否存在
     * 
     * @param id   角色ID（编辑时传入）
     * @param name 角色名称
     * @return 是否存在
     */
    public boolean nameExists(String id, String name) {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery(SysRole.class)
                .eq(SysRole::getName, name);

        if (StringUtils.hasText(id)) {
            wrapper.ne(SysRole::getId, id);
        }

        return exists(wrapper);
    }

    /**
     * 获取单个角色详情（带权限）
     * 
     * @param id 角色ID
     * @return 角色DTO
     */
    public SysRoleDto getRoleById(String id) {
        SysRole role = getById(id);
        if (role == null) {
            return null;
        }

        SysRoleDto dto = new SysRoleDto();
        BeanUtils.copyProperties(role, dto);
        dto.setStatus(role.getIsEnabled() != null && role.getIsEnabled() ? 1 : 0);

        // 获取权限列表
        List<SysRolesMenus> rolesMenusList = new SysRolesMenus()
                .selectList(Wrappers.lambdaQuery(SysRolesMenus.class)
                        .eq(SysRolesMenus::getRoleId, role.getId()));

        dto.setPermissions(rolesMenusList.stream()
                .map(SysRolesMenus::getMenuId)
                .collect(Collectors.toList()));

        return dto;
    }

    /**
     * 生成角色编码
     * 
     * @param name 角色名称
     * @return 角色编码
     */
    private String generateRoleCode(String name) {
        // 简单的编码生成规则，可以根据实际需求调整
        return "ROLE_" + name.toUpperCase().replaceAll("\\s+", "_");
    }

}