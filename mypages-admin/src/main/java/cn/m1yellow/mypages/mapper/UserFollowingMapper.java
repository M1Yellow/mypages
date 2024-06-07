package cn.m1yellow.mypages.mapper;

import cn.m1yellow.mypages.dto.UserFollowingDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.m1yellow.mypages.entity.UserFollowing;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户关注表 Mapper 接口
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
public interface UserFollowingMapper extends BaseMapper<UserFollowing> {

    UserFollowingDto getUserFollowing(Map<String, Object> params);

    List<UserFollowingDto> queryUserFollowingList(Map<String, Object> params);

    List<Long> queryTypeIdList(Map<String, Object> params);

    long getFollowingCount(Map<String, Object> params);

}
