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
 * 社交媒体平台表
 * </p>
 *
 * @author M1Yellow
 * @since 2021-05-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserPlatform对象", description = "社交媒体平台表")
public class UserPlatform implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "平台名称")
    private String name;

    @ApiModelProperty(value = "平台英文名称")
    private String nameEn;

    @ApiModelProperty(value = "平台主页")
    private String mainPage;

    @ApiModelProperty(value = "平台logo")
    private String platformLogo;

    @ApiModelProperty(value = "平台长logo")
    private String platformLongLogo;

    @ApiModelProperty(value = "本条数据是否已删除，1-是；0-否，默认0")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
