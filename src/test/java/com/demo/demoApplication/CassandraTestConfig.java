package com.demo.demoApplication;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import com.demo.config.CassandraConfig;

/***
 * This cassandra class is unit test configration class whose scope is test.
 * which inherit property of cassanda config class
 * 
 * @author Viral
 *
 */

@Configuration
@ActiveProfiles("test")
@PropertySource({ "classpath:application.properties", "classpath:application-test.properties" })
public class CassandraTestConfig extends CassandraConfig {

}
