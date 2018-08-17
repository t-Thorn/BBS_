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
    <title>修改头像</title>
    <link rel="stylesheet" href="/css/reset.css"/>
    <link rel="stylesheet" href="/css/homeHead.css"/>
    <link rel="stylesheet" href="/css/homePublic.css"/>
    <link rel="stylesheet" href="/css/base.css"/>
</head>
<body>
<!-- tip -->
<c:if test="${!(tip ==null)}">
    <script>alert("${tip}")</script>
</c:if>
<c:remove var="tip" scope="session"/>
<!-- endtip -->
<div class="baseHead">
    <ul>
        <li><a href="/tiaozhuan?name=proto2/setting">我的资料</a></li>
        <li><a href="/user/uploadImg" class="on">头像</a></li>
        <li><a href="/tiaozhuan?name=proto2/upload_password">密码</a></li>
    </ul>
</div>
<form action="/user/updatephoto" method="post" enctype="multipart/form-data">
    <div class="baseCon">
        <div class="upImg">
            <div class="Imgbtn">选择头像
                <input type="file" class="uploadPic" name="file">
            </div>
            <br>

            <div class="Imgbtn">上传
                <input type="submit" value="" class="uploadPic">
            </div>
            <p>建议尺寸168*168，支持jpg、png、gif,最大不能超过50KB</p>
            <div class="mypic">
                <img src="/photo/${userSession.getPhoto()}?rand=h9xqeI" alt=""/>
            </div>
        </div>
    </div>
</form>

</body>
</html>