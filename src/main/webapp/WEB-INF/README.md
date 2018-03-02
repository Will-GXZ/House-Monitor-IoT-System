## `/WEB-INF` directory

Contents in this folder is designed to be hidden from the public, which means 
that the user cannot access this folder by using a simple static URL. In this 
application, all jsp files are stored is this folder. But this folder is visible 
to the web-application. So we can map the content of this folder with different 
url pattern using the following java configuration:
```java
@Bean
public ViewResolver internalResourceViewResolver() {
    InternalResourceViewResolver bean = new InternalResourceViewResolver();
    bean.setViewClass(JstlView.class);
    bean.setPrefix("/WEB-INF/jsp/");
    bean.setSuffix(".jsp");
    return bean;
}
```
This `InternalResourceViewResolver` map's all view name returned by the handler 
to a jsp file with the same name in `/WEB-INF/jsp/` .
