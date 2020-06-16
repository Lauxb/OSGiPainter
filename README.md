# 产品介绍 #
 OSGiPainter是基于Apache Felix、Maven构建的一个画布Demo，旨在学习Felix如何嵌入程序、启动及调试。

# 启动调试 #
* 启动配置
    Run/Debug Configurations
    1、添加Application
    2、指定Main class: 	com.dist.paint.launcher.Main
    3、指定Working directory:	d:\xx\OSGiPainter\src\release\dist
    4、指定Environment variables:	path=bin
    5、Use classpath of module: 	Launcher
    6、Before launch
    	Run Maven Goal 'OSGiPainter:clean'
    	Run Maven Goal 'OSGiPainter:install'
    ![](snapshot/app_startup_config.png)
    
* 展示效果
    具体请查看snapshot文件夹下的图片。
    ![](snapshot/painter.png)

# Modules结构 #
* Launcher
    自定义启动器。

* Shape
    SimpleShape基本图形接口。圆、正方形等图形是其具体实现。

* Frame
    应用程序界面框架。

* Circle
    实现圆的绘制。

* Square
    实现正方形的绘制。

* Triangle
    实现三角形的绘制。

* Star
    实现五角星的绘制。

* Distribution
    打包模块。


# Maven插件 #

1、maven-bundle-plugin学习

```pom.xml
<plugin>
    <groupId>org.apache.felix</groupId>
    <artifactId>maven-bundle-plugin</artifactId>
    <version>4.2.1</version>
    <extensions>true</extensions>
    <configuration>

        <!--
        *************************************************************
        指定 manifest 文件的生成路径，默认为 ${project.build.outputDirectory}/META-INF。
        *************************************************************
        -->
        <manifestLocation>${project.basedir}/src/main/resources/META-INF</manifestLocation>
        <finalName>${project.name}</finalName>

        <!--
        *************************************************************
        生成的 jar 文件所在路径。不显示配置则默认为 project.build.directory，也就是 target。统一默认配置为 bundles/painter_bundles/，
        以配合 Felix 配置文件 config.properties 的优先级设置，该路径设置 felix.auto.deploy.dir，用以自动发布普通功能性插件。
        如有特殊需求则自行配置，如 Core、Controls、Frame 则配置为 bundles/require_bundles/。
        *************************************************************
        -->
        <buildDirectory>${project.basedir}/../bundles/painter_bundles/</buildDirectory>
        <instructions>
            <Manifest-Version>1.0</Manifest-Version>

            <!--
            *************************************************************
            OSGI R4规范这个值只能是2。
            *************************************************************
            -->
            <Bundle-ManifestVersion>2</Bundle-ManifestVersion>

            <!--
            *************************************************************
            不显式配置则默认为 ${pom.name}。
            *************************************************************
            -->
            <Bundle-Name>${project.name}</Bundle-Name>

            <!--
            *************************************************************
            is computed using the shared Maven2OsgiConverter component, which uses the following algorithm:
            Get the symbolic name as groupId + "." + artifactId, with the following exceptions:
            1. if artifact.getFile is not null and the jar contains a OSGi Manifest with Bundle-SymbolicName property
               then that value is returned.
            2. if groupId has only one section (no dots) and artifact.getFile is not null then the first package name
               with classes is returned. eg. commons-logging:commons-logging -> org.apache.commons.logging.
            3. if artifactId is equal to last section of groupId then groupId is returned. eg. org.apache.maven:maven
               -> org.apache.maven.
            4. if artifactId starts with last section of groupId that portion is removed. eg. org.apache.maven:maven-core
               -> org.apache.maven.core The computed symbolic name is also stored in the $(maven-symbolicname) property
               in case you want to add attributes or directives to it.
               *************************************************************
               -->
            <!--<Bundle-SymbolicName></Bundle-SymbolicName>-->

            <!--
            *************************************************************
            不显式配置则默认 ${pom.version}，按 "MAJOR.MINOR.MICRO.QUALIFIER" 的形式格式化。
            Bundle-Version 和 Bundle-SymbolicName 共同作为一个 Bundle 的唯一标识。
            *************************************************************
            -->
            <Bundle-Version>${bundle.version}</Bundle-Version>

            <!--
            *************************************************************
            不显式配置则默认 ${pom.description}。
            *************************************************************
            -->
            <Bundle-Description>${project.description}</Bundle-Description>

            <!--########## 该项子模块必须自定义 ##########-->
            <!--<Bundle-Activator></Bundle-Activator>-->

            <!--
            *************************************************************
            不显式配置则默认为 ${pom.organization.name}。
            *************************************************************
            -->
            <Bundle-Vendor>${project.organization.name}</Bundle-Vendor>

            <!--
            *************************************************************
            不显式配置则默认为 ${pom.organization.url}。
            *************************************************************
            -->
            <Bundle-DocURL>${project.organization.url}</Bundle-DocURL>

            <!--
            *************************************************************
            打包的资源文件，默认打包 maven 资源目录，也就是 src/main/resources/ 里的内容。
            该配置与 maven-resources-plugin 互斥，并且没有 maven-resources-plugin 灵活，同时配置则以 maven-resources-plugin 为准。
            建议使用 maven-resources-plugin，也就是 <Resources></Resources> 配置项。
            *************************************************************
            -->
            <!--<Include-Resource>{maven-resources}</Include-Resource>-->

            <!--
            *************************************************************
            1. Requre-Bundle 为导入整个 Bundle，Import-Package 为导入指定的包，Import-Package更为灵活。
            2. Import-Package 默认为 *，也就是导入所有引用的包，包括配置的依赖，但这些依赖并非 bundle，不能 Import。
            3. Require-Bundle 所导入的整个包，可以重复导出该 Bundle 已导出过的包，但这样会导致 Required Bundle inline，maven-bundle-plugin 还是不够智能。
            *************************************************************
            -->
            <!--<Require-Bundle></Require-Bundle>-->

            <!--
            *************************************************************
            is assumed to be "*", which imports everything referred to by the bundle content, but not contained in the bundle.
             Any exported packages are also imported by default, to ensure a consistent class space.
             *************************************************************
            -->
            <Import-Package>
                org.osgi.framework,
                org.osgi.util.tracker
            </Import-Package>

            <!--
            *************************************************************
            不显式配置则默认导出本地源代码里的包，使用 {local-packages} 表示本地包
            Export-Package 和 Private-Package 会将所配置的包以内联（inline）的形式打到包里。
            由于 OSGI bundle 间依赖的特殊性，类似Controls 对 Core 的非直接依赖会导致的编译时依赖错误。
            在 eclipse 或者 IDEA 等均有各自的依赖规则以及编译器处理 jar in jar 的依赖问题，maven-compiler-plugin 没有这样的特性。
            两种方案：
            1. Core 直接依赖第三方库，并在打包时 inline 第三方库，其他 bundle 均 Require-Bundle Core 或者 Import-Pakcage 使用 Core 导出的 package；
                目前采用这个方案。
            2. 所有 Bundle 均配置对应第三方库的依赖，打包的时候，将所有的依赖打包到指定的路径，不再 inline 或者 jar in jar。
            3. 如果打包的时候有需求打包为 jar in jar 的形式，则使用 _exportcontents + Embed-Dependency 进行灵活处理。
            4. 本项目采用依赖 inline 的方式来解决 jar in jar 编译时依赖错误的问题（见备注 1），并默认所有子模块导出本地包，并按需导出第三方依赖包。也即 Core
                导出本地包+第三方包，Controls 导出本地包，由此来解决 Controls 重复导出 Core 中包的问题。
            5. 本项目默认不导出任何包，子模块按需求自行配置。
            6. Export-Package 只会 inline 代码，而相关的一些 properties 等资源文件则不会打到包里，因此需要配合 Embed-Dependency inline=true 使用。
            *************************************************************
            -->
            <Export-Package>!*</Export-Package>

            <!--
            *************************************************************
            1. BND 的 exported 配置，功能与 Exported-Pakcage 一致，只是不处理依赖包的内联（inline），通常与 Embed-Dependency 搭配使用，
            避免因为 Exported-Package 的依赖内联，导致与 Embed-Dependency的相同包重复。
            2. 由于 Export-Package 会导致被导出的包内联，因此如果 Controls 导出了 Core 里的某些内容，则会导致 Core 的 inline，产生重复，因此可以使用
             _exportcontents + Embed-Dependency 来进行更为灵活的处理。
             *************************************************************
             -->
            <!--<_exportcontents></_exportcontents>-->

            <!--
            *************************************************************
            Private-Package 默认为源码里 exported 之外的所有包。exported 的优先级高于 private，如果有相同的配置，则 exported 生效。
            *************************************************************
            -->
            <!--<Private-Package></Private-Package>-->

            <!--
            *************************************************************
            需要注意的是 inline=true，设置这个属性的原因见 <Export-Package> 备注。
            *************************************************************
            -->
            <Embed-Dependency>*;scope=compile|system</Embed-Dependency>

            <!--
            *************************************************************
            指定 Embed 依赖相对包的根目录的路径。
            *************************************************************
            -->
            <Embed-Directory>lib</Embed-Directory>

            <!--
            *************************************************************
            配置 Embed 的依赖传递。
            *************************************************************
            -->
            <Embed-Transitive>true</Embed-Transitive>

            <!--
            *************************************************************
            禁用 Require-Capability，这个属性是 Bundle 运行的环境约束，BND 会根据编译版本以及相关依赖包自动生成，很多时候生成的内容并不是我们想要的。
            1. 本项目可以约定为 1.7，即如下的 <Require-Capability> 配置。
            2. 本项目也可以直接禁用 Require-Capability，如下使用 <_noee>true</_noee> 配置，默认采用这种方案。
            *************************************************************
            -->
            <!--<Require-Capability>osgi.ee;filter:="(&amp;(osgi.ee=JavaSE)(version=1.7))"</Require-Capability>-->
            <_noee>true</_noee>
        </instructions>
    </configuration>
</plugin>
```
