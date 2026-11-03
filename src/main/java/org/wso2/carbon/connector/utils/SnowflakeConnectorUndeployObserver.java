/*
 * Copyright (c) 2026, WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.connector.utils;

import org.apache.synapse.config.AbstractSynapseObserver;
import org.apache.synapse.config.SynapseConfiguration;
import org.apache.synapse.libraries.model.Library;
import org.wso2.carbon.connector.core.connection.ConnectionHandler;

/**
 * Listen for library un deploy events and cleanup connections created by the connector.
 */
public class SnowflakeConnectorUndeployObserver extends AbstractSynapseObserver {

    private SynapseConfiguration synapseConfiguration;
    private String connectorName = Constants.CONNECTOR_NAME;

    public SnowflakeConnectorUndeployObserver(SynapseConfiguration synapseConfiguration) {
        this.synapseConfiguration = synapseConfiguration;
    }

    @Override
    public void synapseLibraryRemoved(Library library) {
        String libraryPath = library.getFileName();
        // No need to use File.separator here since libraryPath we are getting from synapse is formatted with "/"
        String libraryName = libraryPath.substring(libraryPath.lastIndexOf("/"));
        if ((libraryName.contains(Constants.LIBRARY_NAME_PATTEN1) || libraryName.contains(
            Constants.LIBRARY_NAME_PATTEN2)) && library.getPackage()
            .equals(Constants.CONNECTOR_LIBRARY_PACKAGE_TYPE)) {
            if (log.isDebugEnabled()) {
                log.debug(
                    "Snowflake Connector is being un deployed. Closing all connections created.");
            }
            ConnectionHandler.getConnectionHandler().
                shutdownConnections(Constants.CONNECTOR_NAME);
            synapseConfiguration.unregisterObserver(this);
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SnowflakeConnectorUndeployObserver that = (SnowflakeConnectorUndeployObserver) o;
        return connectorName.equals(that.connectorName);
    }

    public int hashCode() {
        return connectorName.hashCode();
    }
}
