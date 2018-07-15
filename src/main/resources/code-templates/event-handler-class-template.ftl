package ${packageName};

<#list importPackages as importPackage>
import ${importPackage};
</#list>

public class ${toCamel(className)} implements EventHandler<${toCamel(request.name)}> {

    public void handle(${toCamel(request.name)} ${toHeadlessCamel(request.name)}) {
    <#list events as event>
        // add logic before ${toCamel(event.name)} emit
        ${toCamel(event.name)} ${toHeadlessCamel(event.name)} = new ${toCamel(event.name)}();
        ${toHeadlessCamel(event.name)}.emit();
        // add logic after ${toCamel(event.name)} emit
    </#list>
    }

}