<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head lang="en">

    <title>我的消息</title>
    <link rel="stylesheet" href="/css/reset.css"/>
    <link rel="stylesheet" href="/css/homeHead.css"/>
    <link rel="stylesheet" href="/css/homePublic.css"/>
    <link rel="stylesheet" href="/css/myMsg.css"/>
</head>
<body style="background-color: #fff;">
<div class="baseHead" style="background-color: #fff;">
    <p>我的消息</p>
    <c:forEach var="row" items="${reply}">
        <div class="myMsgCon">
            <div class="myMsgCon_detail">

                <a href="javascript:void(0);"
                   onclick="myfunction('/BBS/post?param=${row.getPostid()}')">${row.getReplyer() }回复你：${row.getContent() }</a>
            </div>
            <div class="writeFoot3">${row.getReplytime() }</div>
        </div>

    </c:forEach>


</div>
<div class="indexFooter">
    <div class="indexFooter_con">

    </div>
</div>
</div>
</div>
<script>
    function myfunction(name) {
        window.open(name);
    }
</script>
</body>
</html>