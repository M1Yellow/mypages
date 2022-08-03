package cn.m1yellow.mypages.service.impl;

import cn.m1yellow.mypages.common.constant.GlobalConstant;
import cn.m1yellow.mypages.common.util.*;
import cn.m1yellow.mypages.constant.PlatformInfo;
import cn.m1yellow.mypages.dto.UserFollowingDto;
import cn.m1yellow.mypages.entity.UserFollowing;
import cn.m1yellow.mypages.entity.UserFollowingRemark;
import cn.m1yellow.mypages.excavation.bo.UserInfoItem;
import cn.m1yellow.mypages.excavation.service.DataExcavateService;
import cn.m1yellow.mypages.mapper.UserFollowingMapper;
import cn.m1yellow.mypages.service.UserFollowingRemarkService;
import cn.m1yellow.mypages.service.UserFollowingService;
import cn.m1yellow.mypages.vo.home.UserFollowingItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户关注表 服务实现类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
@Slf4j
@Service
public class UserFollowingServiceImpl extends ServiceImpl<UserFollowingMapper, UserFollowing> implements UserFollowingService {

    @Value("${user.avatar.savedir}")
    private String saveDir;
    @Autowired
    private UserFollowingRemarkService userFollowingRemarkService;
    @Autowired
    private RedisUtil redisUtil;


    /**
     * @Resource、@Autowired、@Qualifier的注解注入及区别 在Java代码中可以使用 @Resource  或者 @Autowired 注解方式来进行注入。
     * 虽然 @Resource 和 @Autowried 都可以完成依赖注入，但是他们是有区别的。
     * @Resource 默认是按照名称来装配注入的，只有当找不到与名称匹配的bean才会按照类型来注入。
     * 它有两个属性是比较重要的:
     * ①. name: Spring 将 name 的属性值解析为 bean 的名称， 使用 byName 的自动注入策略
     * ②. type: Spring 将 type的属性值解析为 bean 的类型，使用 byType 的自动注入策略
     * 注: 如果既不指定 name 属性又不指定 type 属性，Spring这时通过反射机制使用 byName 自动注入策略
     * @Resource 的装配顺序
     * 1. 如果同时指定了 name 属性和 type 属性，那么 Spring 将从容器中找唯一匹配的 bean 进行装配，找不到则抛出异常
     * 2. 如果指定了 name 属性值，则从容器中查找名称匹配的 bean 进行装配，找不到则抛出异常
     * 3. 如果指定了 type 属性值，则从容器中查找类型匹配的唯一的 bean 进行装配，找不到或者找到多个都会抛出异常
     * 4. 如果都不指定，则会自动按照 byName 方式进行装配， 如果没有匹配，则回退一个原始类型进行匹配，如果匹配则自动装配
     * @Autowried 默认是按照类型进行装配注入，默认情况下，它要求依赖对象必须存在，如果允许 null 值，可以设置它 required 为false。
     * 如果我们想要按名称进行装配的话，可以添加一个 @Qualifier 注解解决。
     */

    @Resource
    private UserFollowingMapper userFollowingMapper;
    @Resource(name = "dataOfBiliExcavateService")
    private DataExcavateService dataOfBiliExcavateService;
    @Resource(name = "dataOfWeiboExcavateService")
    private DataExcavateService dataOfWeiboExcavateService;


    @Override
    public UserInfoItem doExcavate(UserFollowingDto following) {

        if (following == null || !following.getIsUser()) {
            return null;
        }

        // 获取、校验用户所属平台的id
        String userId = getUserKeyFromMainPage(following);
        if (StringUtils.isBlank(userId)) {
            log.error("用户主页不符合要求，following id:" + following.getFollowingId());
            return null;
        }

        // 参数封装
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(following.getProfilePhoto())) {
            params.put("profileOriginalDir", following.getProfilePhoto());
        }

        String apiUrl = null; // 请求地址模板
        String fromUrl = null; // 调整后的请求地址
        UserInfoItem userInfoItem = null;

        // 设置保存路径为 classpath 下的目录
        String saveDirFullPath = FileUtil.getSaveDirFullPath(UserFollowingServiceImpl.class, saveDir);

        switch (PlatformInfo.getPlatformInfoByUrl(following.getMainPage())) {
            case BILIBILI:
                apiUrl = "https://api.bilibili.com/x/space/acc/info?mid=userId&jsonp=jsonp";
                fromUrl = apiUrl.replace("userId", userId);
                userInfoItem = dataOfBiliExcavateService.singleImageDownloadFromJson(fromUrl, saveDirFullPath, params);
                break;
            case WEIBO:
                //apiUrl = "https://weibo.com/u/userId";
                //apiUrl = "https://m.weibo.cn/api/container/getIndex?type=uid&value=userId&containerid=1005056488142313";
                apiUrl = "https://m.weibo.cn/api/container/getIndex?type=uid&value=userId";
                fromUrl = apiUrl.replace("userId", userId);
                userInfoItem = dataOfWeiboExcavateService.singleImageDownloadFromJson(fromUrl, saveDirFullPath, params);
                break;
            case DOUBAN:

                break;
            case ZHIHU:

                break;
            default:

        }
        return userInfoItem;
    }


    @Override
    public boolean saveUserInfo(UserInfoItem userInfoItem, UserFollowing following) {
        boolean isSuc = false;

        following.setName(ObjectUtil.getString(userInfoItem.getUserName()));
        following.setSignature(ObjectUtil.getString(userInfoItem.getSignature()));
        following.setProfilePhoto(ObjectUtil.getString(userInfoItem.getHeadImgPath()));

        // 去字符串字段两边空格
        ObjectUtil.stringFiledTrim(following);

        isSuc = saveOrUpdate(following);

        return isSuc;
    }

    @Override
    public boolean deleteById(UserFollowing following) {
        return updateById(following);
    }

    @Override
    public UserFollowingDto getUserFollowing(Map params) {
        return userFollowingMapper.getUserFollowing(params);
    }

    @Override
    public int getFollowingCount(Map params) {
        return userFollowingMapper.getFollowingCount(params);
    }

    @Override
    public List<UserFollowingDto> queryUserFollowingList(Map params) {
        return userFollowingMapper.queryUserFollowingList(params);
    }

    @Override
    public Page<UserFollowingDto> getUserFollowingListPage(Map params) {

        List<UserFollowingDto> followingList = queryUserFollowingList(params);
        long pageNo = GlobalConstant.PAGE_NO_DEFAULT;
        //long pageSize = GlobalConstant.PAGE_SIZE_DEFAULT;
        long pageSize = followingList.size();
        long pageTotal = followingList.size();
        if (followingList.size() == GlobalConstant.PAGE_SIZE_DEFAULT) {
            pageTotal = getFollowingCount(params);
        }
        try {
            pageNo = Long.parseLong(ObjectUtil.getString(params.get("pageNo")));
            pageSize = Long.parseLong(ObjectUtil.getString(params.get("pageSize")));
        } catch (NumberFormatException e) {
            // 异常被消化掉了，一点渣都不剩
        }

        Page<UserFollowingDto> followingListPage = new Page<>();
        followingListPage.setCurrent(pageNo);
        followingListPage.setSize(pageSize);
        followingListPage.setTotal(pageTotal);
        followingListPage.setRecords(followingList);

        return followingListPage;
    }

    @Override
    public Page<UserFollowingItem> getUserFollowingItemListPage(Map params) {

        String cacheKey = null;
        String userId = ObjectUtil.getString(params.get("userId"));
        String platformId = ObjectUtil.getString(params.get("platformId"));
        String typeId = ObjectUtil.getString(params.get("typeId"));

        Long pageNo = null;
        Long pageSize = null;
        Long pageTotal = null;
        try {
            pageNo = Long.parseLong(ObjectUtil.getString(params.get("pageNo")));
            pageSize = Long.parseLong(ObjectUtil.getString(params.get("pageSize")));
        } catch (NumberFormatException e) {
            // 异常也被消化掉了，一点渣都不剩
        }
        if (pageNo == null) {
            pageNo = (long) GlobalConstant.PAGE_NO_DEFAULT;
        }
        if (pageSize == null) {
            pageSize = (long) GlobalConstant.PAGE_SIZE_DEFAULT;
        }
        // TODO 数据库 limit 起始，比如，第一页：limit 0, 10; 第二页：limit 10, 10
        long limitStart = (pageNo - 1) * pageSize;
        params.put("pageNo", limitStart); // pageNo 和 limit 的起始数值很容易出错！

        if (userId != null && platformId != null && typeId != null) {
            // cacheKey 格式：USER_FOLLOWING_PAGE_LIST_CACHE_1_3_9
            cacheKey = GlobalConstant.CACHE_USER_FOLLOWING_2HOURS
                    + GlobalConstant.CACHE_NAME_KEY_SEPARATOR
                    + GlobalConstant.USER_FOLLOWING_PAGE_LIST_CACHE_KEY
                    + userId + "_" + platformId + "_" + typeId;
        } else {
            log.debug(">>>> getUserFollowingItemListPage 请求参数错误");
            return null;
        }

        // 先从缓存获取
        Page<UserFollowingItem> userFollowingItemPage = operatingFollowingItemPageCache(cacheKey,
                GlobalConstant.USER_FOLLOWING_PAGE_LIST_CACHE_TIME, pageNo, false, null);
        if (userFollowingItemPage != null) {
            return userFollowingItemPage;
        }

        // 缓存为空，重新加载
        List<UserFollowingDto> followingList = queryUserFollowingList(params);
        if (CollectionUtils.isEmpty(followingList)) {
            log.debug(">>>> queryUserFollowingList 用户关注列表为空");
            return null;
        }
        // 用户列表封装对象添加对应的标签 List<UserFollowingDto> 转换为 List<UserFollowingItem>，item 包含用户及其标签
        List<UserFollowingItem> userFollowingItemList = transformUserFollowingList(followingList);

        // 封装分页数据
        int recordSize = userFollowingItemList.size();
        if (recordSize < pageSize) {
            pageTotal = recordSize + (pageNo - 1) * pageSize;
        } else {
            pageTotal = (long) getFollowingCount(params);
        }

        Page<UserFollowingItem> followingListPage = new Page<>();
        followingListPage.setCurrent(pageNo);
        followingListPage.setSize(pageSize);
        followingListPage.setTotal(pageTotal);
        followingListPage.setRecords(userFollowingItemList);

        // 设置缓存
        operatingFollowingItemPageCache(cacheKey, GlobalConstant.USER_FOLLOWING_PAGE_LIST_CACHE_TIME, pageNo, false, followingListPage);

        return followingListPage;
    }

    @Override
    public List<Long> queryTypeIdList(Map params) {
        return userFollowingMapper.queryTypeIdList(params);
    }

    @Override
    public String getUserKeyFromMainPage(UserFollowingDto following) {
        String userKey = null;
        if (following != null && StringUtils.isNotBlank(following.getMainPage())) {
            String[] mainPageArr = following.getMainPage().split("/");
            if (mainPageArr.length < 2) {
                return null;
            }
            if (following.getIsUser()) {
                switch (PlatformInfo.getPlatformInfoByUrl(following.getMainPage())) {
                    case BILIBILI:
                        userKey = mainPageArr[mainPageArr.length - 2];
                        break;
                    case WEIBO:
                        userKey = mainPageArr[mainPageArr.length - 1];
                        break;
                    case DOUBAN:

                        break;
                    case ZHIHU:

                        break;
                    default:

                }
            } else { // 非用户
                userKey = UUIDGenerateUtil.getUUID32();
            }
        }
        return userKey;
    }

    @Override
    public UserFollowingItem transformUserFollowing(UserFollowingDto followingDto) {
        UserFollowingItem userFollowingItem = new UserFollowingItem();
        userFollowingItem.setUserFollowing(followingDto);
        // 用户在某类型下的关注用户对应的标签列表
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("user_id", followingDto.getUserId());
        queryParams.put("following_id", followingDto.getFollowingId()); // 对应用户关注表的id
        List<UserFollowingRemark> followingRemarkList = userFollowingRemarkService.queryUserFollowingRemarkListRegularly(queryParams);
        // 关注用户添加对应标签
        userFollowingItem.setUserFollowingRemarkList(followingRemarkList);
        return userFollowingItem;
    }

    @Override
    public List<UserFollowingItem> transformUserFollowingList(List<UserFollowingDto> followingDtoList) {
        List<UserFollowingItem> userFollowingItemList = new ArrayList<>();
        for (UserFollowingDto userFollowing : followingDtoList) {
            if (userFollowing == null) continue;

            UserFollowingItem userFollowingItem = new UserFollowingItem();
            userFollowingItem.setUserFollowing(userFollowing);

            // 用户在某类型下的关注用户对应的标签列表
            Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("user_id", userFollowing.getUserId());
            queryParams.put("following_id", userFollowing.getFollowingId()); // 对应用户关注表的id
            List<UserFollowingRemark> followingRemarkList = userFollowingRemarkService.queryUserFollowingRemarkListRegularly(queryParams);
            // 关注用户添加对应标签
            userFollowingItem.setUserFollowingRemarkList(followingRemarkList);
            // 设置类型关注用户对象
            userFollowingItemList.add(userFollowingItem);
        }
        return userFollowingItemList;
    }

    @Override
    public Page<UserFollowingItem> operatingFollowingItemPageCache(String cacheKey, Long cacheTime, Long pageNo,
                                                                   boolean isDel, Page<UserFollowingItem> followingItemPage) {

        if (StringUtils.isBlank(cacheKey) || !cacheKey.contains(GlobalConstant.USER_FOLLOWING_PAGE_LIST_CACHE_KEY)) {
            log.debug("参数错误");
            return null;
        }

        // hash key 里面的字段
        String cacheKeyChild = null;
        if (pageNo != null) { // 注意，pageNo 不能直接给默认值，删除缓存的时候，要判断
            cacheKeyChild = cacheKey + "_" + pageNo;
        }

        if (isDel) { // 删除缓存
            if (StringUtils.isBlank(cacheKeyChild)) { // 删除 hash 下的所有字段和值
                //redisUtil.hdelall(cacheKey);
                redisUtil.del(cacheKey);
            } else { // 只删除 hash 下指定的字段
                redisUtil.hdel(cacheKey, cacheKeyChild);
            }
            return null;
        } else if (followingItemPage != null) { // 新增缓存
            if (cacheTime == null) {
                cacheTime = GlobalConstant.USER_FOLLOWING_PAGE_LIST_CACHE_TIME;
            }
            if (StringUtils.isBlank(cacheKeyChild)) { // 没有传入 pageNo，设置跟 hash 同名的字段值
                cacheKeyChild = cacheKey;
            }
            redisUtil.hset(cacheKey, cacheKeyChild, JSONUtil.toJSON(followingItemPage), cacheTime);
            return null;
        } else { // 获取缓存
            if (StringUtils.isBlank(cacheKeyChild)) { // 没有传入 pageNo，查询跟 hash 同名的字段值
                cacheKeyChild = cacheKey;
            }
            String cacheStr = ObjectUtil.getString(redisUtil.hget(cacheKey, cacheKeyChild));
            if (StringUtils.isNotBlank(cacheStr)) {
                Page<UserFollowingItem> itemListPage = JSONUtil.toBean(cacheStr, new TypeReference<Page<UserFollowingItem>>() {
                });
                if (itemListPage != null) {
                    return itemListPage;
                }
            }
        }

        return null;
    }
}
