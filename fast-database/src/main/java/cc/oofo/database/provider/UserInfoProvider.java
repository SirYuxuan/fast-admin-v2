package cc.oofo.database.provider;

/**
 * 用户信息提供者接口
 * 
 * @author Sir丶雨轩
 * @date 2025/11/14
 */
public interface UserInfoProvider {
    /**
     * 获取用户昵称
     * 
     * @param userId 用户ID
     * @return 用户昵称
     */
    String getNicknameById(String userId);
}