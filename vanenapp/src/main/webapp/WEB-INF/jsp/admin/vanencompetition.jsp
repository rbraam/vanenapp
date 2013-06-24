<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/main.jsp">
    <stripes:layout-component name="head">
        <title>Vanencompetitie beheer</title>
    </stripes:layout-component>

    <stripes:layout-component name="header">
        <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    </stripes:layout-component>
    <stripes:layout-component name="menu">
        <jsp:include page="/WEB-INF/jsp/admin/menu.jsp"/>
    </stripes:layout-component>

    <stripes:layout-component name="body">
       
            <h1>Beheer Vanencompetitie</h1><br/>
         
            
    </stripes:layout-component>

</stripes:layout-render>
