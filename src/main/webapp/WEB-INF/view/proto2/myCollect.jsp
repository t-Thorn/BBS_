<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>我收藏的贴</title>
    <link rel="stylesheet" href="/css/reset.css"/>
    <link rel="stylesheet" href="/css/homeHead.css"/>
    <link rel="stylesheet" href="/css/homePublic.css"/>
    <link rel="stylesheet" href="/css/myWrite.css"/>
</head>
<body>

<div class="homeCen_right">
    <div class="baseHead">
        <ul>
            <li><a href="/user/mypost">我的发帖</a></li>
            <li><a href="/user/mycollect" class="on">我收藏的贴</a></li>
            <li><a href="/user/history">浏览历史</a></li>
        </ul>
    </div>

    <div class="myWrite_con">
        <div class="writeHead">
            <div class="writeHead1">帖子标题</div>
            <div class="writeHead2">发表时间</div>
            <div class="writeHead3">浏览量</div>
            <div class="writeHead4">操作</div>
        </div>

        <c:forEach var="row" items="${post }">
            <div class="writeFoot">
                <div class="writeFoot1">
                    <p>
                        <a href="javascript:void(0);" onclick
                                ="myfunction('/BBS/post?param=${row.getId()}')">${row.getTitle() }</a>
                    </p>
                </div>
                <div class="writeFoot2">${row.getPosttime() }</div>
                <div class="writeFoot3">${row.getViews() }</div>
                <div class="writeFoot4">
                    <a href="/user/cancelcollect?id=${row.getId()}">取消收藏</a>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="indexFooter" align="center">
        <div class="indexFooter_con">

            <c:if test="${currentPage == 1}">

            </c:if>
            <c:if test="${currentPage != 1}">
                <a href="/user/mycollect?page=${currentPage-1}"><<</a>
            </c:if>
            <c:if test="${currentPage == 1}">
                <a href="/user/mycollect?page=1">1</a>
            </c:if>
            <c:if test="${currentPage != 1}">
                <a href="/user/mycollect?page=1">1</a>
            </c:if>
            <%
                int pageTimes = (Integer) request.getAttribute("pageTimes");
                for (int i = 1; i < pageTimes; i++) {
                    request.setAttribute("page", i + 1);
            %>
            <c:if test="${currentPage == page}">
                <a href="/user/mycollect?page=<%=i + 1%>"><%=i + 1%>
                </a>
            </c:if>
            <c:if test="${currentPage != page}">
                <a href="/user/mycollect?page=<%=i + 1%>"><%=i + 1%>
                </a>
            </c:if>
            <%
                }
            %>


            <c:if test="${currentPage != pageTimes}">
                <a href="/user/mycollect?page=${currentPage+1}">>></a>

            </c:if>
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