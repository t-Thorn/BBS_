<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>

<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="/res/css/amazeui.min.css"/>
    <link rel="stylesheet" href="/res/css/admin.css"/>
</head>
<!-- tip -->
<c:if test="${!(tip ==null)}">
    <script>
        alert("${tip}")
    </script>
</c:if>
<body>

<div class="admin-content-body" style="height: auto">
    <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf">
            <strong class="am-text-primary am-text-lg">浏览帖子</strong>
            <small></small>
        </div>
    </div>

    <hr>

    <div class="am-g">

        <div class="am-u-sm-12 am-u-md-3"></div>
        <div class="am-u-sm-12 am-u-md-3">
            <form action="">
                <div class="am-input-group am-input-group-sm">
                    <input type="text" class="am-form-field" id="search" name="search"> <span
                        class="am-input-group-btn">
						<input class="am-btn am-btn-default" type="submit" value="搜索">
					</span>
                </div>
            </form>
        </div>
    </div>
    <div class="am-g">
        <div class="am-u-sm-12">
            <form class="am-form">
                <table class="am-table am-table-striped am-table-hover table-main">
                    <!table class="table table-hover table-checkable order-column full-width"
                    id="example4">
                    <thead>
                    <tr>
                        <th class="table-id" style="width: 30px">ID</th>
                        <th class="table-title">贴名</th>
                        <th class="table-title">发帖人</th>
                        <th class="table-set">操作</th>
                    </tr>
                    <tr>
                        <c:forEach var="row" items="${post}">
                        <td class="center">${row.getId() }</td>
                        <td class="center"><a href="javascript:void(0);"
                                              onclick="myfunction('/BBS/post?param=${row.getId()}')">${row.getTitle() }</a>
                        </td>
                        <td class="center">${row.getUsername() }</td>
                        <td class="center">
                            <c:if test="${ row.getGrade()==0}">
                                <a href="/OA/good?id=${row.getId()}&grade=${row.getGrade()}"
                                   class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
                                    <span class=""></span> 加精

                                </a>
                                <a href="/OA/stick?id=${row.getId()}&grade=${row.getGrade()}"
                                   class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
                                    <span class=""></span> 置顶
                                </a>
                            </c:if>
                            <c:if test="${row.getGrade()==1 }">
                                <a href="/OA/good?id=${row.getId()}&grade=${row.getGrade()}"
                                   class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
                                    <span class=""></span> 取消加精
                                </a>
                                <a href="/OA/stick?id=${row.getId()}&grade=${row.getGrade()}"
                                   class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
                                    <span class=""></span> 置顶
                                </a>
                            </c:if>
                            <c:if test="${row.getGrade()==2 }">
                                <a href="/OA/good?id=${row.getId()}&grade=${row.getGrade()}"
                                   class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
                                    <span class=""></span> 加精
                                </a>
                                <a href="/OA/stick?id=${row.getId()}&grade=${row.getGrade()}"
                                   class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
                                    <span class=""></span> 取消置顶
                                </a>
                            </c:if>
                            <c:if test="${row.getGrade()==3 }">
                                <a href="/OA/good?id=${row.getId()}&grade=${row.getGrade()}"
                                   class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
                                    <span class="am-icon-trash-o"></span> 取消加精
                                </a>
                                <a href="/OA/stick?id=${row.getId()}&grade=${row.getGrade()}"
                                   class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
                                    <span class="am-icon-trash-o"></span> 取消置顶
                                </a>
                            </c:if>
                            <a href="/OA/deletepost?id=${row.getId()}"
                               class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
                                <span class="am-icon-trash-o"></span> 删除

                            </a>
                        </td>
                    </tr>
                    </c:forEach>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
                <div class="pagging">
                    <div class="left">共${postNum}条记录</div>
                    <div class="right">
                        <c:if test="${currentPage == 1}">
                            <span class="disabled"><< 前一页</span>
                        </c:if>
                        <c:if test="${currentPage != 1}">
                            <a href="/OA/Post?page=${currentPage-1}&search=null"><< 前一页</a>
                        </c:if>
                        <c:if test="${currentPage == 1}">
                            <span class="current">1</span>
                        </c:if>
                        <c:if test="${currentPage != 1}">
                            <a href="/OA/Post?page=1&search=null">1</a>
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
                            <a href="/OA/Post?page=<%=i + 1%>&search=null"><%=i + 1%>
                            </a>
                        </c:if>
                        <%
                            }
                        %>

                        <c:if test="${currentPage == pageTimes}">
                            <span class="disabled">后一页 >></span>
                        </c:if>
                        <c:if test="${currentPage != pageTimes}">
                            <a href="/OA/Post?page=${currentPage+1}&search=null">后一页 >></a>
                        </c:if>
                    </div>
                </div>
                <hr>
            </form>
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
