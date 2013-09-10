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
    </head>
    <body>
        <script type="text/javascript">
            function createDate(dateString){
                var tokens = dateString.split("-");
            // m/d/y
                var s= tokens[1]+"/"+tokens[2]+"/"+tokens[0];
                return new Date(s);
            }
            var vanenDate = createDate("${actionBean.vanencompetition.date}");
        </script>
        <a href="#" onclick="javascript: window.print()">Print</a><br/>
        <h1><c:out value="${actionBean.vanencompetition}"/></h1>
        <c:forEach varStatus="stat" items="${actionBean.resultList}" var="l">
            <c:out value="${l}"/>
            <c:out value="${l.karateka}"/>
            <c:if test="${not empty l.karateka}">
                <script type="text/javascript">
                    var birthDate = createDate("${l.karateka.birthdate}");
                    var years = vanenDate.getFullYear() - birthDate.getFullYear();
                    // Reset birthday to the current year.
                    birthDate.setFullYear(vanenDate.getFullYear());
                    // If the user's birthday has not occurred yet this year, subtract 1.
                    if (vanenDate < birthDate){
                        years--;
                    }
                    document.write("("+years+")");
                </script>
            </c:if>
            <br/>
        </c:forEach>        
    </body>
</html>
