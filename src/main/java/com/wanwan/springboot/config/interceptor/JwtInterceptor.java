package com.wanwan.springboot.config.interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.wanwan.springboot.common.Constants;
import com.wanwan.springboot.common.enums.ResultCodeEnum;
import com.wanwan.springboot.config.AuthAccess;
import com.wanwan.springboot.entity.User;
import com.wanwan.springboot.exception.ServiceException;
import com.wanwan.springboot.service.IUserService;
import com.wanwan.springboot.service.impl.UserServiceImpl;
import com.wanwan.springboot.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author：玩玩
 * @since：2024/2/2 11:44
 * @description:
 */
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        String token = request.getHeader("token");
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }else {
            HandlerMethod h = (HandlerMethod) handler;
            AuthAccess authAccess = h.getMethodAnnotation(AuthAccess.class);
            if (authAccess != null) {
                return true;
            }
        }

        // 执行认证
        if (StrUtil.isBlank(token)){
            throw new ServiceException(ResultCodeEnum.TOKEN_INVALID_ERROR);
        }

        //获取token中的用户id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(ResultCodeEnum.TOKEN_CHECK_ERROR);
        }

        User user;
        // String jsonStr = stringRedisTemplate.opsForValue().get("userId_"+userId);
        if(RedisUtil.get("userId_"+userId,User.class)==null){
            user = userService.getById(userId);
            // stringRedisTemplate.opsForValue().set("userId_"+userId, JSON.toJSONString(user),8, TimeUnit.HOURS);
            RedisUtil.put("userId_"+userId,user,24,TimeUnit.HOURS);
        }else{
            // user = JSON.parseObject(stringRedisTemplate.opsForValue().get("userId_" + userId), User.class);
            user = RedisUtil.get("userId_"+userId,User.class);
        }
        // 根据token中的userid查询数据库
        // User user = userService.getById(userId);
        if (user == null){
            throw new ServiceException(ResultCodeEnum.USER_NO_EXIT_ERROR);

        }
        //验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(ResultCodeEnum.TOKEN_CHECK_ERROR);

        }
        return true;
    }


}
