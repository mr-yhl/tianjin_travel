<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/webbase.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages-seckillOrder.css">
    <title>地址管理</title>

</head>
<body>
<!--引入头部-->
<%@include file="header.jsp"%>
<div class="container-fluid">
    <!--header-->
    <div id="account">
        <div class="py-container">
            <div class="yui3-g home">
                <!--左侧列表-->
                <%@include file="home_left.jsp"%>
                <!--右侧主内容-->
                <div class="yui3-u-5-6 order-pay">
                    <div class="body userAddress">
                        <div class="address-title">
                            <span class="title">地址管理</span>
                            <a data-toggle="modal" data-target="#addressModel" data-keyboard="false"   class="sui-btn  btn-info add-new">添加新地址</a>
                            <span class="clearfix"></span>
                        </div>
                        <div class="address-detail">
                            <table class="sui-table table-bordered">
                                <thead>
                                <tr>

                                    <th>姓名</th>
                                    <th>地址</th>
                                    <th>联系电话</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${list}" var="address">

                                    <tr>
                                        <%--在这有个妈卖批要讲,这个变量因为单词写错了,卡了我半小时的时间--%>

                                        <td>${address.contact}</td>
                                        <td>${address.address}</td>
                                        <td>${address.telephone}</td>
                                        <td>
                                            <a href="#" onclick="update(this,${address.aid})">编辑</a>
                                            <a href="#" onclick="delethis('${address.contact}','${address.address}','${address.telephone}','${address.aid}')">删除</a>
                                            <c:if test="${address.isdefault == 0}">
                                                <a href="${pageContext.request.contextPath}/AddressServlet?action=setupthis&aid=${address.aid}">设为默认</a>
                                            </c:if>
                                            <c:if test="${address.isdefault == 1}">
                                                <span style="color: #1ba157">默认地址</span>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>


                        <!-- 地址模态框 -->
                        <div class="modal fade" id="addressModel" tabindex="-1" role="dialog" aria-labelledby="loginModelLable">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <%-- 新增地址--%>
                                    <div class="tab-pane fade in active" >
                                        <form id="xxxx" action="${pageContext.request.contextPath}/AddressServlet" method="post">
                                            <input type="hidden" name="action" value="save">
                                            <div class="modal-body">
                                                <div class="form-group">
                                                    <label>姓名</label>
                                                    <input type="text" class="form-control" name="contact"
                                                           placeholder="姓名">
                                                </div>
                                                <div class="form-group">
                                                    <label>地址</label>
                                                    <input type="text" class="form-control" name="address"
                                                           placeholder="请输入地址">
                                                </div>
                                                <div class="form-group">
                                                    <label>联系电话</label>
                                                    <input type="text" class="form-control" name="telephone"
                                                           placeholder="联系电话">
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <input type="button" class="btn btn-default" data-dismiss="modal"  value="关闭">
                                                <input type="submit" class="btn btn-primary" value="保存"/>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <!-- 地址模态框 -->
                        <div class="modal fade" id="addressMode3" tabindex="-1" role="dialog" aria-labelledby="loginModelLable">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <%-- 新增地址--%>
                                    <div class="tab-pane fade in active" >
                                        <form id="x" action="${pageContext.request.contextPath}/AddressServlet" method="post">
                                            <input type="hidden" name="action" value="deleAddress">
                                            <input type="hidden" name="aid1" id="aid1">
                                            <center>确认要删除吗</center>
                                            <div class="modal-body">
                                                <div class="form-group">
                                                    <label>姓名</label>
                                                    <input type="text" class="form-control" name="delcontact" id="delcontact"
                                                           placeholder="姓名">
                                                </div>
                                                <div class="form-group">
                                                    <label>地址</label>
                                                    <input type="text" class="form-control" name="deladdress" id="deladdress"
                                                           placeholder="请输入地址">
                                                </div>
                                                <div class="form-group">
                                                    <label>联系电话</label>
                                                    <input type="text" class="form-control" name="deltelephone" id="deltelephone"
                                                           placeholder="联系电话">
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <input type="button" class="btn btn-default" data-dismiss="modal"  value="关闭">
                                                <input type="submit" class="btn btn-primary" value="删除"/>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <script>
                            function delethis(concact,address,telephone,aid){
                                $("#aid1").val(aid);
                                $("#delcontact").val(concact);
                                $("#deladdress").val(address);
                                $("#deltelephone").val(telephone);
                                $('#addressMode3').modal({show:true})
                            }
                        </script>

                        <%--修改功能模态框--%>
                        <div class="modal fade" id="addressMode2" tabindex="-1" role="dialog" aria-labelledby="loginModelLable">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">

                                    <div class="tab-pane fade in active" >
                                        <form id="xx" action="${pageContext.request.contextPath}/AddressServlet" method="post">
                                            <input type="hidden" name="action" value="updateByAid">
                                            <input type="hidden" name="aid" id="aid">
                                            <div class="modal-body">

                                                <div class="form-group">
                                                    <label>姓名</label>
                                                    <input type="text" class="form-control" name="upcontact" id="upcontact"
                                                           placeholder="姓名">
                                                </div>
                                                <div class="form-group">
                                                    <label>地址</label>
                                                    <input type="text" class="form-control" name="upaddress" id="upaddress"
                                                           placeholder="请输入地址">
                                                </div>
                                                <div class="form-group">
                                                    <label>联系电话</label>
                                                    <input type="text" class="form-control" name="uptelephone" id="uptelephone"
                                                           placeholder="联系电话">
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <input type="button" class="btn btn-default" data-dismiss="modal"  value="关闭">
                                                <input type="submit" class="btn btn-primary" value="修改"/>
                                            </div>
                                        </form>
                                        <script>
                                            function update(obj,aid) {
                                                let tds= $(obj).parent().parent().find('td');

                                                $("#aid").val(aid);
                                                $("#upcontact").val(tds.eq(0).text());
                                                $("#upaddress").val(tds.eq(1).text());
                                                $("#uptelephone").val(tds.eq(2).text());

                                                $('#addressMode2').modal({show:true})
                                            }
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<!--引入尾部-->
<%@include file="footer.jsp"%>
</body>
</html>
