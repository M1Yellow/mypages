package cn.m1yellow.mypages.security.entity;

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
 * 角色表
 * </p>
 *
 * @author M1Yellow
 * @since 2021-06-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysRole对象", description = "角色表")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色代码，英文，用于程序业务处理")
    private String code;

    @ApiModelProperty(value = "角色名，用于页面显示")
    private String name;

    @ApiModelProperty(value = "角色描述，用于补充提示说明")
    private String description;

    @ApiModelProperty(value = "用于统计对应该角色的用户数量，目前暂未使用")
    private Integer count;

    @ApiModelProperty(value = "角色状态，0-正常；1-禁用，默认0")
    private Integer status;

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
