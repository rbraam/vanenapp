<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/main.jsp">
    <stripes:layout-component name="head">
        <title>Gebruiker beheer</title>
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
        <h1>Beheer Gebruikers</h1><br/>
        <div class="admin-list">
            <table>
                <tr>
                <th>Naam</th>
                <th>Gebruikersnaam</th>
                <th>Organisatie</th>
                <th>Rollen</th>
                <th></th>
                </tr>
                <c:forEach items="${actionBean.users}" var="u">
                    <tr>
                        <td><a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.UserActionBean" event="edit"/>?user=${u.id}'><c:out value="${u.name}"/></a></td>
                        <td><c:out value="${u.username}"/></td>
                        <td><c:out value="${u.organisation.name}"/></td>
                        <td><c:out value="${u.roles}"/></td>
                        <td><a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.UserActionBean" event="delete"/>?user=${u.id}'>Verwijder</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <stripes:form beanclass="com.roybraam.vanenapp.stripes.UserActionBean">
            <c:choose>
                <c:when test="${actionBean.context.eventName == 'edit'}">
                    <stripes:hidden name="user" value="${actionBean.user.id}"/>
                    <div class="form-container">
                        <table class="formtable">                    
                            <tr>
                                <td>Naam</td>
                                <td><stripes:text name="user.name"/></td>
                            </tr>                    
                            <tr>
                                <td>Inlog naam</td>
                                <td><stripes:text name="user.username"/></td>
                            </tr>                    
                            <tr>
                                <td>Wachtwoord</td>
                                <td><stripes:password name="password"/></td>
                            </tr>
                            <tr>
                                <td>Herhaal wachtwoord</td>
                                <td><stripes:password name="passwordRepeat"/></td>
                            </tr>
                            <tr>
                                <td>Organisatie</td>
                                <td>
                                    <stripes:select name="user.organisation">
                                        <stripes:option value="-1">Kies een organisatie</stripes:option>
                                        <stripes:options-collection collection="${actionBean.organisations}" value="id" label="name"/>
                                    </stripes:select>
                                </td>
                            </tr>
                            <tr>
                                <td>Rollen</td>
                                <td>
                                    <stripes:checkbox name="roles" value="SUPERADMIN"/>Super beheerder<br>
                                    <stripes:checkbox name="roles" value="ADMIN"/>Vanencompetitie beheerder<br>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="submitbuttons">
                        <stripes:submit name="save" value="Opslaan"/>
                        <stripes:submit name="cancel" value="Annuleren"/>
                        <c:if test="${not empty actionBean.user.id}">
                            <stripes:submit name="delete" value="Verwijder"/>
                        </c:if>
                    </div>
                </c:when>
                <c:otherwise>
                    <stripes:submit name="edit" value="Nieuwe gebruiker"/>
                </c:otherwise>
            </c:choose>
        </stripes:form>

    </stripes:layout-component>

</stripes:layout-render>
