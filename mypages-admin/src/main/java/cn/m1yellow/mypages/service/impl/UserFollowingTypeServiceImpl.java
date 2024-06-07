package cn.m1yellow.mypages.service.impl;

import cn.m1yellow.mypages.common.constant.GlobalConstant;
import cn.m1yellow.mypages.entity.UserFollowingType;
import cn.m1yellow.mypages.mapper.UserFollowingTypeMapper;
import cn.m1yellow.mypages.service.UserFollowingTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 用户关注分类表 服务实现类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
@Service
public class UserFollowingTypeServiceImpl extends ServiceImpl<UserFollowingTypeMapper, UserFollowingType> implements UserFollowingTypeService {

    @Override
    public List<UserFollowingType> getUserFollowingTypeMergeList(Long userId, Long platformId, UserFollowingType defaultType) {
        QueryWrapper<UserFollowingType> typeQueryWrapper = new QueryWrapper<>();
        typeQueryWrapper.eq("user_id", userId);
        typeQueryWrapper.eq("platform_id", platformId);
        typeQueryWrapper.orderByDesc("sort_no");
        typeQueryWrapper.orderByAsc("id"); // 额外的排序，会产生 file sort 文件排序，对查询性能造成一定的影响，按需求添加
        List<UserFollowingType> userFollowingTypeList = list(typeQueryWrapper);

        // 添加默认类型
        List<UserFollowingType> UserFollowingTypeMergeList = new LinkedList<>();
        if (null == defaultType) {
            defaultType = new UserFollowingType();
            defaultType.setId(0L);
            defaultType.setUserId(userId);
            defaultType.setPlatformId(platformId);
            defaultType.setTypeName(GlobalConstant.USER_FOLLOWING_DEFAULT_TYPE_NAME);
            defaultType.setSortNo(10);
            defaultType.setIsDeleted(false);
            defaultType.setCreateTime(LocalDateTime.now());
            defaultType.setUpdateTime(LocalDateTime.now());
        }
        // 合并默认类型和数据库表类型
        UserFollowingTypeMergeList.add(defaultType);
        if (!CollectionUtils.isEmpty(userFollowingTypeList)) {
            UserFollowingTypeMergeList.addAll(userFollowingTypeList);
        }

        return UserFollowingTypeMergeList;
    }
}
