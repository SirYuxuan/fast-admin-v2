package cc.oofo.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cc.oofo.framework.base.BaseService;
import cc.oofo.framework.exception.BizException;
import cc.oofo.framework.utils.StpUtil;
import cc.oofo.system.entity.SysMenu;
import cc.oofo.system.entity.SysRolesMenus;
import cc.oofo.system.entity.dto.SysMenuDto;
import cc.oofo.system.mapper.SysMenuMapper;
import jakarta.annotation.Resource;

/**
 * 系统菜单服务类
 * 
 * @author Sir丶雨轩
 * @since 2025/11/13
 */
@Service
@Transactional
public class SysMenuService extends BaseService<SysMenu> {

    @Resource
    private SysMenuMapper sysMenuMapper;

    /**
     * 获取当前用户的菜单树
     * 
     * @param isButton 是否包含按钮类型菜单
     * @return 菜单树列表
     */
    public List<SysMenuDto> userMenu() {
        // 查询当前用户有权限的所有菜单
        List<SysMenu> menuList = sysMenuMapper.selectAllByUserId(StpUtil.getLoginId());

        // 转换为DTO并构建树形结构
        return buildMenuTree(menuList, false);
    }

    /**
     * 删除菜单
     * 
     * @param id 菜单ID
     * @return 删除结果
     */
    public void del(String id) {
        // 删除菜单及其子菜单。有子菜单不让删除
        if (query().eq("pid", id).exists()) {
            throw new BizException("该菜单存在子菜单，无法删除");
        }
        removeById(id);
        // 删除角色关系
        new SysRolesMenus().delete(new QueryWrapper<SysRolesMenus>().eq("menu_id", id));
    }

    /**
     * 构建菜单树
     * 
     * @param menuList 菜单列表
     * @return 菜单树
     */
    public List<SysMenuDto> buildMenuTree(List<SysMenu> menuList, Boolean isButton) {
        if (menuList == null || menuList.isEmpty()) {
            return new ArrayList<>();
        }

        // 过滤掉button类型的菜单，转换为DTO并按ID分组，便于查找
        Map<String, SysMenuDto> menuMap = menuList.stream()
                .filter(menu -> menu.getType() != null
                        && (isButton != null && isButton || !menu.getType().name().equalsIgnoreCase("BUTTON")))
                .map(this::convertToDto)
                .collect(Collectors.toMap(SysMenuDto::getId, dto -> dto));

        // 构建树形结构
        List<SysMenuDto> rootMenus = new ArrayList<>();

        for (SysMenuDto menu : menuMap.values()) {
            if (StringUtils.hasText(menu.getPid())) {
                // 有父菜单，添加到父菜单的children中
                SysMenuDto parent = menuMap.get(menu.getPid());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(menu);
                }
            } else {
                // 顶级菜单
                rootMenus.add(menu);
            }
        }

        // 对每个层级进行排序
        sortMenus(rootMenus);
        return rootMenus;
    }

    /**
     * 将SysMenu转换为SysMenuDto
     * 
     * @param menu 菜单实体
     * @return 菜单DTO
     */
    private SysMenuDto convertToDto(SysMenu menu) {
        SysMenuDto dto = new SysMenuDto();
        BeanUtils.copyProperties(menu, dto);

        // 设置type字段（枚举转字符串）
        if (menu.getType() != null) {
            dto.setType(menu.getType().name().toLowerCase());
        }

        // 设置status字段
        if (menu.getStatus() != null) {
            dto.setStatus(menu.getStatus().value());
        }

        // 构建meta对象
        SysMenuDto.MenuMeta meta = new SysMenuDto.MenuMeta();
        meta.setIcon(menu.getIcon());
        meta.setTitle(menu.getMetaTitle());
        meta.setOrder(menu.getMetaOrder());
        meta.setAffixTab(menu.getMetaAffixTab());
        meta.setBadge(menu.getMetaBadge());
        meta.setBadgeType(menu.getMetaBadgeType());
        meta.setBadgeVariants(menu.getMetaBadgeVariants());
        meta.setIframeSrc(menu.getMetaIframeSrc());
        meta.setLink(menu.getMetaLink());

        dto.setMeta(meta);
        dto.setAuthCode(menu.getCode()); // 权限编码

        return dto;
    }

    /**
     * 递归排序菜单
     * 
     * @param menus 菜单列表
     */
    private void sortMenus(List<SysMenuDto> menus) {
        if (menus == null || menus.isEmpty()) {
            return;
        }

        // 按order排序，没有order的放最后
        menus.sort((a, b) -> {
            Integer orderA = (a.getMeta() != null && a.getMeta().getOrder() != null)
                    ? a.getMeta().getOrder()
                    : Integer.MAX_VALUE;
            Integer orderB = (b.getMeta() != null && b.getMeta().getOrder() != null)
                    ? b.getMeta().getOrder()
                    : Integer.MAX_VALUE;

            int result = orderA.compareTo(orderB);
            if (result == 0) {
                // order相同时按id排序
                return a.getId().compareTo(b.getId());
            }
            return result;
        });

        // 递归排序子菜单
        for (SysMenuDto menu : menus) {
            if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
                sortMenus(menu.getChildren());
            }
        }
    }

}
