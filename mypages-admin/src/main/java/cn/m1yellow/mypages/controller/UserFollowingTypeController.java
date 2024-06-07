package cn.m1yellow.mypages.controller;


import cn.m1yellow.mypages.common.api.CommonResult;
import cn.m1yellow.mypages.common.aspect.WebLog;
import cn.m1yellow.mypages.common.constant.GlobalConstant;
import cn.m1yellow.mypages.common.exception.AtomicityException;
import cn.m1yellow.mypages.common.util.ObjectUtil;
import cn.m1yellow.mypages.common.util.RedisUtil;
import cn.m1yellow.mypages.entity.UserFollowingRelation;
import cn.m1yellow.mypages.entity.UserFollowingType;
import cn.m1yellow.mypages.service.UserFollowingRelationService;
import cn.m1yellow.mypages.service.UserFollowingService;
import cn.m1yellow.mypages.service.UserFollowingTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户关注分类表 前端控制器
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
@Slf4j
@RestController
@RequestMapping("/type")
public class UserFollowingTypeController {

    @Autowired
    private UserFollowingService userFollowingService;
    @Autowired
    private UserFollowingTypeService userFollowingTypeService;
    @Autowired
    private UserFollowingRelationService userFollowingRelationService;
    @Autowired
    private RedisUtil redisUtil;


    @ApiOperation("添加/更新类型")
    @RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @WebLog
    @Caching(evict = {
            @CacheEvict(value = GlobalConstant.CACHE_7DAYS, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).HOME_PLATFORM_LIST_DEFAULT_CACHE_KEY"),
            @CacheEvict(value = GlobalConstant.CACHE_USER_FOLLOWING, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).HOME_PLATFORM_LIST_CACHE_KEY + #type.userId"),
            @CacheEvict(value = GlobalConstant.CACHE_10MIN, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).USER_TYPE_LIST_CACHE_KEY + #type.userId + '_' + #type.platformId")
    })
    public CommonResult<UserFollowingType> add(UserFollowingType type) {

        // id为0表示默认类型，默认类型系统自动管理，用户不能自己创建或编辑
        if (null == type || (null != type.getId() && type.getId() < 1)) {
            log.error("请求参数错误");
            return CommonResult.failed("请求参数错误");
        }

        // 校验必须参数
        if (null == type.getUserId() || null == type.getPlatformId() || StringUtils.isBlank(type.getTypeName())) {
            log.error("typeName must be not null.");
            return CommonResult.failed("请检查必须参数");
        }

        // 先判断用户是否已经有相同名称的类型
        QueryWrapper<UserFollowingType> typeQueryWrapper = new QueryWrapper<>();
        typeQueryWrapper.eq("user_id", type.getUserId());
        typeQueryWrapper.eq("platform_id", type.getPlatformId());
        typeQueryWrapper.eq("type_name", type.getTypeName());
        UserFollowingType existedType = userFollowingTypeService.getOne(typeQueryWrapper);
        if (null != existedType) {
            // 存在相同名称的类型，则使用原来的id，执行更新操作
            type.setId(existedType.getId());
        }

        // 去字符串字段两边空格
        ObjectUtil.stringFiledTrim(type);

        if (!userFollowingTypeService.saveOrUpdate(type)) {
            log.error("添加/更新类型失败");
            return CommonResult.failed("操作失败");
        }

        return CommonResult.success(type);
    }


    @ApiOperation("获取分类类型列表")
    @RequestMapping(value = "list", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @WebLog
    @Cacheable(value = GlobalConstant.CACHE_10MIN, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).USER_TYPE_LIST_CACHE_KEY + #userId + '_' + #platformId", unless = "#result==null")
    public CommonResult<List<UserFollowingType>> list(@RequestParam Long userId, @RequestParam Long platformId) {

        if (null == userId || null == platformId) {
            log.error("请求参数错误");
            return CommonResult.failed("请求参数错误");
        }

        // TODO 添加游离于数据库类型表之外的默认类型，方便共用
        List<UserFollowingType> UserFollowingTypeMergeList = userFollowingTypeService.getUserFollowingTypeMergeList(userId, platformId, null);

        return CommonResult.success(UserFollowingTypeMergeList);
    }


    @ApiOperation("移除分类类型")
    @Transactional // 加入事务支持
    @RequestMapping(value = "remove", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @WebLog
    @Caching(evict = {
            @CacheEvict(value = GlobalConstant.CACHE_7DAYS, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).HOME_PLATFORM_LIST_DEFAULT_CACHE_KEY"),
            @CacheEvict(value = GlobalConstant.CACHE_USER_FOLLOWING, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).HOME_PLATFORM_LIST_CACHE_KEY + #userId"),
            @CacheEvict(value = GlobalConstant.CACHE_USER_FOLLOWING, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).USER_FOLLOWING_PAGE_LIST_CACHE_KEY + #userId + '_' + #platformId + '_' + #typeId"),
            @CacheEvict(value = GlobalConstant.CACHE_USER_FOLLOWING, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).USER_FOLLOWING_PAGE_LIST_CACHE_KEY + #userId + '_' + #platformId + '_' + 0"),
            @CacheEvict(value = GlobalConstant.CACHE_10MIN, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).USER_TYPE_LIST_CACHE_KEY + #userId + '_' + #platformId")
    })
    public CommonResult<String> remove(@RequestParam Long userId, @RequestParam Long platformId, @RequestParam Long typeId) {

        // id为0表示默认类型，默认类型系统自动管理
        if (null == userId || null == typeId || typeId < 1 || null == platformId) {
            log.error("请求参数错误");
            return CommonResult.failed("请求参数错误");
        }

        // 删除类型记录，删除之后，其下的关注用户分配到默认分类
        Map<String, Object> params = new HashMap<>(3);
        params.put("user_id", userId);
        params.put("platform_id", platformId);
        params.put("id", typeId);
        if (!userFollowingTypeService.removeByMap(params)) {
            log.error("移除失败，userId: {}, platformId: {}, typeId: {}", userId, platformId, typeId);
            throw new AtomicityException("操作失败");
        }

        /*
        TODO 关于 MyBatis-Plus QueryWrapper 在 service 甚至 controller 层到处都是的问题
        只要后续还是 MyBatis 项目，只要 MyBatis-Plus 不跑路作妖，
        把 QueryWrapper 当作 Map 参数封装，确实能省去很多 mapper.xml sql 代码
        MyBatis Example 同样可能导致到处都是，项目开发手册要求禁止在 service、controller 使用，或者干脆不让用
        如果考虑几年、十几年后项目更换 DAO 数据操作层，MyBatis 基本上只要改 dao 层，而 MyBatis-Plus 很可能 dao、service、controller 三层都要改！！
        TODO MyBatis-Plus 用时一时爽，替换火葬场！一直使用一直爽？
        */

        // 查询要删除的类型下是否有关注用户
        QueryWrapper<UserFollowingRelation> relationQueryWrapper = new QueryWrapper<>();
        relationQueryWrapper.select("id");
        relationQueryWrapper.eq("user_id", userId);
        relationQueryWrapper.eq("platform_id", platformId);
        relationQueryWrapper.eq("type_id", typeId);
        List<UserFollowingRelation> followingList = userFollowingRelationService.list(relationQueryWrapper);

        if (!CollectionUtils.isEmpty(followingList)) {
            // TODO 更改关注用户类型，注意！！更新记录为0的结果是false，有可能数据库记录本来就是0条，这个判断不符合业务要求
            boolean result = userFollowingRelationService.changeUserFollowingTypeByTypeId(userId, platformId, typeId,
                    GlobalConstant.USER_FOLLOWING_DEFAULT_TYPE_ID);
            if (!result) {
                log.error("删除类型后变更其下用户类型失败");
                throw new AtomicityException("删除类型后变更其下用户类型失败");
            }
        }

        return CommonResult.success();
    }

}
