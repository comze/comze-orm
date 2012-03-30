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

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 3.0.0
 * @version AsciiStreamWrapper.java 3.0.0 Jan 12, 2011 5:00:16 PM
 */
public class AsciiStreamWrapper extends ColumnWrapper<InputStream> {

	@Override
	public InputStream handleColumn(ResultSet resultSet, int index) throws SQLException {
		return resultSet.getAsciiStream(index);
	}

}
