<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/main.jsp">
    <stripes:layout-component name="head">
        <title>Vanenapp</title>
    </stripes:layout-component>

    <stripes:layout-component name="header">
        <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    </stripes:layout-component>
    <stripes:layout-component name="menu">
        <jsp:include page="/WEB-INF/jsp/main/menu.jsp"/>
    </stripes:layout-component>

    <stripes:layout-component name="body">
       
        <h2>Welkom bij de Vanenapp</h2>
            
            De VanenApp is ontwikkeld voor de KBN Vanencompetitie. 
            Op <a href="https://github.com/rbraam/vanenapp">GitHub</a> kan u de ontwikkelingen volgen
            rondom dit software pakket.
         
            
    </stripes:layout-component>

</stripes:layout-render>
