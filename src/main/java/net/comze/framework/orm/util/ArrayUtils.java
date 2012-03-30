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
 * @since 3.1.0
 * @version ArrayUtils.java 3.1.0 Mar 27, 2012 3:01:33 PM
 */
public abstract class ArrayUtils {

	public static boolean isEmpty(Object... array) {
		return array == null || array.length < 1;
	}

	public static boolean isNotEmpty(Object... array) {
		return array != null && array.length > 0;
	}

	public static String toString(Object... array) {
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				builder.append(String.format("%s=\"%s\"", array[i] == null ? array[i] : array[i].getClass().getName(), array[i]));
				if (i < array.length - 1) {
					builder.append(", ");
				}
			}
		}
		return builder.append(']').toString();
	}

}
