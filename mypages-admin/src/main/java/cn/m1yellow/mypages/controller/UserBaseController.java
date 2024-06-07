package cn.m1yellow.mypages.controller;


import cn.m1yellow.mypages.common.api.CommonResult;
import cn.m1yellow.mypages.common.aspect.RateLimit;
import cn.m1yellow.mypages.common.aspect.WebLog;
import cn.m1yellow.mypages.common.constant.GlobalConstant;
import cn.m1yellow.mypages.common.util.ObjectUtil;
import cn.m1yellow.mypages.common.util.RedisUtil;
import cn.m1yellow.mypages.security.bo.SecurityUser;
import cn.m1yellow.mypages.security.config.JwtSecurityProperties;
import cn.m1yellow.mypages.security.util.JwtTokenUtil;
import cn.m1yellow.mypages.user.entity.UserBase;
import cn.m1yellow.mypages.user.service.UserBaseService;
import cn.m1yellow.mypages.vo.home.UserInfoDetail;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserBaseController {

    @Autowired
    private UserBaseService userBaseService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtSecurityProperties jwtSecurityProperties;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @ApiOperation("添加/更新用户")
    @RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @WebLog
    @CacheEvict(value = GlobalConstant.CACHE_2HOURS, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).USER_INFO_DETAIL_CACHE_KEY + #userBase.id")
    public CommonResult<UserBase> add(UserBase userBase) {

        if (null == userBase) {
            log.error("请求参数错误");
            return CommonResult.failed("请求参数错误");
        }

        // 是否新增
        boolean isNew = true;
        if (null != userBase.getId()) {
            isNew = false;
        }

        // 去字符串字段两边空格
        ObjectUtil.stringFiledTrim(userBase);

        if (!userBaseService.saveOrUpdate(userBase)) {
            log.error("添加/更新用户失败");
            return CommonResult.failed("添加/更新用户失败");
        }

        return CommonResult.success(userBase);
    }


    @ApiOperation("用户登录")
    @RequestMapping(value = "login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @WebLog
    @RateLimit(number = 2, cycle = 10)
    public CommonResult<Map<String, String>> login(@RequestParam String userName, @RequestParam String password) {

        /**
         * TODO 关于参数校验的想法
         * 适当放过一些参数校验，每个参数都校验，然后都要加上错误信息，代码会显得很臃肿，让人感觉很浅显，全是参数校验
         * 其实，直接不校验，传入不合适的参数，程序自然报错，有时候报错也是一种日志，同样便于排查问题，简洁原始之美
         */

        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            log.error("请求参数错误");
            return CommonResult.failed("请求参数错误");
        }

        String token = null;
        try {
            SecurityUser userDetails = (SecurityUser) userDetailsService.loadUserByUsername(userName);
            if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
                //if (null == userDetails || !password.equals(userDetails.getPassword())) { // 这里的 password 是客户端加密后的
                //throw new BadCredentialsException("用户名或密码错误");
                return CommonResult.validateFailed("用户名或密码错误");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            if (StringUtils.isBlank(token)) {
                throw new BadCredentialsException("登录出现异常，请稍后重试");
            }
        } catch (Exception e) {
            log.warn("登录异常: {}", e.getMessage());
            return CommonResult.failed("系统出现异常，请稍后重试");
        }

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenStart", jwtSecurityProperties.getTokenStart());
        tokenMap.put("tokenHeader", jwtSecurityProperties.getTokenHeader());
        return CommonResult.success(tokenMap);
    }


    @ApiOperation("获取用户信息详情")
    @RequestMapping(value = "detail", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @WebLog
    @Cacheable(value = GlobalConstant.CACHE_2HOURS, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).USER_INFO_DETAIL_CACHE_KEY + #userId", unless = "#result==null")
    public CommonResult<UserInfoDetail> getUserInfoDetail(@RequestParam(required = false) Long userId, @RequestParam(required = false) String userName) {
        if (null == userId && StringUtils.isBlank(userName)) {
            log.error("请求参数错误");
            return CommonResult.failed("请求参数错误");
        }

        UserInfoDetail userInfoDetail = new UserInfoDetail();
        UserBase userBase = null;
        if (null != userId) {
            userBase = userBaseService.getById(userId);
        } else if (StringUtils.isNotBlank(userName)) {
            QueryWrapper<UserBase> userBaseQueryWrapper = new QueryWrapper<>();
            userBaseQueryWrapper.eq("user_name", userName);
            userBase = userBaseService.getOne(userBaseQueryWrapper);
        }
        if (null != userBase) {
            BeanUtils.copyProperties(userBase, userInfoDetail);
            // 补充 userId，userInfoDetail 中的 id 和 userId 都表示用户id，迎合使用习惯
            userInfoDetail.setUserId(userBase.getId());
        }
        // 设置默认头像
        if (StringUtils.isBlank(userInfoDetail.getProfilePhoto())) {
            userInfoDetail.setProfilePhoto(GlobalConstant.USER_DEFAULT_PROFILE_PHOTO_PATH);
        }

        return CommonResult.success(userInfoDetail);
    }


    @ApiOperation("移除用户")
    @RequestMapping(value = "remove", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @WebLog
    @CacheEvict(value = GlobalConstant.CACHE_2HOURS, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).USER_INFO_DETAIL_CACHE_KEY + #userId")
    public CommonResult<String> remove(@RequestParam Long userId) {

        if (null == userId) {
            log.error("请求参数错误");
            return CommonResult.failed("请求参数错误");
        }

        if (!userBaseService.removeById(userId)) {
            log.error("移除失败，id:" + userId);
            return CommonResult.failed("移除失败");
        }

        return CommonResult.success();
    }

}
