VanenApp
========

Applicatie for organizing Vanencompetitions.

Set JDBC resource in webcontainer configuration. for example:
<Resource
	name="jdbc/vanenapp"
	auth="Container"
	type="javax.sql.DataSource"
	username="vanenapp"
	password="vanenapp"
	driverClassName="org.postgresql.Driver"
	url="jdbc:postgresql://localhost:5432/vanenapp"
	maxActive="40"
	validationQuery="select 1"
	timeBetweenEvictionRunsMillis="30000"
	minEvictableIdleTimeMillis="5000"
/>

Add participantpoints.baseUrl and participantpoints.salt in context.xml or other xml at:
CATALINA_BASE/conf/
For example:
```xml
<Context>
  <Parameter name="participantpoints.baseUrl" override="false" value="http://vanen.kbn.nl"/>
  <Parameter name="participantpoints.salt" override="false" value="2134ef3tg48igk586nhgkf9re4tgngfgkeri4ng"/>
</Context>
```
