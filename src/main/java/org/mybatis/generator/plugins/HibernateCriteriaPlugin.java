package org.mybatis.generator.plugins;

import org.apache.log4j.Logger;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.internal.rules.Rules;

import java.util.ArrayList;
import java.util.List;

public class HibernateCriteriaPlugin extends PluginAdapter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HibernateCriteriaPlugin.class);

	private FullyQualifiedJavaType actionFQJT;
	private FullyQualifiedJavaType serviceFQJT;
	/** 数据库类型 */
	private String databaseType;
	private String targetPackage;

	private List<Element> elements;

	@Override
	public boolean validate(List<String> warnings) {
//		databaseType = context.getJdbcConnectionConfiguration().getDriverClass();
////		context.getTableConfigurations()
//		String action = context.getJavaModelGeneratorConfiguration().getTargetPackage() + ".action.actionFQJT";
//		String service = context.getJavaModelGeneratorConfiguration().getTargetPackage() + ".service.actionFQJT";
//		actionFQJT = new FullyQualifiedJavaType(action);
//		serviceFQJT = new FullyQualifiedJavaType(service);
        targetPackage = context.getJavaModelGeneratorConfiguration().getTargetPackage();

		return true;
	}

    private GeneratedJavaFile addAction(IntrospectedTable introspectedTable, TopLevelClass topLevelClass) {
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        addClassComment(topLevelClass, "action");

        FullyQualifiedJavaType testInter = new FullyQualifiedJavaType("com.dishui.testInter");
//        topLevelClass.addSuperInterface(testInter);
        topLevelClass.setSuperClass("test");


        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
//        field.setType(types);
        field.setName("condition"); //$NON-NLS-1$
        addFieldComment(field, "存放条件查询值");
        topLevelClass.addField(field);


        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.setName("actionFQJT"); //$NON-NLS-1$
        method.addBodyLine("condition = new HashMap<String, Object>();"); //$NON-NLS-1$
        topLevelClass.addMethod(method);



        GeneratedJavaFile file = new GeneratedJavaFile(topLevelClass, context.getJavaModelGeneratorConfiguration()
                .getTargetProject());

        return file;
    }


    private GeneratedJavaFile addservice(IntrospectedTable introspectedTable, TopLevelClass topLevelClass) {
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        addClassComment(topLevelClass, "service");

        FullyQualifiedJavaType testInter = new FullyQualifiedJavaType("com.dishui.testInter");
//        topLevelClass.addSuperInterface(testInter);
        topLevelClass.setSuperClass("test");


        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
//        field.setType(types);
        field.setName("condition"); //$NON-NLS-1$
        addFieldComment(field, "存放条件查询值");
        topLevelClass.addField(field);


        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.setName("actionFQJT"); //$NON-NLS-1$
        method.addBodyLine("condition = new HashMap<String, Object>();"); //$NON-NLS-1$
        topLevelClass.addMethod(method);



        GeneratedJavaFile file = new GeneratedJavaFile(topLevelClass, context.getJavaModelGeneratorConfiguration()
                .getTargetProject());

        return file;
    }

    private GeneratedJavaFile addserviceImpl(IntrospectedTable introspectedTable, TopLevelClass topLevelClass) {
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        addClassComment(topLevelClass, "serviceImpl");

        topLevelClass.addImportedType(serviceFQJT);

        FullyQualifiedJavaType testInter = serviceFQJT;
        topLevelClass.addSuperInterface(testInter);


        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
//        field.setType(types);
        field.setName("condition"); //$NON-NLS-1$
        addFieldComment(field, "存放条件查询值");
        topLevelClass.addField(field);


        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.setName("actionFQJT"); //$NON-NLS-1$
        method.addBodyLine("condition = new HashMap<String, Object>();"); //$NON-NLS-1$
        topLevelClass.addMethod(method);



        GeneratedJavaFile file = new GeneratedJavaFile(topLevelClass, context.getJavaModelGeneratorConfiguration()
                .getTargetProject());

        return file;
    }

    @Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {

		List<GeneratedJavaFile> files = new ArrayList<GeneratedJavaFile>();

        String domainObjectName = introspectedTable.getTableConfiguration().getDomainObjectName();
        String action = targetPackage + ".action." + domainObjectName + "Action";
        String service = targetPackage + ".service." + domainObjectName + "Service";
        String serviceImpl = targetPackage + ".service.impl." + domainObjectName + "ServiceImpl";

        actionFQJT = new FullyQualifiedJavaType(action);
        serviceFQJT = new FullyQualifiedJavaType(service);
//        serviceFQJT = new FullyQualifiedJavaType(action);
        FullyQualifiedJavaType serviceImplFQJT = new FullyQualifiedJavaType(serviceImpl);

        TopLevelClass actionTopLevelClass = new TopLevelClass(actionFQJT);
		TopLevelClass serviceTopLevelClass = new TopLevelClass(serviceFQJT);
		TopLevelClass serviceImplTopLevelClass = new TopLevelClass(serviceImplFQJT);

        GeneratedJavaFile actionFile = addAction(introspectedTable, actionTopLevelClass);
        GeneratedJavaFile serviceFile = addservice(introspectedTable, serviceTopLevelClass);
        GeneratedJavaFile serviceImplFile = addserviceImpl(introspectedTable, serviceImplTopLevelClass);

        files.add(actionFile);
        files.add(serviceFile);
        files.add(serviceImplFile);

		return files;
	}



//	@Override
//	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
//		// 接口方法
//		List<Method> methods = interfaze.getMethods();
//		Parameter parameter = new Parameter(actionFQJT, "example");
//		boolean first = true;
//		for (int i = 0; i < methods.size(); i++) {
//			Method method = methods.get(i);
//			if (method.getFormattedContent(0, true).contains("Example")) {
//				int size = method.getParameters().size();
//				if (first) {
//					interfaze.removeImportedType(new FullyQualifiedJavaType(introspectedTable.getExampleType()));
//					interfaze.addImportedType(actionFQJT);
//				}
//				if (size == 1) {
//					method.removeParameter(0);
//					method.addParameter(parameter);
//				} else if (size == 2) {
//					method.removeParameter(1);
//					method.addParameter(1, parameter);
//				}
//				first = false;
//			}
//		}
//		// 实现类
//		methods = topLevelClass.getMethods();
//		first = true;
//		for (int i = 0; i < methods.size(); i++) {
//			Method method = methods.get(i);
//			if (method.getFormattedContent(0, true).contains("Example")) {
//				int size = method.getParameters().size();
//				if (first) {
//					topLevelClass.removeImportedType(new FullyQualifiedJavaType(introspectedTable.getExampleType()));
//					topLevelClass.addImportedType(actionFQJT);
//				}
//				if (size == 1) {
//					method.removeParameter(0);
//					method.addParameter(parameter);
//				} else if (size == 2) {
//					method.removeParameter(1);
//					method.addParameter(1, parameter);
//				}
//				first = false;
//			}
//		}
//		// 内部类
//		InnerClass in = topLevelClass.getInnerClasses().get(0);
//		in.setSuperClass(actionFQJT);
//		Method method = in.getMethods().get(0);
//		method.removeParameter(1);
//		method.addParameter(1, parameter);
//
//		return true;
//	}


}
