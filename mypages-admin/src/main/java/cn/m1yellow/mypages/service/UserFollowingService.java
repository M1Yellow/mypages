package cn.m1yellow.mypages.service;

import cn.m1yellow.mypages.dto.UserFollowingDto;
import cn.m1yellow.mypages.entity.UserFollowing;
import cn.m1yellow.mypages.visit.bo.UserInfoItem;
import cn.m1yellow.mypages.vo.home.UserFollowingItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户关注表 服务类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
public interface UserFollowingService extends IService<UserFollowing> {

    /**
     * 获取用户信息
     *
     * @param following
     * @return
     */
    UserInfoItem doExcavate(UserFollowingDto following);

    /**
     * 保存同步用户信息
     *
     * @param userInfoItem
     * @param following
     * @return
     */
    boolean saveUserInfo(UserInfoItem userInfoItem, UserFollowing following);

    /**
     * 根据 id 逻辑删除
     *
     * @param following
     * @return
     */
    boolean deleteById(UserFollowing following);

    /**
     * 自定义查询一个对象
     *
     * @param params 参数封装 map
     * @return
     */
    UserFollowingDto getUserFollowing(Map<String, Object> params);

    /**
     * 获取关注用户数量
     *
     * @param params
     * @return
     */
    long getFollowingCount(Map<String, Object> params);

    /**
     * 自定义查询列表
     *
     * @param params
     * @return
     */
    List<UserFollowingDto> queryUserFollowingList(Map<String, Object> params);

    /**
     * 获取关注用户分页列表
     *
     * @param params
     * @return
     */
    Page<UserFollowingDto> getUserFollowingListPage(Map<String, Object> params);

    /**
     * 获取关注用户视图层对象分页列表
     *
     * @param params
     * @return
     */
    Page<UserFollowingItem> getUserFollowingItemListPage(Map<String, Object> params);

    /**
     * 获取关注用户的类型 id 列表
     *
     * @param params
     * @return
     */
    List<Long> queryTypeIdList(Map<String, Object> params);

    /**
     * 获取关注用户所在平台的id或标识
     *
     * @param following
     * @return
     */
    String getUserKeyFromMainPage(UserFollowingDto following);

    /**
     * 将关注用户传输对象转换为视图层需要的数据对象（包含标签）
     *
     * @param followingDto
     * @return
     */
    UserFollowingItem transformUserFollowing(UserFollowingDto followingDto);

    /**
     * 将关注用户传输对象列表转换为视图层需要的数据对象列表（包含标签）
     *
     * @param followingDtoList
     * @return
     */
    List<UserFollowingItem> transformUserFollowingList(List<UserFollowingDto> followingDtoList);

    /**
     * 操作关注用户视图层对象列表缓存
     * @param cacheKey 缓存key
     * @param cacheTime 缓存时间
     * @param pageNo 第几页
     * @param isDel 是否为删除缓存
     * @param followingItemPage 要缓存的对象
     * @return 从缓存中获得的对象
     */
    Page<UserFollowingItem> operatingFollowingItemPageCache(String cacheKey, Long cacheTime, Long pageNo,
                                                            boolean isDel, Page<UserFollowingItem> followingItemPage);

}
