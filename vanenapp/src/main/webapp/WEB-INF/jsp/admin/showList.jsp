<%-- 
    Document   : showList
    Created on : Sep 9, 2013, 7:15:20 AM
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/main.jsp">
    <stripes:layout-component name="head">
        <title>Lijsten</title>
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
        <h1>Lijsten vanencompetitie</h1>
        <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsWithoutPoule"/>'>Niet ingedeelde Karateka's</a><br/>
        <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipants"/>'>Deelnemende Karateka's</a><br/>
        <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByBelt"/>'>Deelnemende Karateka's gesorteerd op Band</a><br/>
        <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByAge"/>'>Deelnemende Karateka's gesorteerd op Leeftijd</a><br/>
        <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByBeltAge"/>'>Deelnemende Karateka's gesorteerd op Band en Leeftijd</a><br/>
        <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByWeight"/>'>Deelnemende Karateka's gesorteerd op Gewicht</a><br/>
        <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByAgeWeight"/>'>Deelnemende Karateka's gesorteerd op Leeftijd en Gewicht</a><br/>
        <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listPoules"/>'>Overzicht ingedeelde poules</a><br/>
    </stripes:layout-component>

</stripes:layout-render>