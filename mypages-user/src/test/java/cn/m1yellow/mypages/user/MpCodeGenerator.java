package cn.m1yellow.mypages.user;

import cn.m1yellow.mypages.generator.bo.MpConfigInfo;
import cn.m1yellow.mypages.generator.service.CodeGenerator;

/**
 * MyBatis-Plus 代码生成器 3.5.1 新版本
 */
public class MpCodeGenerator {

    /**
     * 作者
     */
    private static final String AUTHOR = "M1Yellow";

    /**
     * 数据库类型，mysql、oracle
     */
    private static final String DB_NAME = "mysql";

    /**
     * 数据库表
     */
    private static final String[] TABLES = {
            "user_base"
    };

    // 数据库连接信息
    private static final String URL = "jdbc:mysql://localhost:3306/mypages?useUnicode=true&characterEncoding=UTF-8" +
            "&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    /**
     * 模块名称
     */
    private static final String MODULE_NAME = "mypages-user";


    public static void main(String[] args) {
        // 配置信息封装
        MpConfigInfo mpConfigInfo = new MpConfigInfo();
        // 项目根目录
        mpConfigInfo.setProjectDir(System.getProperty("user.dir"));

        // GlobalConfig 全局配置
        mpConfigInfo.setGcOutputDir(mpConfigInfo.getProjectDir() + "/" + MODULE_NAME + "/src/main/java");
        mpConfigInfo.setGcOutputDir(mpConfigInfo.getProjectDir() + "/" + MODULE_NAME + "/src/test"); // 先测试一下
        mpConfigInfo.setGcAuthor(AUTHOR);
        mpConfigInfo.setGcOpen(false);
        mpConfigInfo.setGcFileOverride(true);
        mpConfigInfo.setGcSwagger(true);
        mpConfigInfo.setGcServiceInterface(true);

        // DataSourceConfig 数据源配置
        mpConfigInfo.setDbTables(TABLES);
        mpConfigInfo.setDsUrl(URL);
        mpConfigInfo.setDsDriverName(DRIVER_NAME);
        mpConfigInfo.setDsUsername(USERNAME);
        mpConfigInfo.setDsPassword(PASSWORD);
        mpConfigInfo.setDsDbName(DB_NAME);

        // PackageConfig 包配置
        mpConfigInfo.setPcParent("cn.m1yellow.mypages.user");
        mpConfigInfo.setPcModuleName(null); // 模块内部不用再区分模块
        mpConfigInfo.setPcEntity("entity");
        mpConfigInfo.setPcMapper("mapper");
        mpConfigInfo.setPcService("service");
        mpConfigInfo.setPcController("controller");

        // StrategyConfig 策略配置
        //mpConfigInfo.setScInclude(TABLES);
        // TODO yml配置不生效，代码配置后才生效。可以在这控制是否启用逻辑删除功能。注意，名称是对应数据库表字段
        mpConfigInfo.setScLogicDeleteFieldName("is_deleted");
        mpConfigInfo.setScEntityLombokModel(true);
        mpConfigInfo.setScEntityGenerate(true);
        mpConfigInfo.setScEntityFileOverride(true);
        mpConfigInfo.setScMapperXmlGenerate(true);
        mpConfigInfo.setScBaseResultMap(true);
        mpConfigInfo.setScMapperFileOverride(true);
        mpConfigInfo.setScServiceGenerate(false);
        mpConfigInfo.setScServiceImplGenerate(false);
        mpConfigInfo.setScServiceFileOverride(false);
        mpConfigInfo.setScControllerGenerate(false);
        mpConfigInfo.setScRestControllerStyle(true);
        mpConfigInfo.setScControllerMappingHyphenStyle(true);
        mpConfigInfo.setScControllerFileOverride(false);

        // 执行生成
        CodeGenerator<MpConfigInfo> mpCodeGenerator = new cn.m1yellow.mypages.generator.service.impl.MpCodeGenerator();
        mpCodeGenerator.run(mpConfigInfo);
    }
}
