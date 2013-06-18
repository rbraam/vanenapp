<%-- 
    Document   : Vanencompetition
    Created on : Jun 18, 2013, 5:13:59 PM
    Author     : Roy Braam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vanencompetition</title>
    </head>
    <body>
        <h1>Vanencompetitie</h1>
        <form:form method="post" commandName="vanencompetition" action="vanencompetition">
            Locatie: <form:input path="location"/>
            <input type="submit" value="Opslaan"/>
        </form:form>
    </body>
</html>
