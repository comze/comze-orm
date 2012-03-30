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
 * @since 3.0.0
 * @version ResultSetHandleException.java 3.0.0 Jan 12, 2011 1:30:15 PM
 */
public class ResultSetHandleException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResultSetHandleException() {}

	public ResultSetHandleException(String message) {
		super(message);
	}

	public ResultSetHandleException(Throwable cause) {
		super(cause);
	}

	public ResultSetHandleException(String message, Throwable cause) {
		super(message, cause);
	}

}
