<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/main.jsp">
    <stripes:layout-component name="head">
        <title>Vanenapp Print</title>
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
        <h1>Uitdraaien certificaten</h1>
        <h2>Vanencompetitie</h2>
        <c:out value="${actionBean.vanencompetition}"/> <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.PrintCertificateActionBean" event="print"/>?vanencompetition=${actionBean.vanencompetition.id}'> Print </a>
        <br/>(alle deelnemende karateka's worden uitgeprint, ook de niet in een poule ingedeelde deelnemers)
        <br/>
        <h2>Poules</h2>
        <div>
            Kies hieronder een poule om de certificaten van de poule deelnemers uit te printen of kies een afzonderlijke deelnemer.
            <c:forEach items="${actionBean.poules}" var="poule">
                <div class='print-certificate-poule'>
                    <div class="print-certificate-poule-title"> Poule: <c:out value="${poule}"/> <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.PrintCertificateActionBean" event="print"/>?poules=${poule.id}'> Print </a></div>
                    <c:forEach items="${poule.participants}" var="p">
                        <div class='print-certificate-participant'><c:out value="${p}"/> <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.PrintCertificateActionBean" event="print"/>?participants=${p.id}'> Print </a></div>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>
        
        <h2>Niet ingedeelde deelnemers</h2>
        <div class='print-certificate-participants'>
            <c:forEach items="${actionBean.participants}" var="p">
                <div class='print-certificate-participant'>
                    <c:out value="${p}"/> <a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.PrintCertificateActionBean" event="print"/>?participants=${p.id}'> Print </a>
                </div>
    
            </c:forEach>
        </div>
    </stripes:layout-component>

</stripes:layout-render>
