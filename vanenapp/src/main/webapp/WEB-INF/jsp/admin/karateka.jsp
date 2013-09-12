<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/main.jsp">
    <stripes:layout-component name="head">
        <title>Karateka beheer</title>
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
        <h1>Beheer karateka</h1><br/>
        Hier kunnen karateka's worden toegevoegd en worden gewijzigd. Door te typen
        in het zoek venster wordt de lijst met karateka's verkleind. Door te klikken
        op een karateka wordt het formulier voor het wijzigen van een karateka geopend.
        
        <div id="karatekaListContainer">
                        
        </div>
        <iframe id="editKaratekaFrame" class="form-iframe" src="<stripes:url beanclass="com.roybraam.vanenapp.stripes.KaratekaActionBean" event="cancel"/>"></iframe>
        
        <script type="text/javascript">
            var editKaratekaUrl = '<stripes:url beanclass="com.roybraam.vanenapp.stripes.KaratekaActionBean" event="edit"/>';
            var listKaratekaUrl = '<stripes:url beanclass="com.roybraam.vanenapp.stripes.KaratekaActionBean" event="list"/>';
            var deleteKaratekaUrl = '<stripes:url beanclass="com.roybraam.vanenapp.stripes.KaratekaActionBean" event="delete"/>';
        </script>
        <script src="${contextPath}/js/KaratekaListController.js"></script>
        <script src="${contextPath}/js/karateka.js"></script>
    </stripes:layout-component>

</stripes:layout-render>