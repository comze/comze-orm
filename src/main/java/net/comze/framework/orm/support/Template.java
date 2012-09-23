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
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.comze.framework.orm.bind.ColumnWrapper;
import net.comze.framework.orm.bind.ResultSetWrapper;
import net.comze.framework.orm.bind.RowWrapper;
import net.comze.framework.orm.util.DataAccessException;

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 3.0.0
 * @version Template.java 3.2.2 Sep 23, 2012 4:27:53 PM
 */
public interface Template {

	public <T> T queryForObject(Connection connection, String sql, ResultSetWrapper<T> resultSetWrapper, Object... params) throws DataAccessException;

	public <T> T queryForObject(String sql, ResultSetWrapper<T> resultSetWrapper, Object... params) throws DataAccessException;

	public <T> T queryForObject(Connection connection, String sql, RowWrapper<T> rowWrapper, Object... params) throws DataAccessException;

	public <T> T queryForObject(String sql, RowWrapper<T> rowWrapper, Object... params) throws DataAccessException;

	public <T> T queryForObject(Connection connection, String sql, ColumnWrapper<T> columnWrapper, Object... params) throws DataAccessException;

	public <T> T queryForObject(String sql, ColumnWrapper<T> columnWrapper, Object... params) throws DataAccessException;

	public <T> List<T> queryForList(Connection connection, String sql, RowWrapper<T> rowWrapper, Object... params) throws DataAccessException;

	public <T> List<T> queryForList(String sql, RowWrapper<T> rowWrapper, Object... params) throws DataAccessException;

	public <T> List<T> queryForList(Connection connection, String sql, Class<T> elementType, Object... params) throws DataAccessException;

	public <T> List<T> queryForList(String sql, Class<T> elementType, Object... params) throws DataAccessException;

	public <T> T queryForObject(Connection connection, String sql, Class<T> requiredType, Object... params) throws DataAccessException;

	public <T> T queryForObject(String sql, Class<T> requiredType, Object... params) throws DataAccessException;

	public List<Map<String, Object>> queryForMapList(Connection connection, String sql, Object... params) throws DataAccessException;

	public List<Map<String, Object>> queryForMapList(String sql, Object... params) throws DataAccessException;

	public Map<String, Object> queryForMap(Connection connection, String sql, Object... params) throws DataAccessException;

	public Map<String, Object> queryForMap(String sql, Object... params) throws DataAccessException;

	public List<Object[]> queryForArrayList(Connection connection, String sql, Object... params) throws DataAccessException;

	public List<Object[]> queryForArrayList(String sql, Object... params) throws DataAccessException;

	public Object[] queryForArray(Connection connection, String sql, Object... params) throws DataAccessException;

	public Object[] queryForArray(String sql, Object... params) throws DataAccessException;

	public <T> List<T> queryForBeanList(Connection connection, String sql, Class<T> requiredType, Object... params) throws DataAccessException;

	public <T> List<T> queryForBeanList(String sql, Class<T> requiredType, Object... params) throws DataAccessException;

	public <T> T queryForBean(Connection connection, String sql, Class<T> requiredType, Object... params) throws DataAccessException;

	public <T> T queryForBean(String sql, Class<T> requiredType, Object... params) throws DataAccessException;

	public String queryForString(Connection connection, String sql, Object... params) throws DataAccessException;

	public String queryForString(String sql, Object... params) throws DataAccessException;

	public Boolean queryForBoolean(Connection connection, String sql, Object... params) throws DataAccessException;

	public Boolean queryForBoolean(String sql, Object... params) throws DataAccessException;

	public Byte queryForByte(Connection connection, String sql, Object... params) throws DataAccessException;

	public Byte queryForByte(String sql, Object... params) throws DataAccessException;

	public Short queryForShort(Connection connection, String sql, Object... params) throws DataAccessException;

	public Short queryForShort(String sql, Object... params) throws DataAccessException;

	public Integer queryForInteger(Connection connection, String sql, Object... params) throws DataAccessException;

	public Integer queryForInteger(String sql, Object... params) throws DataAccessException;

	public Long queryForLong(Connection connection, String sql, Object... params) throws DataAccessException;

	public Long queryForLong(String sql, Object... params) throws DataAccessException;

	public Float queryForFloat(Connection connection, String sql, Object... params) throws DataAccessException;

	public Float queryForFloat(String sql, Object... params) throws DataAccessException;

	public Double queryForDouble(Connection connection, String sql, Object... params) throws DataAccessException;

	public Double queryForDouble(String sql, Object... params) throws DataAccessException;

	public BigDecimal queryForBigDecimal(Connection connection, String sql, Object... params) throws DataAccessException;

	public BigDecimal queryForBigDecimal(String sql, Object... params) throws DataAccessException;

	public byte[] queryForBytes(Connection connection, String sql, Object... params) throws DataAccessException;

	public byte[] queryForBytes(String sql, Object... params) throws DataAccessException;

	public Date queryForDate(Connection connection, String sql, Object... params) throws DataAccessException;

	public Date queryForDate(String sql, Object... params) throws DataAccessException;

	public Time queryForTime(Connection connection, String sql, Object... params) throws DataAccessException;

	public Time queryForTime(String sql, Object... params) throws DataAccessException;

	public Timestamp queryForTimestamp(Connection connection, String sql, Object... params) throws DataAccessException;

	public Timestamp queryForTimestamp(String sql, Object... params) throws DataAccessException;

	public InputStream queryForAsciiStream(Connection connection, String sql, Object... params) throws DataAccessException;

	public InputStream queryForAsciiStream(String sql, Object... params) throws DataAccessException;

	public InputStream queryForBinaryStream(Connection connection, String sql, Object... params) throws DataAccessException;

	public InputStream queryForBinaryStream(String sql, Object... params) throws DataAccessException;

	public Blob queryForBlob(Connection connection, String sql, Object... params) throws DataAccessException;

	public Blob queryForBlob(String sql, Object... params) throws DataAccessException;

	public Clob queryForClob(Connection connection, String sql, Object... params) throws DataAccessException;

	public Clob queryForClob(String sql, Object... params) throws DataAccessException;

	public Reader queryForCharacterStream(Connection connection, String sql, Object... params) throws DataAccessException;

	public Reader queryForCharacterStream(String sql, Object... params) throws DataAccessException;

	public int execute(Connection connection, String sql, Object... params) throws DataAccessException;

	public int execute(String sql, Object... params) throws DataAccessException;

}
