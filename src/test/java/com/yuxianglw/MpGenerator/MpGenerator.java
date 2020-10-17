//package com.yuxianglw.MpGenerator;
//
//import com.baomidou.mybatisplus.enums.IdType;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.rules.DbType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//
//public class MpGenerator {
//
//    public static void main(String[] args) {
//        //1. 全局配置
//        GlobalConfig config = new GlobalConfig();
////		String path = "C:\\Users\\Flynn\\Desktop";// 生成路径，可设置为项目路径
//        String path = System.getProperty("user.dir") + "\\src\\main\\java";//设置为当前项目里面，刷新项目可见
//        String author = "zhangtao";//设置作者
//        config.setActiveRecord(true) // 是否支持AR模式
//                .setAuthor(author) // 作者
//                .setOutputDir(path) // 生成路径，可设置为项目路径
//                .setFileOverride(true)  // 文件覆盖
//                .setIdType(IdType.ID_WORKER) // 主键策略
//                //①auto:数据库ID自增；
//                //②input:用户输入ID；
//                //③id_worker:全局唯一ID（64位），内容为空自动填充（默认）；
//                //④uuid:全局唯一ID（32位），内容为空自动填充
//                //.setServiceName("%sService")  // 设置生成的service接口的名字的首字母是否为I，这样设置就没有I
//                .setBaseResultMap(true)
//                .setEnableCache(false)// XML 二级缓存
//                .setOpen(false)//生成后打开文件夹
//                .setBaseColumnList(true);
//
//        //2. 数据源配置
//        DataSourceConfig dsConfig = new DataSourceConfig();
//        dsConfig.setDbType(DbType.MYSQL)  // 设置数据库类型
//                .setDriverName("com.mysql.cj.jdbc.Driver")
//                .setUrl("jdbc:mysql://192.168.100.154:3306/test_db?useUnicode=true&characterEncoding=utf8")
//                .setUsername("root")
//                .setPassword("200967tao");
//
//        //3. 策略配置
//        StrategyConfig stConfig = new StrategyConfig();
//        stConfig.setCapitalMode(true) //全局大写命名
//                .setDbColumnUnderline(true)  // 指定表名 字段名是否使用下划线
//                .setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
//                //.setTablePrefix("t_")    //以什么表开头
//                .setInclude("SYS_USER","SYS_ROLE","SYS_USER_ROLE","SYS_ROLE_PERMISSION","SYS_PERMISSION","SYS_MENU","SYS_PERMISSION_MENU","SYS_COMMON_FILE");  // 生成的表
//
//        //4. 包名策略配置
//        PackageConfig pkConfig = new PackageConfig();
//        pkConfig.setParent("com.yuxianglw")//父包名
//                .setMapper("mapper")
//                .setService("service")
//                .setServiceImpl("service.impl")
//                .setController("controller")
//                .setEntity("entity")
//                .setXml("mapper");
//
//        //5. 整合配置
//        AutoGenerator ag = new AutoGenerator();
//
//        ag.setGlobalConfig(config)
//                .setDataSource(dsConfig)
//                .setStrategy(stConfig)
//                .setPackageInfo(pkConfig);
//
//        //6. 执行
//        ag.execute();
//        System.err.println(author + "攻城狮，您的代码自动生成完毕，路径为：" + path);
//    }
//
//}