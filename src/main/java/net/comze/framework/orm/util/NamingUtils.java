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

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 3.2.7
 * @version NamingUtils.java 3.2.7 Apr 29, 2016 11:37:54 AM
 */
public class NamingUtils {

	public static String toLowerCaseWithUnderscores(String propertyName) {
		StringBuilder buffer = new StringBuilder();
		char[] array = propertyName.toCharArray();

		boolean lowerCase = true;
		for (int i = 0; i < array.length; i++) {
			char c = array[i];
			if (Character.isUpperCase(c)) {
				if (lowerCase && i > 0 && array[i - 1] != '_') {
					buffer.append('_');
				}
				c = Character.toLowerCase(c);
				lowerCase = false;
			} else {
				lowerCase = true;
			}
			buffer.append(c);
		}

		return buffer.toString();
	}

}
