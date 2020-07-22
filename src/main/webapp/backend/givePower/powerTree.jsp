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
				<div class="mem_tit">树状菜单</div>
				<div id="tree">
				</div>
				<script type="text/javascript">
					layui.use(['tree', 'util'], function(){
						//一级权限
						var level1 = ${level1};
						//二级权限
						var level2 = ${level2};
						//三级权限
						var level3 = ${level3};
						//用户已有权限
						var userPower = ${userPower};
						
						  var tree = layui.tree
						  ,layer = layui.layer
						  ,util = layui.util
						  //模拟数据
						  ,data = []
						  //循环一级菜单
						  for(var i=0;i<level1.length;i++){
						  	 var checked = false;
						  	  //循环用户权限
						  	  for(var a = 0;a<userPower.length;a++){
						  	  	if(userPower[a].pId == level1[i].pId){
						  	  		checked = true;
						  	  	}
						  	  }
						  	  //添加一级权限
						  	  data.push({
						  			title : level1[i].pName,
						  			id	:	level1[i].pId,
						  			children : [],
						  			checked : false  
						  	  });
						  	  
						  	  //循环二级权限
						  	  for(var x = 0;x<level2.length;x++){
						  	  	var checked = false;
						  	  	//判断用户是否存在该权限
						  	  	for(var a = 0;a<userPower.length;a++){
							  	  	if(userPower[a].pId == level2[x].pId){
							  	  		checked = true;
							  	  	}
							  	}
							  	
							  	//给一级权限绑定二级权限
							  	if(level2[x].parentId == level1[i].pId){
							  		data[i].children.push({
							  			title	:	level2[x].pName,
							  			id		:	level2[x].pId,
							  			children:	[],
							  			checked :   checked
							  		});
							  	}
							  	//循环三级权限
							  	for(var j = 0;j<level3.length;j++){
							  		var checked = false;
							  		//判断用户是否存在该权限
							  	  	for(var a = 0;a<userPower.length;a++){
								  	  	if(userPower[a].pId == level3[j].pId){
								  	  		checked = true;
								  	  	}
								  	}
								  	
								  	//循环当前一级权限下的二级权限列表
								  	for(var c = 0;c<data[i].children.length;c++){
								  		//给二级权限绑定三级权限
									  	if(level2[x].pId == level3[j].parentId){
									  		//判断当前的二级权限是否存在一级权限的二级权限中
									  		if(data[i].children[c].id == level2[x].pId){
									  			//绑定权限
									  			data[i].children[c].children.push({
									  				title	:	level3[j].pName,
									  				id		:	level3[j].pId,
									  				checked	:	checked
									  			});
									  		}
									  	}
								  	}
							  	}
						  	  }
						  }	
						  //基本演示
						  tree.render({
						    elem: '#tree'
						    ,data: data
						    ,showCheckbox: false  //是否显示复选框
						    ,id: 'demoId1'
						  });
					});
				</script>
			</div>
		</div>
		<!--End 用户中心 End-->
		
		<!--Begin Footer Begin -->
		<%@ include file="/common/pre/footer.jsp"%>
		<!--End Footer End -->
	</div>
</body>
</html>