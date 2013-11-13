<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>


<% request.getSession().invalidate(); %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Uitgelogd</title>
    </head>
    <body>
        <h1>Uitgelogd</h1>
        <b>U bent uitgelogd. <stripes:link beanclass="com.roybraam.vanenapp.stripes.AdminIndexActionBean">Opnieuw inloggen</stripes:link></b>
    </body>
</html>
