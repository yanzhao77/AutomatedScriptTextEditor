//import packagePath
<#list methodPackagePathList as packagePath>
    ${packagePath}
</#list>

def scriptFunction(){
//import script
<#list commandList as command>
    ${command}
</#list>
}

System.out.println("script is ready")

scriptFunction()