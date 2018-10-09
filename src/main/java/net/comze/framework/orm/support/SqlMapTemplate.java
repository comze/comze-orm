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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.comze.framework.orm.bind.ArrayWrapper;
import net.comze.framework.orm.bind.AsciiStreamWrapper;
import net.comze.framework.orm.bind.BeanWrapper;
import net.comze.framework.orm.bind.BigDecimalWrapper;
import net.comze.framework.orm.bind.BinaryStreamWrapper;
import net.comze.framework.orm.bind.BlobWrapper;
import net.comze.framework.orm.bind.BooleanWrapper;
import net.comze.framework.orm.bind.ByteWrapper;
import net.comze.framework.orm.bind.BytesWrapper;
import net.comze.framework.orm.bind.CharacterStreamWrapper;
import net.comze.framework.orm.bind.ClobWrapper;
import net.comze.framework.orm.bind.ColumnWrapper;
import net.comze.framework.orm.bind.DateWrapper;
import net.comze.framework.orm.bind.DoubleWrapper;
import net.comze.framework.orm.bind.EnumWrapper;
import net.comze.framework.orm.bind.FloatWrapper;
import net.comze.framework.orm.bind.IntegerWrapper;
import net.comze.framework.orm.bind.ListWrapper;
import net.comze.framework.orm.bind.LongWrapper;
import net.comze.framework.orm.bind.MapWrapper;
import net.comze.framework.orm.bind.ResultSetWrapper;
import net.comze.framework.orm.bind.RowWrapper;
import net.comze.framework.orm.bind.ShortWrapper;
import net.comze.framework.orm.bind.StringWrapper;
import net.comze.framework.orm.bind.TimeWrapper;
import net.comze.framework.orm.bind.TimestampWrapper;
import net.comze.framework.orm.util.DataAccessException;
import net.comze.framework.orm.util.RowWrapperFactory;

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 3.2.0
 * @version SqlMapTemplate.java 3.2.2 Sep 23, 2012 4:28:08 PM
 */
public class SqlMapTemplate extends DaoSupport implements Template {

	protected <T> T execute(Connection connection, String sql, PreparedStatementHandler<T> preparedStatementHandler, Object... params) throws DataAccessException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			fillStatement(statement, params);
			return preparedStatementHandler.doAfterPrepared(statement);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			close(statement);
		}
	}

	protected <T> T execute(Connection connection, String sql, final ResultSetHandler<T> resultSetHandler, Object... params) throws DataAccessException {
		return execute(connection, sql, new PreparedStatementHandler<T>() {

			@Override
			public T doAfterPrepared(PreparedStatement statement) throws DataAccessException {
				ResultSet resultSet = null;
				try {
					resultSet = statement.executeQuery();
					return resultSetHandler.doInResultSet(resultSet);
				} catch (SQLException e) {
					throw new DataAccessException(e);
				} finally {
					close(resultSet);
				}
			}

		}, params);
	}

	@Override
	public <T> T queryForObject(Connection connection, String sql, final ResultSetWrapper<T> resultSetWrapper, Object... params) throws DataAccessException {
		return execute(connection, sql, new ResultSetHandler<T>() {

			@Override
			public T doInResultSet(ResultSet resultSet) throws DataAccessException {
				try {
					return resultSetWrapper.handle(resultSet);
				} catch (SQLException e) {
					throw new DataAccessException(e);
				}
			}

		}, params);
	}

	@Override
	public <T> T queryForObject(String sql, ResultSetWrapper<T> resultSetWrapper, Object... params) throws DataAccessException {
		Connection connection = null;
		try {
			connection = getConnection(getDataSource());
			return queryForObject(connection, sql, resultSetWrapper, params);
		} finally {
			close(connection);
		}
	}

	@Override
	public <T> T queryForObject(Connection connection, String sql, final RowWrapper<T> rowWrapper, Object... params) throws DataAccessException {
		return execute(connection, sql, new ResultSetHandler<T>() {

			@Override
			public T doInResultSet(ResultSet resultSet) throws DataAccessException {
				try {
					return resultSet.next() ? rowWrapper.handle(resultSet) : (T) null;
				} catch (SQLException e) {
					throw new DataAccessException(e);
				}
			}

		}, params);
	}

	@Override
	public <T> T queryForObject(String sql, final RowWrapper<T> rowWrapper, Object... params) throws DataAccessException {
		Connection connection = null;
		try {
			connection = getConnection(getDataSource());
			return queryForObject(connection, sql, rowWrapper, params);
		} finally {
			close(connection);
		}
	}

	@Override
	public <T> T queryForObject(Connection connection, String sql, final ColumnWrapper<T> columnWrapper, Object... params) throws DataAccessException {
		return execute(connection, sql, new ResultSetHandler<T>() {

			@Override
			public T doInResultSet(ResultSet resultSet) throws DataAccessException {
				try {
					if (resultSet.next()) {
						int columnCount = resultSet.getMetaData().getColumnCount();
						if (columnCount != 1) {
							throw new DataAccessException("Incorrect column: column count is " + columnCount + ", where column count expected 1, in statement " + resultSet.getStatement().toString());
						}
						return columnWrapper.handle(resultSet, 1);
					}
					return (T) null;
				} catch (SQLException e) {
					throw new DataAccessException(e);
				}
			}

		}, params);
	}

	@Override
	public <T> T queryForObject(String sql, final ColumnWrapper<T> columnWrapper, Object... params) throws DataAccessException {
		Connection connection = null;
		try {
			connection = getConnection(getDataSource());
			return queryForObject(connection, sql, columnWrapper, params);
		} finally {
			close(connection);
		}
	}

	@Override
	public <T> List<T> queryForList(Connection connection, String sql, final RowWrapper<T> rowWrapper, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new ResultSetWrapper<List<T>>() {

			@Override
			public List<T> handle(ResultSet resultSet) throws SQLException {
				return new ListWrapper<T>(rowWrapper).handle(resultSet);
			}

		}, params);
	}

	@Override
	public <T> List<T> queryForList(String sql, RowWrapper<T> rowWrapper, Object... params) throws DataAccessException {
		Connection connection = null;
		try {
			connection = getConnection(getDataSource());
			return queryForList(connection, sql, rowWrapper, params);
		} finally {
			close(connection);
		}
	}

	@Override
	public <T> List<T> queryForList(Connection connection, String sql, Class<T> elementType, Object... params) throws DataAccessException {
		return queryForList(connection, sql, RowWrapperFactory.wrapper(elementType), params);
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType, Object... params) throws DataAccessException {
		return queryForList(sql, RowWrapperFactory.wrapper(elementType), params);
	}

	@Override
	public <T> T queryForObject(Connection connection, String sql, Class<T> requiredType, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, RowWrapperFactory.wrapper(requiredType), params);
	}

	@Override
	public <T> T queryForObject(String sql, Class<T> requiredType, Object... params) throws DataAccessException {
		return queryForObject(sql, RowWrapperFactory.wrapper(requiredType), params);
	}

	@Override
	public List<Map<String, Object>> queryForMapList(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForList(connection, sql, new MapWrapper(), params);
	}

	@Override
	public List<Map<String, Object>> queryForMapList(String sql, Object... params) throws DataAccessException {
		return queryForList(sql, new MapWrapper(), params);
	}

	@Override
	public Map<String, Object> queryForMap(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new MapWrapper(), params);
	}

	@Override
	public Map<String, Object> queryForMap(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new MapWrapper(), params);
	}

	@Override
	public List<Object[]> queryForArrayList(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForList(connection, sql, new ArrayWrapper(), params);
	}

	@Override
	public List<Object[]> queryForArrayList(String sql, Object... params) throws DataAccessException {
		return queryForList(sql, new ArrayWrapper(), params);
	}

	@Override
	public Object[] queryForArray(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new ArrayWrapper(), params);
	}

	@Override
	public Object[] queryForArray(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new ArrayWrapper(), params);
	}

	@Override
	public <T> List<T> queryForBeanList(Connection connection, String sql, Class<T> requiredType, Object... params) throws DataAccessException {
		return queryForList(connection, sql, new BeanWrapper<T>(requiredType), params);
	}

	@Override
	public <T> List<T> queryForBeanList(String sql, Class<T> requiredType, Object... params) throws DataAccessException {
		return queryForList(sql, new BeanWrapper<T>(requiredType), params);
	}

	@Override
	public <T> T queryForBean(Connection connection, String sql, Class<T> requiredType, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new BeanWrapper<T>(requiredType), params);
	}

	@Override
	public <T> T queryForBean(String sql, Class<T> requiredType, Object... params) throws DataAccessException {
		return queryForObject(sql, new BeanWrapper<T>(requiredType), params);
	}

	@Override
	public String queryForString(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new StringWrapper(), params);
	}

	@Override
	public String queryForString(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new StringWrapper(), params);
	}

	@Override
	public Boolean queryForBoolean(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new BooleanWrapper(), params);
	}

	@Override
	public Boolean queryForBoolean(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new BooleanWrapper(), params);
	}

	@Override
	public Byte queryForByte(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new ByteWrapper(), params);
	}

	@Override
	public Byte queryForByte(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new ByteWrapper(), params);
	}

	@Override
	public Short queryForShort(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new ShortWrapper(), params);
	}

	@Override
	public Short queryForShort(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new ShortWrapper(), params);
	}

	@Override
	public Integer queryForInteger(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new IntegerWrapper(), params);
	}

	@Override
	public Integer queryForInteger(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new IntegerWrapper(), params);
	}

	@Override
	public Long queryForLong(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new LongWrapper(), params);
	}

	@Override
	public Long queryForLong(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new LongWrapper(), params);
	}

	@Override
	public Float queryForFloat(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new FloatWrapper(), params);
	}

	@Override
	public Float queryForFloat(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new FloatWrapper(), params);
	}

	@Override
	public Double queryForDouble(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new DoubleWrapper(), params);
	}

	@Override
	public Double queryForDouble(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new DoubleWrapper(), params);
	}

	@Override
	public BigDecimal queryForBigDecimal(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new BigDecimalWrapper(), params);
	}

	@Override
	public BigDecimal queryForBigDecimal(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new BigDecimalWrapper(), params);
	}

	@Override
	public byte[] queryForBytes(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new BytesWrapper(), params);
	}

	@Override
	public byte[] queryForBytes(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new BytesWrapper(), params);
	}

	@Override
	public Date queryForDate(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new DateWrapper(), params);
	}

	@Override
	public Date queryForDate(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new DateWrapper(), params);
	}

	@Override
	public Time queryForTime(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new TimeWrapper(), params);
	}

	@Override
	public Time queryForTime(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new TimeWrapper(), params);
	}

	@Override
	public Timestamp queryForTimestamp(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new TimestampWrapper(), params);
	}

	@Override
	public Timestamp queryForTimestamp(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new TimestampWrapper(), params);
	}

	@Override
	public InputStream queryForAsciiStream(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new AsciiStreamWrapper(), params);
	}

	@Override
	public InputStream queryForAsciiStream(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new AsciiStreamWrapper(), params);
	}

	@Override
	public InputStream queryForBinaryStream(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new BinaryStreamWrapper(), params);
	}

	@Override
	public InputStream queryForBinaryStream(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new BinaryStreamWrapper(), params);
	}

	@Override
	public Blob queryForBlob(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new BlobWrapper(), params);
	}

	@Override
	public Blob queryForBlob(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new BlobWrapper(), params);
	}

	@Override
	public Clob queryForClob(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new ClobWrapper(), params);
	}

	@Override
	public Clob queryForClob(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new ClobWrapper(), params);
	}

	@Override
	public Reader queryForCharacterStream(Connection connection, String sql, Object... params) throws DataAccessException {
		return queryForObject(connection, sql, new CharacterStreamWrapper(), params);
	}

	@Override
	public Reader queryForCharacterStream(String sql, Object... params) throws DataAccessException {
		return queryForObject(sql, new CharacterStreamWrapper(), params);
	}

	@Override
	public <T extends Enum<T>> T queryForEnum(Connection connection, String sql, Class<T> requiredType, Object... params) {
		return queryForObject(connection, sql, new EnumWrapper<T>(requiredType), params);
	}

	@Override
	public <T extends Enum<T>> T queryForEnum(String sql, Class<T> requiredType, Object... params) {
		return queryForObject(sql, new EnumWrapper<T>(requiredType), params);
	}

	@Override
	public int execute(Connection connection, String sql, Object... params) throws DataAccessException {
		return execute(connection, sql, new PreparedStatementHandler<Integer>() {

			@Override
			public Integer doAfterPrepared(PreparedStatement statement) throws DataAccessException {
				int affectRows = 0;
				try {
					affectRows = statement.executeUpdate();
				} catch (SQLException e) {
					throw new DataAccessException(e);
				} finally {
					close(statement);
				}
				return affectRows;
			}

		}, params);
	}

	@Override
	public int execute(String sql, Object... params) throws DataAccessException {
		Connection connection = null;
		try {
			connection = getConnection(getDataSource());
			return execute(connection, sql, params);
		} finally {
			close(connection);
		}
	}

}
