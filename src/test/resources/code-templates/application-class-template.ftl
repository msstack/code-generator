package ${packageName};

<#list importPackages as importPackage>
import ${importPackage};
</#list>

public class ${toCamel(className)} extends MicroserviceApplication {

    public static void main(String[] args) throws Exception {
        MicroserviceRunner.run(${toCamel(className)}.class);
    }
}