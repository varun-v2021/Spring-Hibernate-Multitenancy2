package com.hibernate.multitenancy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

@Configuration
public class MultitenantConfiguration {

	@Autowired
	private DataSourceProperties properties;

	
	/**
	 * Defines the data source for the application
	 * 
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "com.hibernate.multitenancy")
	public DataSource dataSource() {
		File[] files = Paths.get("tenants").toFile().listFiles();
		Map<Object, Object> resolvedDataSources = new HashMap<>();

		//for (File propertyFile : files) { //
			Properties tenantProperties = new Properties();
			DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader());

			try {
				//tenantProperties.load(new FileInputStream(propertyFile)); //

				String tenantId = tenantProperties.getProperty("name");

				tenantId= "tenant-2"; //
				
				/*dataSourceBuilder.driverClassName(properties.getDriverClassName())
						.url(tenantProperties.getProperty("datasource.url"))
						.username(tenantProperties.getProperty("datasource.username"))
						.password(tenantProperties.getProperty("datasource.password"));*/
				
				dataSourceBuilder.driverClassName("com.mysql.jdbc.Driver")
				.url(tenantProperties.getProperty("jdbc:mysql://localhost:3306/default_schema"))
				.username(tenantProperties.getProperty("root"))
				.password(tenantProperties.getProperty("root"));

				if (properties.getType() != null) {
					dataSourceBuilder.type(properties.getType());
				}

				resolvedDataSources.put(tenantId, dataSourceBuilder.build());
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		//} //

		// Create the final multi-tenant source.
		// It needs a default database to connect to.
		// Make sure that the default database is actually an empty tenant database.
		// Don't use that for a regular tenant if you want things to be safe!
		MultitenantDataSource dataSource = new MultitenantDataSource();
		dataSource.setDefaultTargetDataSource(defaultDataSource());
		dataSource.setTargetDataSources(resolvedDataSources);

		// Call this to finalize the initialization of the data source.
		dataSource.afterPropertiesSet();

		return dataSource;
	}

	/**
	 * Creates the default data source for the application
	 * 
	 * @return
	 */
	private DataSource defaultDataSource() {
		DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader())
				.driverClassName(properties.getDriverClassName()).url(properties.getUrl())
				.username(properties.getUsername()).password(properties.getPassword());

		if (properties.getType() != null) {
			dataSourceBuilder.type(properties.getType());
		}

		return dataSourceBuilder.build();
	}

}
