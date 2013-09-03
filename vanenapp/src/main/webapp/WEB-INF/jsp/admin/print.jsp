<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/main.jsp">
    <stripes:layout-component name="head">
        <title>Vanenapp Print</title>
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
        <h1>Uitdraaien Poules</h1>
        Hier kan u de ingedeelde poules uitdraaien.
        <div class="two-column">
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.PrintActionBean" event="printPoules"/>'>Alle poules</a><br/>
            
            </div>
            <div class="two-column">
            <c:if test="${not empty actionBean.invalidPoules}">
                De volgende poules zijn niet volledig doordat het aantal deelnemers niet klopt.
                <c:forEach items="${actionBean.invalidPoules}" var="p">
                    <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.PouleActionBean" event="edit"/>?poule=${p.id}'><c:out value="${p.name}"/></a><br/>
                </c:forEach>
            </c:if>
        </div>
    </stripes:layout-component>

</stripes:layout-render>
