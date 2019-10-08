
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
        <style>
            thead {
                font-weight: bold;
            }
            table, th, td {
                border: 0;
            }
        </style>
    </head>
    <body>
        <a href="#" onclick="javascript: window.print()">Print</a><br/>
        <h1><c:out value="${actionBean.vanencompetition}"/></h1>
        <table>
            <thead>
                <td>Naam</td>
                <td>KBN lidmaatschap</td>
                <td>Sportschool</td>
            </thead>
            <tbody>
        <c:forEach varStatus="stat" items="${actionBean.resultList}" var="l">
            <tr>
            <c:if test="${l['class'] =='class com.roybraam.vanenapp.entity.Participant'}">
                <td><c:out value="${l.karateka.surname}"/>, <c:out value="${l.karateka.name}"/> <c:out value="${l.karateka.insert}"/></td>
                <td><c:out value="${l.karateka.memberNumber}"/></td>
                <td><c:out value="${l.karateka.club}"/></td>
            </c:if>
            </tr>
        </c:forEach>
            </tbody>
        </table>
    </body>
</html>
