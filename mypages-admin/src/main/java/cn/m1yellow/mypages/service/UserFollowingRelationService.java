package cn.m1yellow.mypages.service;

import cn.m1yellow.mypages.entity.UserFollowingRelation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户与关注用户关联表 服务类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-23
 */
public interface UserFollowingRelationService extends IService<UserFollowingRelation> {

    /**
     * 根据用户对应的类型id，变更关注用户的类型
     * @param userId 用户id
     * @param platformId 平台id
     * @param typeId 类型id
     * @param newTypeId 新类型id
     * @return 操作结果
     */
    boolean changeUserFollowingTypeByTypeId(Long userId, Long platformId, Long typeId, Long newTypeId);

}
