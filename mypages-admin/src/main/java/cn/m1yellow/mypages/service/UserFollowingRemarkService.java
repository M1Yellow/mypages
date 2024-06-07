package cn.m1yellow.mypages.service;

import cn.m1yellow.mypages.dto.UserFollowingDto;
import cn.m1yellow.mypages.entity.UserFollowingRemark;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户关注备注（便签）表 服务类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
public interface UserFollowingRemarkService extends IService<UserFollowingRemark> {

    List<UserFollowingRemark> queryUserFollowingRemarkListRegularly(Map<String, Object> params);

    boolean saveRemarks(List<UserFollowingRemark> remarkList, UserFollowingDto followingBo);

    long getRemarkCount(Long userId, Long followingId);

}
