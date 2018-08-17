<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head lang="en">

    <title>修改密码</title>
    <link rel="stylesheet" href="/css/reset.css"/>
    <link rel="stylesheet" href="/css/homeHead.css"/>
    <link rel="stylesheet" href="/css/homePublic.css"/>
    <link rel="stylesheet" href="/css/base.css"/>
</head>
<body>
<div class="baseHead">
    <ul>
        <li><a href="/tiaozhuan?name=proto2/setting">我的资料</a></li>
        <li><a href="/user/uploadImg">头像</a></li>
        <li><a href="/tiaozhuan?name=proto2/upload_password" class="on">密码</a></li>
    </ul>
</div>
<!-- tip -->
<c:if test="${!(tip ==null)}">
    <script>
        alert("${tip}")
    </script>
</c:if>
<c:remove var="tip" scope="session"/>
<!-- endtip -->
<form action="/user/updatepwd" method="post"
      id="sub">
    <div class="baseCon">
        <div class="baseCon_son">
            <div class="baseCon_son_left">当前密码</div>
            <div class="baseCon_son_right">
                <input type="password" name="currentpwd">
            </div>
        </div>
        <div class="baseCon_son">
            <div class="baseCon_son_left">新密码</div>
            <div class="baseCon_son_right">
                <input type="password" name="newpwd">
            </div>
        </div>
        <div class="baseCon_son">
            <div class="baseCon_son_left">确认密码</div>
            <div class="baseCon_son_right">
                <input type="password" name="confirmpwd">
            </div>
        </div>
        <input type="submit" value="确认修改" class="upload_sure"/>
</form>
</div>
</body>
</html>