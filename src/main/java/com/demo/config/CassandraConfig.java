package com.demo.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraCqlClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.demo.constants.Constant;

/***
 * Cassandra Configuration class to connect server to Database.
 * 
 * @author Viral
 *
 */
@Configuration
@EnableCassandraRepositories(basePackages = { Constant.BASE_PACKAGE })
public class CassandraConfig extends AbstractCassandraConfiguration {

	/***
	 * Methods include
	 * (mappingContext,converter,session,cassandraTemplate,cluster) are used to
	 * create Cassandra Session.
	 * 
	 */
	@Bean
	public CassandraMappingContext mappingContext() {
		return new CassandraMappingContext();
	}

	@Bean
	public CassandraConverter converter() {
		return new MappingCassandraConverter(mappingContext());
	}

	@Bean("keyspaceBSession")
	public CassandraSessionFactoryBean session() {
		CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
		session.setCluster(cluster().getObject());
		session.setKeyspaceName(Constant.KEYSPACE);
		session.setConverter(converter());
		session.setSchemaAction(SchemaAction.NONE);
		return session;
	}

	@Bean
	public CassandraAdminOperations cassandraTemplate(@Qualifier("keyspaceBSession") final CassandraSessionFactoryBean session) throws Exception {
		return new CassandraAdminTemplate(session().getObject(), converter());
	}

	@Bean
	@Override
	public CassandraCqlClusterFactoryBean cluster() {
		CassandraCqlClusterFactoryBean bean = new CassandraCqlClusterFactoryBean();
		bean.setKeyspaceCreations(getKeyspaceCreations());
		bean.setContactPoints(Constant.NODES);
		bean.setUsername(Constant.USERNAME);
		bean.setPassword(Constant.PASSWORD);
		return bean;
	}

	/***
	 * Cassandra Initial configuration. if schema not present then create schema
	 * otherwise no change
	 */

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}

	@Override
	protected String getKeyspaceName() {
		return Constant.KEYSPACE;
	}

	/***
	 * Cassandra Initial configuration. if tables in particular schema not
	 * present then create tables otherwise no change
	 */
	@Override
	public String[] getEntityBasePackages() {
		return new String[] { "com.demo.valueobject" };
	}

	@Override
	protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
		return Collections.singletonList(CreateKeyspaceSpecification.createKeyspace(Constant.KEYSPACE).ifNotExists()
				.with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication());
	}

	@Override
	protected List getStartupScripts() {
		return Collections.singletonList("CREATE KEYSPACE IF NOT EXISTS " + Constant.KEYSPACE + " WITH replication = {"
				+ " 'class': 'SimpleStrategy', " + " 'replication_factor': '3' " + "};");
	}

	/***
	 * This method is optional as it will drop schema once server down.
	 */

	@Override
	protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
		return Arrays.asList(DropKeyspaceSpecification.dropKeyspace(Constant.KEYSPACE));
	}

}
