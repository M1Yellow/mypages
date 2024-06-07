package cn.m1yellow.mypages.dto;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * <p>
 * 用户观点看法封装对象
 * </p>
 *
 * @author M1Yellow
 * @since 2021-05-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserOpinionDto 对象", description = "用户观点看法封装对象")
public class UserOpinionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表id")
    private Long id;

    @NotNull(message = "用户id不能为空")
    @Min(value = 1L, message = "请检查用户id是否正确")
    @ApiModelProperty(value = "关联用户id")
    private Long userId;

    @NotNull(message = "平台id不能为空")
    @Min(value = 1L, message = "请检查平台id是否正确")
    @ApiModelProperty(value = "关联平台id")
    private Long platformId;

    @NotNull(message = "分类类型id不能为空")
    @Min(value = 0L, message = "请检查类型id是否正确")
    @ApiModelProperty(value = "观点对应类型，0-平台；其他-某一类型，默认0")
    private Long opinionType;

    @NotBlank(message = "观点看法内容不能为空")
    @Size(max = 800, message = "观点看法内容长度不能超过{max}")
    @ApiModelProperty(value = "观点看法内容")
    private String opinionContent;

    @ApiModelProperty(value = "优先级由低到高：1-10，默认5")
    private Integer sortNo;

    @ApiModelProperty(value = "本条数据是否已删除，1-是；0-否，默认0")
    @TableLogic
    private Boolean isDeleted;

}
