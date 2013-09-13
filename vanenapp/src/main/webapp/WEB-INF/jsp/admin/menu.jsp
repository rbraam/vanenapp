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
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.OrganizeVanencompetitionActionBean"><fmt:message key="admin.menu.organizevanencompetition"/></stripes:link></li>
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.ParticipantActionBean"><fmt:message key="admin.menu.participant"/></stripes:link></li>
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.PouleActionBean"><fmt:message key="admin.menu.poule"/></stripes:link></li>
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.PrintActionBean"><fmt:message key="admin.menu.printpoule"/></stripes:link></li>
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.ListActionBean"><fmt:message key="admin.menu.list"/></stripes:link></li>
</ul>

<req:isUserInRole role="admin">
    <h2>Beheer VanenApp</h2>
    <ul class="menuUl">
        <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.OrganisationActionBean"><fmt:message key="admin.menu.organisation"/></stripes:link></li>
        <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.UserActionBean"><fmt:message key="admin.menu.user"/></stripes:link></li>
    </ul>
</req:isuserinrole>
