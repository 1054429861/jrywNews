<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<aside class="sidebar">
    <div class="fixed">
        <div class="widget widget-tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active">
                    <a href="#notice" id="tab1val" aria-controls="notice" role="tab" data-toggle="tab">个人中心</a></li>
                <li role="presentation">
                    <a href="#contact" id="tab2val" aria-controls="contact" role="tab" data-toggle="tab">联系站长</a>
                </li>
            </ul>
            <div class="tab-content">

                <%--登录界面--%>
                <div role="tabpanel" class="tab-pane contact active" id="notice">
                    <div class="login-center-input" id="userlogin" style="margin-left: 20px">
                        <div class="login-center-img" style="display: inline">
                            <img src="../resources/admin/login/images/name.png" width="25px" height="25px">
                        </div>
                        <input class="form-control" style="width: 150px;height: 30px;display: inline" type="text"
                               name="username" id="username" value="" placeholder="请输入您的用户名"
                               onfocus="this.placeholder=&#39;&#39;" onblur="this.placeholder=&#39;请输入您的用户名&#39;">
                        <div></div>
                        <div class="login-center-img" style="display: inline">
                            <img src="../resources/admin/login/images/password.png" width="25px" height="25px">
                        </div>
                        <input class="form-control" style="width: 150px;height: 30px;display: inline" type="password"
                               name="password" id="password" value="" placeholder="请输入您的密码"
                               onfocus="this.placeholder=&#39;&#39;" onblur="this.placeholder=&#39;请输入您的密码&#39;">
                        <div></div>
                        <div class="login-center-input">
                            <div class="login-center-img" style="display: inline">
                                <img src="../resources/admin/login/images/cpacha.png" width="25px" height="25px">
                            </div>
                            <input class="form-control" style="width: 150px;height: 30px;display: inline" type="text"
                                   name="cpacha" id="cpacha" value="" placeholder="请输入验证码"
                                   onfocus="this.placeholder=&#39;&#39;" onblur="this.placeholder=&#39;请输入验证码&#39;">

                            <img id="cpacha-img" title="点击刷新验证码" style="cursor:pointer;"
                                 src="../system/get_cpacha?vl=4&w=150&h=40&type=loginCpacha" width="100px" height="25px"
                                 onclick="changeCpacha()">
                        </div>
                        <div style="margin-left: 35px;margin-top: 10px">
                            <a class="btn btn-default  login-button">登录</a>
                            <a class="btn btn-default  register-button" style="margin-left: 30px">注册</a>
                        </div>
                    </div>
                    <%--个人信息界面--%>
                    <div style="display: none" id="afterlogin">
                        <p><strong class="easyui-tooltip" title="">${user.username}</strong>，欢迎您！</p>
                        <img src="${user.photo}" title="点击更换头像" width="60px" height="60px" onclick="uploadPhoto()">
                    </div>
                    <input type="file" id="photo-file" style="display:none;" onchange="upload()">
                    <div style="margin-bottom: 10px"></div>
                    <a href="../index/frontlogout" class="btn btn-default logout-button">登出</a>
                    <a href="../index/edit_password" class="btn btn-default logout-button">修改密码</a>
                </div>
                    <div role="tabpanel" class="tab-pane contact" id="contact">
                        <h2>QQ:
                            <a href="http://wpa.qq.com/msgrd?v=3&uin=1054429861&site=qq&menu=yes" target="_blank"
                               rel="nofollow" data-toggle="tooltip" data-placement="bottom" title=""
                               data-original-title="点击创建会话">1054429861</a>
                        </h2>
                        <h2>Email:
                            <a href="mailto:1054429861@qq.com" target="_blank" data-toggle="tooltip" rel="nofollow"
                               data-placement="bottom" title="" data-original-title="点击发送邮件">1054429861@qq.com</a></h2>
                    </div>
            </div>


        </div>
    </div>
    </div>
    <div class="widget widget_search">
        <form class="navbar-form" action="../news/search_list" method="get">
            <div class="input-group">
                <input type="text" name="keyword" class="form-control" size="35" placeholder="请输入关键字" maxlength="15"
                       autocomplete="off" value="${keyword }">
                <span class="input-group-btn">
		<button class="btn btn-default btn-search" name="search" type="submit">搜索</button>
		</span></div>
        </form>
    </div>
    </div>
    <div class="widget widget_hot">
        <h3>热门评论文章</h3>
        <ul id="last-comment-list">


        </ul>
    </div>
    <div class="widget widget_sentence">
        <a href="#" target="_blank" rel="nofollow" title="">
            <img style="width: 100%" src="../resources/home/images/ad1.jpg" alt="砥砺前行"></a>
    </div>
    <div class="widget widget_sentence">
        <a href="#" target="_blank" rel="nofollow" title="抗击疫情">
            <img style="width: 100%;height: 385px" src="../resources/home/images/ad2.jpg" alt="抗击疫情"></a>
    </div>
    <div class="widget widget_sentence">
        <h3>友情链接</h3>
        <div class="widget-sentence-link">
            <a href="https://news.163.com/" title="163新闻" target="_blank">网易新闻</a>&nbsp;&nbsp;&nbsp;
            <a href="http://www.jeasyui.net/" title="easyui" target="_blank">easyui</a>&nbsp;&nbsp;&nbsp;
            <a href="https://www.bootcss.com/" title="bootstrop" target="_blank">bootstrop</a>&nbsp;&nbsp;
            <a href="https://jquery.com/" title="jquery" target="_blank">jquery</a>&nbsp;
        </div>
    </div>
</aside>

<script type="text/javascript" src="../resources/admin/easyui/easyui/1.3.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../resources/admin/easyui/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
    /*首页登录*/
    $(function () {
        var user = "<%= session.getAttribute("user")%>";
        if (user != 'null' && user != "") {
            $("#userlogin").attr("style", 'display: none');
            $("#afterlogin").attr("style", 'display: block');
            $("#tab1val").text("我的信息");
        }
        ;

    })

    //上传图片
    function start() {
        var value = $('#p').progressbar('getValue');
        if (value < 100) {
            value += Math.floor(Math.random() * 10);
            $('#p').progressbar('setValue', value);
        } else {
            $('#p').progressbar('setValue', 0)
        }
    };

    function upload() {
        if ($("#photo-file").val() == '') return;
        var formData = new FormData();
        formData.append('photo', document.getElementById('photo-file').files[0]);
        $("#process-dialog").dialog('open');
        var interval = setInterval(start, 200);
        $.ajax({
            url: 'upload_photo',
            type: 'post',
            data: formData,
            contentType: false,
            processData: false,
            success: function (data) {
                clearInterval(interval);
                $("#process-dialog").dialog('close');
                if (data.type == 'success') {
                    $("#preview-photo").attr('src', data.filepath);
                    $("#add-photo").val(data.filepath);
                    $("#edit-preview-photo").attr('src', data.filepath);
                    $("#edit-photo").val(data.filepath);
                    alert("上传成功");
                    window.parent.location = 'index';
                } else {
                    alert(data.msg);

                }
            },
            error: function (data) {
                clearInterval(interval);
                $("#process-dialog").dialog('close');
                alert("上传失败");
            }
        });
    }

    function uploadPhoto() {
        $("#photo-file").click();
    }

    function changeCpacha() {
        //验证码
        $("#cpacha-img").attr("src", '../system/get_cpacha?vl=4&w=150&h=40&type=loginCpacha&t=' + new Date().getTime());
    }

    function add0(m) {
        return m < 10 ? '0' + m : m
    }

    function format(shijianchuo) {
//shijianchuo是整数，否则要parseInt转换
        var time = new Date(shijianchuo);
        var y = time.getFullYear();
        var m = time.getMonth() + 1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
    }

    $(document).ready(function () {
        $.ajax({
            url: '../news/last_comment_list',
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if (data.type == 'success') {
                    var newsList = data.newsList;
                    var html = '';
                    for (var i = 0; i < newsList.length; i++) {
                        var li = '<li><a title="' + newsList[i].title + '" href="../news/detail?id=' + newsList[i].id + '" ><span class="thumbnail">';
                        li += '<img class="thumb" data-original="../resources/home/images/201610181739277776.jpg" src="' + newsList[i].photo + '" alt="' + newsList[i].id + '"  style="display: block;">';
                        li += '</span><span class="text">' + newsList[i].title + '</span><span class="muted"><i class="glyphicon glyphicon-time"></i>';
                        li += format(newsList[i].createTime) + '</span><span class="muted"><i class="glyphicon glyphicon-eye-open"></i>' + newsList[i].viewNumber + '</span></a></li>';
                        html += li;
                    }
                    $("#last-comment-list").append(html);
                }
            }
        });
        $.ajax({
            url: '../index/get_site_info',
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if (data.type == 'success') {
                    $("#total-article-span").text(data.totalArticle);
                    $("#sitetime").text(data.siteDays);
                }
            }
        });
    });
    //登录按钮被点击
    document.querySelector(".login-button").onclick = function () {
        var username = $("#username").val();
        var password = $("#password").val();
        var cpacha = $("#cpacha").val();
        if (username == '' || username == 'undefined') {
            alert("请填写用户名！");
            return;
        }
        if (password == '' || password == 'undefined') {
            alert("请填写密码！");
            return;
        }
        if (cpacha == '' || cpacha == 'undefined') {
            alert("请填写验证码！");
            return;
        }
        $.ajax({
            url: '../index/frontlogin',
            data: {username: username, password: password, cpacha: cpacha},
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if (data.type == 'success') {
                    window.parent.location = window.location.href;
                } else {
                    alert(data.msg);
                    changeCpacha();
                }
            }
        });
    }


    //注册按钮被点击
    document.querySelector(".register-button").onclick = function () {
        var username = $("#username").val();
        var password = $("#password").val();
        var cpacha = $("#cpacha").val();
        if (username == '' || username == 'undefined') {
            alert("请填写用户名！");
            return;
        }
        if (password == '' || password == 'undefined') {
            alert("请填写密码！");
            return;
        }
        if (cpacha == '' || cpacha == 'undefined') {
            alert("请填写验证码！");
            return;
        }
        $.ajax({
            url: '../index/frontregister',
            data: {username: username, password: password, cpacha: cpacha},
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if (data.type == 'success') {
                    alert("注册成功")
                    window.parent.location = window.location.href;
                } else {
                    alert(data.msg);
                    changeCpacha();
                }
            }
        });
    }
</script>