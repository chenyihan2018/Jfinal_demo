package com.jslx.register.generator;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;
import java.io.File;

import static com.jslx.register.generator._JFinalTableMappingGenerator.generatorApiModelKit;

/**
 * 在数据库表有任何变动时，运行一下 main 方法，极速响应变化进行代码重构
 */
public class _JFinalDemoGenerator {
	
	/**
	 * 数据库连接URL,数据库用户名，数据库密码，驱动
	 */
	public static String JDBC_URL;
	public static String JDBC_USERNAME;
	public static String JDBC_PASSWORD;
	public static String JDBC_DRIVER;

	public static DataSource getDataSource() {
		Prop props = PropKit.use(new File(PathKit.getWebRootPath() + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "WEB-INF" + File.separator + "config" + File.separator + "config.properties"));
		JDBC_URL = props.getProperties().getProperty("jdbc.url");
		JDBC_USERNAME = props.getProperties().getProperty("jdbc.username");
		JDBC_PASSWORD = props.getProperties().getProperty("jdbc.password");
		JDBC_DRIVER = props.getProperties().getProperty("jdbc.driver");
		
		DruidPlugin druidPlugin = new DruidPlugin(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD, JDBC_DRIVER);
        druidPlugin.start();
        return druidPlugin.getDataSource();
    }
	
	public static void main(String[] args) {
        // base model 所使用的包名
        String baseModelPackageName = "com.jslx.register.model.base";
        // base model 文件保存路径
        String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/com/jslx/register/model/base";
        
        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = "com.jslx.register.model";
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir + "/..";
        
        // 创建生成器
        Generator generator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
        // 设置是否生成链式 setter 方法
        generator.setGenerateChainSetter(false);
        // 添加不需要生成的表名
        generator.addExcludedTable();

        // 设置是否在 Model 中生成 dao 对象
        generator.setGenerateDaoInModel(true);
        // 设置是否生成链式 setter 方法
        generator.setGenerateChainSetter(true);
        // 设置是否生成字典文件
        generator.setGenerateDataDictionary(false);
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
        generator.setRemovedTableNamePrefixes("t_");
        // 生成
        generator.generate();
    
        
        // 生成ApiModelKit.java
        // ApiModelKit 所使用的包名
        String apiModelKitPackageName = "com.jslx.register.model";
        // 继承com.jfinal.plugin.activerecord.Table类 存放位置的包路径名
        String extendsTablePackageName = "com.jslx.register.generator.Table";
        // ApiModelKit 文件保存路径 (与MappingKit同目录)
        String apiModelKitOutputDir = PathKit.getWebRootPath() + "/src/main/java/com/jslx/register/model/";
        // 生成
        generatorApiModelKit(getDataSource(), apiModelKitPackageName, extendsTablePackageName, apiModelKitOutputDir);
        
    }
}
