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
        Klik links de karateka's aan die meedoen aan deze vanencompetitie. De karateka's die meedoen
        staan rechts in het vak.
        <stripes:form beanclass="com.roybraam.vanenapp.stripes.ParticipantActionBean" onsubmit="onSubmitForm()" onkeypress="return checkEnter(event)">
            <!--intput type="hidden" name="participants" id="participants" value=""/-->
            <stripes:hidden name="participants" id="participants"/>
            <stripes:hidden name="competitionType"/>
            <div id="karatekaListContainer" class="two-column">

            </div>
            <div id="participantListContainer" class="two-column">

            </div>
            <div>
                <stripes:submit name="save">Opslaan</stripes:submit>
            </div>
        </stripes:form>
        <script src="${contextPath}/js/KaratekaListController.js"></script>
        <script src="${contextPath}/js/participant.js"></script>
        <script type="text/javascript">
            var listKaratekaUrl = '<stripes:url beanclass="com.roybraam.vanenapp.stripes.KaratekaActionBean" event="list"/>';
            var participants = Ext.JSON.decode('${actionBean.participantsJson}');
        </script>
    </stripes:layout-component>

</stripes:layout-render>
