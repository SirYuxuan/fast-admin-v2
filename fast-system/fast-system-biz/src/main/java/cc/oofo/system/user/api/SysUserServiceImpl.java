package cc.oofo.system.user.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cc.oofo.framework.core.service.BaseService;
import cc.oofo.system.menu.entity.SysMenu;
import cc.oofo.system.menu.mapper.SysMenuMapper;
import cc.oofo.system.user.dto.AuthUserDto;
import cc.oofo.system.user.entity.SysUser;
import jakarta.annotation.Resource;

/**
 * 系统用户服务接口实现类
 * 
 * @author Sir丶雨轩
 */
@Service
public class SysUserServiceImpl extends BaseService<SysUser> implements SysUserApi {

    @Resource
    private SysMenuMapper sysMenuMapper;

    /**
     * 根据用户名获取认证用户信息
     * 
     * @param username 用户名
     * @return 认证用户信息
     */
    public AuthUserDto getAuthUser(String username) {

        SysUser user = query().eq("username", username).one();
        if (user == null) {
            return null;
        }

        AuthUserDto authUserDto = new AuthUserDto();
        authUserDto.setId(user.getId());
        authUserDto.setUsername(user.getUsername());
        authUserDto.setPassword(user.getPassword());
        // 转换枚举
        authUserDto.setStatus(user.getStatus());
        return authUserDto;
    }

    /**
     * 获取用户的权限编码列表
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

}
