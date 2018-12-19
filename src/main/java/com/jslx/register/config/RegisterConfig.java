package com.jslx.register.config;

import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.*;
import com.jfinal.core.Controller;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.redis.Redis;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.jfplugin.mail.MailPlugin;
import com.jslx.register.model._MappingKit;
import com.kabao.ext.kit.JaxbKit;
import com.kabao.ext.plugin.sqlinxml.SqlInXmlPlugin;
import freemarker.template.TemplateModelException;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: lybing
 * @create: 2018-12-12 13:41
 * @desc: 核心配置文件
 */

public class RegisterConfig extends JFinalConfig {
    public static String CDN_RESOURCES;
    public static String SHORTMESSAGE_SERVICE_URL;
    public static String SHORTMESSAGE_SERVICE_SOURCE;
    public static String PROJECT_DOMAIN;
    public static String COOKIE_KEY_PREFIX;
    public static Map<String, String> ROUTES = new HashMap();
    /**
     * 状态码 MAP
     */
    public static Map<String, String> STATUS_CODE_MAP = new HashMap();

    /**
     * 阿里云 oss 配置
     */
    public static String ALIYUN_OSS_ACCESS_ID;
    public static String ALIYUN_OSS_ACCESS_KEY;
    public static String ALIYUN_OSS_ENDPOINT;
    public static String ALIYUN_OSS_BUCKET_NAME;
    public static String ALIYUN_SSO_IMG_URL_PREX;

    Log log = Log.getLog(RegisterConfig.class);

    @Override
    public void afterJFinalStart() {
        try {
            FreeMarkerRender.getConfiguration().setSharedVariable("resource", CDN_RESOURCES);
        } catch (TemplateModelException e) {
            e.printStackTrace();
        }
        log.info("      【Jfinal 配置成功】");
        Redis.use().set("foo", "bar");
        log.info("      【redis 连接成功】");
    }

    @Override
    public void configConstant(Constants constants) {
        loadConfigProperties();
        loadStatusCodes();
        // log4j2
        constants.setLogFactory(new Log4j2Factory());
        // development mode
        constants.setViewType(ViewType.FREE_MARKER);
        constants.setDevMode(getPropertyToBoolean("config.devmode", false));
    }

    private void loadStatusCodes() {
        Prop props = PropKit.use("status-code.properties");
        Enumeration en = props.getProperties().keys();
        while (en.hasMoreElements()) {
            String name = en.nextElement().toString();
            String path = props.getProperties().getProperty(name);
            STATUS_CODE_MAP.put(name, path);
        }
        log.info("      【status-code.properties loaded】");
    }

    private void loadConfigProperties() {
        String configLocation = PathKit.getWebRootPath() + File.separator + "WEB-INF"+File.separator+"config"+File.separator+"config.properties";
        this.loadPropertyFile(new File(configLocation));
        // 域变量赋值
        CDN_RESOURCES = getProperty("cdn.resource", "http://localhost:8080/");
        SHORTMESSAGE_SERVICE_URL = getProperty("shortmessage.service.url", "");
        SHORTMESSAGE_SERVICE_SOURCE = getProperty("shortmessage.service.source", "");
        PROJECT_DOMAIN = getProperty("config.project.domain", "");
        COOKIE_KEY_PREFIX = getProperty("config.uuidname", "UUID");

        ALIYUN_OSS_ACCESS_ID = getProperty("aliyun.oss.access_id","");
        ALIYUN_OSS_ACCESS_KEY = getProperty("aliyun.oss.access_key","");
        ALIYUN_OSS_ENDPOINT = getProperty("aliyun.oss.endpoint","");
        ALIYUN_OSS_BUCKET_NAME = getProperty("aliyun.oss.bucket_name","");
        ALIYUN_SSO_IMG_URL_PREX = getProperty("aliyun.oss.img_url_prex","");
    }

    @Override
    public void configRoute(Routes routes) {
        log.info("      【routes config...】");
        // 加载路由配置文件并配置路由
        File directoryRoute = new File(this.getClass().getClassLoader().getResource("routes").getFile());
        File[] routeFiles = directoryRoute.listFiles((file, s) -> s.endsWith("route.xml"));
        for (File routeFile : routeFiles) {
            RouteGroup group = JaxbKit.unmarshal(routeFile, RouteGroup.class);
            String type = group.type;
            String pkge = group.pkge;
            ROUTES.put(pkge, ("".equals(type) ? "" : type + "/"));
            for (RouteItem routeItem : group.routeItems) {
                try {
                    String url = type + routeItem.controllerkey;
                    String javaClass = pkge + "." + routeItem.className;
                    routes.add(url, (Class<? extends Controller>) Class.forName(javaClass));
                    log.info(String.format("      【Mapped {%s} to '%s'】", url, javaClass));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    @Override
    public void configEngine(Engine engine) {
    }

    @Override
    public void configPlugin(Plugins plugins) {
        plugins.add(getSqlInXmlPlugin());
        DruidPlugin druidPlugin = getDruidPlugin();
        plugins.add(druidPlugin);
        plugins.add(getActiveRecord(druidPlugin));
        plugins.add(getRedisPlugin());

        Cron4jPlugin cp  = new Cron4jPlugin(PropKit.use("cron4jConfig.properties"),"cron4j");
        plugins.add(cp);

        MailPlugin mailPlugin = new MailPlugin(PropKit.use("mail.properties").getProperties());
        plugins.add(mailPlugin);
//        plugins.add(getShiroPlugin());
    }

    private SqlInXmlPlugin getSqlInXmlPlugin() {
        File[] sqlXmls = new File(this.getClass().getClassLoader().getResource("").getFile()).listFiles((file, name) -> name.endsWith("sql.xml"));
        for (File sqlXml : sqlXmls) {
            log.warn(String.format("【'%s' loaded】", sqlXml.getName()));
        }
        log.info("      【SqlInXmlPlugin loaded】");
        return new SqlInXmlPlugin();
    }

    private RedisPlugin getRedisPlugin() {
        RedisPlugin redisPlugin = new RedisPlugin(
                getProperty("redis.name",""),
                getProperty("redis.url",""),
                getPropertyToInt("redis.port", 6379),
                getProperty("redis.password",""));
        JedisPoolConfig jedisPoolConfig = redisPlugin.getJedisPoolConfig();
        //设置最大连接数
        jedisPoolConfig.setMaxTotal(3200);
        //设置最大空闲连接数
        jedisPoolConfig.setMaxIdle(50);
        //设置初始化连接数
        jedisPoolConfig.setMinIdle(10);
        redisPlugin.setSerializer(StringRedisSerializer.me);
        log.info("      【redisPlugin loaded】");
        return redisPlugin;
    }

//    private ShiroPlugin  getShiroPlugin(){
//        ShiroPlugin shiroPlugin = new ShiroPlugin();
//        shiroPlugin.start();
//        shiroPlugin.setLoginUrl("/login.do");//登陆url：未验证成功跳转
//        shiroPlugin.setSuccessUrl("/index.do");//登陆成功url：验证成功自动跳转
//        shiroPlugin.setUnauthorizedUrl("/login/needPermission");//授权url：未授权成功自动跳转
//        return shiroPlugin;
//    }

    private ActiveRecordPlugin getActiveRecord(DruidPlugin druidPlugin) {
        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        // 所有映射在 MappingKit 中自动化搞定
        _MappingKit.mapping(arp);
        //对修改的sql文件实现热加载
        arp.setDevMode(true);
        arp.setShowSql(getPropertyToBoolean("config.showsql", false));
        // 配置Mysql方言
        arp.setDialect(new MysqlDialect());
        // 配置属性名(字段名)大小写不敏感容器工厂
        arp.setContainerFactory(new CaseInsensitiveContainerFactory());
        log.info("      【ActiveRecordPlugin loaded】");
        return arp;
    }

    private DruidPlugin getDruidPlugin() {
        DruidPlugin druidPlugin = new DruidPlugin(
                getProperty("jdbc.url",""),
                getProperty("jdbc.username",""),
                getProperty("jdbc.password",""),
                getProperty("jdbc.driver", JdbcConstants.MYSQL_DRIVER));
        // 配置druid
        druidPlugin.setMinIdle(60);
        druidPlugin.setMaxPoolPreparedStatementPerConnectionSize(1600);
        StatFilter stat = new StatFilter();
        // 设置是否合并sql
        stat.setMergeSql(getPropertyToBoolean("config.mergesql", true));
        druidPlugin.addFilter(stat);
        // 添加Druid监控
        WallFilter wall = new WallFilter();
        wall.setDbType(JdbcConstants.MYSQL);
        druidPlugin.addFilter(wall);
        // 使用Druid LogFilter打印可执行的sql
        Log4j2Filter logFilter = new Log4j2Filter();
        logFilter.setStatementLogEnabled(false);
        logFilter.setStatementLogErrorEnabled(true);
        logFilter.setStatementExecutableSqlLogEnable(true);
        druidPlugin.addFilter(logFilter);
        log.info("      【DruidPlugin loaded】");
        return druidPlugin;
    }

    @Override
    public void configInterceptor(Interceptors interceptors) {
//        interceptors.add(new ShiroInterceptor());
    }

    @Override
    public void configHandler(Handlers handlers) {
        handlers.add(new ContextPathHandler());
        // 声明Druid监控页面UR
        handlers.add(new DruidStatViewHandler("/druid"));
    }

    @Override
    public void beforeJFinalStop() {


    }

}
