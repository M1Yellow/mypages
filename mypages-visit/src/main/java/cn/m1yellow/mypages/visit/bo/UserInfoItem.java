package cn.m1yellow.mypages.visit.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户信息封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserInfoItem 对象", description = "用户信息封装")
public class UserInfoItem implements Serializable {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "个性签名")
    private String signature;

    @ApiModelProperty(value = "头像地址")
    private String headImgUrl;

    @ApiModelProperty(value = "头像名称")
    private String headImgName;

    @ApiModelProperty(value = "头像存储路径")
    private String headImgPath;

}
