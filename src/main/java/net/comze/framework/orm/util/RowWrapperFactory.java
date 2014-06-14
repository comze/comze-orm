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
package net.comze.framework.orm.util;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;

import net.comze.framework.orm.bind.ArrayWrapper;
import net.comze.framework.orm.bind.BeanWrapper;
import net.comze.framework.orm.bind.BigDecimalWrapper;
import net.comze.framework.orm.bind.BlobWrapper;
import net.comze.framework.orm.bind.BooleanWrapper;
import net.comze.framework.orm.bind.ByteWrapper;
import net.comze.framework.orm.bind.BytesWrapper;
import net.comze.framework.orm.bind.ClobWrapper;
import net.comze.framework.orm.bind.ColumnWrapper;
import net.comze.framework.orm.bind.DateWrapper;
import net.comze.framework.orm.bind.SQLDateWrapper;
import net.comze.framework.orm.bind.DoubleWrapper;
import net.comze.framework.orm.bind.FloatWrapper;
import net.comze.framework.orm.bind.IntegerWrapper;
import net.comze.framework.orm.bind.LongWrapper;
import net.comze.framework.orm.bind.MapWrapper;
import net.comze.framework.orm.bind.NClobWrapper;
import net.comze.framework.orm.bind.ObjectWrapper;
import net.comze.framework.orm.bind.RefWrapper;
import net.comze.framework.orm.bind.RowIdWrapper;
import net.comze.framework.orm.bind.RowWrapper;
import net.comze.framework.orm.bind.SQLArrayWrapper;
import net.comze.framework.orm.bind.SQLXMLWrapper;
import net.comze.framework.orm.bind.ShortWrapper;
import net.comze.framework.orm.bind.StringWrapper;
import net.comze.framework.orm.bind.TimeWrapper;
import net.comze.framework.orm.bind.TimestampWrapper;
import net.comze.framework.orm.bind.URLWrapper;

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 3.0.0
 * @version RowWrapperFactory.java 3.2.7 Jun 14, 2014 11:41:53 AM
 */
public abstract class RowWrapperFactory {

	public static final String JDK_SPECIFICATION_VENDOR = java.lang.Object.class.getPackage().getSpecificationVendor();

	@SuppressWarnings("unchecked")
	public static <T> RowWrapper<T> wrapper(Class<T> requiredType) {
		class RowWrapperAdapter<W> implements RowWrapper<W> {

			public RowWrapperAdapter(ColumnWrapper<W> columnWrapper) {
				this.columnWrapper = columnWrapper;
			}

			private ColumnWrapper<W> columnWrapper;

			@Override
			public W handle(ResultSet resultSet) throws SQLException {
				return columnWrapper.handle(resultSet, 1);
			}

		}

		if (requiredType.equals(Array.class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Array>(new SQLArrayWrapper());
		}
		if (requiredType.isArray() && !requiredType.equals(byte[].class)) {
			return (RowWrapper<T>) new ArrayWrapper();
		}
		if (requiredType.equals(InputStream.class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Object>(new ObjectWrapper()); // :~
		}
		if (requiredType.equals(BigDecimal.class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<BigDecimal>(new BigDecimalWrapper());
		}
		if (requiredType.equals(Blob.class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Blob>(new BlobWrapper());
		}
		if (requiredType.equals(Boolean.class) || requiredType.equals(Boolean.TYPE)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Boolean>(new BooleanWrapper());
		}
		if (requiredType.equals(byte[].class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<byte[]>(new BytesWrapper());
		}
		if (requiredType.equals(Byte.class) || requiredType.equals(Byte.TYPE)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Byte>(new ByteWrapper());
		}
		if (requiredType.equals(Reader.class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Object>(new ObjectWrapper()); // :~
		}
		if (requiredType.equals(Clob.class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Clob>(new ClobWrapper());
		}
		if (requiredType.equals(Date.class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Date>(new SQLDateWrapper());
		}
		if (requiredType.equals(Double.class) || requiredType.equals(Double.TYPE)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Double>(new DoubleWrapper());
		}
		if (requiredType.equals(Float.class) || requiredType.equals(Float.TYPE)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Float>(new FloatWrapper());
		}
		if (requiredType.equals(Integer.class) || requiredType.equals(Integer.TYPE)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Integer>(new IntegerWrapper());
		}
		if (requiredType.equals(Long.class) || requiredType.equals(Long.TYPE)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Long>(new LongWrapper());
		}
		if (requiredType.equals(NClob.class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<NClob>(new NClobWrapper());
		}
		if (requiredType.equals(Number.class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Double>(new DoubleWrapper());
		}
		if (Map.class.isAssignableFrom(requiredType)) {
			return (RowWrapper<T>) new MapWrapper();
		}
		if (requiredType.equals(Object.class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Object>(new ObjectWrapper());
		}
		if (requiredType.equals(Ref.class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Ref>(new RefWrapper());
		}
		if (requiredType.equals(RowId.class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<RowId>(new RowIdWrapper());
		}
		if (requiredType.equals(Short.class) || requiredType.equals(Short.TYPE)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Short>(new ShortWrapper());
		}
		if (requiredType.equals(SQLXML.class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<SQLXML>(new SQLXMLWrapper());
		}
		if (requiredType.equals(String.class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<String>(new StringWrapper());
		}
		if (requiredType.equals(Timestamp.class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Timestamp>(new TimestampWrapper());
		}
		if (requiredType.equals(Time.class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<Time>(new TimeWrapper());
		}
		if (requiredType.equals(URL.class)) {
			return (RowWrapper<T>) new RowWrapperAdapter<URL>(new URLWrapper());
		}
		if (java.util.Date.class.isAssignableFrom(requiredType)) {
			return (RowWrapper<T>) new RowWrapperAdapter<java.util.Date>(new DateWrapper());
		}
		if (ObjectUtils.isNotNull(requiredType.getPackage()) && JDK_SPECIFICATION_VENDOR.equals(requiredType.getPackage().getSpecificationVendor())) {
			return (RowWrapper<T>) new RowWrapperAdapter<Object>(new ObjectWrapper());
		}
		return (RowWrapper<T>) new BeanWrapper<T>(requiredType);
	}

}
