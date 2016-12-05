package chaos.core.model;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.DefaultCommentGenerator;

/**
 * mybatis 添加中午注释
 */
public class CommentGenerator extends DefaultCommentGenerator {

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (introspectedColumn.getRemarks() != null && !introspectedColumn.getRemarks().equals("")) {
            field.addJavaDocLine("/**");
            field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
            field.addJavaDocLine(" */");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("@ApiField(\"");
        sb.append(field.getName());
        sb.append("/");
        sb.append(introspectedColumn.getRemarks());
        sb.append("//\")");

        field.addFormattedAnnotations(sb,0);
        field.addAnnotation("@ApiField(\""+field.getName()+"/"+introspectedColumn.getRemarks()+"//\")");
//        super.addFieldComment(field, introspectedTable, introspectedColumn);
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTableNameAtRuntime());
        innerClass.addJavaDocLine(" */");
//        super.addClassComment(innerClass, introspectedTable, markAsDoNotDelete);
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * " + introspectedColumn.getRemarks());
        method.addJavaDocLine(" */");
//        super.addGetterComment(method, introspectedTable, introspectedColumn);
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * " + introspectedColumn.getRemarks());
        method.addJavaDocLine(" */");
//        super.addSetterComment(method, introspectedTable, introspectedColumn);
    }

    @Override
    public void addComment(XmlElement xmlElement) {
//        不添加注释
//        super.addComment(xmlElement);
    }
}