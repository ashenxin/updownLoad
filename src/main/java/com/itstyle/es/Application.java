package com.itstyle.es;
import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableDubboConfiguration
public class Application extends WebMvcConfigurerAdapter {
	private static final Logger logger = Logger.getLogger(Application.class);
	
	public static void main(String[] args){
		SpringApplication.run(Application.class, args);
		logger.info("启动成功");
	}
}