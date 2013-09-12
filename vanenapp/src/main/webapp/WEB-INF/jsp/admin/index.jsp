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
                <td></td>
                <td></td>
            </tr>
        </table>
        </p>
            
    </stripes:layout-component>

</stripes:layout-render>
