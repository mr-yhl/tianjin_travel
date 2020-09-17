<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/webbase.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages-weixinpay.css">
    <script src="./js/qrcode.min.js"></script>
    <title>微信支付</title>

</head>
<body>
<!--引入头部-->
<%@include file="header.jsp"%>
<div class="container-fluid">
    <div class="cart py-container">
        <!--主内容-->
        <div class="checkout py-container  pay">
            <div class="checkout-tit">
                <h4 class="fl tit-txt"><span class="success-icon"></span><span  class="success-info">订单提交成功，请您及时付款！订单号：${oid}</span></h4>
                <span class="fr"><em class="sui-lead">应付金额：</em><em  class="orange money">￥${total}</em>元</span>
                <div class="clearfix"></div>
            </div>
            <div class="checkout-steps">
                <div class="fl weixin">微信支付</div>
                <div class="fl sao">
                   <%--<p class="red" style="padding-bottom: 40px">请扫屏幕二维码。</p>--%>
                    <div class="fl code">
                        <div id="qrcode"></div>
                        <script>
                            let qrCode= new QRCode("qrcode",'${payUrl}');
                        </script>
                        <div class="saosao">
                            <p>请使用微信扫一扫</p>
                            <p>扫描二维码支付</p>
                        </div>
                    </div>
                    <div class="fl" style="background:url(./img/phone-bg.png) no-repeat;width:350px;height:400px;margin-left:40px">

                    </div>

                </div>
                <div class="clearfix"></div>
            </div>
        </div>
        <script>
            // 1.每间隔5秒，查询订单支付状态
            let intervalNum =  setInterval(function () {
                // alert(1);
                // 2.发送ajax请求查询
                let url = '${pageContext.request.contextPath}/OrderServlet';
                let data = 'action=isPay&oid=${oid}';
                $.get(url,data,function (resp) {
                    if (resp.success) {
                        // 支付成功
                        location.href='${pageContext.request.contextPath}/pay_success.jsp';
                    }
                })
            },5000);

            // 如果超过10分钟，未支付，微信会关闭支付连接，咱们就跳转到支付失败页面
            setTimeout(function () {
                // 关闭定时器
                clearInterval(intervalNum);
                location.href='${pageContext.request.contextPath}/pay_fail.jsp';
            },1000000)
        </script>
    </div>
</div>
<!--引入尾部-->
<%@include file="footer.jsp"%>
</body>
</html>
