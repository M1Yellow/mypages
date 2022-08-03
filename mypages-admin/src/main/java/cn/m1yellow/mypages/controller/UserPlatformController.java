package cn.m1yellow.mypages.controller;


import cn.m1yellow.mypages.common.api.CommonResult;
import cn.m1yellow.mypages.common.aspect.WebLog;
import cn.m1yellow.mypages.common.constant.GlobalConstant;
import cn.m1yellow.mypages.common.util.CheckParamUtil;
import cn.m1yellow.mypages.common.util.JSONUtil;
import cn.m1yellow.mypages.common.util.ObjectUtil;
import cn.m1yellow.mypages.common.util.RedisUtil;
import cn.m1yellow.mypages.dto.UserPlatformDto;
import cn.m1yellow.mypages.entity.UserPlatform;
import cn.m1yellow.mypages.service.UserPlatformService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 社交媒体平台表 前端控制器
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
@Slf4j
@RestController
@RequestMapping("/platform")
public class UserPlatformController {

    @Autowired
    private UserPlatformService userPlatformService;
    @Autowired
    private RedisUtil redisUtil;


    @ApiOperation("添加/更新平台")
    @RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @WebLog
    @Caching(evict = {
            @CacheEvict(value = GlobalConstant.CACHE_USER_FOLLOWING_2HOURS, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).HOME_PLATFORM_LIST_CACHE_KEY + #platform.userId"),
            @CacheEvict(value = GlobalConstant.CACHE_10MIN, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).USER_PLATFORM_LIST_CACHE_KEY + #platform.userId")
    })
    public CommonResult<UserPlatformDto> add(UserPlatformDto platform) {

        CheckParamUtil.validate(platform);

        UserPlatform savePlatform = new UserPlatform();
        BeanUtils.copyProperties(platform, savePlatform);
        // 纠正id
        savePlatform.setId(platform.getPlatformId());

        // 去字符串字段两边空格
        ObjectUtil.stringFiledTrim(savePlatform);

        if (!userPlatformService.saveOrUpdate(savePlatform)) {
            log.error("添加/更新平台失败");
            return CommonResult.failed("操作失败");
        }

        return CommonResult.success();
    }


    @ApiOperation("获取平台列表")
    @RequestMapping(value = "list", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @WebLog
    @Cacheable(value = GlobalConstant.CACHE_10MIN, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).USER_PLATFORM_LIST_CACHE_KEY + #userId", unless = "#result.data==null")
    public CommonResult<List<UserPlatformDto>> list(@RequestParam Long userId) {

        if (userId == null) {
            log.error("请求参数错误");
            return CommonResult.failed("请求参数错误");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<UserPlatformDto> userPlatformList = userPlatformService.queryUserPlatformList(params);

        return CommonResult.success(userPlatformList);
    }


    @ApiOperation("获取平台基础列表")
    @RequestMapping(value = "baseList", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @WebLog
    public CommonResult<List<UserPlatform>> baseList(@RequestParam Long userId, @RequestParam(required = false) boolean isAdd) {

        if (userId == null) {
            log.error("请求参数错误");
            return CommonResult.failed("请求参数错误");
        }

        // 先从缓存中获取
        if (isAdd) { // 添加平台，需要的是用户没有添加过的平台列表
            String cacheStr = ObjectUtil.getString(redisUtil.get(GlobalConstant.USER_PLATFORM_LIST_ADD_CACHE_KEY + userId));
            if (StringUtils.isNotBlank(cacheStr)) {
                List<UserPlatform> userPlatformList = JSONUtil.toList(cacheStr, UserPlatform.class);
                if (userPlatformList != null && userPlatformList.size() > 0) {
                    return CommonResult.success(userPlatformList);
                }
            }
        } else { // 只是查询基础平台列表，则返回所有可用平台列表
            String cacheStr = ObjectUtil.getString(redisUtil.get(GlobalConstant.PLATFORM_LIST_BASE_CACHE_KEY));
            if (StringUtils.isNotBlank(cacheStr)) {
                List<UserPlatform> userPlatformList = JSONUtil.toList(cacheStr, UserPlatform.class);
                if (userPlatformList != null && userPlatformList.size() > 0) {
                    return CommonResult.success(userPlatformList);
                }
            }
        }

        QueryWrapper<UserPlatform> platformQueryWrapper = null;
        List<Long> existedPlatformIdList = null;
        if (isAdd) {
            // 查询用户已经添加过的平台列表
            List<UserPlatformDto> existedUserPlatformList = null;
            // 先从缓存中获取
            String cacheStr = ObjectUtil.getString(redisUtil.get(GlobalConstant.USER_PLATFORM_LIST_CACHE_KEY + userId));
            if (StringUtils.isNotBlank(cacheStr)) {
                existedUserPlatformList = JSONUtil.toList(cacheStr, UserPlatformDto.class);
            }
            if (existedUserPlatformList == null) {
                // 缓存不存在，则查询数据库
                Map<String, Object> params = new HashMap<>();
                params.put("userId", userId);
                existedUserPlatformList = userPlatformService.queryUserPlatformList(params);
                if (existedUserPlatformList != null && existedUserPlatformList.size() > 0) {
                    // 重新设置缓存
                    redisUtil.set(GlobalConstant.USER_PLATFORM_LIST_CACHE_KEY + userId, JSONUtil.toJSON(existedUserPlatformList),
                            GlobalConstant.USER_PLATFORM_TYPE_LIST_CACHE_TIME);
                }
            }
            if (existedUserPlatformList != null && existedUserPlatformList.size() > 0) {
                // 注意，缓存不能在这重新设置哦，在查库的时候设置
                // 解析已存在的平台id
                existedPlatformIdList = existedUserPlatformList.stream().map(UserPlatformDto::getPlatformId).distinct().collect(Collectors.toList());
            }
            if (existedPlatformIdList != null && existedPlatformIdList.size() > 0) {
                platformQueryWrapper = new QueryWrapper<>();
                platformQueryWrapper.notIn("id", existedPlatformIdList); // 剔除已添加过的平台
            }
        }

        List<UserPlatform> userPlatformList = userPlatformService.list(platformQueryWrapper);

        // 查询完成之后，设置缓存
        if (userPlatformList != null && userPlatformList.size() > 0) {
            String cacheKey;
            if (isAdd) {
                cacheKey = GlobalConstant.USER_PLATFORM_LIST_ADD_CACHE_KEY + userId;
            } else {
                cacheKey = GlobalConstant.PLATFORM_LIST_BASE_CACHE_KEY;
            }
            redisUtil.set(cacheKey, JSONUtil.toJSON(userPlatformList), GlobalConstant.USER_PLATFORM_TYPE_LIST_CACHE_TIME);
        }

        return CommonResult.success(userPlatformList);
    }


    @ApiOperation("移除平台")
    @RequestMapping(value = "remove", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @WebLog
    @Caching(evict = {
            @CacheEvict(value = GlobalConstant.CACHE_USER_FOLLOWING_2HOURS, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).HOME_PLATFORM_LIST_CACHE_KEY + #userId"),
            @CacheEvict(value = GlobalConstant.CACHE_10MIN, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).USER_PLATFORM_LIST_CACHE_KEY + #userId")
    })
    public CommonResult<String> remove(@RequestParam Long userId, @RequestParam Long id) {

        if (id == null) {
            log.error("请求参数错误");
            return CommonResult.failed("请求参数错误");
        }

        if (!userPlatformService.removeById(id)) {
            log.error("移除平台失败，id: {}", id);
            return CommonResult.failed("操作失败");
        }

        return CommonResult.success();
    }

}
