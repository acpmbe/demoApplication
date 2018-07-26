package com.demo.demoApplication;

import java.io.IOException;
import java.util.Properties;

import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.demo.constants.Constant;

/***
 * This class is Used to load Embedded Cassandra server for unit test. Use
 * applicaton-test.property
 * 
 * if Cassandra server running not found at port 9152 then will try to connect
 * at default port 9042
 * 
 * @author Viral
 *
 */
@ContextConfiguration(classes = CassandraTestConfig.class)
@Import(CassandraTestConfig.class)
public class CassandraEmbeddedServerTestConfig {

	public static final Logger logger = LoggerFactory.getLogger(CassandraEmbeddedServerTestConfig.class);

	@Value("${spring.data.cassandra.hosts}")
	String cassandraHosts;
	@Value("${spring.data.cassandra.port:9145}")
	String cassandraPort;
	@Value("${spring.data.cassandra.keyspace-name}")
	String cassandraKeySpace;

	/***
	 * Before Test case start this method will be called to start Cassandra
	 * Embedded Server
	 */

	@BeforeClass
	public static void initCassandra() {
		try {
			Properties prop = new Properties();
			prop.load(CassandraEmbeddedServerTestConfig.class.getClassLoader().getResourceAsStream("application-test.properties"));
			String cassandraHosts = prop.getProperty("spring.data.cassandra.hosts");
			String cassandraPort = prop.getProperty("spring.data.cassandra.port");

			EmbeddedCassandraServerHelper.startEmbeddedCassandra("test-cassandra.yaml", 200000);
			logger.info("Connect to embedded db");
			Cluster cluster = Cluster.builder().addContactPoints(cassandraHosts).withPort(Integer.parseInt(cassandraPort)).build();
			Session session = cluster.connect();
			logger.info("Initialize keyspace");
			// session.execute("create keyspace " + Constant.KEYSPACE +
			// " WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};");
			// session.execute("use " + Constant.KEYSPACE + ";");
			session.execute("create keyspace " + Constant.KEYSPACE + " WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};");
			session.execute("use " + Constant.KEYSPACE + ";");

		} catch (TTransportException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	/***
	 * After Test case complete this method will be called to stop Cassandra
	 * Embedded Server
	 */
	@AfterClass
	public static void cleanCassandra() {
		EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
	}

}
