<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>发表帖子</title>
    <link rel="stylesheet" href="/css/reset.css"/>
    <link rel="stylesheet" href="/css/public.css"/>
    <link rel="stylesheet" href="/css/write.css"/>
    <script src="/js/jquery-1.8.3.min.js"></script>
    <script src="/js/wangEditor.min.js"></script>
    <script src="/js/EditorCall.js"></script>
    <script>
        function validate() {
            var content = editor.txt.text()
            if ($("#title").attr("value") == "") {
                $("#contentTip").text("");
                $("#titleTip").text("请输入标题");
                $("#title").focus();
                return false;
            }
            else if (content == "") {
                $("#titleTip").text("")
                $("#contentTip").text("请输入内容");
                $("#editor").focus();
                return false;
            } else {
                switch ($("#choose").val()) {
                    case "普通":
                        $("#type").val(0)
                        break;
                    case "求助":
                        $("#type").val(1)
                        break;
                    case "经验":
                        $("#type").val(2)
                        break;
                    case "闲聊":
                        $("#type").val(3)
                        break;
                }
                $("#content").val(editor.txt.html())
                $("#newpost").submit();
            }
        }
    </script>
</head>
<body>
<header class="ltHead">
    <div class="ltHead_cen">
        <a href=""><img src="/img/logo.png" alt="" class="headPic1"/></a>
        <ul class="headNav">
            <li><a href="">首页</a></li>
        </ul>

        <c:choose>
            <c:when test="${userSession.getUsername()==null}">
                <div class="ltForm">
                    <a href=""><img src="/img/indexForm_bg.png" alt="" class="headPic2"/></a>
                    <ul>
                        <li><a href="/user/user">登入</a></li>
                        <li><a href="/user/user">注册</a></li>
                    </ul>
                </div>
            </c:when>
            <c:otherwise>
                <div class="lt_login">
                    <ul>
                        <li><a href="/myinform">${userSession.getName()}</a></li>
                        <li><a href="/user/out">退出</a></li>
                    </ul>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</header>
<div class="writeCon">
    <div class="writeCon_head">
        <p>发新帖</p>
    </div>

    <div class="writeCon_cen">
        <form method="post" action="/Post/newpost" id="newpost">
            <div class="chooseDevide">
                <div class="chooseDevide_left">
                    所在分类
                </div>
                <div class="chooseDevide_right">
                    <div class="nice-select" name="nice-select">
                        <input type="hidden" name="type" id="type">
                        <input type="text" value="普通" readonly="true" id="choose"/>
                        <ul>
                            <li class="click" data-val="">普通</li>
                            <li class="click" data-val="">经验</li>
                            <li class="click" data-val="">闲聊</li>
                            <li class="click" data-val="">求助</li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="writeMsg">
                <input type="text" placeholder="请输入标题" name="title" id="title"/>
                <span id="titleTip" style="color: #ff004c;height: 30px;font-size: 16px">
                </span>
            </div>

            <div class="writeDown">
                <div class="content" id="editor"></div>
                <input type="hidden" id="content" name="content">
            </div>
            <span id="contentTip" style="color: #ff004c;height: 30px;font-size: 16px">
                </span>
            <center>
                <button class="reform" type="button" onclick=" validate()">发布</button>
            </center>
        </form>
    </div>
</div>
</body>
</html>

<script>
    $('[name="nice-select"]').click(function (e) {
        $('[name="nice-select"]').find('ul').hide();
        $(this).find('ul').show();
        e.stopPropagation();
    });
    $('[name="nice-select"] li').hover(function (e) {
        $(this).toggleClass('on');
        e.stopPropagation();
    });
    $('[name="nice-select"] li').click(function (e) {
        var val = $(this).text();
        var dataVal = $(this).attr("data-value");
        $(this).parents('[name="nice-select"]').find('input').val(val);
        $('[name="nice-select"] ul').hide();
        e.stopPropagation();
    });
    $(document).click(function () {
        $('[name="nice-select"] ul').hide();
    });
</script>