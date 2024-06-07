package cn.m1yellow.mypages.service;

import cn.m1yellow.mypages.entity.UserFollowingType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户关注分类表 服务类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
public interface UserFollowingTypeService extends IService<UserFollowingType> {

    /**
     * 获取合并后的类型列表
     * @param userId 用户id
     * @param platformId 平台id
     * @param defaultType 游离的默认类型，为null会默认创建对象
     * @return 合并后结果
     */
    List<UserFollowingType> getUserFollowingTypeMergeList(Long userId, Long platformId, UserFollowingType defaultType);

}
