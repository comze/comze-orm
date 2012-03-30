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
 * @since 3.0.0
 * @version ColumnWrapper.java 3.0.0 Jan 9, 2011 6:36:58 PM
 */
public abstract class ColumnWrapper<T> extends RowWrapper<T> {

	protected static final int DEFAULT_COLUMN_INDEX = 1;

	@Override
	public T handleRow(ResultSet resultSet) throws SQLException {
		int columnCount = resultSet.getMetaData().getColumnCount();
		if (columnCount != 1) {
			throw new SQLException("Incorrect column: column count is " + columnCount + ", where column count expected 1, in statement " + resultSet.getStatement().toString());
		}
		return handleColumn(resultSet, DEFAULT_COLUMN_INDEX);
	}

	public abstract T handleColumn(ResultSet resultSet, int index) throws SQLException;

}
