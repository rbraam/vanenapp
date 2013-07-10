<%-- 
    Author     : Roy Braam
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<stripes:layout-definition>

    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

            <link rel="stylesheet" type="text/css" href="${contextPath}/extjs/resources/css/ext-all-gray.css">
            <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/main.css">

            <stripes:layout-component name="head"/>

            <script type="text/javascript">
                if (console == undefined) {
                    var console = {};
                    console.log = function(logmsg) {
                        //alert(logmsg);
                    }
                }
            </script>
            <script type="text/javascript" src="${contextPath}/extjs/ext-all.js"></script>
            <script type="text/javascript" src="${contextPath}/extjs/locale/ext-lang-nl.js"></script>
        </head>
        <body>           
            <div id="content">
                <stripes:layout-component name="body"/>
            </div>
        </body>
    </html>

</stripes:layout-definition>