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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import net.comze.framework.orm.util.ObjectUtils;

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 3.0.0
 * @version DateWrapper.java 3.2.8 Feb 2, 2015 5:41:59 PM
 */
public class DateWrapper implements ColumnWrapper<Date> {

	@Override
	public Date handle(ResultSet resultSet, int index) throws SQLException {
		Object value = resultSet.getObject(index);
		if (ObjectUtils.isNull(value)) {
			return (Date) value;
		}
		if (value instanceof Date) {
			return new Date(((Date) value).getTime());
		}
		if (value instanceof Long) {
			return new Date((Long) value);
		}
		throw new SQLException("Could not convert '" + value + "' of type '" + value.getClass().getName() + "' to class '" + Date.class.getName() + "'");
	}

}
