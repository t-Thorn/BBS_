<%@ page language="java" pageEncoding="UTF-8" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link rel="stylesheet" href="/res/layui/css/layui.css">
    <link rel="stylesheet" href="/res/css/global.css">
    <script src="/res/layui/layui.js"></script>
</head>
<body>
<div class="header">
    <div class="main">
        <a href="index.html"
           style="margin-left: 30px; line-height: 65px; color: white; font-size: 18px;">论坛管理系统后台</a>
        <div class="nav"></div>

        <div class="nav-user" style="margin-right: 150px">
            <!-- 登入后的状态 -->
            <a class="avatar" href="/BBS/page"> <img src="/photo/${userSession.getPhoto()}"></a>
            <cite style="color: white;">管理员"${userSession.getUsername()} </cite>
            <a style="color: white;" href="/user/out">退出</a>
        </div>
    </div>
</div>


<div class="main fly-user-main layui-clear">
    <ul class="layui-nav layui-nav-tree layui-inline" lay-filter="user">
        <li class="layui-nav-item layui-this"><a href="/user/OA">
            <i class="layui-icon">&#xe609;</i> 浏览帖子
        </a></li>
        <li class="layui-nav-item "><a href="/user/select?search=null" target="right">
            <i class="layui-icon">&#xe612;</i> 用户管理
        </a></li>

    </ul>

    <div class="site-tree-mobile layui-hide">
        <i class="layui-icon">&#xe602;</i>
    </div>
    <div class="site-mobile-shade"></div>

    <div class="fly-panel fly-panel-user"
    ">
    <div class="layui-tab layui-tab-brief" lay-filter="user">

        <div class="layui-tab-content"
             style="padding: 20px; padding-top: 0px;">

            <iframe src="/OA/Post?search=null" width="88%" style="border: none;"
                    height="800" border="none" name="right"></iframe>
        </div>
    </div>
</div>
</div>
</div>
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="myplugs/js/plugs.js"></script>
[
<script>
    layui.use('element', function () {
        var element = layui.element();
    });
</script>
</body>
</html>
