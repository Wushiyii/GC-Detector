# JVM-Detector
GC事件探测，窗口时间内大于阈值或高CPU活动会报警并打印JStack

#### 启动
在JVM启动参数后面加上 -javaagent:jar路径。 如
```java
-Xms8M
-Xmx8M
-XX:+UseParNewGC
-XX:+UseConcMarkSweepGC
-javaagent:/Users/apple/IdeaProjects/my/JVM-Detector/target/JVM-Detector-1.0.jar
```

#### 打印忙碌堆栈
```
2022-07-11 13:35:14.845|JVM-Detector-timer|ERROR|[GCSamplerTask] GC-count over limit, gcCountThreshold=2 gcName=ConcurrentMarkSweep increasedGcCount=30
2022-07-11 13:35:15.520|JVM-Detector-timer|INFO |"main" Id=1 cpuUsage=0.0% deltaTime=0ms time=2213ms RUNNABLE
    at java.util.jar.Attributes.read(Attributes.java:394)
    at java.util.jar.Manifest.read(Manifest.java:234)
    at java.util.jar.JarInputStream.checkManifest(JarInputStream.java:95)
    at java.util.jar.JarInputStream.<init>(JarInputStream.java:86)
    at java.util.jar.JarInputStream.<init>(JarInputStream.java:62)
    at org.apache.catalina.util.ExtensionValidator.getManifest(ExtensionValidator.java:321)
    at org.apache.catalina.util.ExtensionValidator.addSystemResource(ExtensionValidator.java:174)
    at org.apache.catalina.util.ExtensionValidator.<clinit>(ExtensionValidator.java:90)
    at org.apache.catalina.core.StandardContext.startInternal(StandardContext.java:4949)
    at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
    at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1384)
    at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1374)
    at java.util.concurrent.FutureTask.run(FutureTask.java:266)
    at org.apache.tomcat.util.threads.InlineExecutorService.execute(InlineExecutorService.java:75)
    at java.util.concurrent.AbstractExecutorService.submit(AbstractExecutorService.java:134)
    at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:909)
    at org.apache.catalina.core.StandardHost.startInternal(StandardHost.java:843)
    at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
    at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1384)
    at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1374)
    at java.util.concurrent.FutureTask.run(FutureTask.java:266)
    at org.apache.tomcat.util.threads.InlineExecutorService.execute(InlineExecutorService.java:75)
    at java.util.concurrent.AbstractExecutorService.submit(AbstractExecutorService.java:134)
    at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:909)
    at org.apache.catalina.core.StandardEngine.startInternal(StandardEngine.java:262)
    at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
    at org.apache.catalina.core.StandardService.startInternal(StandardService.java:421)
    at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
    at org.apache.catalina.core.StandardServer.startInternal(StandardServer.java:930)
    at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183)
    at org.apache.catalina.startup.Tomcat.start(Tomcat.java:486)
    at org.springframework.boot.web.embedded.tomcat.TomcatWebServer.initialize(TomcatWebServer.java:123)
    at org.springframework.boot.web.embedded.tomcat.TomcatWebServer.<init>(TomcatWebServer.java:104)
    at org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory.getTomcatWebServer(TomcatServletWebServerFactory.java:437)
    at org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory.getWebServer(TomcatServletWebServerFactory.java:191)
    at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.createWebServer(ServletWebServerApplicationContext.java:178)
    at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.onRefresh(ServletWebServerApplicationContext.java:158)
    at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:545)
    at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:143)
    at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:758)
    at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:750)
    at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:397)
    at org.springframework.boot.SpringApplication.run(SpringApplication.java:315)
    at org.springframework.boot.builder.SpringApplicationBuilder.run(SpringApplicationBuilder.java:140)
    
```


#### 原理



