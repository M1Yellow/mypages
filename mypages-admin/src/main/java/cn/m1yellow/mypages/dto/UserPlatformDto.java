package cn.m1yellow.mypages.dto;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户平台封装对象
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserPlatformDto 对象", description = "用户平台封装对象")
public class UserPlatformDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户平台关系表id")
    private Long id;

    @NotNull(message = "用户id不能为空")
    @Min(value = 1L, message = "请检查用户id是否正确")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "关联平台表id") // 用作新增平台参数封装的时候，platformId 可以为空
    private Long platformId;

    @NotBlank(message = "平台名不能为空")
    @Size(max = 50, message = "平台中文名长度不能超过{max}")
    @ApiModelProperty(value = "平台名称")
    private String name;

    @NotBlank(message = "平台英文名不能为空")
    @Size(max = 50, message = "平台英文名长度不能超过{max}")
    @ApiModelProperty(value = "平台英文名称")
    private String nameEn;

    @NotBlank(message = "平台主页不能为空")
    @Pattern(regexp = "(https?)://([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\\.)*[a-zA-Z0-9-]+\\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*", message = "主页地址格式不正确")
    @Size(max = 100, message = "主页长度不能超过{max}")
    @ApiModelProperty(value = "平台主页")
    private String mainPage;

    @NotBlank(message = "平台logo不能为空")
    @Size(max = 150, message = "平台logo图片地址长度不能超过{max}")
    @ApiModelProperty(value = "平台logo")
    private String platformLogo;

    @NotBlank(message = "平台长logo不能为空")
    @Size(max = 150, message = "平台长logo图片地址长度不能超过{max}")
    @ApiModelProperty(value = "平台长logo")
    private String platformLongLogo;

    @ApiModelProperty(value = "优先级由低到高：1-10，默认5。取用户平台关系表的字段")
    private Integer sortNo;

    @ApiModelProperty(value = "本条数据是否已删除，1-是；0-否，默认0。取用户平台关系表的字段")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}
