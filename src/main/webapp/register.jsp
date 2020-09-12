<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>注册</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
</head>
<body>
<!--引入头部-->
<%@include file="header.jsp"%>
<!-- 头部 end -->
<div class="rg_layout">
    <div class="rg_form clearfix">
        <%--左侧--%>
        <div class="rg_form_left">
            <p>新用户注册</p>
            <p>USER REGISTER</p>
        </div>
        <div class="rg_form_center">
            <!--注册表单-->
            <form id="registerForm" action="${pageContext.request.contextPath}/UserServlet" method="post">
                <!--提交处理请求的标识符-->
                <input type="hidden" name="action" value="register">
                <table style="margin-top: 25px;width: 558px">
                    <tr>
                        <td class="td_left">
                            <label for="username">用户名</label>
                        </td>
                        <td class="td_right">
                            <input type="text" id="username" name="username" placeholder="请输入账号">
							<span id="userInfo" style="font-size:10px"></span>
                        </td>
                        <script>
                            // 绑定事件
                            $("#username").blur(function() {
                            // 获取属性值
                            let username = this.value;
                            // 通过ajax发送请求使用get函数
                            let url = '${pageContext.request.contextPath}/UserServlet';
                            let data = 'action=ajaxCheackUsername&username='+username;
                            $.get(url,data,function (resp) {
                                // 处理结果
                                //alert(resp.message)
                                if (resp.success){
                                    $("#userInfo").css("color","green").html(resp.message);
                                }else {

                                    $("#userInfo").css("color","red").html(resp.message);


                                }

                            });
                            });
                        </script>
                    </tr>
                    <tr>
                        <td class="td_left">
                            <label for="telephone">手机号</label>
                        </td>
                        <td class="td_right">
                            <input type="text" id="telephone" name="telephone" placeholder="请输入您的手机号">
                            <span id="telInfo" style="font-size:10px"></span>
                        </td>
                        <script>
                            // 绑定事件
                            $("#telephone").blur(function() {
                                // 获取属性值
                                let telephone = this.value;
                                // 通过ajax发送请求使用get函数

                                        // 校验通过
                                        let url = '${pageContext.request.contextPath}/UserServlet';
                                        let data = 'action=ajaxCheackTelephone&telephone='+telephone;
                                        $.get(url,data,function (resp) {
                                            // 处理结果
                                            //alert(resp.message)
                                            if (resp.success){
                                                $("#telInfo").css("color","green").html(resp.message);
                                                $("#sendSmsCode").prop("disabled",false);
                                            }else {

                                                $("#telInfo").css("color","red").html(resp.message);
                                                $("#sendSmsCode").prop("disabled",true);

                                            }

                                        });



                            });
                        </script>
                    </tr>
                    <tr>
                        <td class="td_left">
                            <label for="password">密码</label>
                        </td>
                        <td class="td_right">
                            <input type="password" id="password" name="password" placeholder="请输入密码">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                            <label for="smsCode">验证码</label>
                        </td>
                        <td class="td_right check">
                            <input type="text" id="smsCode" name="smsCode" class="check" placeholder="请输入验证码">
                          
                            <input id="sendSmsCode" value="发送手机验证码" class="btn btn-link"/>
                            <script>
                                $("#sendSmsCode").click(function () {
                                    let telephone = $("#telephone").val();

                                    let reg = /^1[3456789]\d{9}$/;
                                    if (reg.test(telephone)) {
                                        let url = '${pageContext.request.contextPath}/UserServlet';
                                        let data = 'action=ajaxSendSms&telephone='+telephone;
                                        $.get(url,data,function (resp) {
                                            // 处理结果
                                            alert(resp.message);

                                        })
                                        countDown(this);
                                }else {
                                    alert("手机号不合法");
                                }
                                });
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                        </td>
                        <td class="td_right check">
                            <input type="submit" class="submit" value="注册">
                            <span id="msg" style="color: red;">${message}</span>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <%--右侧--%>
        <div class="rg_form_right">
            <p>
                已有账号？
                <a href="javascript:$('#loginBtn').click()">立即登录</a>
            </p>
        </div>
    </div>
</div>
<!--引入尾部-->
<%@include file="footer.jsp"%>


</body>
</html>
