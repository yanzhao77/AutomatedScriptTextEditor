#import packagePath
<#list methodPackagePathList as packagePath>
    ${packagePath}
</#list>

def scriptFunction():
#import script
<#list commandList as command>
    ${command}
</#list>

println("script is ready")

scriptFunction()

