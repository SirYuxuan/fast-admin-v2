package cc.oofo.system.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cc.oofo.database.provider.UserInfoProvider;
import cc.oofo.framework.core.service.BaseService;
import cc.oofo.system.menu.entity.SysMenu;
import cc.oofo.system.menu.mapper.SysMenuMapper;
import cc.oofo.system.user.api.SysUserApi;
import cc.oofo.system.user.dto.AuthUserDto;
import cc.oofo.system.user.dto.SysUserInfoDto;
import cc.oofo.system.user.entity.SysUser;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;

/**
 * 系统用户服务实现类
 * 
 * @author Sir丶雨轩
 * @since 2025/11/13
 */
@Service
public class SysUserService extends BaseService<SysUser> implements SysUserApi, UserInfoProvider {

    @Resource
    private SysMenuMapper sysMenuMapper;

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

    /**
     * 根据用户名获取认证用户信息
     * 
     * @param username 用户名
     * @return 认证用户信息
     */
    @Override
    public AuthUserDto getAuthUser(String username) {
        SysUser user = query().eq("username", username).one();
        if (user == null) {
            return null;
        }

        AuthUserDto authUserDto = new AuthUserDto();
        authUserDto.setId(user.getId());
        authUserDto.setUsername(user.getUsername());
        authUserDto.setPassword(user.getPassword());
        authUserDto.setStatus(user.getStatus());
        return authUserDto;
    }

    /**
     * 获取用户权限编码列表
     * 
     * @param userId 用户ID
     * @return 权限编码列表
     */
    @Override
    public List<String> getUserPermissionCodes(String userId) {
        List<SysMenu> menuList = sysMenuMapper.selectAllByUserId(userId);
        return menuList.stream()
                .map(SysMenu::getCode)
                .filter(StringUtils::hasText)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 根据用户ID获取用户昵称
     * 
     * @param userId 用户ID
     * @return 用户昵称
     */
    @Override
    public String getNicknameById(String userId) {
        SysUser user = getById(userId);
        return user != null ? user.getNickname() : "未知用户";
    }
}