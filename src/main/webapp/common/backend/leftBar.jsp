<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="m_left">
	<div class="left_n">管理中心</div>
	<input type="hidden" id="userId" value="${sessionScope.easybuyUserLogin.id }"/>
	<c:forEach var="level1" items="${sessionScope.easybuyUserLogin.powerList}">
	  <div class="left_m">
			<!-- 一级菜单 -->
			${level1.pName}
			<!-- 二级菜单 -->
			<ul>
				<c:if test="${level1.pId == 26 }">
					<li><a href="${ctx }/spring/user/menuTree" class="now">权限菜单</a></li>
				</c:if>
				<c:forEach var="level2" items="${level1.powerTwo}">
					<c:if test="${level2.pId != 48 }">
						${level2.pName}
					</c:if>
				</c:forEach>
			</ul>
	  </div>
	</c:forEach>
</div>
<script type="text/javascript" src="${ctx}/statics/js/leftBar/left.js"></script>
<script src="${ctx}/statics/js/backend/Product.js"></script>
<script src="${ctx}/statics/js/backend/backend.js"></script>