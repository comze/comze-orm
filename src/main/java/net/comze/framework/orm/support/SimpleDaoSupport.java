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
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
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
import net.comze.framework.orm.bind.DateWrapper;
import net.comze.framework.orm.bind.DoubleWrapper;
import net.comze.framework.orm.bind.FloatWrapper;
import net.comze.framework.orm.bind.IntegerWrapper;
import net.comze.framework.orm.bind.ListWrapper;
import net.comze.framework.orm.bind.LongWrapper;
import net.comze.framework.orm.bind.MapWrapper;
import net.comze.framework.orm.bind.RowWrapper;
import net.comze.framework.orm.bind.ShortWrapper;
import net.comze.framework.orm.bind.StringWrapper;
import net.comze.framework.orm.bind.TimeWrapper;
import net.comze.framework.orm.bind.TimestampWrapper;
import net.comze.framework.orm.util.ArrayUtils;
import net.comze.framework.orm.util.DataAccessException;
import net.comze.framework.orm.util.ObjectUtils;
import net.comze.framework.orm.util.RowWrapperFactory;

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 3.0.0
 * @version SimpleDaoSupport.java 3.1.0 Mar 22, 2012 5:06:02 PM
 */
public class SimpleDaoSupport extends DaoSupport implements SqlMapTemplate {

	@Override
	public <T> List<T> queryForList(Connection connection, String sql, RowWrapper<T> rowWrapper, Object... params) throws DataAccessException {
		PreparedStatement stamtement = null;
		ResultSet resultSet = null;
		List<T> result = null;
		try {
			stamtement = connection.prepareStatement(sql);
			fillStatement(stamtement, params);
			resultSet = stamtement.executeQuery();
			result = new ListWrapper<T>(rowWrapper).handle(resultSet);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			try {
				close(resultSet);
			} finally {
				close(stamtement);
			}
		}
		return result;
	}

	@Override
	public <T> List<T> queryForList(String sql, RowWrapper<T> rowWrapper, Object... params) throws DataAccessException {
		Connection connection = null;
		try {
			connection = getDataSource().getConnection();
			return queryForList(connection, sql, rowWrapper, params);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			close(connection);
		}
	}

	@Override
	public <T> List<T> queryForList(String sql, RowWrapper<T> rowWrapper) throws DataAccessException {
		return queryForList(sql, rowWrapper, (Object[]) null);
	}

	@Override
	public <T> T queryForObject(Connection connection, String sql, RowWrapper<T> rowWrapper, Object... params) throws DataAccessException {
		PreparedStatement stamtement = null;
		ResultSet resultSet = null;
		T result = null;
		try {
			stamtement = connection.prepareStatement(sql);
			fillStatement(stamtement, params);
			resultSet = stamtement.executeQuery();
			result = rowWrapper.handle(resultSet);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			try {
				close(resultSet);
			} finally {
				close(stamtement);
			}
		}
		return result;
	}

	@Override
	public <T> T queryForObject(String sql, RowWrapper<T> rowWrapper, Object... params) throws DataAccessException {
		Connection connection = null;
		try {
			connection = getDataSource().getConnection();
			return queryForObject(connection, sql, rowWrapper, params);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			close(connection);
		}
	}

	@Override
	public <T> T queryForObject(String sql, RowWrapper<T> rowWrapper) throws DataAccessException {
		return queryForObject(sql, rowWrapper, (Object[]) null);
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
	public <T> List<T> queryForList(String sql, Class<T> elementType) throws DataAccessException {
		return queryForList(sql, RowWrapperFactory.wrapper(elementType));
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
	public <T> T queryForObject(String sql, Class<T> requiredType) throws DataAccessException {
		return queryForObject(sql, RowWrapperFactory.wrapper(requiredType));
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
	public List<Map<String, Object>> queryForMapList(String sql) throws DataAccessException {
		return queryForList(sql, new MapWrapper());
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
	public Map<String, Object> queryForMap(String sql) throws DataAccessException {
		return queryForObject(sql, new MapWrapper());
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
	public List<Object[]> queryForArrayList(String sql) throws DataAccessException {
		return queryForList(sql, new ArrayWrapper());
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
	public Object[] queryForArray(String sql) throws DataAccessException {
		return queryForObject(sql, new ArrayWrapper());
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
	public <T> List<T> queryForBeanList(String sql, Class<T> requiredType) throws DataAccessException {
		return queryForList(sql, new BeanWrapper<T>(requiredType));
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
	public <T> T queryForBean(String sql, Class<T> requiredType) throws DataAccessException {
		return queryForObject(sql, new BeanWrapper<T>(requiredType));
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
	public String queryForString(String sql) throws DataAccessException {
		return queryForObject(sql, new StringWrapper());
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
	public Boolean queryForBoolean(String sql) throws DataAccessException {
		return queryForObject(sql, new BooleanWrapper());
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
	public Byte queryForByte(String sql) throws DataAccessException {
		return queryForObject(sql, new ByteWrapper());
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
	public Short queryForShort(String sql) throws DataAccessException {
		return queryForObject(sql, new ShortWrapper());
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
	public Integer queryForInteger(String sql) throws DataAccessException {
		return queryForObject(sql, new IntegerWrapper());
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
	public Long queryForLong(String sql) throws DataAccessException {
		return queryForObject(sql, new LongWrapper());
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
	public Float queryForFloat(String sql) throws DataAccessException {
		return queryForObject(sql, new FloatWrapper());
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
	public Double queryForDouble(String sql) throws DataAccessException {
		return queryForObject(sql, new DoubleWrapper());
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
	public BigDecimal queryForBigDecimal(String sql) throws DataAccessException {
		return queryForObject(sql, new BigDecimalWrapper());
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
	public byte[] queryForBytes(String sql) throws DataAccessException {
		return queryForObject(sql, new BytesWrapper());
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
	public Date queryForDate(String sql) throws DataAccessException {
		return queryForObject(sql, new DateWrapper());
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
	public Time queryForTime(String sql) throws DataAccessException {
		return queryForObject(sql, new TimeWrapper());
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
	public Timestamp queryForTimestamp(String sql) throws DataAccessException {
		return queryForObject(sql, new TimestampWrapper());
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
	public InputStream queryForAsciiStream(String sql) throws DataAccessException {
		return queryForObject(sql, new AsciiStreamWrapper());
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
	public InputStream queryForBinaryStream(String sql) throws DataAccessException {
		return queryForObject(sql, new BinaryStreamWrapper());
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
	public Blob queryForBlob(String sql) throws DataAccessException {
		return queryForObject(sql, new BlobWrapper());
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
	public Clob queryForClob(String sql) throws DataAccessException {
		return queryForObject(sql, new ClobWrapper());
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
	public Reader queryForCharacterStream(String sql) throws DataAccessException {
		return queryForObject(sql, new CharacterStreamWrapper());
	}

	@Override
	public int execute(Connection connection, String sql, Object... params) throws DataAccessException {
		PreparedStatement stamtement = null;
		int affectRows = 0;
		try {
			stamtement = connection.prepareStatement(sql);
			fillStatement(stamtement, params);
			affectRows = stamtement.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			close(stamtement);
		}
		return affectRows;
	}

	@Override
	public int execute(String sql, Object... params) throws DataAccessException {
		Connection connection = null;
		try {
			connection = getDataSource().getConnection();
			return execute(connection, sql, params);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			close(connection);
		}
	}

	@Override
	public int execute(String sql) throws DataAccessException {
		return execute(sql, (Object[]) null);
	}

	public SqlMapTemplate getSqlMapTemplate() {
		return this;
	}

	protected void fillStatement(PreparedStatement stamtement, Object[] params) throws SQLException {
		if (ArrayUtils.isEmpty(params)) {
			return ;
		}
		for (int i = 0; i < params.length; i++) {
			if (ObjectUtils.isNotNull(params[i])) {
				stamtement.setObject(i + 1, params[i]);
			} else {
				stamtement.setNull(i + 1, Types.VARCHAR);
			}
		}
	}

	protected void close(ResultSet resultSet) throws DataAccessException {
		if (ObjectUtils.isNotNull(resultSet)) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				throw new DataAccessException(e);
			}
		}
	}

	protected void close(Statement stamtement) throws DataAccessException {
		if (ObjectUtils.isNotNull(stamtement)) {
			try {
				stamtement.close();
			} catch (SQLException e) {
				throw new DataAccessException(e);
			}
		}
	}

	protected void close(Connection connection) throws DataAccessException {
		if (ObjectUtils.isNotNull(connection)) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new DataAccessException(e);
			}
		}
	}

}
