package ${packageName};

<#list importPackages as importPackage>
import ${importPackage};
</#list>

@Path("/")
public class ${toCamel(className)} implements ${toCamel(handlerType)}<${toCamel(request.name)}, ${toCamel(response.name)}> {

    @Path("/${toKebab(className)}")
    public ${toCamel(response.name)} handle(${toCamel(request.name)} ${toHeadlessCamel(request.name)}) {
    <#list events as event>
        // add logic before ${toCamel(event.name)} emit
        ${toCamel(event.name)} ${toHeadlessCamel(event.name)} = new ${toCamel(event.name)}();
        // add logic after ${toCamel(event)} emit
        ${toCamel(response.name)} ${toHeadlessCamel(response.name)} = new ${toCamel(response.name)}();
        return ${toHeadlessCamel(response.name)};
    </#list>
    }

}