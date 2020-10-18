package com.yuxianglw.AutoGenerator;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
/**
 * 自动生成类
 *
 * @author zhangtao
 * @date 2020/06/13
 */
public class CodeGeneration {

    //main函数
    public static void main(String[] args) {

        AutoGenerator autoGenerator = new AutoGenerator();

        //全局配置
        GlobalConfig gc = new GlobalConfig();
        String path = System.getProperty("user.dir");//得到当前项目的路径
        gc.setOutputDir(path + "/src/main/java");   //生成文件输出根目录
        gc.setOpen(false);//生成完成后不弹出文件框
        gc.setFileOverride(true);  //文件覆盖
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor("zhangtao");// 作者

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        autoGenerator.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);   //设置数据库类型
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("200967tao");
        dsc.setUrl("jdbc:mysql://192.168.100.154:3306/test_db?useUnicode=true&characterEncoding=utf8");  //指定数据库
        autoGenerator.setDataSource(dsc);

       // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        strategy.setInclude(new String[] { "SYS_USER","SYS_ROLE","SYS_USER_ROLE","SYS_ROLE_PERMISSION","SYS_PERMISSION","SYS_MENU","SYS_PERMISSION_MENU","SYS_COMMON_FILE"});
        // 自动lombok
        strategy.setEntityLombokModel(true);
        //逻辑删除标识字段
        strategy.setLogicDeleteFieldName("DEL_FLAG");
        // 乐观锁
        strategy.setVersionFieldName("REVISION");
        //rest风格
        strategy.setRestControllerStyle(true);
        autoGenerator.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.yuxianglw");
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        pc.setXml("mapper");
        pc.setEntity("entity");
        autoGenerator.setPackageInfo(pc);

        // 执行生成
        autoGenerator.execute();
        System.out.println("-----end-----");
    }
}
