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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import net.comze.framework.orm.util.ObjectUtils;

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 1.0.0
 * @version JndiDataSourceFactory.java 3.2.0 Aug 15, 2012 5:13:16 PM
 */
public class JndiDataSourceFactory implements DataSourceFactory {

	protected static final String RESOURCE_PREFIX = "java:comp/env/";

	private DataSource dataSource;

	public JndiDataSourceFactory(String jndiName) {
		try {
			dataSource = (DataSource) lookup(jndiName);
		} catch (Exception e) {
			throw new RuntimeException("Initializing " + JndiDataSourceFactory.class.getName() + " fail: " + e.getMessage(), e);
		}
	}

	protected Object lookup(String jndiName) throws NamingException {
		ObjectUtils.notNull(jndiName, "jndiName");
		String dataSourceName = convertJndiName(jndiName);
		Context context = new InitialContext();
		return context.lookup(dataSourceName);
	}

	protected String convertJndiName(String jndiName) {
		if (!jndiName.startsWith(RESOURCE_PREFIX) && jndiName.indexOf(':') == -1) {
			jndiName = RESOURCE_PREFIX + jndiName;
		}
		return jndiName;
	}

	@Override
	public DataSource getDataSource() {
		return this.dataSource;
	}

}
