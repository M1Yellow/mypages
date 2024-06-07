package cn.m1yellow.mypages.service.impl;

import cn.m1yellow.mypages.entity.UserFollowingRelation;
import cn.m1yellow.mypages.mapper.UserFollowingRelationMapper;
import cn.m1yellow.mypages.service.UserFollowingRelationService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户与关注用户关联表 服务实现类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-23
 */
@Service
public class UserFollowingRelationServiceImpl extends ServiceImpl<UserFollowingRelationMapper, UserFollowingRelation> implements UserFollowingRelationService {

    @Override
    public boolean changeUserFollowingTypeByTypeId(Long userId, Long platformId, Long typeId, Long newTypeId) {
        if (null == userId || null == typeId || null == newTypeId) {
            return false;
        }
        UpdateWrapper<UserFollowingRelation> relationUpdateWrapper = new UpdateWrapper<>();
        relationUpdateWrapper.set("type_id", newTypeId);
        relationUpdateWrapper.eq("user_id", userId);
        relationUpdateWrapper.eq("platform_id", platformId);
        relationUpdateWrapper.eq("type_id", typeId);

        return update(relationUpdateWrapper);
    }
}
