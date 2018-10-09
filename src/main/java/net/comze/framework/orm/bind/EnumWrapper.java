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

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 3.3.1
 * @version EnumWrapper.java 3.2.7 Oct 9, 2018 10:53:40 AM
 */
public class EnumWrapper<T> implements ColumnWrapper<T> {

	private Class<T> requiredType;

	public EnumWrapper(Class<T> requiredType) {
		this.requiredType = requiredType;
	}

	@Override
	public T handle(ResultSet resultSet, int index) throws SQLException {
		String name = resultSet.getString(index);
		for (T value: requiredType.getEnumConstants()) {
			if (((Enum<?>) value).name().equals(name)) {
				return (T) value;
			}
		}
		throw new SQLException("No enum constant " + requiredType.getCanonicalName() + "." + name);
	}

}
