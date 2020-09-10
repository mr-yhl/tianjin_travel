<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>注册成功</title>

</head>
<body>
<!--引入头部-->
<%@include file="header.jsp"%>
<!-- 头部 end -->
<div style="text-align:center;height: 290px;margin-top: 50px">
    <span style="font-size: 30px">恭喜您，注册成功！</span>
    <div><span id="spanInfo" style="color: red">5</span>秒后，跳转到 <a href="${pageContext.request.contextPath}/index.jsp">首页</a> </div>
    <script>
        // 5秒倒计时
        let num = 5;

        setInterval(function () {
            num --;
            $('#spanInfo').text(num);// 重置页面提示
            if(num == 0){
                location.href='${pageContext.request.contextPath}/index.jsp'
            }
        },1000);
    </script>
</div>
<!--引入尾部-->
<%@include file="footer.jsp"%>
</body>
</html>
