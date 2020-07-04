package com.course.server.dto;
<#--typeSet作用整理出所有用到的java类型 生成import语句，一种类型只需要import一次 所以用set去重-->
<#list typeSet as type>
    <#if type='Date'>
        import java.util.Date;
        import com.fasterxml.jackson.annotation.JsonFormat;
    </#if>
    <#if type="BigDecimal">
        import java.math.BigDecimal;
    </#if>
</#list>


public class ${Domain}Dto {

<#list fieldList as field>
    /*
    *${field.comment}
    */

<#--    将后端日期类型格式话在返回给前端-->
    <#if field.javaType=='Date'>
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    </#if>
    private ${field.javaType} ${field.nameHump};
</#list>
<#list fieldList as field>
    public ${field.javaType} get${field.nameBigHump}() {
    return ${field.nameHump};
    }
    public void  set${field.nameBigHump}(${field.javaType} ${field.nameHump}) {
    this.${field.nameHump}=${field.nameHump};
    }
</#list>
@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
<#list fieldList as field>
    sb.append(", ${field.nameHump}=").append(${field.nameHump});
</#list>
sb.append("]");
return sb.toString();
}
}