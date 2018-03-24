package ${packageName};

<#list importPackages as importPackage>
import ${importPackage};
</#list>

public class ${toCamel(className)} {

    public void handle() {
    <#list events as event>
        // add logic before ${toCamel(event)} emit
        ${toCamel(event)} ${toHeadlessCamel(event)} = new ${toCamel(event)}();
        // add logic after ${toCamel(event)} emit
    </#list>
    }

}