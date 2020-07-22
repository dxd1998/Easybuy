<%@page import="com.dxd.pojo.EasybuyUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	var contextPath = "${ctx}";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>易买网</title>
</head>
<body>
	<script type="text/javascript" src="${ctx}/statics/js/pager/pager.js"></script>
	<!--Begin Header Begin-->
	<%@ include file="/common/pre/header.jsp"%>
	<%@ include file="/common/backend/searchBar.jsp"%>
	<!--End Header End-->
	<div class="i_bg bg_color">
		<!--Begin 用户中心 Begin -->
		<div class="m_content">
			<!-- 左侧Begin -->
			<%@ include file="/common/backend/leftBar.jsp"%>
			<!-- 修改密码 -->
			<div class="m_right" id="content">
				<div class="mem_tit">权限分配</div>
				<p align="right">
					<c:forEach var="level1" items="${sessionScope.easybuyUserLogin.powerList }">
						<c:forEach var="level2" items="${level1.powerTwo }">
							<c:if test="${level2.pId == 15 }">
								<c:forEach var="level3" items="${level2.powerThree }">
									${level3.pName}
									<!-- Modal -->
									<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
									  <div class="modal-dialog"  style="width:900px; role="document">
									    <div class="modal-content">
									      <div class="modal-header">
									        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
									        <h4 class="modal-title" id="myModalLabel">权限添加</h4>
									      </div>
									      <div class="modal-body">
									          <!-- 添加权限主体 -->
									          <form>
												  <div class="form-group">
												    <label for="exampleInputEmail1">权限等级:</label>
												    <select id="pType" onchange="typeChange(this)" class="form-control">
												    	<option value="0">请选择</option>
												    	<option value="1">目录</option>
												    	<option value="2">菜单</option>
												    	<option value="3">按钮</option>
												    </select>
												  </div>
												  <div class="form-group" id="parentPower">
												    <label for="exampleInputEmail1">父级权限:</label>
												    <select id="parentId" disabled="disabled" class="form-control">
												    	<option value="0">请选择</option>
												    </select>
												  </div>
												  <div style="display: none;font-weight:bolder;color:red;" id="tishi">
												  		
												  </div>
												  <div class="form-group">
												    <label for="exampleInputEmail1">权限名称:</label>
												    <input type="text" class="form-control" onblur="checkPowerName()" id="pName" placeholder="请输入权限名称">
												  </div>
												  <div class="form-group">
												    <label for="exampleInputEmail1">权限Dom:</label>
												    <input type="text" class="form-control" id="pDom" placeholder="请输入权限DoM">
												  </div>
												   <div class="form-group">
												    <label for="exampleInputEmail1">权限描述:</label>
												    <input type="text" class="form-control" id="pDesc" placeholder="请输入权限描述">
												  </div>
												</form>
									      </div>
									      <div class="modal-footer">
									        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModal()">关闭</button>
									        <button type="button" class="btn btn-primary" id="addPowerDom" onclick="addPower()">添加</button>
									      </div>
									    </div>
									  </div>
									</div>
								</c:forEach>
							</c:if>
						</c:forEach>
					</c:forEach>
				</p>
				<br/><br/>
				<span style="font-weight:bolder;font-size:14px;margin-left:200px;">用户姓名:</span>
				<div class="form-group" style="margin-left:255px;">
				    <select id="userName" onchange="getPower(this)" class="form-control" style="height:35px;width:200px;">
				    	<option value="0">请选择</option>
				    	<c:forEach var="user" items="${requestScope.userList }">
				    		<option value="${user.id }">${user.userName}</option>
				    	</c:forEach>
				    </select>
			  	</div>
			  	<span style="font-weight:bolder;font-size:14px;margin-left:200px;">二级权限:</span>
				<div class="form-group" style="margin-left:255px;">
				    <!-- 展示所有二级权限 -->
				    <c:forEach var="level2" items="${requestScope.level2 }" varStatus="status">
				    	<input type="checkbox" name="power2" value="${level2.pId}"/>
				    	<span>${level2.pDesc}</span>
				    	&nbsp;&nbsp;
				    	<c:if test="${status.index+1 == 8 }">
				    		<br/>
				    	</c:if>
				    </c:forEach>
			  	</div>
			  	<span style="font-weight:bolder;font-size:14px;margin-left:200px;">三级权限:</span>
				<div class="form-group" style="margin-left:255px;">
				    <!-- 展示所有二级权限 -->
				    <c:forEach var="level3" items="${requestScope.level3 }" varStatus="status">
				    	<input type="checkbox" name="power3" value="${level3.pId}"/>
				    	<span>${level3.pDesc}</span>
				    	&nbsp;&nbsp;
				    	<c:if test="${status.index+1 == 8 }">
				    		<br/>
				    	</c:if>
				    </c:forEach>
			  	</div><br/><br/>
			  	<a href="javascript:void(0);"style="margin-right:400px;" onclick="savePower()" class="add_b">确认修改</a>
			</div>
		</div>
		<!--End 用户中心 End-->
		
		<!--Begin Footer Begin -->
		<%@ include file="/common/pre/footer.jsp"%>
		<!--End Footer End -->
	</div>
</body>
</html>