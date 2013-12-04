VanenApp
========

Applicatie for organizing Vanencompetitions.

Add participantpoints.baseUrl and participantpoints.salt in context.xml or other xml at:
CATALINA_BASE/conf/
For example:
```xml
<Context>
  <Parameter name="participantpoints.baseUrl" override="false" value="http://vanen.kbn.nl"/>
  <Parameter name="participantpoints.salt" override="false" value="2134ef3tg48igk586nhgkf9re4tgngfgkeri4ng"/>
</Context>
```
