package cn.m1yellow.mypages.vo.home;

import cn.m1yellow.mypages.dto.UserFollowingDto;
import cn.m1yellow.mypages.entity.UserFollowingRemark;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 用户关注用户对象封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserFollowingItem对象", description = "用户关注用户对象封装")
public class UserFollowingItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户关注用户对象")
    private UserFollowingDto userFollowing;

    @ApiModelProperty(value = "用户关注用户对象标签列表")
    private List<UserFollowingRemark> userFollowingRemarkList;

}
