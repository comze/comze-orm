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
import java.sql.RowId;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;

import net.comze.framework.orm.bind.BigDecimalWrapper;
import net.comze.framework.orm.bind.BlobWrapper;
import net.comze.framework.orm.bind.BooleanWrapper;
import net.comze.framework.orm.bind.ByteWrapper;
import net.comze.framework.orm.bind.BytesWrapper;
import net.comze.framework.orm.bind.ClobWrapper;
import net.comze.framework.orm.bind.ColumnWrapper;
import net.comze.framework.orm.bind.DateWrapper;
import net.comze.framework.orm.bind.DoubleWrapper;
import net.comze.framework.orm.bind.EnumWrapper;
import net.comze.framework.orm.bind.FloatWrapper;
import net.comze.framework.orm.bind.IntegerWrapper;
import net.comze.framework.orm.bind.LongWrapper;
import net.comze.framework.orm.bind.NClobWrapper;
import net.comze.framework.orm.bind.ObjectWrapper;
import net.comze.framework.orm.bind.RefWrapper;
import net.comze.framework.orm.bind.RowIdWrapper;
import net.comze.framework.orm.bind.SQLArrayWrapper;
import net.comze.framework.orm.bind.SQLDateWrapper;
import net.comze.framework.orm.bind.SQLXMLWrapper;
import net.comze.framework.orm.bind.ShortWrapper;
import net.comze.framework.orm.bind.StringWrapper;
import net.comze.framework.orm.bind.TimeWrapper;
import net.comze.framework.orm.bind.TimestampWrapper;
import net.comze.framework.orm.bind.URLWrapper;

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 3.1.0
 * @version ColumnWrapperFactory.java 3.2.7 Jun 14, 2014 11:30:47 AM
 */
public abstract class ColumnWrapperFactory {

	@SuppressWarnings("unchecked")
	public static <T> ColumnWrapper<T> wrapper(Class<T> requiredType) {
		if (requiredType.equals(Array.class)) {
			return (ColumnWrapper<T>) new SQLArrayWrapper();
		}
		if (requiredType.equals(InputStream.class)) {
			return (ColumnWrapper<T>) new ObjectWrapper(); // :~
		}
		if (requiredType.equals(BigDecimal.class)) {
			return (ColumnWrapper<T>) new BigDecimalWrapper();
		}
		if (requiredType.equals(Blob.class)) {
			return (ColumnWrapper<T>) new BlobWrapper();
		}
		if (requiredType.equals(Boolean.class) || requiredType.equals(Boolean.TYPE)) {
			return (ColumnWrapper<T>) new BooleanWrapper();
		}
		if (requiredType.equals(byte[].class)) {
			return (ColumnWrapper<T>) new BytesWrapper();
		}
		if (requiredType.equals(Byte.class) || requiredType.equals(Byte.TYPE)) {
			return (ColumnWrapper<T>) new ByteWrapper();
		}
		if (requiredType.equals(Reader.class)) {
			return (ColumnWrapper<T>) new ObjectWrapper(); // :~
		}
		if (requiredType.equals(Clob.class)) {
			return (ColumnWrapper<T>) new ClobWrapper();
		}
		if (requiredType.equals(Date.class)) {
			return (ColumnWrapper<T>) new SQLDateWrapper();
		}
		if (requiredType.equals(Double.class) || requiredType.equals(Double.TYPE)) {
			return (ColumnWrapper<T>) new DoubleWrapper();
		}
		if (requiredType.equals(Float.class) || requiredType.equals(Float.TYPE)) {
			return (ColumnWrapper<T>) new FloatWrapper();
		}
		if (requiredType.equals(Integer.class) || requiredType.equals(Integer.TYPE)) {
			return (ColumnWrapper<T>) new IntegerWrapper();
		}
		if (requiredType.equals(Long.class) || requiredType.equals(Long.TYPE)) {
			return (ColumnWrapper<T>) new LongWrapper();
		}
		if (requiredType.equals(NClob.class)) {
			return (ColumnWrapper<T>) new NClobWrapper();
		}
		if (requiredType.equals(Number.class)) {
			return (ColumnWrapper<T>) new DoubleWrapper();
		}
		if (requiredType.equals(Object.class)) {
			return (ColumnWrapper<T>) new ObjectWrapper();
		}
		if (requiredType.equals(Ref.class)) {
			return (ColumnWrapper<T>) new RefWrapper();
		}
		if (requiredType.equals(RowId.class)) {
			return (ColumnWrapper<T>) new RowIdWrapper();
		}
		if (requiredType.equals(Short.class) || requiredType.equals(Short.TYPE)) {
			return (ColumnWrapper<T>) new ShortWrapper();
		}
		if (requiredType.equals(SQLXML.class)) {
			return (ColumnWrapper<T>) new SQLXMLWrapper();
		}
		if (requiredType.equals(String.class)) {
			return (ColumnWrapper<T>) new StringWrapper();
		}
		if (requiredType.equals(Timestamp.class)) {
			return (ColumnWrapper<T>) new TimestampWrapper();
		}
		if (requiredType.equals(Time.class)) {
			return (ColumnWrapper<T>) new TimeWrapper();
		}
		if (requiredType.equals(URL.class)) {
			return (ColumnWrapper<T>) new URLWrapper();
		}
		if (java.util.Date.class.isAssignableFrom(requiredType)) {
			return (ColumnWrapper<T>) new DateWrapper();
		}
		if (requiredType.isEnum()) {
			return (ColumnWrapper<T>) new EnumWrapper<T>(requiredType);
		}
		return (ColumnWrapper<T>) new ObjectWrapper();
	}

}
