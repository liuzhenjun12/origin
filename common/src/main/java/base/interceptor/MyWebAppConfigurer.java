package base.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 拦截器
* @ClassName: MyWebAppConfigurer
*
 */
@Configuration
public class MyWebAppConfigurer  extends WebMvcConfigurationSupport {
	@Value("${file.staticAccessPath}")
	private String staticAccessPath;
	@Value("${file.uploadFolder}")
	private String uploadFolder;
	/** 解决跨域问题 **/
	@Override
	public void addCorsMappings(CorsRegistry registry){
		/*
			registry.addMapping("/**")
			// 设置允许跨域请求的域名
			.allowedOrigins("*")
			// 是否允许证书
			.allowCredentials(true)
			// 设置允许的方法
			.allowedMethods("GET", "POST", "DELETE", "PUT")
			// 设置允许的header属性
			.allowedHeaders("*")
			// 跨域允许时间
			.maxAge(3600);
			super.addCorsMappings(registry);
		*/
	}
	
	/** 添加拦截器 **/
	@Override
	protected void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**","/error/**");
		super.addInterceptors(registry);
	}
	
	/** 这里配置视图解析器 **/
	@Override
	protected void configureViewResolvers(ViewResolverRegistry registry){
		super.configureViewResolvers(registry);
	}
	
	/** 配置内容裁决的一些选项 **/
	@Override
	protected void configureContentNegotiation(ContentNegotiationConfigurer configurer){
		super.configureContentNegotiation(configurer);
	}
	
	/** 视图跳转控制器 **/
	@Override
	protected void addViewControllers(ViewControllerRegistry registry) {
		
		super.addViewControllers(registry);
	}
	
	
	/** 静态资源处理 **/
	@Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:"+uploadFolder);
    }
	/** 默认静态资源处理器 **/
	
	protected void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		//super.configureDefaultServletHandling(configurer);
		//configurer.enable("stati");
		configurer.enable();
	}
	
	
	
	
}
