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
package net.comze.framework.orm.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.sql.DataSource;

import net.comze.framework.orm.util.ArrayUtils;
import net.comze.framework.orm.util.DataAccessException;
import net.comze.framework.orm.util.ObjectUtils;

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 3.1.0
 * @version DaoSupport.java 3.2.0 Aug 15, 2012 5:27:19 PM
 */
public abstract class DaoSupport implements DaoContext {

	private DataSource dataSource;

	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public DataSource getDataSource() {
		return dataSource;
	}

	public static Connection getConnection(DataSource dataSource) throws DataAccessException {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public static void fillStatement(PreparedStatement stamtement, Object[] params) throws DataAccessException {
		if (ArrayUtils.isEmpty(params)) {
			return;
		}
		for (int i = 0; i < params.length; i++) {
			try {
				if (ObjectUtils.isNotNull(params[i])) {
					stamtement.setObject(i + 1, params[i]);
				} else {
					stamtement.setNull(i + 1, Types.VARCHAR);
				}
			} catch (SQLException e) {
				throw new DataAccessException(e);
			}
		}
	}

	public static void close(ResultSet resultSet) throws DataAccessException {
		if (ObjectUtils.isNotNull(resultSet)) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				throw new DataAccessException(e);
			}
		}
	}

	public static void close(Statement stamtement) throws DataAccessException {
		if (ObjectUtils.isNotNull(stamtement)) {
			try {
				stamtement.close();
			} catch (SQLException e) {
				throw new DataAccessException(e);
			}
		}
	}

	public static void close(Connection connection) throws DataAccessException {
		if (ObjectUtils.isNotNull(connection)) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new DataAccessException(e);
			}
		}
	}

}
