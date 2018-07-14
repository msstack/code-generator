package ${packageName};

<#list importPackages as importPackage>
import ${importPackage};
</#list>

public class ${toCamel(className)} {

<#list attributes as attribute>
    <#if attribute.array>
    private List<${toCamel(attribute.type)}> ${toHeadlessCamel(attribute.name)};
    <#else>
    private ${toCamel(attribute.type)} ${toHeadlessCamel(attribute.name)};
    </#if>
</#list>

    // generating getters for entity attributes
<#list attributes as attribute>
    <#if attribute.array>
    public List<${toCamel(attribute.type)}> get${toCamel(attribute.name)}() {
        return this.${toHeadlessCamel(attribute.name)};
    }
    <#else>
    public ${toCamel(attribute.type)} get${toCamel(attribute.name)}() {
        return this.${toHeadlessCamel(attribute.name)};
    }
    </#if>
</#list>
    // end generating getters for entity attributes

}