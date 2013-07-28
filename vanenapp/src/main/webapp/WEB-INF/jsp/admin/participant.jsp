<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/main.jsp">
    <stripes:layout-component name="head">
        <title>Deelnemers vanencompetitie</title>
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
        <h1>Deelnemers vanencompetitie</h1><br/>
        <div id="karatekaListContainer" class="two-column">
                        
        </div>
        <div id="participantListContainer" class="two-column">
                        
        </div>
        <script src="${contextPath}/js/KaratekaListController.js"></script>
        <script src="${contextPath}/js/participant.js"></script>
        <script type="text/javascript">
            var listKaratekaUrl = '<stripes:url beanclass="com.roybraam.vanenapp.stripes.KaratekaActionBean" event="list"/>';
            var participants = Ext.JSON.decode("${actionBean.participantsJson}");
        </script>
    </stripes:layout-component>

</stripes:layout-render>
