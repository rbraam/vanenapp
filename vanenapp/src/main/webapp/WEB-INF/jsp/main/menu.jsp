<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<ul class="menuUl">
    <li><stripes:link beanclass="com.roybraam.vanenapp.stripes.AdminIndexActionBean"><fmt:message key="main.menu.admin"/></stripes:link></li>
</ul>
<div class="logo-container">
    <image src="${contextPath}/resources/images/logo.jpg"/>
 </div>