package com.jslx.register.generator;

import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.ColumnMeta;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.activerecord.generator.TableMeta;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class _JFinalTableMappingGenerator {
	
	private static String packageTemplate = "package %s;%n%n";
    private static String importTemplate = "import com.jfinal.plugin.activerecord.Model;%nimport com.jfinal.plugin.activerecord.TableMapping;%n";
    private static String importTableTemplate = "import %s;%n%n";
    private static String importUtilTemplate = "import java.util.HashMap;%nimport java.util.Map;%n%n";
    private static String apiModelKitMethodDefineTemplate = "public final class _ApiModelKit {\n\n" +
            "\tpublic static void init(String tableName, String primaryKey,\n" +
            "\t\t\t\t\tClass<? extends Model<?>> modelClass, Map<String, Class<?>> attrTypeMapTypeMap) {\n" +
            "\t\tTable table = new Table(tableName, primaryKey, modelClass);\n" +
            "\t\ttable.setColumnTypeMap(attrTypeMapTypeMap);\n" +
            "\t\tTableMapping.me().putTable(table);\n" +
            "\t}\n\n" +
            "\tpublic static void initModel() {\n" +
            "\t\tSystem.out.println(\"Starting mapping model...\");%n";
    
    /**
     * 生成_ApiModelKit.java
     *
     * @param dataSource              数据源
     * @param apiModelKitPackageName  ApiModelKit 所使用的包名
     * @param extendsTablePackageName 继承com.jfinal.plugin.activerecord.Table类 存放位置的包路径名
     * @param apiModelKitOutputDir    ApiModelKit 文件保存路径 (与MappingKit同目录)
     */
    public static void generatorApiModelKit(DataSource dataSource, String apiModelKitPackageName, String extendsTablePackageName, String apiModelKitOutputDir) {
        System.out.println("Generate ApiModelKit file ...");
        System.out.println("ApiModelKit Output Dir: " + apiModelKitOutputDir);
        StringBuilder ret = new StringBuilder();
        
        // 生成package
        ret.append(String.format(packageTemplate, new Object[]{apiModelKitPackageName}));
        
        // 生成import
        ret.append(String.format(importTemplate, new Object[0]));
        
        // 生成继承后的Table类
        ret.append(String.format(importTableTemplate, new Object[]{extendsTablePackageName}));
        
        // 生成import HashMap,Map
        ret.append(String.format(importUtilTemplate, new Object[]{extendsTablePackageName}));
        
        // 生成主体代码
        ret.append(String.format(apiModelKitMethodDefineTemplate, new Object[0]));
        ret.append(String.format(generatorMethodBody(dataSource), new Object[0]));
        ret.append(String.format("\t}%n%n}%n%n", new Object[0]));
        
        // 生成文件
        writeToFile(ret, apiModelKitOutputDir);
    }
    
    public static String generatorMethodBody(DataSource dataSource) {
        //设置 MetaBuilder
        MetaBuilder metaBuilder = new MetaBuilder(dataSource);
        //设置数据库方言
        metaBuilder.setDialect(new MysqlDialect());
        List<TableMeta> tableMetas = metaBuilder.build();
        if (tableMetas.size() == 0) {
            System.out.println("TableMeta 数量为 0，不生成任何文件");
            return "";
        }

        //文件内容
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tMap<String, Class<?>> columnMap = new HashMap<String, Class<?>>();\r\n");
        
        //循环表
        for (TableMeta tableMeta : tableMetas) {

            //循环列
            for (ColumnMeta columnMeta : tableMeta.columnMetas) {
                //生成格式为columnMap.put("id", java.lang.String.class);
                sb.append("\t\tcolumnMap.put(\"");
                sb.append(columnMeta.name);
                sb.append("\", ");
                sb.append(columnMeta.javaType);
                sb.append(".class);\r\n");
            }

            //手动注册表,生成格式为TableInitKit.init("blog", "id", Blog.class, columnMap);
            sb.append("\t\tinit(\"");
            sb.append(tableMeta.name);
            sb.append("\", \"");
            sb.append(tableMeta.primaryKey);
            sb.append("\", ");
            sb.append(tableMeta.modelName);
            sb.append(".class, columnMap);\r\n");

            sb.append("\r\n");

            //提醒该表未设计primaryKey
            if ("".equals(tableMeta.primaryKey)) {
                System.out.println(tableMeta.modelName + "表primaryKey未设置！！！");
            }

        }
        return sb.toString();
    }
    
    public static void writeToFile(StringBuilder ret, String apiModelKitOutputDir) {
        FileWriter fw = null;
        
        try {
            File e = new File(apiModelKitOutputDir);
            if (!e.exists()) {
                e.mkdirs();
            }
            
            String target = apiModelKitOutputDir + File.separator + "_ApiModelKit.java";
            fw = new FileWriter(target);
            fw.write(ret.toString());
        } catch (IOException var12) {
            throw new RuntimeException(var12);
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException var11) {
                    LogKit.error(var11.getMessage(), var11);
                }
            }
            
        }
        
    }

}
