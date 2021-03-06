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
        <h1>Poules vanencompetitie</h1>
        <script type="text/javascript" src="${contextPath}/js/AgeCalculator.js"></script>
        <script type="text/javascript">
            var ageCalc = new AgeCalculator("${actionBean.vanencompetition.date}");
        </script>
        <div class="choose-vanencompetition">
            ${actionBean.vanencompetition}
            <stripes:link beanclass="com.roybraam.vanenapp.stripes.OrganizeVanencompetitionActionBean">Kies</stripes:link>
        </div>
        <div class="admin-list">
            <table>
                <tr>
                    <th>Toevoeging op naam</th>
                    <th>Vanaf Kyu</th>                
                    <th>Tot Kyu</th>                
                    <th>Van leeftijd</th>                
                    <th>Tot leeftijd</th>
                    <th>Type</th>
                    <th>Geslacht</th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach items="${actionBean.poules}" var="p">
                    <tr>
                        <td><c:out value="${p.name}"/></td>
                        <td><c:out value="${p.startKyu}"/></td>
                        <td><c:out value="${p.endKyu}"/></td>
                        <td><c:out value="${p.startAge}"/></td>
                        <td><c:out value="${p.endAge}"/></td>
                        <td><c:out value="${p.type}"/></td>
                        <td><c:out value="${p.gender}"/></td>
                        <td><a href='<stripes:url beanclass="com.roybraam.vanenapp.stripes.PouleActionBean" event="edit"/>?poule=${p.id}'>Wijzig</a></td>
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
                                    <td>Toevoeging op naam</td>
                                    <td><stripes:text name="poule.name"/></td>
                                </tr>
                                <tr>
                                    <td>Vanaf Kyu</td>
                                    <td>
                                        <stripes:select name="startKyu" id="startKyu" value="KYU_8">
                                            <stripes:options-enumeration enum="com.roybraam.vanenapp.entity.Kyu" label="description" />
                                        </stripes:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Tot Kyu</td>
                                    <td>
                                        <stripes:select name="endKyu" id="endKyu" value="KYU_1">
                                            <stripes:options-enumeration enum="com.roybraam.vanenapp.entity.Kyu" label="description" />
                                        </stripes:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Vanaf leeftijd</td>
                                    <td><stripes:text name="poule.startAge" id="startAge"/></td>
                                </tr>
                                <tr>
                                    <td>T/m leeftijd</td>
                                    <td><stripes:text name="poule.endAge" id="endAge"/></td>
                                </tr>
                                <tr>
                                    <td>Type</td>
                                    <td>
                                        <stripes:radio name="type" value="KATA" checked="checked" onchange="typeChange()"/>Kata<br/>
                                        <stripes:radio name="type" value="KUMITE" onchange="typeChange()"/>Kumité
                                    </td>
                                </tr>
                                <tr>
                                    <td>Geslacht</td>
                                    <td>
                                        <stripes:select name="poule.gender" id="gender">
                                            <stripes:option label="Beide" value=""/>
                                            <stripes:option value="MALE" label="Jongens"/>
                                            <stripes:option value="FEMALE" label="Meisjes"/>
                                        </stripes:select>
                                    </td>
                                </tr>
                                <tr id="startWeightRow">
                                    <td>Vanaf gewicht (kg)</td>
                                    <td><stripes:text name="poule.startWeight" id="startWeight"/></td>
                                </tr>
                                <tr id="endWeightRow">
                                    <td>T/m gewicht (kg)</td>
                                    <td><stripes:text name="poule.endWeight" id="endWeight"/></td>
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
                    <c:out value="${p}"/>
                    <script type="text/javascript">
                        var years=ageCalc.getAge("${p.karateka.birthdate}");
                        document.write("("+years+")");
                    </script><br/>
                </c:forEach>
            </div>
        </div>

    </stripes:layout-component>

</stripes:layout-render>