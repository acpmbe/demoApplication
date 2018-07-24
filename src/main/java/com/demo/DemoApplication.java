package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.demo.constants.Constant;
import com.demo.service.DataService;

/***
 * Main SpringBoot Application Class
 * 
 * @author Viral
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(Constant.BASE_PACKAGE)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
