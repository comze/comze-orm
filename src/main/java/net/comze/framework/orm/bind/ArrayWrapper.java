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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 3.0.0
 * @version ArrayWrapper.java 3.2.0 Aug 16, 2012 3:22:17 PM
 */
public class ArrayWrapper implements RowWrapper<Object[]> {

	@Override
	public Object[] handle(ResultSet resultSet) throws SQLException {
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();
		Object[] resultRow = new Object[columnCount];
		for (int i = 1; i <= columnCount; i++) {
			resultRow[i - 1] = resultSet.getObject(i);
		}
		return resultRow;
	}

}
