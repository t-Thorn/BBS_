<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head lang="en">
    <title>我的主页</title>
    <link rel="stylesheet" href="/css/reset.css"/>
    <link rel="stylesheet" href="/css/homeHead.css"/>
    <link rel="stylesheet" href="/css/homePublic.css"/>
    <link rel="stylesheet" href="/css/base.css"/>
</head>
<body>
<div class="baseHead">
    <ul>
        <li><a href="/tiaozhuan?name=proto2/setting" class="on">我的资料</a></li>
        <li><a href="/user/uploadImg">头像</a></li>
        <li><a href="/tiaozhuan?name=proto2/upload_password">密码</a></li>
    </ul>
</div>
<!-- tip -->
<c:if test="${!(tip ==null)}">
    <script>alert("${tip}")</script>
</c:if>
<c:remove var="tip" scope="session"/>
<!-- endtip -->
<div class="baseCon">
    <form action="/user/update" method="post">
        <div class="baseCon_son">
            <div class="baseCon_son_left">
                姓名
            </div>
            <div class="baseCon_son_right">
                <input type="text" name="name" value="${userSession.getName()}" }>
            </div>
        </div>
        <div class="baseCon_son">
            <div class="baseCon_son_left">
                年龄
            </div>
            <div class="baseCon_son_right">
                <input type="text" name="age" value="${userSession.getAge()}">
            </div>
        </div>
        <div class="baseCon_son">
            <div class="baseCon_son_left">
                身份证
            </div>
            <div class="baseCon_son_right">
                <input type="text" name="identity" value="${userSession.getIdentity()} ">
            </div>
        </div>
        <div class="chooseSex">
            <p><input type="radio" name="sex" value="男" <c:if
                    test="${userSession.getGender() eq '男'}">
                      checked="checked"</c:if>><label>男</label></p>
            <p><input type="radio" name="sex" value="女" <c:if
                    test="${userSession.getGender() eq '女'}">
                      checked="checked"</c:if>><label>女</label></p>
        </div>
        <input type="submit" value="确认修改" class="upload_sure"/>
    </form>
</div>

</body>