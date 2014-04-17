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

import java.util.Properties;

import javax.sql.DataSource;

import net.comze.framework.orm.util.ObjectUtils;
import net.comze.framework.orm.util.ResourceLoader;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 1.0.0
 * @version DbcpDataSourceFactory.java 3.2.6 Apr 16, 2014 5:15:32 PM
 */
public class DbcpDataSourceFactory implements DataSourceFactory {

	private static final String DEFAULE_PROPERTIES_FILE = "jdbc.properties";

	private static final String DBCP_INITIALSIZE = "dbcp.initialSize";

	private static final String DBCP_MAXACTIVE = "dbcp.maxActive";

	private static final String DBCP_MAXIDLE = "dbcp.maxIdle";

	private static final String DBCP_MINIDLE = "dbcp.minIdle";

	private static final String DBCP_MAXWAIT = "dbcp.maxWait";

	private static final String DBCP_REMOVEABANDONED = "dbcp.removeAbandoned";

	private static final String DBCP_REMOVEABANDONEDTIMEOUT = "dbcp.removeAbandonedTimeout";

	private static final String DBCP_VALIDATIONQUERY = "dbcp.validationQuery";

	private static final String DBCP_VALIDATIONQUERYTIMEOUT = "dbcp.validationQueryTimeout";

	private static final String DBCP_TESTONCREATE = "dbcp.testOnCreate";

	private static final String DBCP_TESTONBORROW = "dbcp.testOnBorrow";

	private static final String DBCP_TESTONRETURN = "dbcp.testOnReturn";

	private static final String DBCP_TESTWHILEIDLE = "dbcp.testWhileIdle";

	private static final String DBCP_TIMEBETWEENEVICTIONRUNSMILLIS = "dbcp.timeBetweenEvictionRunsMillis";

	private static final String DBCP_MINEVICTABLEIDLETIMEMILLIS = "dbcp.minEvictableIdleTimeMillis";

	private static final String DBCP_NUMTESTSPEREVICTIONRUN = "dbcp.numTestsPerEvictionRun";

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
		ObjectUtils.notNull(properties, "properties");
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(properties.getProperty(JDBC_DRIVER));
		basicDataSource.setUrl(properties.getProperty(JDBC_URL));
		basicDataSource.setUsername(properties.getProperty(JDBC_USERNAME));
		basicDataSource.setPassword(properties.getProperty(JDBC_PASSWORD));

		if (properties.containsKey(DBCP_INITIALSIZE)) {
			basicDataSource.setInitialSize(Integer.parseInt(properties.getProperty(DBCP_INITIALSIZE)));
		}
		if (properties.containsKey(DBCP_MAXACTIVE)) {
			basicDataSource.setMaxActive(Integer.parseInt(properties.getProperty(DBCP_MAXACTIVE)));
		}
		if (properties.containsKey(DBCP_MAXIDLE)) {
			basicDataSource.setMaxIdle(Integer.parseInt(properties.getProperty(DBCP_MAXIDLE)));
		}
		if (properties.containsKey(DBCP_MAXWAIT)) {
			basicDataSource.setMaxWait(Long.parseLong(properties.getProperty(DBCP_MAXWAIT)));
		}
		if (properties.containsKey(DBCP_MINEVICTABLEIDLETIMEMILLIS)) {
			basicDataSource.setMinEvictableIdleTimeMillis(Long.parseLong(properties.getProperty(DBCP_MINEVICTABLEIDLETIMEMILLIS)));
		}
		if (properties.containsKey(DBCP_MINIDLE)) {
			basicDataSource.setMinIdle(Integer.parseInt(properties.getProperty(DBCP_MINIDLE)));
		}
		if (properties.containsKey(DBCP_NUMTESTSPEREVICTIONRUN)) {
			basicDataSource.setNumTestsPerEvictionRun(Integer.parseInt(properties.getProperty(DBCP_NUMTESTSPEREVICTIONRUN)));
		}
		if (properties.containsKey(DBCP_REMOVEABANDONED)) {
			basicDataSource.setRemoveAbandoned(Boolean.parseBoolean(properties.getProperty(DBCP_REMOVEABANDONED)));
		}
		if (properties.containsKey(DBCP_REMOVEABANDONEDTIMEOUT)) {
			basicDataSource.setRemoveAbandonedTimeout(Integer.parseInt(properties.getProperty(DBCP_REMOVEABANDONEDTIMEOUT)));
		}
		if (properties.containsKey(DBCP_TESTONBORROW)) {
			basicDataSource.setTestOnBorrow(Boolean.parseBoolean(properties.getProperty(DBCP_TESTONBORROW)));
		}
		if (properties.containsKey(DBCP_TESTONCREATE)) {
			basicDataSource.setTestOnReturn(Boolean.parseBoolean(properties.getProperty(DBCP_TESTONCREATE)));
		}
		if (properties.containsKey(DBCP_TESTONRETURN)) {
			basicDataSource.setTestOnReturn(Boolean.parseBoolean(properties.getProperty(DBCP_TESTONRETURN)));
		}
		if (properties.containsKey(DBCP_TESTWHILEIDLE)) {
			basicDataSource.setTestWhileIdle(Boolean.parseBoolean(properties.getProperty(DBCP_TESTWHILEIDLE)));
		}
		if (properties.containsKey(DBCP_TIMEBETWEENEVICTIONRUNSMILLIS)) {
			basicDataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(properties.getProperty(DBCP_TIMEBETWEENEVICTIONRUNSMILLIS)));
		}
		if (properties.containsKey(DBCP_VALIDATIONQUERY)) {
			basicDataSource.setValidationQuery(properties.getProperty(DBCP_VALIDATIONQUERY));
		}
		if (properties.containsKey(DBCP_VALIDATIONQUERYTIMEOUT)) {
			basicDataSource.setValidationQueryTimeout(Integer.parseInt(properties.getProperty(DBCP_VALIDATIONQUERYTIMEOUT)));
		}
		return basicDataSource;
	}

}
