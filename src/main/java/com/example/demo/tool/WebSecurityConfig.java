package com.example.demo.tool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.example.demo.service.DemoService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Bean
	DemoService demoService(){
		return new DemoService();
	} 

}
