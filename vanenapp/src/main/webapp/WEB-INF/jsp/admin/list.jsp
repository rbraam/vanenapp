<%-- 
    Document   : list
    Created on : Sep 9, 2013, 7:14:52 AM
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
        <script type="text/javascript" src="${contextPath}/js/AgeCalculator.js"></script>
    </head>
    <body>
        <script type="text/javascript">
            var ageCalc = new AgeCalculator("${actionBean.vanencompetition.date}");
        </script>
        <a href="#" onclick="javascript: window.print()">Print</a><br/>
        <h1><c:out value="${actionBean.vanencompetition}"/></h1>
        <c:forEach varStatus="stat" items="${actionBean.resultList}" var="l">
            
            <c:out value="${l}"/>
            <c:if test="${l['class'] =='class com.roybraam.vanenapp.entity.Participant'}">
                <script type="text/javascript">
                    var years=ageCalc.getAge("${l.karateka.birthdate}");
                    document.write("("+years+")");
                </script>
            </c:if>
            <br/>
        </c:forEach>        
    </body>
</html>
