package cn.m1yellow.mypages.generator.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * MyBatis-Plus 代码生成器的参数配置信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MpConfigInfo对象", description = "代码生成器的参数配置信息")
public class MpConfigInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目根目录")
    private String projectDir;
    @ApiModelProperty(value = "数据库表")
    private String[] dbTables;

    // GlobalConfig 全局配置
    @ApiModelProperty(value = "代码生成主目录")
    private String gcOutputDir;
    @ApiModelProperty(value = "代码作者")
    private String gcAuthor;
    @ApiModelProperty(value = "是否打开目录")
    private boolean gcOpen;
    @ApiModelProperty(value = "重新生成文件时是否覆盖，新版本有更细化的控制")
    private boolean gcFileOverride;
    @ApiModelProperty(value = "去掉 I 前缀，默认生成的 service 会有 I 前缀，新版本默认没有了")
    private String gcServiceName;
    @ApiModelProperty(value = "是否启用实体属性 Swagger 注解")
    private boolean gcSwagger;
    @Builder.Default // 默认值
    @ApiModelProperty(value = "是否生成 service 接口")
    private boolean gcServiceInterface = true;

    // DataSourceConfig 数据源配置
    @ApiModelProperty(value = "数据库连接地址")
    private String dsUrl;
    @ApiModelProperty(value = "数据库驱动包")
    private String dsDriverName;
    @ApiModelProperty(value = "数据库连接用户名")
    private String dsUsername;
    @ApiModelProperty(value = "数据库连接密码")
    private String dsPassword;
    @ApiModelProperty(value = "数据库类型名称：mysql；oracle")
    private String dsDbName;

    // PackageConfig 包配置
    @ApiModelProperty(value = "父包名")
    private String pcParent;
    @ApiModelProperty(value = "模块名")
    private String pcModuleName;
    @ApiModelProperty(value = "entity 包名")
    private String pcEntity;
    @ApiModelProperty(value = "mapper 包名")
    private String pcMapper;
    @ApiModelProperty(value = "service 包名")
    private String pcService;
    @ApiModelProperty(value = "controller 包名")
    private String pcController;

    // StrategyConfig 策略配置
    @ApiModelProperty(value = "要操作的数据库表")
    private String[] scInclude;
    @ApiModelProperty(value = "逻辑删除字段")
    private String scLogicDeleteFieldName;
    @ApiModelProperty(value = "是否启用 lombok")
    private boolean scEntityLombokModel;
    @ApiModelProperty(value = "是否启用 BaseResultMap 生成")
    private boolean scBaseResultMap;
    @Builder.Default
    @ApiModelProperty(value = "是否生成Entity")
    private boolean scEntityGenerate = true;
    @ApiModelProperty(value = "重新生成entity文件时是否覆盖")
    private boolean scEntityFileOverride;
    @Builder.Default
    @ApiModelProperty(value = "是否生成Mapper&xml")
    private boolean scMapperXmlGenerate = true;
    @ApiModelProperty(value = "重新生成mapper&xml文件时是否覆盖")
    private boolean scMapperFileOverride;
    @ApiModelProperty(value = "是否生成service&Impl")
    private boolean scServiceGenerate;
    @ApiModelProperty(value = "是否生成serviceImpl")
    private boolean scServiceImplGenerate;
    @ApiModelProperty(value = "重新生成service文件时是否覆盖")
    private boolean scServiceFileOverride;
    @ApiModelProperty(value = "是否启用 restful 风格")
    private boolean scRestControllerStyle;
    @ApiModelProperty(value = "是否开启mapping地址驼峰转连字符")
    private boolean scControllerMappingHyphenStyle;
    @ApiModelProperty(value = "是否生成controller")
    private boolean scControllerGenerate;
    @ApiModelProperty(value = "重新生成controller文件时是否覆盖")
    private boolean scControllerFileOverride;

}
