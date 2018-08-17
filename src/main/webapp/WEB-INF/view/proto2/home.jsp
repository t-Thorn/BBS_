<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=8">
    <meta http-equiv="Expires" content="0">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-control" content="no-cache">
    <meta http-equiv="Cache" content="no-cache">

    <meta charset="UTF-8">
    <title>我的主页</title>
    <link rel="stylesheet" href="/css/reset.css"/>
    <link rel="stylesheet" href="/css/homeHead.css"/>
    <link rel="stylesheet" href="/css/homePublic.css"/>
    <link rel="stylesheet" href="/css/home.css"/>
</head>
<body>
<div>
    <div class="home_self">

        <img src="/photo/${userSession.getPhoto()}">
    </div>
    <!--男性为male，女性为female-->
    <div class="home_name"><p <c:if test="${gender eq '男'}"> class="male"</c:if> <c:if
            test="${gender eq '女'}"> class="female"</c:if>>${name }</p>
    </div>
    <%--    <div class="home_msg">
            <ul>
                <li>粉丝数${fans}</li>
            </ul>
        </div>--%>
</div>
</body>
</html>