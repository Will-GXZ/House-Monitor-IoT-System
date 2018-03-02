## `/resources` directory
This directory contains static files like `css`, `java script`, `html`. 
And it's mapped to `url: /resources/**` by using following java configuration:
```java
@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/resources/**")
          .addResourceLocations("/resources/"); 
    }
    
    ...
}
```
