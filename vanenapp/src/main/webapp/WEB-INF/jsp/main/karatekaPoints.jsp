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
       
        <h2>Punten</h2>
        <c:if test="${not empty actionBean.participants}">
            <c:out value="${actionBean.participants[0].karateka.basePointsKata}"/>
            <c:out value="${actionBean.participants[0].karateka.basePointsKumite}"/>
            <c:forEach items="${actionBean.participants}" var="p">
                <c:out value="${p.points}"/>
            </c:forEach>
        </c:if>
            
    </stripes:layout-component>

</stripes:layout-render>
