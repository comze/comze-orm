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

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 1.0.0
 * @version JdbcResource.java 3.1.0 Mar 22, 2012 3:31:10 PM
 */
public class JdbcResource {

	public static final String JDBC_DRIVER = "jdbc.driverClassName";

	public static final String JDBC_URL = "jdbc.url";

	public static final String JDBC_USERNAME = "jdbc.username";

	public static final String JDBC_PASSWORD = "jdbc.password";

	public static final String POOL_MAX_ACTIVE = "pool.maxactive";

	public static final String POOL_MAX_IDLE = "pool.maxidle";

	public static final String POOL_MAX_WAIT = "pool.maxwait";

	public static final String POOL_VALIDATION_QUERY = "pool.validationQuery";

	private Map<String, Object> attributeMap = new HashMap<String, Object>();

	public void setAttribute(String key, Object value) {
		attributeMap.put(key, value);
	}

	public Object getAttribute(String key) {
		return attributeMap.get(key);
	}

	public void clear() {
		attributeMap.clear();
	}

}
