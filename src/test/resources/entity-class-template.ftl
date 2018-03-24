package ${packageName};

<#list importPackages as importPackage>
import ${importPackage};
</#list>

public class ${toCamel(className)} {

<#list attributes as attribute>
<#if attribute.multiplicity == "single">
    private ${toCamel(attribute.type)} ${toHeadlessCamel(attribute.name)};
</#if>
<#if attribute.multiplicity == "array">
    private List<${toCamel(attribute.type)}> ${toHeadlessCamel(attribute.name)};
</#if>
</#list>

    // generating getters for entity attributes
<#list attributes as attribute>
<#if attribute.multiplicity == "single">
    public ${toCamel(attribute.type)} get${toCamel(attribute.name)}() {
        return this.${toHeadlessCamel(attribute.name)};
    }
</#if>
<#if attribute.multiplicity == "array">
    public List<${toCamel(attribute.type)}> get${toCamel(attribute.name)}() {
        return this.${toHeadlessCamel(attribute.name)};
    }
</#if>
</#list>
    // end generating getters for entity attributes

    // generating event handlers
<#list events as event>
    public void apply(${toCamel(event)} ${toHeadlessCamel(event)}) {
        // add ${toCamel(event)} based logic here
    }
</#list>
    // end generating event handlers

}