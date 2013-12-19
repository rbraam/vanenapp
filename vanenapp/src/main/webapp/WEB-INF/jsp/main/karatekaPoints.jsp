<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/mobile.jsp">
    <stripes:layout-component name="head">
        <title>Punten</title>
    </stripes:layout-component>

    <stripes:layout-component name="body">
        <c:set var="catValues" value="<%=com.roybraam.vanenapp.entity.Category.values()%>"/>
        <div class="karateka-points-vanen">
             <c:out value="${actionBean.p.vanencompetition}"/>
        </div>
        <div class="karateka-points-name">
            <c:out value="${actionBean.karateka.fullName}"/>
        </div>
        <div class="karateka-points-label">
            Behaalde punten:
        </div>
        <div class="karateka-points" style="font-size: 60pt;">
            <c:out value="${actionBean.p.points}"/>
        </div>
        <div class="karateka-points-label">
            Nieuw totaal:
        </div>
        <div class="karateka-points">
            <c:out value="${actionBean.points}"/>
        </div>
        <div class="karateka-points-category">
            (<c:out value="${catValues[actionBean.category].name}"/>)
        </div>
        <div class="karateka-points-label">
            Voltooide categoriën:
        </div>
        
        <div class="badges">
            <c:forEach items="${catValues}" var="c">
                <c:set var="badgeClass" value="badge-gray"/>
                <c:if test="${c.id < actionBean.category}">
                    <c:set var="badgeClass" value="badge"/>                        
                </c:if>
                <div class="${badgeClass}">
                    <img src="${contextPath}/resources/images/award_category_${c.id}.png"/>
                </div>
            </c:forEach>
        </div>
        
            
    </stripes:layout-component>

</stripes:layout-render>
