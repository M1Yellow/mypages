package cn.m1yellow.mypages.generator.service.impl;

import cn.m1yellow.mypages.generator.bo.MpConfigInfo;
import cn.m1yellow.mypages.generator.service.CodeGenerator;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.Controller;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.builder.Mapper;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.querys.OracleQuery;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.stereotype.Service;

/**
 * MyBatis-Plus 代码生成器实现类 3.5.1 新版本
 */
@Service(value = "mpCodeGenerator")
public class MpCodeGenerator implements CodeGenerator<MpConfigInfo> {

    /**
     * 全局配置
     */
    private GlobalConfig globalConfig(MpConfigInfo mpConfigInfo) {
        GlobalConfig.Builder builder = new GlobalConfig.Builder();
        builder.author(mpConfigInfo.getGcAuthor()); // 创建人
        builder.outputDir(mpConfigInfo.getGcOutputDir()); // 指定代码生成的输出目录
        //builder.dateType(DateType.ONLY_DATE); // 时间类型，java.util.date，默认值: DateType.TIME_PACK
        if (!mpConfigInfo.isGcOpen()) builder.disableOpenDir(); // 禁止自动打开输出目录，默认会打开
        if (mpConfigInfo.isGcSwagger()) builder.enableSwagger(); // 开启 Swagger 模式，默认值: false
        //if (!mpConfigInfo.isGcServiceInterface()) builder.disableServiceInterface(); // 不生成service接口，看情况设置，默认会生成

        return builder.build();
    }

    /**
     * 数据源配置
     */
    private DataSourceConfig dataSourceConfig(MpConfigInfo mpConfigInfo) {
        return new DataSourceConfig.Builder(mpConfigInfo.getDsUrl(), mpConfigInfo.getDsUsername(),
                mpConfigInfo.getDsPassword()).dbQuery(getDbQueryByName(mpConfigInfo.getDsDbName())).build();
    }

    private IDbQuery getDbQueryByName(String dbName) {
        if (null != dbName && !dbName.trim().equals("")) {
            switch (dbName.trim().toLowerCase()) {
                case "oracle":
                    return new OracleQuery();
            }
        }
        return new MySqlQuery();
    }

    /**
     * 包配置
     */
    private static PackageConfig packageConfig(MpConfigInfo mpConfigInfo) {
        return new PackageConfig.Builder()
                .parent(mpConfigInfo.getPcParent())
                //.moduleName(mpConfigInfo.getPcModuleName()) // 全局配置输出路径指定了模块，这里不用再指定
                // entity service mapper xml controller 路径默认即可
                //.pathInfo(Collections.singletonMap(OutputFile.xml, projectDir + "/" + moduleName + "/src/main/resources/mapper/"))
                .build();
    }

    /**
     * 策略配置
     */
    private static StrategyConfig strategyConfig(MpConfigInfo mpConfigInfo) {
        StrategyConfig.Builder builder = new StrategyConfig.Builder();
        Entity.Builder entityBuilder = builder.entityBuilder();
        Mapper.Builder mapperBuilder = builder.mapperBuilder();
        com.baomidou.mybatisplus.generator.config.builder.Service.Builder serviceBuilder = builder.serviceBuilder();
        Controller.Builder controllerBuilder = builder.controllerBuilder();

        // Entity 策略配置
        entityBuilder.formatFileName("%s"); // 名称格式
        entityBuilder.logicDeleteColumnName(mpConfigInfo.getScLogicDeleteFieldName()); // 逻辑删除字段名(数据库字段)
        // 模板文件可以直接到 mybatis-plus-generator 源码里 templates 复制代码出来，粘贴到 resources 下的 templates 目录
        //entityBuilder.javaTemplate("/templates/entity.java"); // 不用带上.ftl/.vm, 会根据使用的模板引擎自动识别
        if (mpConfigInfo.isScEntityLombokModel()) entityBuilder.enableLombok(); // 允许 Lombok，默认不允许
        if (mpConfigInfo.isScEntityFileOverride()) entityBuilder.enableFileOverride(); // 覆盖之前文件，默认不覆盖
        if (!mpConfigInfo.isScEntityGenerate()) entityBuilder.disable();

        // Mapper 策略配置
        mapperBuilder.formatMapperFileName("%sMapper");
        mapperBuilder.formatXmlFileName("%sMapper");
        if (mpConfigInfo.isScBaseResultMap()) mapperBuilder.enableBaseResultMap();
        if (mpConfigInfo.isScMapperFileOverride()) mapperBuilder.enableFileOverride();
        if (!mpConfigInfo.isScMapperXmlGenerate()) mapperBuilder.disable();

        // Service 策略配置
        serviceBuilder.formatServiceFileName("%sService");
        serviceBuilder.formatServiceImplFileName("%sServiceImpl");
        if (mpConfigInfo.isScServiceFileOverride()) serviceBuilder.enableFileOverride();
        if (!mpConfigInfo.isScServiceGenerate()) serviceBuilder.disable();
        if (!mpConfigInfo.isScServiceImplGenerate()) serviceBuilder.disableServiceImpl();

        // Controller 策略配置
        controllerBuilder.formatFileName("%sController");
        if (mpConfigInfo.isScRestControllerStyle()) controllerBuilder.enableRestStyle();
        if (mpConfigInfo.isScControllerFileOverride()) controllerBuilder.enableFileOverride();
        if (mpConfigInfo.isScControllerMappingHyphenStyle()) controllerBuilder.enableHyphenStyle();
        if (!mpConfigInfo.isScControllerGenerate()) controllerBuilder.disable(); // 3.5.6 版本不生效

        // 相关表名配置
        String[] tableNames = mpConfigInfo.getDbTables();
        // 通配符模式，比如，sys_* 会生成 sys_ 开头的所有表
        if (tableNames.length == 1 && tableNames[0].contains("*")) {
            String[] likeStr = tableNames[0].split("_");
            String likePrefix = likeStr[0] + "_";
            builder.likeTable(new LikeTable(likePrefix));
        } else {
            builder.addInclude(tableNames);
        }

        return builder.build();
    }

    /**
     * 自定义配置
     */
    private static InjectionConfig injectionConfig(MpConfigInfo mpConfigInfo) {
        InjectionConfig.Builder builder = new InjectionConfig.Builder();
        return builder.build();
    }

    @Override
    public void run(MpConfigInfo mpConfigInfo) {
        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfig(mpConfigInfo));
        autoGenerator.global(globalConfig(mpConfigInfo));
        autoGenerator.packageInfo(packageConfig(mpConfigInfo));
        autoGenerator.strategy(strategyConfig(mpConfigInfo));
        autoGenerator.injection(injectionConfig(mpConfigInfo));
        autoGenerator.execute(new FreemarkerTemplateEngine()); // 设置模板引擎之后，会自动从 /templates 目录下读取模板
    }
}
