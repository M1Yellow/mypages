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
 * 用户观点看法表
 * </p>
 *
 * @author M1Yellow
 * @since 2021-05-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserOpinion对象", description = "用户观点看法表")
public class UserOpinion implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "关联用户id")
    private Long userId;

    @ApiModelProperty(value = "关联平台id")
    private Long platformId;

    @ApiModelProperty(value = "观点对应类型，0-平台；其他-某一类型，默认0")
    private Long opinionType;

    @ApiModelProperty(value = "观点看法内容")
    private String opinionContent;

    @ApiModelProperty(value = "优先级由低到高：1-10，默认5")
    private Integer sortNo;

    @ApiModelProperty(value = "本条数据是否已删除，1-是；0-否，默认0")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
