package cn.m1yellow.mypages.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author M1Yellow
 * @since 2021-05-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserBase对象", description = "用户表")
public class UserBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String mobile;

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
