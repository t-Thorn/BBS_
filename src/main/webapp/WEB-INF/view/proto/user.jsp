<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>

    <title></title>
    <link rel="stylesheet" href="/res/css/amazeui.min.css"/>
    <link rel="stylesheet" href="/res/css/admin.css"/>
</head>
<c:if test="${!(tip ==null)}">
    <script>
        alert("${tip}")
    </script>
</c:if>
<body>
<div class="admin-content-body">
    <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">用户管理</strong>
            <small></small>
        </div>
    </div>

    <hr>

    <div class="am-g">
        <div class="am-u-sm-12 am-u-md-3"></div>
        <div class="am-u-sm-12 am-u-md-3">
            <form action="/user/select">
                <div class="am-input-group am-input-group-sm">
                    <input type="text" class="am-form-field" name="search" id="search"> <span
                        class="am-input-group-btn">
						 ${tip}<input class="am-btn am-btn-default" type="submit" value="搜索">
					</span>
                </div>
            </form>
        </div>
    </div>
    <div class="am-g">
        <div class="am-u-sm-12">
            <form class="am-form">
                <table class="am-table am-table-striped am-table-hover table-main">
                    <thead>
                    <tr>
                        <th class="table-title">用户名</th>
                        <th class="center">姓名</th>
                        <th class="center">年龄</th>
                        <th class="table-author am-hide-sm-only">性别</th>
                        <th class="table-type">注册时间</th>
                        <th class="center">粉丝数</th>
                        <th class="table-set">操作</th>
                    </tr>
                    <tr>
                        <c:forEach var="row" items="${user}">
                        <td class="center">${row.getUsername() }</td>
                        <td class="center">${row.getName() }</td>
                        <td class="center">${row.getAge() }</td>
                        <td class="center">${row.getGender() }</td>
                        <td class="center">${row.getRegdate() }</td>
                        <td class="center">${row.getFans() }</td>
                        <td class="center">

                            <a href="/user/delete?username=${row.getUsername()}"
                               class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
                                <span class="am-icon-trash-o"></span> 删除
                            </a>
                        </td>
                    </tr>
                    </c:forEach>

                    </thead>

                </table>
                <div class="pagging">
                    <div class="left">共${userNum}条记录</div>
                    <div class="right">
                        <c:if test="${currentPage == 1}">
                            <span class="disabled"><< 前一页</span>
                        </c:if>
                        <c:if test="${currentPage != 1}">
                            <a href="/user/select?page=${currentPage-1}&search=null"><< 前一页</a>
                        </c:if>
                        <c:if test="${currentPage == 1}">
                            <span class="current">1</span>
                        </c:if>
                        <c:if test="${currentPage != 1}">
                            <a href="/user/select?page=1&search=null">1</a>
                        </c:if>
                        <%
                            int pageTimes = (Integer) request.getAttribute("pageTimes");
                            for (int i = 1; i < pageTimes; i++) {
                                request.setAttribute("page", i + 1);
                        %>
                        <c:if test="${currentPage == page}">
                            <span class="current"><%=i + 1%></span>
                        </c:if>
                        <c:if test="${currentPage != page}">
                            <a href="/user/select?page=<%=i + 1%>&search=null"><%=i + 1%>
                            </a>
                        </c:if>
                        <%
                            }
                        %>

                        <c:if test="${currentPage == pageTimes}">
                            <span class="disabled">后一页 >></span>
                        </c:if>
                        <c:if test="${currentPage != pageTimes}">
                            <a href="/user/select?page=${currentPage+1}&search=null">后一页 >></a>
                        </c:if>
                    </div>
                </div>
                <hr>
            </form>
        </div>
    </div>
</div>
</body>
</html>
