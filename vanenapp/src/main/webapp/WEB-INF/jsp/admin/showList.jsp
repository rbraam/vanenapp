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
        <div class="choose-vanencompetition">
            ${actionBean.vanencompetition}
            <stripes:link beanclass="com.roybraam.vanenapp.stripes.OrganizeVanencompetitionActionBean">Kies</stripes:link>
        </div>
        <div>
            <h2>Algemeen</h2>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsWithoutPoule"/>'>Niet ingedeelde Karateka's</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipants"/>'>Deelnemende Karateka's</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByBelt"/>'>Deelnemende Karateka's gesorteerd op Band</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByAge"/>'>Deelnemende Karateka's gesorteerd op Leeftijd</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByBeltAge"/>'>Deelnemende Karateka's gesorteerd op Band en Leeftijd</a><br/>  
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByAgeBelt"/>'>Deelnemende Karateka's gesorteerd op Leeftijd en Band</a><br/>
        </div>
        <div>
            <h2>Kata</h2>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsWithoutPoule"/>?competitionType=Kata'>Niet ingedeelde Karateka's</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipants"/>?competitionType=Kata'>Deelnemende Karateka's</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByBelt"/>?competitionType=Kata'>Deelnemende Karateka's gesorteerd op Band</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByAge"/>?competitionType=Kata'>Deelnemende Karateka's gesorteerd op Leeftijd</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByBeltAge"/>?competitionType=Kata'>Deelnemende Karateka's gesorteerd op Band en Leeftijd</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByAgeBelt"/>?competitionType=Kata'>Deelnemende Karateka's gesorteerd op Leeftijd en Band</a><br/>
            <h3>Gesorteerd op geslacht</h3>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsWithoutPoule"/>?competitionType=Kata&orderBy=gender'>Niet ingedeelde Karateka's</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipants"/>?competitionType=Kata&orderBy=gender'>Deelnemende Karateka's</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByBelt"/>?competitionType=Kata&orderBy=gender'>Deelnemende Karateka's gesorteerd op Band</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByAge"/>?competitionType=Kata&orderBy=gender'>Deelnemende Karateka's gesorteerd op Leeftijd</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByBeltAge"/>?competitionType=Kata&orderBy=gender'>Deelnemende Karateka's gesorteerd op Band en Leeftijd</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByAgeBelt"/>?competitionType=Kata&orderBy=gender'>Deelnemende Karateka's gesorteerd op Leeftijd en Band</a><br/>
        </div>
        <div>
            <h2>Kumite</h2>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsWithoutPoule"/>?competitionType=Kumite'>Niet ingedeelde Karateka's</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipants"/>?competitionType=Kumite'>Deelnemende Karateka's</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByBelt"/>?competitionType=Kumite'>Deelnemende Karateka's gesorteerd op Band</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByAge"/>?competitionType=Kumite'>Deelnemende Karateka's gesorteerd op Leeftijd</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByBeltAge"/>?competitionType=Kumite'>Deelnemende Karateka's gesorteerd op Band en Leeftijd</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByAgeBelt"/>?competitionType=Kumite'>Deelnemende Karateka's gesorteerd op Leeftijd en Band</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByWeight"/>?competitionType=Kumite'>Deelnemende Karateka's gesorteerd op Gewicht</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByAgeWeight"/>?competitionType=Kumite'>Deelnemende Karateka's gesorteerd op Leeftijd en Gewicht</a><br/>
            
            <h3>Gesorteerd op geslacht</h3>
            
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsWithoutPoule"/>?competitionType=Kumite&orderBy=gender'>Niet ingedeelde Karateka's</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipants"/>?competitionType=Kumite&orderBy=gender'>Deelnemende Karateka's</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByBelt"/>?competitionType=Kumite&orderBy=gender'>Deelnemende Karateka's gesorteerd op Band</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByAge"/>?competitionType=Kumite&orderBy=gender'>Deelnemende Karateka's gesorteerd op Leeftijd</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByBeltAge"/>?competitionType=Kumite&orderBy=gender'>Deelnemende Karateka's gesorteerd op Band en Leeftijd</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByAgeBelt"/>?competitionType=Kumite&orderBy=gender'>Deelnemende Karateka's gesorteerd op Leeftijd en Band</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByWeight"/>?competitionType=Kumite&orderBy=gender'>Deelnemende Karateka's gesorteerd op Gewicht</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByAgeWeight"/>?competitionType=Kumite&orderBy=gender'>Deelnemende Karateka's gesorteerd op Leeftijd en Gewicht</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByWeight"/>?competitionType=Kumite&orderBy=gender&ageMin=6&ageMax=10'>Deelnemende Karateka's gesorteerd op Gewicht 6-10 jaar</a><br/>
            <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listParticipantsSortByWeight"/>?competitionType=Kumite&orderBy=gender&ageMin=11&ageMax=17'>Deelnemende Karateka's gesorteerd op Gewicht 11-17</a><br/>
        </div>
        <h2>Poules</h2>
        <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.ListActionBean" event="listPoules"/>'>Overzicht ingedeelde poules</a><br/>
    </stripes:layout-component>

</stripes:layout-render>