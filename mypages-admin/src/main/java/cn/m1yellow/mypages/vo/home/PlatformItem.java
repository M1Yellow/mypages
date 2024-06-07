package cn.m1yellow.mypages.vo.home;

import cn.m1yellow.mypages.dto.UserPlatformDto;
import cn.m1yellow.mypages.entity.UserOpinion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 首页平台内容数据封装对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false) // 生成 equals() 和 hashCode()方法，callSuper 默认仅使用该类中定义的属性且不调用父类的方法
@ApiModel(value = "PlatformItem对象", description = "首页平台内容数据封装对象")
public class PlatformItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "平台信息对象")
    private UserPlatformDto platformBaseInfo;

    @ApiModelProperty(value = "用户对平台的看法列表")
    private List<UserOpinion> platformOpinionList;

    @ApiModelProperty(value = "用户关注用户类型列表")
    private List<UserFollowingTypeItem> userFollowingTypeList;

}
