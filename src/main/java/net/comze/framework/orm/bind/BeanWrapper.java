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
package net.comze.framework.orm.bind;

import java.beans.PropertyEditor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import net.comze.framework.orm.util.BeanAccessException;
import net.comze.framework.orm.util.BeanProperty;
import net.comze.framework.orm.util.BeanUtils;
import net.comze.framework.orm.util.ColumnWrapperFactory;
import net.comze.framework.orm.util.ObjectUtils;
import net.comze.framework.orm.util.ResultSetUtils;
import net.comze.framework.orm.util.StringUtils;

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 3.0.0
 * @version BeanWrapper.java 3.2.1 Sep 21, 2012 12:02:24 PM
 */
public class BeanWrapper<T> implements RowWrapper<T> {

	private Class<T> requiredType;

	public BeanWrapper(Class<T> requiredType) {
		this.requiredType = requiredType;
	}

	@Override
	public T handle(ResultSet resultSet) throws SQLException {
		Map<String, BeanProperty> beanPropertyMap = BeanUtils.getBeanPropertyMap(requiredType);
		T resultRow = BeanUtils.newInstance(requiredType);
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();

		for (int i = 1; i <= columnCount; i++) {
			String columnName = ResultSetUtils.getColumnName(resultSet, i);
			if (StringUtils.isEmpty(columnName)) {
				throw new SQLException("Get column name fail: the column name is empty in statement '" + resultSet.getStatement().toString() + "'");
			}
			BeanProperty beanProperty = beanPropertyMap.get(columnName);
			if (ObjectUtils.isNull(beanProperty)) {
				continue;
			}
			PropertyEditor propertyEditor = beanProperty.getPropertyEditor();
			Class<?> clumnType = Object.class;
			if (ObjectUtils.isNull(propertyEditor) && ObjectUtils.isNotNull(beanProperty.getType())) {
				clumnType = beanProperty.getType();
			}
			Object value = ColumnWrapperFactory.wrapper(clumnType).handle(resultSet, i);
			Method writeMethod = beanProperty.getWriteMethod();
			if (ObjectUtils.isNull(writeMethod)) {
				throw new BeanAccessException("Write method not found: '" + requiredType.getName() + ", " + beanProperty.getName() + "'");
			}

			setObject(resultRow, writeMethod, propertyEditor, value);
		}

		return resultRow;
	}

	protected void setObject(Object object, Method writeMethod, PropertyEditor propertyEditor, Object value) throws SQLException {
		if (ObjectUtils.isNotNull(propertyEditor)) {
			if (value instanceof String) {
				propertyEditor.setAsText((String) value);
				value = propertyEditor.getAsText();
			} else {
				propertyEditor.setValue(value);
				value = propertyEditor.getValue();
			}
		}
		BeanUtils.invoke(object, writeMethod, value);
	}

}
