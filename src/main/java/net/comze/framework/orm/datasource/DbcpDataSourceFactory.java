/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.comze.framework.orm.datasource;

import static net.comze.framework.orm.util.JdbcResource.JDBC_DRIVER;
import static net.comze.framework.orm.util.JdbcResource.JDBC_PASSWORD;
import static net.comze.framework.orm.util.JdbcResource.JDBC_URL;
import static net.comze.framework.orm.util.JdbcResource.JDBC_USERNAME;
import static net.comze.framework.orm.util.JdbcResource.POOL_MAX_ACTIVE;
import static net.comze.framework.orm.util.JdbcResource.POOL_MAX_IDLE;
import static net.comze.framework.orm.util.JdbcResource.POOL_MAX_WAIT;
import static net.comze.framework.orm.util.JdbcResource.POOL_VALIDATION_QUERY;

import java.util.Properties;

import javax.sql.DataSource;

import net.comze.framework.orm.util.Assert;
import net.comze.framework.orm.util.ObjectUtils;
import net.comze.framework.orm.util.ResourceLoader;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 1.0.0
 * @version DbcpDataSourceFactory.java 3.1.0 Mar 22, 2012 3:36:12 PM
 */
public class DbcpDataSourceFactory implements DataSourceFactory {

	private static final String DEFAULE_PROPERTIES_FILE = "jdbc.properties";

	private DataSource dataSource;

	public DbcpDataSourceFactory(Properties properties) {
		try {
			this.dataSource = initialize(properties);
		} catch (Exception e) {
			throw new RuntimeException("Initializing " + DbcpDataSourceFactory.class.getName() + " fail: " + e.getMessage(), e);
		}
	}

	public DbcpDataSourceFactory() {
		String resourceName = DEFAULE_PROPERTIES_FILE;
		try {
			Properties properties = ResourceLoader.loadProperties(resourceName);
			this.dataSource = initialize(properties);
		} catch (Exception e) {
			throw new RuntimeException("Initializing " + DbcpDataSourceFactory.class.getName() + " fail: " + e.getMessage(), e);
		}
	}

	@Override
	public DataSource getDataSource() {
		return dataSource;
	}

	private BasicDataSource initialize(Properties properties) {
		Assert.notNull(properties, "properties");
		BasicDataSource basicDataSource = null;
		if (properties.containsKey(JDBC_DRIVER)) {
			basicDataSource = new BasicDataSource();
			String driver = properties.getProperty(JDBC_DRIVER);
			String url = properties.getProperty(JDBC_URL);
			String username = properties.getProperty(JDBC_USERNAME);
			String password = properties.getProperty(JDBC_PASSWORD);
			String validationQuery = properties.getProperty(POOL_VALIDATION_QUERY);
			String maxActive = properties.getProperty(POOL_MAX_ACTIVE);
			String maxIdle = properties.getProperty(POOL_MAX_IDLE);
			String maxWait = properties.getProperty(POOL_MAX_WAIT);
			basicDataSource.setUrl(url);
			basicDataSource.setDriverClassName(driver);
			basicDataSource.setUsername(username);
			basicDataSource.setPassword(password);
			if (ObjectUtils.isNotNull(validationQuery)) {
				basicDataSource.setValidationQuery(validationQuery);
			}
			if (ObjectUtils.isNotNull(maxActive)) {
				basicDataSource.setMaxActive(Integer.parseInt(maxActive));
			}
			if (ObjectUtils.isNotNull(maxIdle)) {
				basicDataSource.setMaxIdle(Integer.parseInt(maxIdle));
			}
			if (ObjectUtils.isNotNull(maxWait)) {
				basicDataSource.setMaxWait(Integer.parseInt(maxWait));
			}
		}
		return basicDataSource;
	}

}
