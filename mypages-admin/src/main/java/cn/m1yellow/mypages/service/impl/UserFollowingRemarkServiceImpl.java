package cn.m1yellow.mypages.service.impl;

import cn.m1yellow.mypages.common.util.ObjectUtil;
import cn.m1yellow.mypages.dto.UserFollowingDto;
import cn.m1yellow.mypages.entity.UserFollowingRemark;
import cn.m1yellow.mypages.mapper.UserFollowingRemarkMapper;
import cn.m1yellow.mypages.service.UserFollowingRemarkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户关注备注（便签）表 服务实现类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
@Slf4j
@Service
public class UserFollowingRemarkServiceImpl extends ServiceImpl<UserFollowingRemarkMapper, UserFollowingRemark> implements UserFollowingRemarkService {

    @Override
    public List<UserFollowingRemark> queryUserFollowingRemarkListRegularly(Map<String, Object> params) {
        QueryWrapper<UserFollowingRemark> followingRemarkQueryWrapper = new QueryWrapper<>();
        followingRemarkQueryWrapper.eq("user_id", params.get("user_id"));
        followingRemarkQueryWrapper.eq("following_id", params.get("following_id"));
        //followingRemarkQueryWrapper.eq("is_deleted", 0); // 已经有逻辑删除功能
        followingRemarkQueryWrapper.orderByDesc("sort_no");
        return this.list(followingRemarkQueryWrapper);
    }

    @Override
    @Transactional // 多条修改 sql 需要开启事务
    public boolean saveRemarks(List<UserFollowingRemark> remarkList, UserFollowingDto following) {

        log.info(">>>> saveRemarks, remarkList={}, following={}", remarkList, following);

        if (CollectionUtils.isEmpty(remarkList)) {
            log.info(">>>> save remark: remarkList is invalid.");
            return false;
        }

        if (null == following || null == following.getUserId() || null == following.getFollowingId()) {
            log.info(">>>> save remark: following is invalid.");
            return false;
        }

        // 先查询用户标签，判断标签是否存在
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("user_id", following.getUserId());
        queryParams.put("following_id", following.getFollowingId()); // saveFollowing.getId()
        List<UserFollowingRemark> existedRemarkList = queryUserFollowingRemarkListRegularly(queryParams);
        log.info(">>>> save remark: existedRemarkList: {}", existedRemarkList);

        // 直接在循环外面创建实例，放在循环内 new 实例容易产生坑！会导致永远只有一个元素
        List<Long> savedRemarkIdList = new ArrayList<>();
        for (UserFollowingRemark remark : remarkList) {
            if (null == remark || StringUtils.isBlank(remark.getLabelName())) {
                log.info(">>>> save remark: userId={}, followingId={}, labelName is blank.",
                        following.getUserId(), following.getFollowingId());
                continue;
            }

            if (null == remark.getUserId()) {
                remark.setUserId(following.getUserId());
            }
            if (null == remark.getFollowingId()) {
                remark.setFollowingId(following.getFollowingId());
            }

            if (!CollectionUtils.isEmpty(existedRemarkList)) {
                for (UserFollowingRemark existedRemark : existedRemarkList) {
                    // 判断标签是否已存在：标签存在，且添加的 remark id 为 null，则执行修改，不重复添加
                    if (existedRemark.getLabelName().trim().equals(remark.getLabelName().trim()) && null == remark.getId()) {

                        remark.setId(existedRemark.getId());
                        log.info(">>>> save remark: userId={}, followingId={}, remark is already existed: {}, do update.",
                                following.getUserId(), following.getFollowingId(), remark.getLabelName());
                        break;
                    }
                }
            }
            UserFollowingRemark followingRemark = new UserFollowingRemark();
            followingRemark.setId(remark.getId()); // 有id则更新，无则新增
            followingRemark.setUserId(following.getUserId());
            followingRemark.setFollowingId(following.getFollowingId()); // 是关注用户表的id，标签自然是跟关注的用户对应
            followingRemark.setLabelName(remark.getLabelName().trim());
            followingRemark.setSortNo(remark.getSortNo());
            followingRemark.setIsDeleted(false);

            // 去字符串字段两边空格
            ObjectUtil.stringFiledTrim(followingRemark);

            saveOrUpdate(followingRemark);

            // 记录保存成功的id
            savedRemarkIdList.add(followingRemark.getId());

        }

        // id 去重
        savedRemarkIdList = savedRemarkIdList.stream().distinct().collect(Collectors.toList());
        log.info(">>>> save remark: savedRemarkIdList: {}", savedRemarkIdList);

        // 删除本次保存id以外的记录
        if (!CollectionUtils.isEmpty(savedRemarkIdList)) {
            UpdateWrapper<UserFollowingRemark> updateWrapper = new UpdateWrapper();
            updateWrapper.set("is_deleted", 1);
            updateWrapper.eq("user_id", following.getUserId());
            updateWrapper.eq("following_id", following.getFollowingId());
            updateWrapper.notIn("id", savedRemarkIdList);

            update(updateWrapper);
        }

        return true;
    }

    @Override
    public long getRemarkCount(Long userId, Long followingId) {
        QueryWrapper<UserFollowingRemark> remarkQueryWrapper = new QueryWrapper<>();
        remarkQueryWrapper.eq("user_id", userId);
        remarkQueryWrapper.eq("following_id", followingId);

        return count(remarkQueryWrapper);
    }

    @Override
    public boolean removeAll(Long userId, Long followingId) {
        QueryWrapper<UserFollowingRemark> remarkQueryWrapper = new QueryWrapper<>();
        remarkQueryWrapper.eq("user_id", userId);
        remarkQueryWrapper.eq("following_id", followingId);

        return remove(remarkQueryWrapper);
    }
}
