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

package org.wso2.carbon.connector.utils;

/**
 * Contains the constants used by the Snowflake connector.
 */
public class Constants {

    public static final String CONNECTOR_NAME = "snowflake";
    public static final String CONNECTION_NAME = "name";
    public static final String SNOWFLAKE_DRIVER = "net.snowflake.client.jdbc.SnowflakeDriver";

    public static final String ACCOUNT_IDENTIFIER = "accountIdentifier";
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String QUERY = "query";
    public static final String EXECUTE_QUERY = "executeQuery";
    public static final String PAYLOAD = "payload";

    public static final String PROPERTY_ERROR_CODE = "ERROR_CODE";
    public static final String PROPERTY_ERROR_MESSAGE = "ERROR_MESSAGE";
    public static final String PROPERTY_ERROR_DETAIL = "ERROR_DETAIL";
    public static final String PROPERTY_ERROR_EXCEPTION = "ERROR_EXCEPTION";
    public static final String STATUS_CODE = "HTTP_SC";
    public static final Object HTTP_STATUS_500 = "500";
    public static final String CLIENT_SESSION_KEEP_ALIVE = "CLIENT_SESSION_KEEP_ALIVE";
    public static final String KEEP_ALIVE = "keepAlive";

}
