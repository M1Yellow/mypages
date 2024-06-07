package cn.m1yellow.mypages.vo.home;

import cn.m1yellow.mypages.common.util.desensitize.Sensitive;
import cn.m1yellow.mypages.common.util.desensitize.SensitiveTypeEnum;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户信息详情封装对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserInfoDetail 对象", description = "用户信息详情封装对象")
public class UserInfoDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "冗余用户id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @Sensitive(type = SensitiveTypeEnum.PASSWORD)
    @ApiModelProperty(value = "密码")
    private String password;

    @Sensitive(type = SensitiveTypeEnum.MOBILE_PHONE)
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @Sensitive(type = SensitiveTypeEnum.EMAIL)
    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "形象照片（头像）")
    private String profilePhoto;

    @ApiModelProperty(value = "性别，1-男；0-女，默认1")
    private Integer gender;

    @ApiModelProperty(value = "锁定时间，null-未锁定；当前时间之前-锁定；当前时间之后-待锁定")
    private LocalDateTime lockTime;

    @ApiModelProperty(value = "本条数据是否已删除，1-是；0-否，默认0")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
