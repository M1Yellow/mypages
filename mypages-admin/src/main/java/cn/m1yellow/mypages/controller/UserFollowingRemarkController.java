package cn.m1yellow.mypages.controller;


import cn.m1yellow.mypages.common.api.CommonResult;
import cn.m1yellow.mypages.common.aspect.DoCache;
import cn.m1yellow.mypages.common.aspect.WebLog;
import cn.m1yellow.mypages.common.util.ObjectUtil;
import cn.m1yellow.mypages.entity.UserFollowingRemark;
import cn.m1yellow.mypages.service.UserFollowingRemarkService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户关注备注（便签）表 前端控制器
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
@Slf4j
@RestController
@RequestMapping("/remark")
public class UserFollowingRemarkController {

    @Autowired
    private UserFollowingRemarkService userFollowingRemarkService;


    @ApiOperation("添加/更新用户标签")
    @RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @WebLog
    @DoCache
    public CommonResult<UserFollowingRemark> add(@RequestBody UserFollowingRemark remark) {

        if (null == remark) {
            log.error("请求参数错误");
            return CommonResult.failed("请求参数错误");
        }

        // 去字符串字段两边空格
        ObjectUtil.stringFiledTrim(remark);

        if (!userFollowingRemarkService.saveOrUpdate(remark)) {
            log.error("添加/更新标签失败");
            return CommonResult.failed("操作失败");
        }

        return CommonResult.success(remark);
    }


    @ApiOperation("移除用户单个标签")
    @RequestMapping(value = "removeOne", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @WebLog
    @DoCache
    public CommonResult<String> removeOne(@RequestParam Long userId, @RequestParam Long id) {

        if (null == userId || null == id) {
            log.error("请求参数错误");
            return CommonResult.failed("请求参数错误");
        }

        Map<String, Object> params = new HashMap<>(2);
        params.put("user_id", userId);
        params.put("id", id);
        if (!userFollowingRemarkService.removeByMap(params)) {
            log.error("移除失败，userId: {}, id: {}", userId, id);
            return CommonResult.failed("操作失败");
        }

        return CommonResult.success();
    }


    @ApiOperation("移除用户标签")
    @RequestMapping(value = "removeBelongs", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @WebLog
    @DoCache
    public CommonResult<String> removeBelongs(@RequestParam Long userId, @RequestParam Long followingId) {

        if (null == userId || null == followingId) {
            log.error("请求参数错误");
            return CommonResult.failed("请求参数错误");
        }

        Map<String, Object> params = new HashMap<>(2);
        params.put("user_id", userId);
        params.put("following_id", followingId);
        if (!userFollowingRemarkService.removeByMap(params)) {
            log.error("移除失败，userId: {}, followingId: {}", userId, followingId);
            return CommonResult.failed("操作失败");
        }

        return CommonResult.success();
    }

}
