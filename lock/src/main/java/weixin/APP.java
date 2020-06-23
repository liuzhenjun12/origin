package weixin;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.MultipartConfigElement;

@ComponentScan({"base","weixin"})
@EntityScan(basePackages = {"weixin.model"})
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"weixin.mapper"})
public class APP {
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    public static void main(String[] args) {
        SpringApplication.run(APP.class,args);
    }
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(uploadFolder);
        return factory.createMultipartConfig();
    }

}
