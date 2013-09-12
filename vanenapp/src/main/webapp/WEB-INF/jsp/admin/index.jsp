<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/main.jsp">
    <stripes:layout-component name="head">
        <title>Vanenapp beheer</title>
    </stripes:layout-component>

    <stripes:layout-component name="header">
        <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    </stripes:layout-component>
    <stripes:layout-component name="menu">
        <jsp:include page="/WEB-INF/jsp/admin/menu.jsp"/>
    </stripes:layout-component>

    <stripes:layout-component name="body">
        <h1> VanenApp beheer pagina's</h1>
        <p>Welkom op het beheer gedeelte van de VanenApp.
        Vanuit deze pagina kan u starten met het op papier organiseren van een vanencompetitie.</p>
        <p>In het linker menu kan u kiezen uit een aantal opties:
        <table>
            <tr>
                <td> <fmt:message key="admin.menu.vanencompetition"/> </td>
                <td></td>
            </tr>
            <tr>
                <td><fmt:message key="admin.menu.karateka"/></td>
                <td>
                    Met deze optie kunnen nieuwe karateka's worden aangemaakt en 
                    er kunnen karateka's worden gewijzigd.
                    Voordat er begonnen kan worden met het indelen van een 
                    Vanencompetitie moet er voor gezorgd worden
                    dat de Kyu-graden van de deelnemende karateka's kloppen in het systeem. 
                </td>
            </tr>
            <tr>
                <td><fmt:message key="admin.mdenu.organizevanencompetition"/></td>
                <td>
                    Met deze optie kan er een nieuwe Vanencompetitie worden aangemaakt en 
                    kan een reeds aangemaakte Vanencompetitie worden gewijzigd.
                </td>
            </tr>
            <tr>
                <td><fmt:message key="admin.menu.participant"/></td>
                <td>
                    Hier kunnen de karateka's die deelnemen aan de geselecteerde 
                    vanencompetitie worden geselecteerd.
                    De selectie wordt gedaan op de ingevoerde karateka's
                </td>
            </tr>
            <tr>
                <td><fmt:message key="admin.menu.poule"/></td>
                <td>
                    In dit gedeelte van de applicatie kunnen de poules worden 
                    ingedeeld op basis van de eerder geselecteerde
                    deelnemers.
                </td>
            </tr>
            <tr>
                <td><fmt:message key="admin.menu.printpoule"/></td>
                <td>
                    Vanuit dit scherm kunnen de ingedeelde poules worden uitgeprint.
                </td>
            </tr>
            <tr>
                <td><fmt:message key="admin.menu.list"/></td>
                <td>
                    Met deze menu optie kunnen er lijsten worden opgevraagd die 
                    kunnen helpen bij het indelen van poules voor een vanencompetitie.
                </td>
            </tr>
        </table>
        </p>
    </stripes:layout-component>

</stripes:layout-render>
