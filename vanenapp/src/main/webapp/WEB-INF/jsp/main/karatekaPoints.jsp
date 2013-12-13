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
        <div class="karateka-points-name">
            
        </div>
        <div class="karateka-points">
            <c:out value="${actionBean.points}"/>
        </div>
        Voltooide categorieën:
        <c:set var="catValues" value="<%=com.roybraam.vanenapp.entity.Category.values()%>"/>
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
