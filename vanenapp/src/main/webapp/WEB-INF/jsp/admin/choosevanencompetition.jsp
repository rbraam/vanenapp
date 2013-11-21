<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/main.jsp">
    <stripes:layout-component name="head">
        <title>Kies vanencompetitie</title>
    </stripes:layout-component>

    <stripes:layout-component name="header">
        <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    </stripes:layout-component>
    <stripes:layout-component name="menu">
        <jsp:include page="/WEB-INF/jsp/admin/menu.jsp"/>
    </stripes:layout-component>

    <stripes:layout-component name="body">
        <p>
            <stripes:errors/>
            <stripes:messages/>
        </p>
        <h1>Kies vanencompetitie</h1><br/>
        <div class="admin-list">
            <table>
                <tr>
                    <th>Organisatie</th>
                    <th>Locatie</th>
                    <th>Datum</th>
                    <th></th>
                </tr>
                <c:forEach items="${actionBean.vanencompetitions}" var="v">
                    <tr>
                        <td><c:out value="${v.location}"/></td>
                        <td><fmt:formatDate value="${v.date}" pattern="dd-MM-yyyy"/></td>
                        <td><c:out value="${v.organisation.name}"/></td>
                        <c:choose>
                            <c:when test="${not empty actionBean.forward}">
                                <td><a href='<stripes:url beanclass="${actionBean.forward}" event="${actionBean.forwardEvent}"/>?vanencompetition=${v.id}&${actionBean.forwardParam}'>Selecteer</a></td>
                            </c:when>
                            <c:otherwise>
                                <td><a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ParticipantActionBean"/>?competitionType=KATA&vanencompetition=${v.id}'>Selecteer</a></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </stripes:layout-component>

</stripes:layout-render>
