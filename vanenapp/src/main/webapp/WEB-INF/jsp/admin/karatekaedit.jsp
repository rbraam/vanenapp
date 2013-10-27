<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/frame.jsp">
    <stripes:layout-component name="head">
        <title>Karateka beheer</title>
    </stripes:layout-component>

    <stripes:layout-component name="body">
        <p>
            <stripes:errors/>
            <stripes:messages/>
        </p>
        <stripes:form beanclass="com.roybraam.vanenapp.stripes.KaratekaActionBean">
            <c:choose>
                <c:when test="${actionBean.context.eventName == 'edit'}">
                    <stripes:hidden name="karateka" value="${actionBean.karateka.id}"/>
                    <div class="form-container">
                        <table class="formtable">                    
                            <tr>
                                <td>Naam</td>
                                <td><stripes:text name="karateka.name"/></td>
                            </tr>
                            <tr>
                                <td>Achternaam</td>
                                <td><stripes:text name="karateka.surname"/></td>
                            </tr>
                            <tr>
                                <td>Tussenvoegsel</td>
                                <td><stripes:text name="karateka.insert"/></td>
                            </tr>
                            <tr>
                                <td>Kyu-graad</td>
                                <td>
                                    <stripes:select name="belt">
                                        <stripes:options-enumeration enum="com.roybraam.vanenapp.entity.Kyu" label="description"/>
                                    </stripes:select>
                                </td>
                            </tr>
                            <tr>
                                <td>Geslacht</td>
                                <td>
                                    <stripes:radio name="karateka.gender" value="MALE" /> Mannelijk
                                    <stripes:radio name="karateka.gender" value="FEMALE"/> Vrouwlijk
                                </td>
                            </tr>
                            <tr>
                                <td>Geboortedatum</td>
                                <td id="birthdateTd"></td>
                            <tr>
                                <td>Gewicht</td>
                                <td><stripes:text name="karateka.weight"/></td>
                            </tr>
                            <tr>
                                <td>Basis punten *</td>
                                <td><stripes:text name="karateka.basePoints"/></td>
                            </tr>
                            <tr><td colspan="2">* Een optelling van de punten die niet zijn geregistreerd in 1 van de Vanencompetities in deze applicatie.
                                Bijvoorbeeld omdat de applicatie toen nog niet gebruikt werd of omdat de vanencompetitie is weggegooid maar de punten
                                nog wel bewaard moeten blijven.</td></tr>
                        </table>
                    </div>
                    <div class="submitbuttons">
                        <stripes:submit name="save" value="Opslaan"/>
                        <stripes:submit name="delete" value="Verwijderen"/>
                        <stripes:submit name="cancel" value="Annuleren"/>
                    </div>
                    <script type="text/javascript">
                        var birthdate = new Date();
                        <c:if test="${not empty actionBean.karateka.birthdate}">
                            birthdate = '<fmt:formatDate value="${actionBean.karateka.birthdate}" pattern="dd-MM-yyyy"/>';
                        </c:if>
                        Ext.onReady(function() {
                            Ext.create('Ext.form.field.Date', {
                                renderTo: 'birthdateTd',
                                value: birthdate,
                                maxValue: new Date(),
                                name: "karateka.birthdate"
                            });
                        });
                    </script>
                </c:when>
                <c:when test="${actionBean.context.eventName == 'delete'}">
                    <stripes:submit name="edit" value="Nieuwe karateka"/>
                    <script type="text/javascript">
                        window.parent.listController.refresh();
                    </script>
                </c:when>
                <c:otherwise>
                    <stripes:submit name="edit" value="Nieuwe karateka"/>
                </c:otherwise>
            </c:choose>
        </stripes:form>

    </stripes:layout-component>

</stripes:layout-render>