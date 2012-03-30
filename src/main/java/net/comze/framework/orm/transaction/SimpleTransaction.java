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
package net.comze.framework.orm.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import net.comze.framework.orm.util.TransactionException;

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 3.1.0
 * @version SimpleTransaction.java 3.1.0 Mar 27, 2012 4:14:48 PM
 */
public class SimpleTransaction implements Transaction {

	protected Connection connection;

	public SimpleTransaction(Connection connection, boolean autoCommit) {
		this.connection = connection;
		try {
			if (connection.getAutoCommit() != autoCommit) {
				connection.setAutoCommit(autoCommit);
			}
		} catch (SQLException e) {
			throw new TransactionException("Set auto commit error: " + e.getMessage(), e);
		}
	}

	@Override
	public Connection open() throws TransactionException {
		return connection;
	}

	@Override
	public void commit() throws TransactionException {
		try {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
		} catch (SQLException e) {
			new TransactionException("Commit error: " + e.getMessage(), e);
		}
	}

	@Override
	public void rollback() throws TransactionException {
		try {
			if (!connection.getAutoCommit()) {
				connection.rollback();
			}
		} catch (SQLException e) {
			throw new TransactionException("Rollback error: " + e.getMessage(), e);
		}
	}

	@Override
	public void close() throws TransactionException {
		try {
			if (!connection.getAutoCommit()) {
				connection.setAutoCommit(true);
			}
			connection.close();
		} catch (SQLException e) {
			throw new TransactionException("Close error: " + e.getMessage(), e);
		}
	}

}
