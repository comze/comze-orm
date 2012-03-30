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
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import net.comze.framework.orm.bind.ArrayWrapper;
import net.comze.framework.orm.bind.BeanWrapper;
import net.comze.framework.orm.bind.BigDecimalWrapper;
import net.comze.framework.orm.bind.BlobWrapper;
import net.comze.framework.orm.bind.BooleanWrapper;
import net.comze.framework.orm.bind.BytesWrapper;
import net.comze.framework.orm.bind.ClobWrapper;
import net.comze.framework.orm.bind.DateWrapper;
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
 * @version RowWrapperFactory.java 3.0.0 Jan 12, 2011 2:27:49 PM
 */
public abstract class RowWrapperFactory {

	public static final String JDK_SPECIFICATION_VENDOR = java.lang.Object.class.getPackage().getSpecificationVendor();

	@SuppressWarnings("unchecked")
	public static <T> RowWrapper<T> wrapper(Class<T> requiredType) {
		if (requiredType.equals(Array.class)) {
			return (RowWrapper<T>) new SQLArrayWrapper();
		}
		if (requiredType.isArray() && !requiredType.equals(byte[].class)) {
			return (RowWrapper<T>) new ArrayWrapper();
		}
		if (requiredType.equals(InputStream.class)) {
			return (RowWrapper<T>) new ObjectWrapper(); // :~
		}
		if (requiredType.equals(BigDecimal.class)) {
			return (RowWrapper<T>) new BigDecimalWrapper();
		}
		if (requiredType.equals(Blob.class)) {
			return (RowWrapper<T>) new BlobWrapper();
		}
		if (requiredType.equals(Boolean.class) || requiredType.equals(Boolean.TYPE)) {
			return (RowWrapper<T>) new BooleanWrapper();
		}
		if (requiredType.equals(byte[].class)) {
			return (RowWrapper<T>) new BytesWrapper();
		}
		if (requiredType.equals(Byte.class) || requiredType.equals(Byte.TYPE)) {
			return (RowWrapper<T>) new BytesWrapper();
		}
		if (requiredType.equals(Reader.class)) {
			return (RowWrapper<T>) new ObjectWrapper(); // :~
		}
		if (requiredType.equals(Clob.class)) {
			return (RowWrapper<T>) new ClobWrapper();
		}
		if (Date.class.isAssignableFrom(requiredType)) {
			return (RowWrapper<T>) new DateWrapper();
		}
		if (requiredType.equals(Double.class) || requiredType.equals(Double.TYPE)) {
			return (RowWrapper<T>) new DoubleWrapper();
		}
		if (requiredType.equals(Float.class) || requiredType.equals(Float.TYPE)) {
			return (RowWrapper<T>) new FloatWrapper();
		}
		if (requiredType.equals(Integer.class) || requiredType.equals(Integer.TYPE)) {
			return (RowWrapper<T>) new IntegerWrapper();
		}
		if (requiredType.equals(Long.class) || requiredType.equals(Long.TYPE)) {
			return (RowWrapper<T>) new LongWrapper();
		}
		if (requiredType.equals(NClob.class)) {
			return (RowWrapper<T>) new NClobWrapper();
		}
		if (Map.class.isAssignableFrom(requiredType)) {
			return (RowWrapper<T>) new MapWrapper();
		}
		if (requiredType.equals(Object.class)) {
			return (RowWrapper<T>) new ObjectWrapper();
		}
		if (requiredType.equals(Ref.class)) {
			return (RowWrapper<T>) new RefWrapper();
		}
		if (requiredType.equals(RowId.class)) {
			return (RowWrapper<T>) new RowIdWrapper();
		}
		if (requiredType.equals(Short.class) || requiredType.equals(Short.TYPE)) {
			return (RowWrapper<T>) new ShortWrapper();
		}
		if (requiredType.equals(SQLXML.class)) {
			return (RowWrapper<T>) new SQLXMLWrapper();
		}
		if (requiredType.equals(String.class)) {
			return (RowWrapper<T>) new StringWrapper();
		}
		if (requiredType.equals(Timestamp.class)) {
			return (RowWrapper<T>) new TimestampWrapper();
		}
		if (requiredType.equals(Time.class)) {
			return (RowWrapper<T>) new TimeWrapper();
		}
		if (requiredType.equals(URL.class)) {
			return (RowWrapper<T>) new URLWrapper();
		}
		if (requiredType.getPackage().getSpecificationVendor().equals(JDK_SPECIFICATION_VENDOR)) {
			return (RowWrapper<T>) new ObjectWrapper();
		}
		return (RowWrapper<T>) new BeanWrapper<T>(requiredType);
	}

}
