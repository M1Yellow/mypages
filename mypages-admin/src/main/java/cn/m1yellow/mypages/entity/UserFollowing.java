package cn.m1yellow.mypages.entity;

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
 * 用户关注表
 * </p>
 *
 * @author M1Yellow
 * @since 2021-05-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserFollowing对象", description = "用户关注表")
public class UserFollowing implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "关联平台id")
    private Long platformId;

    @ApiModelProperty(value = "关联用户来源平台的id或标识")
    private String userKey;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "主页地址")
    private String mainPage;

    @ApiModelProperty(value = "形象照片（头像）")
    private String profilePhoto;

    @ApiModelProperty(value = "个性签名")
    private String signature;

    @ApiModelProperty(value = "是否为用户，1-是用户；0-不是，默认1")
    private Boolean isUser;

    @ApiModelProperty(value = "本条数据是否已删除，1-是；0-否，默认0")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
