<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head lang="en">
    <meta charset="UTF-8">
    <title>我的资料</title>
    <link rel="stylesheet" href="/css/reset.css"/>
    <link rel="stylesheet" href="/css/homeHead.css"/>
    <link rel="stylesheet" href="/css/homePublic.css"/>
    <link rel="stylesheet" href="/css/base.css"/>
    <script src="js/jquery-1.8.3.min.js"></script>
    <script>
        $(function () {
            $('li').click(function (e) {
                $("#list li").removeClass("on")
                $(this).addClass("on")
            });
        })

        function changesub(e) {
            alert(1);
            $("#sub").Attr("src", e)
        }
    </script>
</head>

<body>
<header class="zyHead">
    <div class="zyHead_cen">
        <a href="">
            <img src="/img/per-con.png" alt="" class="headPic1"/>
        </a>
        <a href="/BBS/page" class="backIndex">返回首页</a>
        <!--未登入开始-->
        <!--<div class="ltForm">-->
        <!--<a href=""><img src="/img/indexForm_bg.png" alt="" class="headPic2"/></a>-->
        <!--<ul>-->
        <!--<li><a href="">登入</a></li>-->
        <!--<li><a href="">注册</a></li>-->
        <!--</ul>-->
        <!--</div>-->
        <!-- 未登入结束-->
        <!-- 登入开始，未登入时以下隐藏-->
        <div class="lt_login">
            <ul>
                <li>
                    <a href="/myinform">${userSession.getName()}</a>
                </li>
                <li>
                    <a href="/user/out">退出</a>
                </li>
            </ul>
        </div>
        <!-- 登入结束-->
    </div>
</header>
<div class="homeCen">
    <div class="homeCen_left">
        <ul id="list">
            <li class="on">
                <a href="tiaozhuan?name=proto2/home" target="sub">我的主页</a>
            </li>
            <li>
                <a href="tiaozhuan?name=proto2/setting" target="sub">信息更改</a>
            </li>
            <li>
                <a href="/user/mypost" target="sub">我的贴子</a>
            </li>
            <li>
                <a href="/reply/get" target="sub">我的回复</a>
            </li>
        </ul>
    </div>
    <iframe id="sub" class="homeCen_right" src="tiaozhuan?name=proto2/home" width="500px"
            height="500px"
            frameborder="0" name="sub" scrolling="no">
    </iframe>
</div>
</body>
</html>
