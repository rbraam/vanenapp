<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/main.jsp">
    <stripes:layout-component name="head">
        <title>Vanencompetitie beheer</title>
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
        <h1>Beheer Vanencompetitie</h1><br/>
        <div class="admin-list">
            <table>
                <tr>
                    <th>Locatie</th>
                    <th>Datum</th>
                    <th>Organisatie</th>
                    <th></th>
                </tr>
                <c:forEach items="${actionBean.vanencompetitions}" var="v">
                    <tr>
                        <td><a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.VanencompetitionActionBean" event="edit"/>?vanencompetition=${v.id}'><c:out value="${v.location}"/></a></td>
                        <td><fmt:formatDate value="${v.date}" pattern="dd-MM-yyyy"/></td>
                        <td><c:out value="${v.organisation.name}"/></td>
                        <td><a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.VanencompetitionActionBean" event="delete"/>?vanencompetition=${v.id}'>Verwijder</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <stripes:form beanclass="com.roybraam.vanenapp.stripes.VanencompetitionActionBean">
            <c:choose>
                <c:when test="${actionBean.context.eventName == 'edit'}">
                    <stripes:hidden name="vanencompetition" value="${actionBean.vanencompetition.id}"/>
                    <div class="form-container">
                        <table class="formtable">                    
                            <tr>
                                <td>Locatie</td>
                                <td><stripes:text name="vanencompetition.location"/></td>
                            </tr>
                            <tr>
                                <td>Datum</td>
                                <td id="dateTd"></td>
                            </tr>
                            <tr>
                                <td>Einde inschrijven</td>
                                <td id="endDateTd"></td>
                            </tr>
                            <tr>
                                <td>Organisatie</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fn:length(actionBean.organisations)>1}">
                                            <stripes:select name="vanencompetition.organisation">
                                                <stripes:options-collection collection="${actionBean.organisations}" value="id" label="name"/>
                                            </stripes:select>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${actionBean.vanencompetition.organisation.name}"/>
                                            <stripes:hidden name="vanencompetition.organisation" value="${actionBean.vanencompetition.organisation.id}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="submitbuttons">
                        <stripes:submit name="save" value="Opslaan"/>
                        <stripes:submit name="cancel" value="Annuleren"/>
                    </div>
                    <script type="text/javascript">
                        var date = new Date();
                        var endDate = new Date();
                        <c:if test="${not empty actionBean.vanencompetition.date}">
                            date = '<fmt:formatDate value="${actionBean.vanencompetition.date}" pattern="dd-MM-yyyy"/>';
                        </c:if>
                        <c:if test="${not empty actionBean.vanencompetition.subscriptionEnd}">
                            endDate = '<fmt:formatDate value="${actionBean.vanencompetition.subscriptionEnd}" pattern="dd-MM-yyyy"/>';
                        </c:if>
                        Ext.onReady(function() {
                            Ext.create('Ext.form.field.Date', {
                                renderTo: 'dateTd',
                                value: date,
                                minValue: new Date(),
                                name: "vanencompetition.date"
                            });
                        
                            var date2 =  Ext.create('Ext.form.field.Date', {
                                renderTo: 'endDateTd',
                                value: endDate,
                                minValue: new Date(),
                                name: "vanencompetition.subscriptionEnd"
                            });
                        });
                    </script>
                </c:when>
                <c:otherwise>
                    <stripes:submit name="edit" value="Nieuwe vanencompetitie"/>
                </c:otherwise>
            </c:choose>
        </stripes:form>

    </stripes:layout-component>

</stripes:layout-render>
