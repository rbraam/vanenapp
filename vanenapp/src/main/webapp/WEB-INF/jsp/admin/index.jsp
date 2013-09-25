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
        <p>
            Welkom op het beheer gedeelte van de VanenApp.
            Vanuit deze pagina kan u starten met het op papier organiseren van een vanencompetitie.
        </p>
        <p>
            In het linker menu kan u kiezen uit een aantal opties:
        </p>
        <table>
            <tr>
                <td class="table-key"><fmt:message key="admin.menu.vanencompetition"/></td>
                <td>
                    Hier kan u een nieuwe vanencompetitie aanmaken en/of een 
                    bestaande competitie wijzigen.
                </td>
            </tr>
            <tr>
                <td class="table-key"><fmt:message key="admin.menu.karateka"/></td>
                <td>
                    Met deze optie kunnen nieuwe karateka's worden aangemaakt en 
                    er kunnen karateka's worden gewijzigd.
                    Voordat er begonnen kan worden met het indelen van een 
                    Vanencompetitie moet er voor gezorgd worden
                    dat de Kyu-graden van de deelnemende karateka's kloppen in het systeem. 
                </td>
            </tr>
            <tr>
                <td class="table-key"><fmt:message key="admin.menu.organizevanencompetition"/></td>
                <td>
                    Met deze optie kan er een nieuwe Vanencompetitie worden aangemaakt en 
                    kan een reeds aangemaakte Vanencompetitie worden gewijzigd.
                </td>
            </tr>
            <tr>
                <td class="table-key"><fmt:message key="admin.menu.kataparticipant"/> / <fmt:message key="admin.menu.kumiteparticipant"/></td>
                <td>
                    Hier kunnen de karateka's die deelnemen aan de geselecteerde 
                    vanencompetitie worden geselecteerd.
                    De selectie wordt gedaan op de ingevoerde karateka's
                </td>
            </tr>
            <tr>
                <td class="table-key"><fmt:message key="admin.menu.poule"/></td>
                <td>
                    In dit gedeelte van de applicatie kunnen de poules worden 
                    ingedeeld op basis van de eerder geselecteerde
                    deelnemers.
                </td>
            </tr>
            <tr>
                <td class="table-key"><fmt:message key="admin.menu.printpoule"/></td>
                <td>
                    Vanuit dit scherm kunnen de ingedeelde poules worden uitgeprint.
                </td>
            </tr>
            <tr>
                <td class="table-key"><fmt:message key="admin.menu.list"/></td>
                <td>
                    Met deze menu optie kunnen er lijsten worden opgevraagd die 
                    kunnen helpen bij het indelen van poules voor een vanencompetitie.
                </td>
            </tr>
            <req:isUserInRole role="admin">
                <tr>
                    <td class="table-key" colspan="2">
                        De volgende opties zijn alleen toegankelijk voor beheerders
                        van de VanenApp
                    </td>
                </tr>
                <tr>
                    <td class="table-key">
                        <fmt:message key="admin.menu.organisation"/>
                    </td>
                    <td>
                        Hier kan een nieuwe organisaties worden aangemaakt. Een organisatie
                        is een entiteit dat een vanencompetitie organiseerd en 
                        gebruik wil maken van de VanenApp. Een organisatie kan 
                        meerdere gebruikers van de VanenApp bevatten.
                    </td>
                </tr>
                <tr>
                    <td class="table-key">
                        <fmt:message key="admin.menu.user"/>
                    </td>
                    <td>
                        Hier kunnen nieuwe gebruikers worden aangemaakt. Een gebruiker
                        is gekoppeld aan een organisatie. De gebruiker kan de door
                        die georganisatie georganiseerde vanencompetities wijzigen.
                    </td>
                </tr>
            </req:isuserinrole>>
        </table>
        </p>
    </stripes:layout-component>

</stripes:layout-render>
