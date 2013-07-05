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
        <div id="filterContainer">
            <table>
                <tr>
                    <td>Zoek:</td>
                    <td><div id="filterTextBox"></div></td>
                </tr>
            </table> 
        </div>
        <div id="karatekaList">
            
        </div>
        <iframe id="editKaratekaFrame" class="form-iframe" src="<stripes:url beanclass="com.roybraam.vanenapp.stripes.KaratekaActionBean" event="cancel"/>"></iframe>
        
        <script type="text/javascript">
            var editKaratekaUrl = '<stripes:url beanclass="com.roybraam.vanenapp.stripes.KaratekaActionBean" event="edit"/>';
            var listKaratekaUrl = '<stripes:url beanclass="com.roybraam.vanenapp.stripes.KaratekaActionBean" event="list"/>';
        </script>
        <script src="${contextPath}/js/KaratekaListController.js"></script>
        <script src="${contextPath}/js/karateka.js"></script>
    </stripes:layout-component>

</stripes:layout-render>