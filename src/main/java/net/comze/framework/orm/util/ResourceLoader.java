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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 1.0.0
 * @version ResourceLoader.java 3.1.0 Mar 22, 2012 2:55:05 PM
 */
public abstract class ResourceLoader {

	public static InputStream loadResource(String name) throws IOException {
		ClassLoader classLoader = ResourceLoader.class.getClassLoader();
		InputStream inputStream = null;
		if (ObjectUtils.isNotNull(classLoader)) {
			inputStream = classLoader.getResourceAsStream(name);
		}
		if (ObjectUtils.isNull(inputStream)) {
			classLoader = ClassLoader.getSystemClassLoader();
			inputStream = classLoader.getResourceAsStream(name);
		}
		if (ObjectUtils.isNull(inputStream)) {
			File file = new File(name);
			if (file.exists() && file.isFile()) {
				inputStream = new FileInputStream(file);
			}
		}
		return inputStream;
	}

	public static Properties loadProperties(String name) throws IOException {
		InputStream inputStream = null;
		Properties properties = null;
		try {
			inputStream = loadResource(name);
			if (ObjectUtils.isNotNull(inputStream)) {
				properties = new Properties();
				properties.load(inputStream);
			}
		} finally {
			if (ObjectUtils.isNotNull(inputStream)) {
				inputStream.close();
			}
		}
		return properties;
	}

}
