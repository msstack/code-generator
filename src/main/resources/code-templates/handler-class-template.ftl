package ${packageName};

<#list importPackages as importPackage>
import ${importPackage};
</#list>

public class ${toCamel(className)} {

    public ${toCamel(response.name)} handle(${toCamel(request.name)} ${toHeadlessCamel(request.name)}) {
    <#list events as event>
        // add logic before ${toCamel(event)} emit
        ${toCamel(event)} ${toHeadlessCamel(event)} = new ${toCamel(event)}();
        // add logic after ${toCamel(event)} emit
        ${toCamel(response.name)} ${toHeadlessCamel(response.name)} = new ${toCamel(response.name)}();
        return ${toHeadlessCamel(response.name)};
    </#list>
    }

}