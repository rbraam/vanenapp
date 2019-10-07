
<%--
    Document   : kbnList
    Created on : Oct 7, 2019, 10:14:52 PM
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@ page isELIgnored="false"%>

<html>
    <head> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Poules</title>
        <link href="${contextPath}/resources/css/printpoule.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="${contextPath}/extjs/ext-all.js"></script>
        <script type="text/javascript" src="${contextPath}/extjs/locale/ext-lang-nl.js"></script>
    </head>
    <body>
        <a href="#" onclick="javascript: window.print()">Print</a><br/>
        <h1><c:out value="${actionBean.vanencompetition}"/></h1>
        <c:forEach varStatus="stat" items="${actionBean.resultList}" var="l">
            <c:if test="${l['class'] =='class com.roybraam.vanenapp.entity.Participant'}">
                <c:out value="${l.karateka.surname}"/>,
                <c:out value="${l.karateka.name}"/>
                <c:out value="${l.karateka.insert}"/>
                <b><c:out value="${l.karateka.memberNumber}"/></b>
                (<c:out value="${l.karateka.club}"/>)
            </c:if>
            <br/>
        </c:forEach>        
    </body>
</html>
