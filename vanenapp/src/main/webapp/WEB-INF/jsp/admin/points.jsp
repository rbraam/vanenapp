<%-- 
    Document   : points
    Created on : Oct 22, 2013, 7:20:31 AM
    Author     : Roy Braam
--%>
<%-- 
    Document   : selectVanencompetition
    Created on : Oct 22, 2013, 7:21:23 AM
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
        <h1>Vul de punten in van de afgeronde Vanencompetitie</h1>
        <div>
            <stripes:form beanclass="com.roybraam.vanenapp.stripes.PointsActionBean">
                <stripes:hidden name="vanencompetition" value="$actionBean.vanencompetition.id"/>
                <c:forEach items="${actionBean.poules}" var="poule">
                    <h3><c:out value="${poule}"/></h3>
                    <div class="points_poule">
                        <table>

                            <c:forEach items="${poule.participants}" var="p">
                                <tr>
                                    <td><c:out value="${p.karateka}"/></td>
                                    <td>
                                        <stripes:text name="points[${p.id}]" size="1"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </c:forEach>
                <div class="submitbuttons">
                    <stripes:submit name="save" value="Opslaan"/>
                    <stripes:submit name="cancel" value="Annuleren"/>
                </div>
            </stripes:form>
        </div>
        
    </stripes:layout-component>
</stripes:layout-render>
