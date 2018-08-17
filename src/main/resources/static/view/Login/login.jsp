<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>

<!-- Head -->
<head>

    <title>登录表单</title>

    <!-- Meta-Tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <script type="application/x-javascript"> addEventListener("load", function () {
        setTimeout(hideURLbar, 0);
    }, false);

    function hideURLbar() {
        window.scrollTo(0, 1);
    } </script>
    <!-- //Meta-Tags -->

    <!-- Style -->
    <link rel="stylesheet" href="/css/style.css" type="text/css" media="all">


</head>
<!-- //Head -->


<!-- tip -->
<c:if test="${!(tip ==null)}">
    <script>alert("${tip}")</script>
</c:if>
<!-- endtip -->


<!-- Body -->
<body>

<h1>登录表单</h1>

<div class="container w3layouts agileits">

    <div class="login w3layouts agileits">
        <h2>登 录</h2>
        <form action="/user/login" method="post">
            <input type="text" Name="username" placeholder="用户名" required="">
            <input type="password" Name="password" placeholder="密码" required="">
            <div class="send-button w3layouts agileits">
                <input type="submit" value="登 录">
            </div>
        </form>


        <div class="social-icons w3layouts agileits">

        </div>
        <div class="clear"></div>
    </div>
    <div class="copyrights">
        Collect from <a href="http://www.cssmoban.com/">企业网站模板</a>
    </div>
    <div class="register w3layouts agileits">
        <h2>注 册</h2>
        <form action="/user/reg" method="post">
            <input type="text" Name="username" placeholder="用户名" required="required">
            <input id="password" type="password" Name="password" placeholder="密码"
                   required="required">
            <input id="determine_password" type="password" Name="repassword"
                   placeholder="确认密码" required="required">
            <input type="text" Name="identity" placeholder="身份证号" required="required"
                   pattern="^\d{17}(\d|x)$" title="十七位身份证">
            <input type="text" Name="name" placeholder="昵称" required="required">
            <div class="send-button w3layouts agileits">

                <input type="submit" value="免费注册">


            </div>
        </form>

        <div class="clear"></div>
    </div>

    <div class="clear"></div>

</div>

<div class="footer w3layouts agileits">
    <p>
        Copyright &copy; More Templates <a href="http://www.cssmoban.com/"
                                           target="_blank" title="模板之家">模板之家</a> - Collect from <a
            href="http://www.cssmoban.com/" title="网页模板" target="_blank">网页模板</a>
    </p>
</div>

<script>


</script>

</body>
<!-- //Body -->

</html>