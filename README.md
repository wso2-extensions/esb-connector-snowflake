# Snowflake Connector
The Snowflake Connector enables you to utilize a complete range of Snowflake operations through the Java Database Connectivity (JDBC) API using WSO2 MI. It provides the capability to execute queries, perform operations, and execute operations with Snowflake databases.

## Setting up the Snowflake
To use the Snowflake database, you must have a valid Snowflake account. To create a snowflake account, please visit the official [Snowflake website](https://www.snowflake.com/en/) and complete the registration process. Once registered you will obtain username and password which you can login to Snowflake account and accountIdentifier which is the a unique identifier for your Snowflake account within your business entity. This accountIdentifier also serves as a distinct identifier across the broader Snowflake network.

### Deploying the client libraries
Download Snowflake JDBC driver and copy in to the /lib directory

--------------------------------------------------------
# Snowflake Connector Example

--------------------------------------------------------

# Snowflake Connector Reference

The following operations allow you to work with the Snowflake Connector. Click an operation name to see parameter details and samples on how to use it.

## Initialize the connector
To use the Snowflake connector, add the <snowflake.init> element in your configuration before carrying out any Snowflake operations.

| Parameter Name      | Description                                 | Required |      
| :---                |    :----:                                   |  ---:    | 
| accountIdentifier   | The unique identifies a Snowflake account   | Yes      |
| user                | Snowflake account username                  | Yes      |
| password            | Snowflake account password                  | Yes      |

Sample Configuration

```xml
<snowflake.init>
    <name>SNOWFLAKE_CONNECTION</name>
    <accountIdentifier>ec25934.ap-south-1.aws</accountIdentifier>
    <user>Wso2SnowflakeUser</user>
    <password>Wso2SnowflakeUser</password>
</snowflake.init>
``` 

## Operations

### query
Query a given SQL statement
| Parameter Name      | Description                                 | Required |      
| :---                |    :----:                                   |  ---:    |
| query               | SQL query to execute                        | Yes      |

Sample Configuration

```xml
<snowflake.query configKey="SNOWFLAKE_CONNECTION">
    <query>SELECT * FROM HOTEL_SERENDIB.PUBLIC.RESERVATIONS</query>
</snowflake.query>
``` 
### execute
Execute a given SQL statement

| Parameter Name      | Description                                   | Required  |      
| :---                |    :----:                                     |  ---:     | 
| executeQuery        | SQL query to execute                          | Yes       |
| payload             | Payload to be used in the execute query       | Optional  |

Sample Configuration

```xml
<snowflake.execute configKey="SNOWFLAKE_CONNECTION">
    <executeQuery>INSERT INTO HOTEL_SERENDIB.PUBLIC.RESERVATIONS (NICNUMBER, FIRSTNAME, LASTNAME, CHECKIN, CHECKOUT, ADULTS, CHILDREN, ROOMTYPE, SPECIALREQUESTS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)</executeQuery>
    <payload>{json-eval($)}</payload>
</snowflake.execute>
``` 
### batch execute
Batch execute a given SQL statement.

| Parameter Name      | Description                                   | Required  |      
| :---                |    :----:                                     |  ---:     | 
| executeQuery        | SQL query to execute                          | Yes       |
| payload             | Bulk Payload to be used in the query          | Yes       |

Sample Configuration

```xml
<snowflake.batchExecute configKey="SNOWFLAKE_CONNECTION">
    <executeQuery>INSERT INTO HOTEL_SERENDIB.PUBLIC.RESERVATIONS (NICNUMBER, FIRSTNAME, LASTNAME, CHECKIN, CHECKOUT, ADULTS, CHILDREN, ROOMTYPE, SPECIALREQUESTS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)</executeQuery>
    <payload>{json-eval($)}</payload>
</snowflake.batchExecute>
``` 
