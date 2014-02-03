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
        <div class="choose-vanencompetition">
            ${actionBean.vanencompetition}
            <stripes:link beanclass="com.roybraam.vanenapp.stripes.OrganizeVanencompetitionActionBean">Kies</stripes:link>
        </div>
        <h3>Hier kan u de ingedeelde poules uitdraaien.</h3><br/>
        <div class="two-column">
            <h2>Kata</h2>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.PrintActionBean" event="printPoules"/>?competitionType=KATA'>Alle kata poules</a><br/>
            <c:forEach items="${actionBean.validKataPoulesWithKyu}" var="b">
                <c:choose>
                    <c:when test="${b.value > 0}">
                        <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.PrintActionBean" event="printPoules"/>?belt=${b.key}&competitionType=KATA'> <c:out value="${b.key.description}"/> (<c:out value="${b.value}"/> poule(s))</a><br/>
                    </c:when>
                    <c:otherwise>
                        Er zijn geen poules voor <c:out value="${b.key.description}"/><br/>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <h2>Kumite</h2>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.PrintActionBean" event="printPoules"/>?competitionType=KUMITE'>Alle kumite poules</a><br/>
        </div>
        <div class="two-column">
            <c:if test="${not empty actionBean.invalidKataPoules}">
                <h2>Kata</h2>
                De volgende poules zijn niet volledig doordat het aantal deelnemers niet klopt. Deze poules kunnen niet uitgedraaid worden.
                <c:forEach items="${actionBean.invalidKataPoules}" var="p">
                    <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.PouleActionBean" event="edit"/>?poule=${p.id}'><c:out value="${p}"/></a><br/>
                </c:forEach>
            </c:if>
            <c:if test="${not empty actionBean.invalidKumitePoules}">
                <h2>Kumite</h2>
                De volgende poules zijn niet volledig doordat het aantal deelnemers niet klopt. Deze poules kunnen niet uitgedraaid worden.
                <c:forEach items="${actionBean.invalidKumitePoules}" var="p">
                    <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.PouleActionBean" event="edit"/>?poule=${p.id}'><c:out value="${p}"/></a><br/>
                </c:forEach>
            </c:if>
        </div>
    </stripes:layout-component>

</stripes:layout-render>
