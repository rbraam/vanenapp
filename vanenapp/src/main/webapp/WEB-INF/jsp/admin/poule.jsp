<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/main.jsp">
    <stripes:layout-component name="head">
        <title>Poules vanencompetitie</title>
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
        <h1>Poules vanencompetitie</h1><br/>
        <div class="admin-list">
            <table>
                <tr>
                    <th>Naam</th>
                    <th>Vanaf Kyu</th>                
                    <th>Tot Kyu</th>                
                    <th>Van leeftijd</th>                
                    <th>Tot leeftijd</th>
                    <th>Type</th>
                    <th></th>
                </tr>
                <c:forEach items="${actionBean.poules}" var="p">
                    <tr>
                        <td><a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.PouleActionBean" event="edit"/>?poule=${p.id}'><c:out value="${p.name}"/></a></td>
                        <td><c:out value="${p.startKyu}"/></td>
                        <td><c:out value="${p.endKyu}"/></td>
                        <td><c:out value="${p.startAge}"/></td>
                        <td><c:out value="${p.endAge}"/></td>
                        <td><c:out value="${p.type}"/></td>
                        <td><a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.PouleActionBean" event="delete"/>?poule=${p.id}'>Verwijder</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="participant-left-col">
            <stripes:form beanclass="com.roybraam.vanenapp.stripes.PouleActionBean">
                <c:choose>
                    <c:when test="${actionBean.context.eventName == 'edit'}">
                        <stripes:hidden id="pouleId" name="poule" value="${actionBean.poule.id}"/>
                        <div class="form-container">
                            <table class="formtable">                    
                                <tr>
                                    <td>Naam</td>
                                    <td><stripes:text name="poule.name"/></td>
                                </tr>
                                <tr>
                                    <td>Vanaf Kyu</td>
                                    <td>
                                        <stripes:select name="startKyu" id="startKyu">
                                            <stripes:options-enumeration enum="com.roybraam.vanenapp.entity.Kyu" label="description"/>
                                        </stripes:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Tot Kyu</td>
                                    <td>
                                        <stripes:select name="endKyu" id="endKyu">
                                            <stripes:options-enumeration enum="com.roybraam.vanenapp.entity.Kyu" label="description" />
                                        </stripes:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Vanaf leeftijd</td>
                                    <td><stripes:text name="poule.startAge" id="startAge"/></td>
                                </tr>
                                <tr>
                                    <td>Tot leeftijd</td>
                                    <td><stripes:text name="poule.endAge" id="endAge"/></td>
                                </tr>
                                <tr>
                                    <td>Deelnemers</td>
                                    <td><div id="candidateContainer" class="candidate-container"></div></td>
                                </tr>
                            </table>
                        </div>
                        <div class="submitbuttons">
                            <stripes:submit name="save" value="Opslaan"/>
                            <stripes:submit name="cancel" value="Annuleren"/>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <stripes:submit name="edit" value="Nieuwe poule"/>
                    </c:otherwise>
                </c:choose>
            </stripes:form>
        </div>
        <div class="participants-without-poule-col">
            <b>Niet ingedeelde deelnemers:</b>
            <div>
                <c:forEach items="${actionBean.participantsWithoutPoule}" var="p">
                     <c:out value="${p}"/><br/>
                </c:forEach>
            </div>
        </div>
        <script type="text/javascript">
            var savedParticipants = [];
            <c:forEach items="${actionBean.participants}" var="p">
            savedParticipants.push(${p.id});
            </c:forEach>
                var pouleId = null;
            <c:if test="${actionBean.poule !=null}">
                pouleId = "${actionBean.poule.id}";
            </c:if>
                var listParticipantUrl = '<stripes:url beanclass="com.roybraam.vanenapp.stripes.PouleActionBean" event="participantList"/>';
        </script>
        <script src="${contextPath}/js/CandidateController.js"></script>
        <script src="${contextPath}/js/poule.js"></script>

    </stripes:layout-component>

</stripes:layout-render>