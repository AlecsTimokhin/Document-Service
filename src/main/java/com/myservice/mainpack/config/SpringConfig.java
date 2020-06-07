package com.myservice.mainpack.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myservice.mainpack.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import static com.myservice.mainpack.util.JacksonObjectMapper.getMapper;


@Configuration
public class SpringConfig implements WebMvcConfigurer {

    @Value("${upload.path_Src}")
    private String uploadPathSrc;

    @Value("${upload.path_Load}")
    private String uploadPathLoad;

    public SecurityInterceptor securityInterceptor;
    public SecurityInterceptor getSecurityInterceptor() { return securityInterceptor; }
    @Autowired
    public void setSecurityInterceptor(SecurityInterceptor securityInterceptor) { this.securityInterceptor = securityInterceptor; }


    // for start copy files from start DIR to init DIR
    @EventListener(ContextRefreshedEvent.class)
    public void onStart() throws IOException {

        for (File file: Objects.requireNonNull(new File(uploadPathLoad).listFiles())) {
            if (file.isFile()) {
                file.delete();
            }
        }

        String  sourceDirName = uploadPathLoad + "/documents_for_start";
        File folder = new File(sourceDirName);
        File[] listOfFiles = folder.listFiles();
        Path destDir = Paths.get(uploadPathLoad);

        if (listOfFiles != null){
            for (File file : listOfFiles){
                Files.copy(file.toPath(), destDir.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
            }
        }

    }


    @PreDestroy
    public void onDestroy(){
        // Delete all documents from init DIR
        for (File file: Objects.requireNonNull(new File(uploadPathLoad).listFiles())) {
            if (file.isFile()) {
                file.delete();
            }
        }
    }


    // For Thymeleaf
    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:templates/");
        templateResolver.setSuffix(".html");
        //templateResolver.setTemplateMode("HTML5");
        return templateResolver;
    }


    @Bean
    public ObjectMapper getJacksonObjectMapper() {
        return getMapper();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**");
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/login").setViewName("users/login");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //#To load fileName from disk
        registry.addResourceHandler("/document/**")
                .addResourceLocations("file://" + uploadPathSrc + "/");

        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

    }


}
