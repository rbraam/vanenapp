<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>
[admin menu]
<h2>Beheer</h2>
<ul class="menuUl">
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.VanencompetitionActionBean">Vanencompetitie</stripes:link></li>
    
</ul>
<req:isUserInRole role="admin">
    <h2>Beheer applicatie</h2>
    <ul class="menuUl">
        <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.OrganisationActionBean">Organisaties</stripes:link></li>
        <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.UserActionBean">Gebruikers</stripes:link></li>
    </ul>
</req:isuserinrole>