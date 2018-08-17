<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>帖子</title>
    <link rel="stylesheet" href="/css/reset.css"/>
    <link rel="stylesheet" href="/css/public.css"/>
    <link rel="stylesheet" href="/css/index.css"/>
    <script src="/js/jquery-1.8.3.min.js"></script>
    <script src="/js/wangEditor.min.js"></script>
    <script src="/js/EditorCall.js"></script>
    <script src="/js/BBSDetail.js"></script>
    <script>
        $(function () {
            if (${refloor!=null && refloor!=-1})
                window.location.hash = "#floor${refloor}";
            if (${userSession!=null && userSession.getUsername()!=null}) {
                $.post("/Post/iscollection",
                    {
                        postid: "${post.getId()}",
                        method: "0"
                    }
                    , function (data, status) {

                        if (status == "success") {
                            if (data.status == 1) {
                                $("#collection").removeClass("tzCollect_left")
                                $("#collection").addClass("tzCollect_left_on")
                                $("#collection").html("已收藏")
                            } else {
                                $("#collection").removeClass("tzCollect_left_on")
                                $("#collection").addClass("tzCollect_left")
                                $("#collection").html("收藏")
                            }
                        }
                    }
                );
            }
        })

        function collection() {
            if (${userSession!=null && userSession.getUsername()!=null}) {
                $.post("/Post/iscollection",
                    {
                        postid: "${post.getId()}",
                        method: "1"
                    },
                    function (data, status) {
                        if (status == "success") {
                            if (data.status == "1") {
                                $("#collection").removeClass("tzCollect_left")
                                $("#collection").addClass("tzCollect_left_on")
                                if (data.num != -1) $("#collectionnum").html(data.num)
                                $("#collection").html("已收藏")
                            } else {
                                $("#collection").removeClass("tzCollect_left_on")
                                $("#collection").addClass("tzCollect_left")
                                if (data.num != -1) $("#collectionnum").html(data.num)
                                $("#collection").html("收藏")
                            }
                        }
                    });
            }
        }

        function newreply() {
            if (${userSession!=null && userSession.getUsername()!=null}) {
                var reply = editor.txt.text();
                if (reply == "") {
                    alert("请输入回复")
                    return false;
                } else {
                    $("#content").val(editor.txt.html())
                    $("#newreply").submit()
                }
            } else
                alert("请登录")
        }
    </script>
    <script>
        function comment(postid, floor, method, floorex) {
            if (${userSession!=null && userSession.getUsername()!=null}) {
                if (method == 0) {
                    //新增回复
                    var comment = $("#floor" + floor + " :text").val();
                    if (comment == "") {
                        alert("请输入评论");
                        $("#floor" + floor + " :text").focus();
                        return false;
                    }
                    $.post("/Post/subreply", {
                        postid: postid,
                        floor: floor,
                        content: $("#floor" + floor + " :text").val()
                    }, function (data, status) {
                        if (status == "success") {
                            //新增元素
                            if (data.content != "[object XMLDocument]" && data.content != "") {
                                var div = document.createElement("div");
                                div.classList.add("pendDetail_replayCon");
                                div.setAttribute("id", floor + "floorex" + data.floorex);
                                // 以 DOM 创建新元素
                                var p = document.createElement("p");
                                p.innerText = data.replyer + "回复" + data.parentreplyer + ":" + data.content;
                                div.append(p)
                                var a = document.createElement("a");
                                a.setAttribute("href", "javascript:void(0)");
                                a.innerText = "删除";
                                a.setAttribute("onclick", "comment(" + postid + ", " + floor + ", 1," +
                                    data.floorex + ")");
                                div.appendChild(a)
                                $("#floor" + floor + ">.subreply").append(div);
                                $("#floor" + floor + ">.pendDetail_action").hide();
                            } else
                                alert("回复失败")
                        } else
                            alert("回复失败")
                    });
                } else {
                    $.post("/Post/subreply", {
                            postid: postid,
                            floor: floor,
                            content: $("#floor" + floor + " :text").val(),
                            floorex: floorex
                        },
                        function (data, status) {
                            if (status == "success") {
                                //删除元素
                                if (data.content != "[object XMLDocument]" && data.content != "") {
                                    $("#" + floor + "floorex" + floorex).remove();
                                } else
                                    alert("删除失败")
                            } else
                                alert("删除失败")
                        });
                }
            } else
                alert("请登录")
        }
    </script>
</head>
<body>
<header class="ltHead">
    <div class="ltHead_cen">
        <ul class="headNav">
            <li>
                <a href="/BBS/page?method=1" style="padding: 0 6px;border: 1px  solid
            white; border-radius: 5px;">首页</a>
            </li>
        </ul>
        <!--未登入开始-->
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
<div class="indexMain">
    <div class="indexMain_left">
        <div class="tzCon">
            <div class="tzCon_head">
                <div class="tzCon_head_left"><img src="/photo/${post.getUser().getPhoto()}"></div>
                <div class="tzCon_head_right">
                    <h1>${post.getTitle()}</h1>
                    <ul>
                        <li>${post.getUser().getName()}</li>
                        <li><fmt:formatDate value="${post.getPosttime()}"
                                            pattern="yyyy-MM-dd HH:mm"/></li>
                        <li>${post.getViews()}</li>
                    </ul>
                </div>
                <div class="clear"></div>
            </div>
            <div class="tzCon_con">
                ${firstReply.getContent()}
            </div>
            <div class="tzCon_foot">
                <div class="tzCollect">
                    <c:choose>
                        <c:when test="${userSession!=null&&
                        userSession.getUsername()!=null && userSession.getUsername()!=post.getUser().getUsername()}">
                            <div id="collection" class="tzCollect_left"
                                 onclick="collection()">收藏
                            </div>
                            <div class="tzCollect_right"
                                 id="collectionnum">${post.getCollectionnum()}</div>
                        </c:when>
                        <c:otherwise>
                            <div class="tzCollect_left_on">收藏数</div>
                            <div class="tzCollect_right">${post.getCollectionnum()}</div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <div class="newPending">
            <div class="newPending_head">
                <div class="tzHeng"></div>
                <div class="newPending_head_tittle">最新评论(${post.getPostnum()})</div>
            </div>
            <!--楼主可以删除评论、自己可以删除自己的评论删除按钮酌情出现-->
            <c:forEach var="topreply" items="${top}">
                <div class="newPending_son">
                    <div class="pendPic"><img src="/photo/${topreply.getUser().getPhoto()}"></div>
                    <div class="pendDetail">
                        <div class="pendDetail_head">
                            <p>${topreply.getUser().getName()}
                                <span>
                                    <fmt:formatDate value="${topreply.getReplytime()}"
                                                    pattern="yyyy-MM-dd HH:mm"/>
                            </span></p>
                            <i>${topreply.getFloor()}楼</i>
                        </div>
                        <div class="pendDetail_con">
                            <p>${topreply.getContent()}</p>
                        </div>
                        <div id="floor${topreply.getFloor()}">
                            <div class="subreply">
                                <c:forEach var="subreply" items="${sub}">
                                    <c:if test="${subreply.getFloor()==topreply.getFloor()}">
                                        <div class="pendDetail_replayCon"
                                             id="${topreply.getFloor()}floorex${subreply.getFloorex()}">
                                            <p>
                                                    ${subreply.getUser().getName()} 回复
                                                    ${topreply.getUser().getName()}:
                                                    ${subreply.getContent()}
                                            </p>
                                            <c:if test="${userSession.getUsername()==topreply.getReplyer() or
                                post.getUser().getUsername()==userSession.getUsername() or
                                subreply.getReplyer()==userSession.getUsername()}">
                                                <a href="javascript:void(0);"
                                                   onclick="comment(${post.getId()},
                                                       ${topreply.getFloor()},1,
                                                       ${subreply.getFloorex()})">
                                                    删除</a>
                                            </c:if>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div class="pendDetail_btn">
                                <ul>
                                    <li class="replayBtn">回复</li>
                                    <c:if
                                            test="${topreply.getReplyer()==userSession.getUsername() or
                                post.getUser().getUsername()==userSession.getUsername()}">
                                        <form method="post" action="/Post/delreply" id="delreply">
                                            <!--删除回复-->
                                            <input type="hidden" name="postid"
                                                   value="${post.getId()}">
                                            <input type="hidden" name="floor"
                                                   value="${topreply.getFloor()}">
                                            <input class="delateBtn" type="submit" value="删除">
                                            </input>
                                        </form>
                                    </c:if>
                                </ul>
                            </div>
                            <div class="pendDetail_action">
                                <!--新增楼中楼-->
                                <p>回复${topreply.getUser().getName()}</p>
                                <input type="text" name="comment"/>
                                    <%--<button onclick="comment(${post.getId(),topreply.getFloor()})"
                                            type="button">评论--%>
                                <button
                                        onclick="comment(${post.getId()},
                                            ${topreply.getFloor()},0)"
                                        type="button">评论
                                </button>
                                <button type="button">取消</button>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
            </c:forEach>
        </div>
        <div class="indexFooter">
            <div class="indexFooter_con">
                <c:if test="${nowpage>1}">
                    <a href="/BBS/post?param=${post.getId()}&param1=${nowpage-1}"><</a>
                </c:if>
                <c:forEach var="page" begin="${nowpage-2>0?nowpage-2:1}"
                           end="${pages-nowpage>=2?nowpage+2:pages}">
                    <c:choose>
                        <c:when test="${page==nowpage}">
                            <a href="/BBS/post?param=${post.getId()}&param1=${page}"
                               class="on">${page}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="/BBS/post?param=${post.getId()}&param1=${page}">${page}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${nowpage!=null and nowpage !=pages}">
                    <a href="/BBS/post?param=${post.getId()}&param1=${nowpage+1}">></a>
                </c:if>
            </div>
        </div>
        <div class="writePending">
            <div class="writePending_con">
                <form id="newreply" action="/Post/newreply" method="post">
                    <input type="hidden" id="content" name="content">
                    <input type="hidden" id="postid" name="postid" value="${post.getId()}">
                    <div id="editor"></div>
                    <center>
                        <button type="button" onclick="newreply()">回复</button>
                    </center>
                </form>
            </div>
        </div>
    </div>
    <div class="indexMain_right">
        <div class="myMsg">
            <div class="myMsg_con">
                <div class="myMsg_conPic">
                    <img src="/photo/${post.getUser().getPhoto()}">
                </div>
                <p>${post.getUser().getName()}</p>
            </div>
            <div class="myMsg_footer">
                <ul>
                    <li><a href="">
                        <p>主题数</p>
                        <p>${owner.getMyPostnum()}</p>
                    </a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="clear"></div>
</div>
<footer class="publicFooter">
    <p>Copyrigh &copy; 2017-2018 PaiWang 上海钦合投资管理有限公司版权所有 沪ICP备16032224号-2</p>
</footer>
</body>
</html>
<script src="/js/jquery-1.8.3.min.js"></script>
<script src="/js/tiezi.js"></script>
<script>

</script>