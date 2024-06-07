package cn.m1yellow.mypages.controller;


import cn.m1yellow.mypages.common.api.CommonResult;
import cn.m1yellow.mypages.common.aspect.DoCache;
import cn.m1yellow.mypages.common.aspect.WebLog;
import cn.m1yellow.mypages.common.util.ObjectUtil;
import cn.m1yellow.mypages.dto.UserPlatformDto;
import cn.m1yellow.mypages.entity.UserPlatformRelation;
import cn.m1yellow.mypages.service.UserPlatformRelationService;
import cn.m1yellow.mypages.service.UserPlatformService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户与平台关联表 前端控制器
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-23
 */
@Slf4j
@RestController
@RequestMapping("/platform-relation")
public class UserPlatformRelationController {

    @Autowired
    private UserPlatformService userPlatformService;
    @Autowired
    private UserPlatformRelationService userPlatformRelationService;


    @ApiOperation("添加/更新用户平台关系")
    @RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @WebLog
    @DoCache
    public CommonResult<UserPlatformDto> add(UserPlatformDto platform) {

        //CheckParamUtil.validate(platform);
        if (null == platform || null == platform.getUserId() || null == platform.getPlatformId()) {
            log.error("请求参数错误");
            return CommonResult.failed("请求参数错误");
        }

        UserPlatformRelation savePlatformRelation = new UserPlatformRelation();
        BeanUtils.copyProperties(platform, savePlatformRelation);

        // 去字符串字段两边空格
        ObjectUtil.stringFiledTrim(savePlatformRelation);

        if (!userPlatformRelationService.saveOrUpdate(savePlatformRelation)) {
            log.error("添加/更新平台失败");
            return CommonResult.failed("操作失败");
        }

        // 重新加载封装对象
        Map<String, Object> params = new HashMap<>(2);
        params.put("userId", platform.getUserId());
        params.put("platformId", platform.getPlatformId());
        UserPlatformDto reloadUserPlatform = userPlatformService.getUserPlatform(params);

        return CommonResult.success(reloadUserPlatform);
    }


    @ApiOperation("移除用户平台关系")
    @RequestMapping(value = "remove", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @WebLog
    @DoCache
    public CommonResult<String> remove(@RequestParam Long userId, @RequestParam Long platformId) {

        if (null == userId || null == platformId) {
            log.error("请求参数错误");
            return CommonResult.failed("请求参数错误");
        }

        Map<String, Object> params = new HashMap<>(2);
        params.put("user_id", userId);
        params.put("platform_id", platformId);
        if (!userPlatformRelationService.removeByMap(params)) {
            log.error("移除失败，userId: {}, platformId: {}", userId, platformId);
            return CommonResult.failed("操作失败");
        }

        return CommonResult.success();
    }

}
