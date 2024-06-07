package cn.m1yellow.mypages.dto;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 用户与关注用户关联封装对象
 * </p>
 *
 * @author M1Yellow
 * @since 2021-05-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserFollowingRelationDto 对象", description = "用户与关注用户关联封装对象")
public class UserFollowingRelationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表id")
    private Long id;

    @NotNull(message = "用户id不能为空")
    @Min(value = 1L, message = "请检查用户id是否正确")
    @ApiModelProperty(value = "关联用户id")
    private Long userId;

    @NotNull(message = "关注用户id不能为空")
    @Min(value = 1L, message = "请检查关注用户id是否正确")
    @ApiModelProperty(value = "关联关注用户id")
    private Long followingId;

    @NotNull(message = "平台id不能为空")
    @Min(value = 1L, message = "请检查平台id是否正确")
    @ApiModelProperty(value = "关联平台id，冗余字段，用于提升查询效率")
    private Long platformId;

    @NotNull(message = "分类类型id不能为空")
    @Min(value = 0L, message = "请检查类型id是否正确") // TODO 类型为0，表示默认分类
    @ApiModelProperty(value = "关联关注类型表id，1-默认分类，用于变更类型")
    private Long typeId;

    @Min(value = 0L, message = "请检查原始类型id是否正确")
    @ApiModelProperty(value = "原始类型id，用户变更分组后，清理原来类型的用户里列表缓存")
    private Long oldTypeId;

    @ApiModelProperty(value = "优先级由低到高：1-10，默认5。8-思想、学习；7-美食、营养；6、健身、锻炼；5-兴趣、生活；4~其他")
    private Integer sortNo;

    @ApiModelProperty(value = "本条数据是否已删除，1-是；0-否，默认0")
    @TableLogic
    private Boolean isDeleted;

}
