package cn.m1yellow.mypages;

import cn.m1yellow.mypages.generator.bo.MpConfigInfo;
import cn.m1yellow.mypages.generator.service.CodeGenerator;

/**
 * MyBatis-Plus 代码生成器
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
            //"user_base",
            //"user_platform"
            //,"user_platform_relation"
            //,"user_following"
            //,"user_following_relation"
            //"user_following_type"
            //,"user_following_remark"
            //,"user_opinion"
            //,"user_check_update"
            "sys_config"
    };

    // 数据库连接信息
    private static final String URL = "jdbc:mysql://192.168.31.147:3306/mypages-test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456.a";

    /**
     * 模块名称
     */
    private static final String MODULE_NAME = "mypages-admin";


    public static void main(String[] args) {
        // 配置信息封装
        MpConfigInfo mpConfigInfo = new MpConfigInfo();

        // GlobalConfig 全局配置
        mpConfigInfo.setGcOutputDir(System.getProperty("user.dir") + "/" + MODULE_NAME + "/src/main/java"); // System.getProperty("user.dir") 为当前项目根目录
        mpConfigInfo.setGcAuthor(AUTHOR);
        mpConfigInfo.setGcOpen(false);
        mpConfigInfo.setGcFileOverride(true);
        mpConfigInfo.setGcServiceName("%sService");
        mpConfigInfo.setGcSwagger2(true);

        // DataSourceConfig 数据源配置
        mpConfigInfo.setDbTables(TABLES);
        mpConfigInfo.setDsUrl(URL);
        mpConfigInfo.setDsDriverName(DRIVER_NAME);
        mpConfigInfo.setDsUsername(USERNAME);
        mpConfigInfo.setDsPassword(PASSWORD);
        mpConfigInfo.setDsDbName(DB_NAME);

        // PackageConfig 包配置
        mpConfigInfo.setPcParent("cn.m1yellow.mypages");
        mpConfigInfo.setPcModuleName(null);
        mpConfigInfo.setPcEntity("entity");
        mpConfigInfo.setPcMapper("mapper");
        mpConfigInfo.setPcService("service");
        mpConfigInfo.setPcController("controller");

        // StrategyConfig 策略配置
        //mpConfigInfo.setScInclude(TABLES);
        // TODO yml配置不生效，代码配置后才生效。可以在这控制是否启用逻辑删除功能。注意，名称是对应数据库表字段
        mpConfigInfo.setScLogicDeleteFieldName("is_deleted");
        mpConfigInfo.setScEntityLombokModel(true);
        mpConfigInfo.setScRestControllerStyle(true);
        mpConfigInfo.setScControllerMappingHyphenStyle(true);

        // 执行生成器
        CodeGenerator mpCodeGenerator = new cn.m1yellow.mypages.generator.service.impl.MpCodeGenerator();
        mpCodeGenerator.run(mpConfigInfo);
    }
}
