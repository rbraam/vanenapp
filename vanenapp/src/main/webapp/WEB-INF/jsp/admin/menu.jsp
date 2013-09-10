<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>
[admin menu]
<h2>Beheer</h2>
<ul class="menuUl">
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.VanencompetitionActionBean">Vanencompetitie</stripes:link></li>
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.KaratekaActionBean">Karateka</stripes:link></li>
</ul>
<h2>Organiseer Vanencompetitie</h2>
<ul class="menuUl">
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.ParticipantActionBean">Deelnemers selecteren</stripes:link></li>
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.OrganizeVanencompetitionActionBean">Selecteer Vanencompetitie</stripes:link></li>
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.ParticipantActionBean">Selecteer deelnemers</stripes:link></li>
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.PouleActionBean">Maak poule indeling</stripes:link></li>
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.PrintActionBean">Uitdraaien poules</stripes:link></li>
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.ListActionBean">Lijsten</stripes:link></li>
</ul>

<req:isUserInRole role="admin">
    <h2>Beheer VanenApp</h2>
    <ul class="menuUl">
        <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.OrganisationActionBean">Organisaties</stripes:link></li>
        <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.UserActionBean">Gebruikers</stripes:link></li>
    </ul>
</req:isuserinrole>
