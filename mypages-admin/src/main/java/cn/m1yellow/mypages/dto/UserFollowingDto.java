package cn.m1yellow.mypages.dto;

import cn.m1yellow.mypages.entity.UserFollowingRemark;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 关注用户信息封装对象
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserFollowingDto 对象", description = "关注用户信息封装对象")
public class UserFollowingDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "这里的值为用户与关注用户关系表（user_following_relation）id")
    private Long id;

    @NotNull(message = "用户id不能为空")
    @Min(value = 1L, message = "请检查用户id是否正确")
    @ApiModelProperty(value = "用户与关注用户关系表查询的用户id")
    private Long userId;

    @ApiModelProperty(value = "对应关注用户表（user_following）的id")
    private Long followingId;

    @NotNull(message = "平台id不能为空")
    @Min(value = 1L, message = "请检查平台id是否正确")
    @ApiModelProperty(value = "关联平台id")
    private Long platformId;

    @NotNull(message = "分类类型id不能为空")
    @Min(value = 0L, message = "请检查类型id是否正确") // 0-默认分类
    @ApiModelProperty(value = "关联关注类型表id，1-默认分类")
    private Long typeId;

    @ApiModelProperty(value = "关联用户来源平台的id或标识")
    private String userKey;

    @NotBlank(message = "用户名不能为空")
    // EL表达式。案例：@DecimalMax(value = "350", message = " ${formatter.format('%1$.2f', validatedValue)} 大于 {value}", groups = Group2.class)
    @Size(max = 20, message = "用户名长度不能超过{max}")
    @ApiModelProperty(value = "用户名")
    private String name;

    @NotBlank(message = "用户主页不能为空")
    //@Email(message = "主页地址格式不正确") // 不用自带的 @Email 校验，前后端使用同一验证格式
    @Pattern(regexp = "(https?)://([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\\.)*[a-zA-Z0-9-]+\\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*", message = "主页地址格式不正确")
    @Size(max = 200, message = "主页长度不能超过{max}")
    @ApiModelProperty(value = "主页地址")
    private String mainPage;

    @Size(max = 150, message = "头像图片地址长度不能超过{max}")
    @ApiModelProperty(value = "形象照片（头像）")
    private String profilePhoto;

    @Size(max = 200, message = "签名长度不能超过{max}")
    @ApiModelProperty(value = "个性签名")
    private String signature;

    @ApiModelProperty(value = "关注用户的标签列表")
    private List<UserFollowingRemark> remarkList;

    @ApiModelProperty(value = "关注用户的标签列表json格式")
    private String remarkListJson;

    @Builder.Default // 默认值
    @ApiModelProperty(value = "是否为用户，1-是用户；0-不是，默认1")
    private Boolean isUser = true;

    @Builder.Default
    @Min(value = 0, message = "优先级由低到高：0-100")
    @Max(value = 100, message = "优先级由低到高：0-100")
    @ApiModelProperty(value = "优先级由低到高：0-100，默认50")
    private Integer sortNo = 50;

    @Builder.Default
    @ApiModelProperty(value = "本条数据是否已删除，1-是；0-否，默认0")
    private Boolean isDeleted = false;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}
