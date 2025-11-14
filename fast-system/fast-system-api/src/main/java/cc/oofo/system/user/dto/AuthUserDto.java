package cc.oofo.system.user.dto;

import cc.oofo.system.user.enums.SysUserStatus;

/**
 * 认证用户DTO
 * 
 * @author Sir丶雨轩
 * @since 2025/11/14
 */
public class AuthUserDto {

    private String id;
    private String username;
    private String password;
    private SysUserStatus status;

    /**
     * 验证用户状态是否有效
     * 
     * @return 是否有效
     */
    public boolean isStatusValid() {
        return status == SysUserStatus.NORMAL;
    }

    /**
     * 获取状态错误消息
     * 
     * @return 状态错误消息
     */
    public String getStatusMessage() {
        return "您的账户已经被" + status.getName() + ", 请联系系统管理员.";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SysUserStatus getStatus() {
        return status;
    }

    public void setStatus(SysUserStatus status) {
        this.status = status;
    }
}