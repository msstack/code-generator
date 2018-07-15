package ${packageName};

<#list importPackages as importPackage>
import ${importPackage};
</#list>

@Path("${toKebab(endPoint)}")
public class ${toCamel(className)} implements ${toCamel(handlerType)}<${toCamel(request.name)}, ${toCamel(response.name)}> {

    @Path("")
    @Override
    public ${toCamel(response.name)} handle(${toCamel(request.name)} ${toHeadlessCamel(request.name)}) {
    <#list events as event>
        // add logic before ${toCamel(event.name)} emit
        ${toCamel(event.name)} ${toHeadlessCamel(event.name)} = new ${toCamel(event.name)}();
        ${toHeadlessCamel(event.name)}.emit();
        // add logic after ${toCamel(event.name)} emit
    </#list>
        ${toCamel(response.name)} ${toHeadlessCamel(response.name)} = new ${toCamel(response.name)}();
        return ${toHeadlessCamel(response.name)};
    }

}