package org.mybatis.generator.plugins;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * 给dao增加注解增加注解
 *
 * @author QQ:34847009
 * @date 2010-10-21 下午09:33:48
 */
public class AnnotationPlugin extends PluginAdapter {
    private FullyQualifiedJavaType entity;
    private FullyQualifiedJavaType table;
    private FullyQualifiedJavaType column;

    public AnnotationPlugin() {
        super();
        entity = new FullyQualifiedJavaType("org.hibernate.annotations.Entity"); //$NON-NLS-1$
        table = new FullyQualifiedJavaType("org.hibernate.annotations.Table"); //$NON-NLS-1$
        column = new FullyQualifiedJavaType("org.hibernate.annotations.Column"); //$NON-NLS-1$
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Method method = topLevelClass.getMethods().get(0);
        addAnnotation(topLevelClass, method);
//		method.addParameter(new Parameter(sqlMapClient, "sqlMapClient"));
        method.removeBodyLine(0);
        method.addBodyLine("super.setSqlMapClient(sqlMapClient);");
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        List<Method> methods = topLevelClass.getMethods();
        addAnnotation2(topLevelClass, methods, introspectedTable);

        return true;
    }

    /**
     * 添加注解
     *
     * @param topLevelClass
     * @param method
     */
    protected void addAnnotation(TopLevelClass topLevelClass, Method method) {
//		topLevelClass.addImportedType(sqlMapClient);
//		topLevelClass.addImportedType(autowired);
//		topLevelClass.addImportedType(repository);
        method.addAnnotation("@Autowired");
        topLevelClass.addAnnotation("@Repository");
    }

    protected void addAnnotation2(TopLevelClass topLevelClass, List<Method> methods, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType(entity);
        topLevelClass.addImportedType(table);
        topLevelClass.addImportedType(column);

//        for (Method method : methods) {
//            String name = method.getName();
//            if (name.startsWith("get")) {
//                System.out.println(name);
//                method.addAnnotation("@Column(name=\"" +2+ "\")");
//            }
//        }

        topLevelClass.addAnnotation("@Entity");
//        System.out.println(introspectedTable.getTableConfiguration().getTableName());
        topLevelClass.addAnnotation("@Table(name=\"" + introspectedTable.getTableConfiguration().getTableName() + "\")");
    }

}
