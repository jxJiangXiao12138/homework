<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>管理员登录</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="${pageContext.request.contextPath}/js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        //切换验证码
        function refreshCode(){
            //1.获取验证码图片对象
            var vcode = document.getElementById("vcode");

            //2.设置其src属性，加时间戳
            vcode.src = "${pageContext.request.contextPath}/checkCode?time="+new Date().getTime();
        }

        $(function () {
            <%--$("#login").click(function () {--%>
                <%--$.post("${pageContext.request.contextPath}/user/login", "username=" + $("#username").val() + "&password=" + $("#password").val()+"&checkCode="+$("#checkCode").val(), function (data) {--%>
                    <%--if (data.flag) {--%>
                        <%--//存储用户cookie--%>
                        <%--if ($("#check").prop("checked")) {--%>
                            <%--//alert($("#check").prop("checked"));--%>
                            <%--//保存cookie--%>
                            <%--saveCookie(true);--%>
                        <%--} else {--%>
                            <%--//删除cookie--%>
                            <%--saveCookie(false);--%>
                        <%--}--%>
                    <%--} else {--%>
                        <%--$("#errorMsg").html(data.errorMsg);--%>
                    <%--}--%>
                    <%--//成功--%>
                    <%--location.href = "index.html";--%>

                <%--});--%>
        //         //  return false;
        //     });
        //     //页面加载时需要判断cookie是否存在
        //     if ($.cookie("flag")) {
        //         $("#username").val($.cookie("username"));
        //         $("#password").val($.cookie("password"));
        //         $("#check").prop("checked", "checked");
        //     } else {
        //
        //     }
        //
        // });
    </script>
</head>
<body>
<div class="container" style="width: 400px;">
    <h3 style="text-align: center;">管理员登录</h3>
    <form action="${pageContext.request.contextPath}/user/login" method="post">
        <div class="form-group">
            <label for="username">用户名：</label>
            <input type="text" name="username" class="form-control" id="username" placeholder="请输入用户名"/>
        </div>

        <div class="form-group">
            <label for="password">密码：</label>
            <input type="password" name="password" class="form-control" id="password" placeholder="请输入密码"/>
        </div>

        <div class="form-inline">
            <label for="checkCode">验证码：</label>
            <input type="text" name="checkCode" class="form-control" id="checkCode" placeholder="请输入验证码" style="width: 120px;"/>
            <a href="javascript:refreshCode();">
                <img src="${pageContext.request.contextPath}/checkCode" title="看不清点击刷新" id="vcode"/>
            </a>
        </div>
        <hr/>
        <div class="form-group" style="text-align: center;">
            <input class="btn btn btn-primary" type="submit" id="login" value="登录">
            <div class="auto_login">
                <input type="radio" name="check" value="flag" id="check">
                <span>记住密码</span>
            </div>

        </div>
    </form>

    <!-- 出错显示的信息框 -->
    <div class="alert alert-warning alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" >
            <span>&times;</span>
        </button>
        <strong>${login_msg}</strong>
    </div>
</div>
</body>
</html>