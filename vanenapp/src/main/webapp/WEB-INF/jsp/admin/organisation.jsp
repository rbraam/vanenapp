<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/main.jsp">
    <stripes:layout-component name="head">
        <title>Organisatie beheer</title>
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
        <h1>Beheer Organisaties</h1><br/>
        <div class="admin-list">
            <table>
                <tr>
                <th>Naam</th>
                <th></th>
                </tr>
                <c:forEach items="${actionBean.organisations}" var="o">
                    <tr>
                        <td><a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.OrganisationActionBean" event="edit"/>?organisation=${o.id}'><c:out value="${o.name}"/></a></td>
                        <td><a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.OrganisationActionBean" event="delete"/>?organisation=${o.id}'>Verwijder</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <stripes:form beanclass="com.roybraam.vanenapp.stripes.OrganisationActionBean">
            <c:choose>
                <c:when test="${actionBean.context.eventName == 'edit'}">
                    <stripes:hidden name="organisation" value="${actionBean.organisation.id}"/>
                    <div class="form-container">
                        <table class="formtable">                    
                            <tr>
                                <td>Naam</td>
                                <td><stripes:text name="organisation.name"/></td>
                            </tr>
                        </table>
                    </div>
                    <div class="submitbuttons">
                        <stripes:submit name="save" value="Opslaan"/>
                        <stripes:submit name="cancel" value="Annuleren"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <stripes:submit name="edit" value="Nieuwe organisatie"/>
                </c:otherwise>
            </c:choose>
        </stripes:form>

    </stripes:layout-component>

</stripes:layout-render>
