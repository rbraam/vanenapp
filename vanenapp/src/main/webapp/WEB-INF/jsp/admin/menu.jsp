<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>
<h2>Beheer</h2>
<ul class="menuUl">
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.VanencompetitionActionBean"><fmt:message key="admin.menu.vanencompetition"/></stripes:link></li>
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.KaratekaActionBean"><fmt:message key="admin.menu.karateka"/></stripes:link></li>
</ul>
<h2>Organiseer Vanencompetitie</h2>
<ul class="menuUl">
    <li><a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ParticipantActionBean" event="view"/>?competitionType=KATA'><fmt:message key="admin.menu.kataparticipant"/></a></li>
    <li><a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ParticipantActionBean" event="view"/>?competitionType=KUMITE'><fmt:message key="admin.menu.kumiteparticipant"/></a></li>
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.PouleActionBean"><fmt:message key="admin.menu.poule"/></stripes:link></li>
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.PrintActionBean"><fmt:message key="admin.menu.printpoule"/></stripes:link></li>    
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.PrintCertificateActionBean"><fmt:message key="admin.menu.printcertificate"/></stripes:link></li>
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.ListActionBean"><fmt:message key="admin.menu.list"/></stripes:link></li>
</ul>
<h2>Beheer punten</h2>
<ul class="menuUl">
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.PointsActionBean"><fmt:message key="admin.menu.points"/></stripes:link></li>
</ul>
<% if (request.isUserInRole("superadmin")) { %>
    <h2>Beheer VanenApp</h2>
    <ul class="menuUl">
        <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.OrganisationActionBean"><fmt:message key="admin.menu.organisation"/></stripes:link></li>
        <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.UserActionBean"><fmt:message key="admin.menu.user"/></stripes:link></li>
    </ul>
<% } %>
<br/><br/><div class="menuUl">
    <a href="${contextPath}/logout.jsp">Log uit</a>
</div>
<br/>
<div class="logo-container">
    <a target="_blank" alt="logo" border="0" href="http://www.kbn.nl"><image src="${contextPath}/resources/images/logo.jpg"/></a>
 </div>
 