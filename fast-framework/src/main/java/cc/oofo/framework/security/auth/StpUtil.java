package cc.oofo.framework.security.auth;

import cc.oofo.framework.core.entity.BaseEntity;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.stp.StpLogic;

public class StpUtil {

    private static final String USER_INFO = "userInfo";
    /**
     * 底层的 STP_LOGIC 对象
     */
    private static final StpLogic STP_LOGIC = cn.dev33.satoken.stp.StpUtil.getStpLogic();

    /**
     * 获取指定Token对应的账号id，如果未登录，则返回 null
     * 
     * @param tokenValue token
     * @return 账号id
     */
    public static String getLoginId(String tokenValue) {
        return STP_LOGIC.getLoginIdByToken(tokenValue).toString();
    }

    /**
     * 获取当前会话账号id, 如果未登录，则抛出异常
     * 
     * @return 账号id
     */
    public static String getLoginId() {
        return STP_LOGIC.getLoginId().toString();
    }

    /**
     * 会话登录并保存用户信息
     *
     * @param user 用户信息
     */
    public static void login(BaseEntity<?> user) {
        STP_LOGIC.login(user.getId());
        STP_LOGIC.getSession().set(USER_INFO, user);
    }

    /**
     * 获取当前登录用户
     *
     * @param clazz 用户类型
     * @param <T>   用户类型
     * @return 登录用户
     */
    @SuppressWarnings("unchecked")
    public static <T> T getUser(Class<T> clazz) {
        if (!STP_LOGIC.isLogin()) {
            throw new SaTokenException("您还没有登录");
        }
        return (T) STP_LOGIC.getSession().get(USER_INFO, clazz);
    }

    /**
     * 当前会话是否已经登录
     * 
     * @return 是否已登录
     */
    public static boolean isLogin() {
        return STP_LOGIC.isLogin();
    }

    /**
     * 获取当前登录用户的Token
     * 
     * @return TokenValue
     */
    public static String getTokenValue() {
        return STP_LOGIC.getTokenValue();
    }

}