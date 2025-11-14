package cc.oofo.framework.core.controller;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础控制器
 * 
 * @param <S> 业务类型
 * @author Sir丶雨轩
 * @since 2025/11/13
 */

public class BaseController<S> {

    @Autowired
    protected S baseService;

}