/*
 * Copyright (c) 2023, WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.connector.operations;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.ManagedLifecycle;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.SynapseEnvironment;
import org.wso2.carbon.connector.connection.SnowflakeConnection;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.connection.ConnectionHandler;
import org.wso2.carbon.connector.core.util.ConnectorUtils;
import org.wso2.carbon.connector.exception.InvalidConfigurationException;
import org.wso2.carbon.connector.pojo.ConnectionConfiguration;
import org.wso2.carbon.connector.utils.Constants;
import org.wso2.carbon.connector.utils.Error;
import org.wso2.carbon.connector.utils.SnowflakeUtils;
import java.sql.SQLException;

/**
 * Implements the init operation.
 */
public class SnowflakeConfig extends AbstractConnector implements ManagedLifecycle {

    private static final Log log = LogFactory.getLog(SnowflakeConfig.class);

    private static final String OPERATION_NAME = "init";

    @Override
    public void connect(MessageContext messageContext) {

        String connectorName = Constants.CONNECTOR_NAME;
        String connectionName = (String) ConnectorUtils.
                lookupTemplateParamater(messageContext, Constants.CONNECTION_NAME);

        try {
            ConnectionConfiguration configuration = getConnectionConfigFromContext(messageContext);
            ConnectionHandler handler = ConnectionHandler.getConnectionHandler();

            if (!handler.checkIfConnectionExists(connectorName, connectionName)) {
                if (log.isDebugEnabled()) {
                    log.debug(String.format("Connection does not exist for %s connector with connection name: %s. " +
                            "Hence a new connection will be created.", connectorName, connectionName));
                }

                SnowflakeConnection snowflakeConnection = new SnowflakeConnection(configuration);
                try {
                    handler.createConnection(Constants.CONNECTOR_NAME, connectionName, snowflakeConnection, messageContext);
                } catch (NoSuchMethodError e) {
                    //Running in a version of Mediation that does not support local entry undeploy callback
                    handler.createConnection(Constants.CONNECTOR_NAME, connectionName, snowflakeConnection);
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug(String.format("Connection exists for %s connector with connection name: %s.", connectorName,
                            connectionName));
                }
            }
        } catch (InvalidConfigurationException e) {
            String errorDetail = "[" + connectionName + "] Failed to initiate snowflake connector configuration.";
            handleError(messageContext, e, Error.INVALID_CONFIGURATION, errorDetail);
        } catch (SQLException e) {
            String errorDetail = "[" + connectionName + "] Failed to initiate snowflake connection.";
            handleError(messageContext, e, Error.OPERATION_ERROR, errorDetail);
        }
    }

    @Override
    public void init(SynapseEnvironment synapseEnvironment) {
        // Nothing to do when initiating the connector
    }
    @Override
    public void destroy() {
        ConnectionHandler.getConnectionHandler().shutdownConnections(Constants.CONNECTOR_NAME);
    }

    /**
     * Set error to context and handle.
     *
     * @param messageContext Message Context.
     * @param e              Exception.
     * @param error          Error.
     * @param errorDetail    Error Detail.
     */
    private void handleError(MessageContext messageContext, Exception e, Error error, String errorDetail) {
        SnowflakeUtils.setError(OPERATION_NAME, messageContext, e, error);
        handleException(errorDetail, e, messageContext);
    }

    /**
     * Get connection configuration from message context.
     *
     * @param messageContext Message Context.
     * @return Connection Configuration.
     * @throws InvalidConfigurationException Invalid Configuration Exception.
     */
    private ConnectionConfiguration getConnectionConfigFromContext(MessageContext messageContext)
            throws InvalidConfigurationException {
        String accountIdentifier =
                (String) ConnectorUtils.lookupTemplateParamater(messageContext, Constants.ACCOUNT_IDENTIFIER);
        String user = (String) ConnectorUtils.lookupTemplateParamater(messageContext, Constants.USER);
        String password = (String) ConnectorUtils.lookupTemplateParamater(messageContext, Constants.PASSWORD);
        String keepAlive = (String) ConnectorUtils.lookupTemplateParamater(messageContext, Constants.KEEP_ALIVE);

        ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration();
        connectionConfiguration.setAccountIdentifier(accountIdentifier);
        connectionConfiguration.setUser(user);
        connectionConfiguration.setPassword(password);
        connectionConfiguration.setKeepAlive(keepAlive);
        return connectionConfiguration;
    }

}
