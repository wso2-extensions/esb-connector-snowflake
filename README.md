# Snowflake Connector
The Snowflake Connector enables you to utilize a complete range of Snowflake operations through the Java Database Connectivity (JDBC) API using WSO2 MI. It provides the capability to execute queries, perform operations, and execute operations with Snowflake databases.

## Setting up the Snowflake
To use the Snowflake database, you must have a valid Snowflake account. To create a snowflake account, please visit the official [Snowflake website](https://www.snowflake.com/en/) and complete the registration process. Once registered you will obtain username and password which you can login to Snowflake account and accountIdentifier which is the a unique identifier for your Snowflake account within your business entity. This accountIdentifier also serves as a distinct identifier across the broader Snowflake network.

# Snowflake Connector Reference

The following operations allow you to work with the Snowflake Connector. Click an operation name to see parameter details and samples on how to use it.

## Initialize the connector
To use the Snowflake connector, add the <snowflake.init> element in your configuration before carrying out any Snowflake operations.

| Parameter Name      | Description                                 | Required |      
| :---                |    :----:                                   |  ---:    | 
| accountIdentifier   | The unique identifies a Snowflake account   | Yes      |
| user                | Snowflake account username                  | Yes      |
| password            | Snowflake account password                  | Yes      |

**Sample Configuration**

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

**Sample Configuration**

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

**Sample Configuration**

```xml
<snowflake.execute configKey="SNOWFLAKE_CONNECTION">
    <executeQuery>INSERT INTO HOTEL_SERENDIB.PUBLIC.RESERVATIONS (NICNUMBER, FIRSTNAME, LASTNAME, CHECKIN, CHECKOUT, ADULTS, CHILDREN, ROOMTYPE, SPECIALREQUESTS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)</executeQuery>
    <payload>{json-eval($)}</payload>
</snowflake.execute>
``` 

**Sample Request**

```json
{
    "NICNUMBER": "9876543210",
    "FIRSTNAME": "Alice",
    "LASTNAME": "Johnson",
    "CHECKIN": "2023-10-01",
    "CHECKOUT": "2023-10-05",
    "ADULTS": 1,
    "CHILDREN": 0,
    "ROOMTYPE": "Single",
    "SPECIALREQUESTS": "Quiet room"
}
```

### batch execute
Batch execute a given SQL statement.

| Parameter Name      | Description                                   | Required  |      
| :---                |    :----:                                     |  ---:     | 
| executeQuery        | SQL query to execute                          | Yes       |
| payload             | Bulk Payload to be used in the query          | Yes       |

**Sample Configuration**

```xml
<snowflake.batchExecute configKey="SNOWFLAKE_CONNECTION">
    <executeQuery>INSERT INTO HOTEL_SERENDIB.PUBLIC.RESERVATIONS (NICNUMBER, FIRSTNAME, LASTNAME, CHECKIN, CHECKOUT, ADULTS, CHILDREN, ROOMTYPE, SPECIALREQUESTS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)</executeQuery>
    <payload>{json-eval($)}</payload>
</snowflake.batchExecute>
``` 
**Sample Request**

```json
[
    {
        "NICNUMBER": "1234567890",
        "FIRSTNAME": "John",
        "LASTNAME": "Smith",
        "CHECKIN": "2023-09-01",
        "CHECKOUT": "2023-09-03",
        "ADULTS": "2",
        "CHILDREN": "0",
        "ROOMTYPE": "Double",
        "SPECIALREQUESTS": "None"
    },
    {
        "NICNUMBER": "0987654321",
        "FIRSTNAME": "Jane",
        "LASTNAME": "Doe",
        "CHECKIN": "2023-09-05",
        "CHECKOUT": "2023-09-10",
        "ADULTS": "2",
        "CHILDREN": "1",
        "ROOMTYPE": "Suite",
        "SPECIALREQUESTS": "Extra bed for child"
    },
    {
        "NICNUMBER": "5678901234",
        "FIRSTNAME": "David",
        "LASTNAME": "Williams",
        "CHECKIN": "2023-09-08",
        "CHECKOUT": "2023-09-12",
        "ADULTS": "2",
        "CHILDREN": "0",
        "ROOMTYPE": "Suite",
        "SPECIALREQUESTS": "Early check-in"
    }
]
```
