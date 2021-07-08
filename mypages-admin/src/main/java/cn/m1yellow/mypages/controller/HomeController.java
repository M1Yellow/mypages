package cn.m1yellow.mypages.controller;

import cn.m1yellow.mypages.common.api.CommonResult;
import cn.m1yellow.mypages.common.aspect.RateLimit;
import cn.m1yellow.mypages.common.aspect.WebLog;
import cn.m1yellow.mypages.common.constant.GlobalConstant;
import cn.m1yellow.mypages.common.util.FastJsonUtil;
import cn.m1yellow.mypages.common.util.ObjectUtil;
import cn.m1yellow.mypages.common.util.RedisUtil;
import cn.m1yellow.mypages.dto.UserFollowingDto;
import cn.m1yellow.mypages.dto.UserPlatformDto;
import cn.m1yellow.mypages.entity.UserFollowingType;
import cn.m1yellow.mypages.entity.UserOpinion;
import cn.m1yellow.mypages.service.*;
import cn.m1yellow.mypages.vo.home.PlatformItem;
import cn.m1yellow.mypages.vo.home.UserFollowingItem;
import cn.m1yellow.mypages.vo.home.UserFollowingTypeItem;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
 * 首页内容 controller
 */
@Slf4j
@RestController // 整个 controller 返回 json 格式数据。@ResponseBody 是单个方法返回 json 格式数据
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserPlatformService userPlatformService;
    @Autowired
    private UserOpinionService userOpinionService;
    @Autowired
    private UserFollowingTypeService userFollowingTypeService;
    @Autowired
    private UserFollowingService userFollowingService;
    @Autowired
    private UserFollowingRemarkService userFollowingRemarkService;
    @Autowired
    private RedisUtil redisUtil;


    /*
    RequestMapping 注解中 consumes 与 produces 的区别：
    HTTP协议Header中的ContentType 和Accept

    在 Request 中
    ContentType 用来告诉服务器当前发送的数据是什么格式
    Accept 用来告诉服务器，客户端能认识哪些格式，最好返回这些格式中的其中一种
    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*\/\*;q=0.8,application/signed-exchange;v=b3;q=0.9

    consumes 用来限制 ContentType
    produces 用来限制 Accept，设置返回数据的类型以及编码，produces = "application/json;charset=utf-8"
    */


    @ApiOperation("首页默认内容")
    //@RequestMapping 设置 "" 是能匹配到 "/" 的，反过来则不行
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @WebLog
    @RateLimit(number = 2, cycle = 10)
    @Cacheable(value = GlobalConstant.CACHE_7DAYS, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).HOME_PLATFORM_LIST_DEFAULT_CACHE_KEY", unless = "#result==null")
    public CommonResult<List<PlatformItem>> homeContent() {

        return platformList(null);
    }


    @ApiOperation("首页平台所有内容")
    @RequestMapping(value = "platformList", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @WebLog
    @Cacheable(value = GlobalConstant.CACHE_USER_FOLLOWING_2HOURS, key = "T(cn.m1yellow.mypages.common.constant.GlobalConstant).HOME_PLATFORM_LIST_CACHE_KEY + #userId", unless = "#result==null")
    public CommonResult<List<PlatformItem>> platformList(@RequestParam Long userId) {

        // 默认内容取哪个用户的数据；观点记录条数；关注用户个数
        // 是否为首页默认内容
        boolean isHomeDefault = false;
        if (userId == null) {
            userId = GlobalConstant.HOME_PLATFORM_LIST_DEFAULT_USER_ID;
            isHomeDefault = true;
        }
        int platformNum = GlobalConstant.HOME_PLATFORM_LIST_DEFAULT_PLATFORM_NUM;
        int opinionNum = GlobalConstant.HOME_PLATFORM_LIST_DEFAULT_OPINION_NUM;
        int followingNum = GlobalConstant.HOME_PLATFORM_LIST_DEFAULT_FOLLOWING_NUM;


        /**
         * 数据查询封装顺序
         * 每个平台的数据 -> 平台下每个类型的数据 -> 类型下每个用户的数据
         */

        // 查询平台表，获取平台数据
        Map<String, Object> platformQueryParams = new HashMap<>();
        platformQueryParams.put("userId", userId);
        if (isHomeDefault) {
            platformQueryParams.put("pageNo", 0);
            platformQueryParams.put("pageSize", platformNum);
        }
        List<UserPlatformDto> PlatformList = userPlatformService.queryUserPlatformList(platformQueryParams);

        if (PlatformList == null || PlatformList.size() <= 0) { // 平台表数据异常
            log.error("平台表数据异常");
            return CommonResult.failed("用户平台信息加载失败");
        }

        // TODO 获取内容代码太长，后续需抽取封装。长篇内容对不熟悉业务的人非常不友好。
        // >>>> 首页平台内容数据封装 >>>>
        List<PlatformItem> platformItemList = new ArrayList<>();
        for (UserPlatformDto platform : PlatformList) {
            if (platform == null) continue;

            // 平台基础信息封装对象
            PlatformItem platformItem = new PlatformItem();
            platformItem.setPlatformBaseInfo(platform);

            // 用户对平台看法表内容封装
            QueryWrapper<UserOpinion> opinionQueryWrapper = new QueryWrapper();
            //opinionQueryWrapper.eq("is_deleted", 0);
            opinionQueryWrapper.eq("user_id", userId);
            opinionQueryWrapper.eq("platform_id", platform.getPlatformId()); // 对应平台表的id
            opinionQueryWrapper.eq("opinion_type", 0); // 观点对应类型，0-平台；其他-某一类型
            opinionQueryWrapper.orderByDesc("sort_no");
            opinionQueryWrapper.orderByAsc("id"); // TODO 注意，产生 file sort 可能会影响效率

            List<UserOpinion> platformOpinionList = null;
            // 观点不用判断 isHomeDefault，都是默认只展示前几条，点击展开后再加载剩余的
            Page<UserOpinion> opinionPage = new Page<>(0, opinionNum);
            Page<UserOpinion> platformOpinionListPage = userOpinionService.page(opinionPage, opinionQueryWrapper);
            platformOpinionList = platformOpinionListPage.getRecords();
            platformItem.setPlatformOpinionList(platformOpinionList);

            // 关注类型列表封装
            List<UserFollowingType> userFollowingTypeMergeList = userFollowingTypeService.getUserFollowingTypeMergeList(userId, platform.getPlatformId(), null);
            if (CollectionUtils.isEmpty(userFollowingTypeMergeList)) {
                log.info(">>>> platform userFollowingTypeList is empty, platform id: {}", platform.getPlatformId());
                continue;
            }

            List<UserFollowingTypeItem> userFollowingTypeList = new LinkedList<>();
            for (UserFollowingType type : userFollowingTypeMergeList) {
                if (type == null) continue;

                // 用户对关注类型的看法列表
                List<UserOpinion> typeOpinionList = null;
                // 非默认类型才加载观点，默认类型不支持添加观点
                if (!type.getId().equals(GlobalConstant.USER_FOLLOWING_DEFAULT_TYPE_ID)) {
                    QueryWrapper<UserOpinion> typeOpinionQueryWrapper = new QueryWrapper();
                    typeOpinionQueryWrapper.eq("user_id", userId);
                    typeOpinionQueryWrapper.eq("platform_id", platform.getPlatformId()); // 关联平台id
                    typeOpinionQueryWrapper.eq("opinion_type", type.getId()); // 观点对应类型，0-平台；其他-某一类型
                    typeOpinionQueryWrapper.orderByDesc("sort_no");
                    typeOpinionQueryWrapper.orderByAsc("id");
                    // 观点不用判断 isHomeDefault，都是默认只展示前几条，点击展开后再加载剩余的
                    Page<UserOpinion> typeOpinionPage = new Page<>(0, opinionNum);
                    Page<UserOpinion> typeOpinionListPage = userOpinionService.page(typeOpinionPage, typeOpinionQueryWrapper);
                    typeOpinionList = typeOpinionListPage.getRecords();
                }

                // 用户关注类型信息封装对象
                UserFollowingTypeItem userFollowingTypeItem = new UserFollowingTypeItem();
                // 设置类型信息
                userFollowingTypeItem.setUserFollowingTypeInfo(type);
                // 设置类型观点
                userFollowingTypeItem.setUserOpinionList(typeOpinionList);


                // 用户在平台某类型下的关注用户列表
                // TODO 用户未登录，首页默认加载数据。登录后，关注用户使用分页请求查询
                if (isHomeDefault) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("userId", userId);
                    params.put("platformId", platform.getId());
                    params.put("typeId", type.getId());
                    params.put("pageNo", 0);
                    params.put("pageSize", followingNum);
                    List<UserFollowingDto> typeFollowingList = userFollowingService.queryUserFollowingList(params);

                    // TODO 如果这个类型的观点列表和关注用户列表都为空，不返回给页面
                    if (CollectionUtils.isEmpty(typeOpinionList) && CollectionUtils.isEmpty(typeFollowingList)) {
                        log.info(">>>> typeOpinionList and typeFollowingList is empty, userId: {}, typeId: {}", userId, type.getId());
                        continue;
                    }

                    // 用户列表封装对象添加对应的标签 List<UserFollowingDto> 转换为 List<UserFollowingItem>，item 包含用户及其标签
                    List<UserFollowingItem> userFollowingItemList = userFollowingService.transformUserFollowingList(typeFollowingList);

                    // 准备分页数据
                    Page<UserFollowingItem> userFollowingItemListPage = new Page<>();
                    userFollowingItemListPage.setRecords(userFollowingItemList);
                    userFollowingItemListPage.setCurrent(GlobalConstant.PAGE_NO_DEFAULT);
                    userFollowingItemListPage.setSize(GlobalConstant.PAGE_SIZE_DEFAULT);
                    if (CollectionUtils.isEmpty(userFollowingItemList)) {
                        userFollowingItemListPage.setTotal(0);
                    } else if (userFollowingItemList.size() < GlobalConstant.PAGE_SIZE_DEFAULT) {
                        userFollowingItemListPage.setTotal(userFollowingItemList.size());
                    } else {
                        params.clear();
                        params.put("userId", userId);
                        params.put("platformId", platform.getId());
                        params.put("typeId", type.getId());
                        int pageTotal = userFollowingService.getFollowingCount(params);
                        userFollowingItemListPage.setTotal(pageTotal);
                    }

                    // 用户关注类型列表封装（改成分页数据）
                    //userFollowingTypeItem.setUserFollowingList(userFollowingItemList);
                    userFollowingTypeItem.setUserFollowingListPage(userFollowingItemListPage);
                }
                userFollowingTypeList.add(userFollowingTypeItem);

            }

            // <<<< 首页平台内容数据封装 <<<<
            platformItem.setUserFollowingTypeList(userFollowingTypeList);
            platformItemList.add(platformItem);

        }

        return CommonResult.success(platformItemList);
    }


    /**
     * 从缓存中获取首页数据
     *
     * @param userId
     * @return
     */
    public List<PlatformItem> getPlatformListFromCache(Long userId) {
        List<PlatformItem> platformItemList = null;
        String platformListCache;
        if (userId != null) { // 用户已登录，根据用户id取缓存
            platformListCache = ObjectUtil.getString(redisUtil.get(GlobalConstant.HOME_PLATFORM_LIST_CACHE_KEY + userId));
        } else { // TODO 用户没登录，默认显示的首页数据
            platformListCache = ObjectUtil.getString(redisUtil.get(GlobalConstant.HOME_PLATFORM_LIST_DEFAULT_CACHE_KEY));
        }
        if (StringUtils.isNotBlank(platformListCache)) {
            // gson 对层层嵌套的复杂对象，由于序列化泛型擦除，类型对应不上，会导致页面解析不了！
            // fastJson debug 能对应上类型，页面也能解析对应类型
            platformItemList = FastJsonUtil.json2List(platformListCache, PlatformItem.class);
        }

        return platformItemList;
    }

}
