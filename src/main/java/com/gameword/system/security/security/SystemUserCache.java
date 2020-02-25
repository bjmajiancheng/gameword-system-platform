package com.gameword.system.security.security;

import com.gameword.system.common.tools.IBasicCache;
import com.gameword.system.security.constants.SecurityConstant;
import com.gameword.system.security.dto.FunctionDto;
import com.gameword.system.security.service.IUserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by majiancheng on 2019/9/16.
 */
public class SystemUserCache implements org.springframework.security.core.userdetails.UserCache, InitializingBean {

    private IBasicCache<String, Object> cache = null;

    private IUserService userService;

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public void setCache(IBasicCache<String, Object> cache) {
        this.cache = cache;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.cache == null) this.cache = new SystemNullCache<String, Object>();
        Assert.notNull(this.cache);
    }

    @Override
    public UserDetails getUserFromCache(String username) {
        UserDetails userDetails = null;
        try {
            userDetails = (UserDetails) cache.get(SecurityConstant.USER_CACHE_PREFIX + username);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return userDetails == null ? null : userDetails;
        }

    }

    @Override
    public void putUserInCache(UserDetails user) {
        cache.set(SecurityConstant.USER_CACHE_PREFIX + user.getUsername(), user);
        List<FunctionDto> functionList = userService.findUserFunctionByLoginName(user.getUsername());
        cache.set(SecurityConstant.FUNCTION_CACHE_PREFIX + user.getUsername(), functionList);
    }

    @Override
    public void removeUserFromCache(String username) {
        cache.del(SecurityConstant.USER_CACHE_PREFIX + username);
        cache.del(SecurityConstant.FUNCTION_CACHE_PREFIX + username);
    }

    public void removeUserFromCacheByUserId(Integer userId) {
        String loginName = userService.findLoginNameByUserId(userId);
        if (loginName != null) {
            removeUserFromCache(loginName);
        }
    }
}
