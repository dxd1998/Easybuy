<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>易买网</title>
</head>
<body>

	<!--Begin Header Begin-->
	<%@ include file="/common/pre/header.jsp"%>

	<%@ include file="/common/backend/searchBar.jsp"%>
	<!--End Header End-->
	<div class="i_bg bg_color">
		<!--Begin 用户中心 Begin -->
		<div class="m_content">
			<!-- 左侧Begin -->
			<%@ include file="/common/backend/leftBar.jsp"%>
			<!-- 右侧动态加载Begin -->
			<div class="m_right">
				<div class="mem_tit">用户列表</div>
				<p align="right">
					<c:forEach var="level1" items="${sessionScope.easybuyUserLogin.powerList }">
						<c:forEach var="level2" items="${level1.powerTwo }">
							<c:if test="${level2.pId == 10 }">
								<c:forEach var="level3" items="${level2.powerThree }">
									${level3.pName}
								</c:forEach>
							</c:if>
						</c:forEach>
					</c:forEach>
				</p>
				<br/><br/>
				<table border="0" class="order_tab"
					style="width: 930px; text-align: center; margin-bottom: 30px;"
					cellspacing="0" cellpadding="0">
					<tr>
						<td width="155">用户昵称</td>
						<td width="155">真实姓名</td>
						<td width="155">用户性别</td>
						<td width="155">用户类型</td>
						<td width="155" colspan="2">用户操作</td>
					</tr>
					<input type="hidden" id="currentPage" value="${pager.currentPage}"/>
					<c:forEach items="${list}" var="listUser">
						<tr>
							<td>${listUser.loginName }</td>
							<td>${listUser.userName }</td>
							<td><c:if test="${listUser.sex==1 }">男</c:if>
								<c:if test="${listUser.sex==2 }">女</c:if></td>
							<td><c:if test="${listUser.type==1 }">管理员</c:if>
								<c:if test="${listUser.type==0 }">用户</c:if></td>
							<c:forEach var="level1" items="${sessionScope.easybuyUserLogin.powerList }">
								<c:forEach var="level2" items="${level1.powerTwo }">
									<c:if test="${level2.pId == 10}">
										<c:forEach var="level3" items="${level2.powerThree }">
											<c:if test="${level3.pId != 17 }">
												<td>
													<input type="hidden" value="${listUser.id}"/>
													${level3.pName }
												</td>
											</c:if>
										</c:forEach>
									</c:if>
								</c:forEach>
							</c:forEach>
						</tr>
					</c:forEach>
				</table>
				<%@ include file="/common/pre/pagerBar.jsp"%>
			</div>
		</div>
		<!--End 用户中心 End-->

		<!--Begin Footer Begin -->
		<%@ include file="/common/pre/footer.jsp"%>
		<!--End Footer End -->
	</div>
	<script type="text/javascript" src="${ctx}/statics/js/backend/user.js"></script>
</body>
</html>