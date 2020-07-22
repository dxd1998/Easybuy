/**
 * 商品管理删除点击事件！
 * 
 * @returns
 */
function _confirm2(thisDom) {
	mizhu.confirm('温馨提醒', '要删除该商品？', "确定", "取消", function(flag) {
		if (flag) {
			var id = $(thisDom).prev().val();
			//alert(id);
			delProduct(id);
		}
	});
}
/**
 * 根据ID删除商品信息！
 * 
 * @param id
 * @returns
 */
function delProduct(id) {
	$.ajax({
		url : contextPath + "/spring/product/deleteById",
		method : "post",
		data : {
			"id" : id,
		},
		success : function(jsonStr) {
			var result = eval("(" + jsonStr + ")");
			// 删除成功与否弹出信息！
			mizhu.toast(result.message, 1500);
			if (result.status == 1) {
				var currentPage = $("#thisCurrentPage").val();
				var ise = {
					"currentPage" : currentPage
				};
				$(".m_right").load(contextPath+ "/spring/product/index .m_right>*",ise);
			}
		}
	});

}

/**
 * 修改/添加用户信息信息点击事件！
 * 
 * @returns
 */
function s_btn(thisId, currentPage) {
	var id = thisId;
	//alert(id);
	// 获取用户输入的数据！
	var name = $("#loginName").val();
	var userName = $("#userName").val();
	var password = $("#password").val();
	var repPassword = $("#repPassword").val();
	var identityCode = $("#identityCode").val();
	var sex = $("input[name='ra']:checked").val();
	var email = $("#email").val();
	var mobile = $("#mobile").val();
	var user = $("#user").val();
	//alert(user);
	/* 非空验证 */
	if (name == "" || name == null) {
		mizhu.alert('操作提醒', "用户名不能为空！");
		return;
	} else if (userName == "" || userName == null) {
		mizhu.alert('操作提醒', "真实姓名不能为空！");
		return;
	}
	/* 非空验证 */
	if (id == null || id == 0) {
		if (password == "" || password == null) {
			mizhu.alert('操作提醒', "密码不能为空！");
			return;
		} else if (repPassword == null || repPassword == "") {
			mizhu.alert('操作提醒', "确认密码不能为空！");
			return;
		} else if (password != repPassword) {
			mizhu.alert('操作提醒', "两次密码输入不一致请核对！");
			return;
		}
	}
	/* 非空验证 */
	if (identityCode == "" || identityCode == null) {
		mizhu.alert('操作提醒', "身份证号不能为空！！");
		return;
	} else if (mobile == "" || mobile == null) {
		mizhu.alert('操作提醒', "手机号不能为空！");
		return;
		// 验证邮箱格式
	} else if (email != null && email != "" && !checkEamil(email)) {
		mizhu.alert('操作提醒', "邮箱格式不正确！");
		return;
		// 验证手机
	} else if (mobile != null && mobile != "" && !checkMobile(mobile)) {
		mizhu.alert('操作提醒', "手机格式不正确！");
		return;
		// 验证身份证
	} else if (identityCode != null && identityCode != ""
			&& !checkIdentityCode(identityCode)) {
		mizhu.alert('操作提醒', "身份证号格式不正确！");
		return;
	} else {
		//判断用户名是否存在
		$.ajax({
			url		:		contextPath+"/spring/user/loginNameCount&name="+name,
			method	:		"post",
			success	:		function(data){
				var json = eval('('+data+')');
				//alert(json.message);
				//alert(name);
				var oldloginName =$("#oldname").val();
				if(json.status == -1 && name != oldloginName){
					mizhu.alert('操作提醒','该用户名已经存在!');
					return;
				}else{ 
					//执行修改或添加
					var item = {
							"loginName" : name,
							"userName" : userName,
							"password" : password,
							"identityCode" : identityCode,
							"sex" : sex,
							"email" : email,
							"mobile" : mobile,
							"type" : user,
							"id" : thisId,
							"currentPage" :currentPage
						};
						$.post(contextPath + "/spring/user/modify", item,
							function(data) {
								var result = eval("(" + data + ")");
								if (result.message == "添加成功!") {  //添加成功!
									mizhu.toast('操作成功！', 2000);
									//添加成功后跳回用户显示页面
									$(".m_right").load( contextPath + "/spring/user/user .m_right>*");
								}else if(result.message == "更新成功!"){ //修改成功!
									mizhu.toast('更新成功!',1200);
									//修改成功后跳回用户显示页面
									$(".m_right").load( contextPath + "/spring/user/user .m_right>*", "currentPage="+currentPage);
								}
						});
				}
			}
		});
	}
};

/**
 * 验证邮箱正则表达式！
 */
function checkEamil(eamil) {
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (reg.test(eamil)) {
		return true;
	} else {
		return false;
	}
}

/**
 * 验证手机号码正则表达式！
 */
function checkMobile(phone) {
	var reg = /^\d{5,11}$/;
	if (reg.test(phone)) {
		return true;
	} else {
		return false;
	}
}

/**
 * 验证身份证号码正则表达式！
 */
function checkIdentityCode(mem) {
	var reg = /^\w{18}$/;
	if (reg.test(mem)) {
		return true;
	} else {
		return false;
	}
}
/**
 * 修改密码按钮点击事件
 */
function UpdatePwd(){
	//非空验证
	var $loginPwd = $("#loginPwd").val(); //用户原始密码
	var $loginName = $("#loginName").val(); //用户登录名
	//用户输入的值
	var $oldpwd = $("#oldpwd").val();
	var $newpwd = $("#newpwd").val();
	var $repwd = $("#repwd").val();
	//获得提示span
	var $span = $(".tishi");
	//判断原始密码是否正确
	if($oldpwd == ""){
		mizhu.alert("操作提示","原密码不能为空!");
		return;
	}else if($oldpwd != $loginPwd){
		mizhu.alert("操作提示","原密码输入错误!");
		return;
	}else if($newpwd == ""){
		mizhu.alert("操作提示","新密码不能为空!");
		return;
	}else if($repwd != $newpwd){
		mizhu.alert("操作提示","二次密码输入不一致!");
		return
	}else{
		//执行修改,使用ajax
		$.ajax({
			url			:		contextPath+"/spring/user/modifyPassWord",
			method		:		"post",
			data		:		{"loginName":$loginName,"password":$newpwd},
			success		:		function(data){
				var json = eval('('+data+')');
				if(json.status == 1){
					//alert(json.message);
					//跳转至登录页面
					location.href=contextPath+"/spring/user/forgetPwd";
				}
			}
			
		});
	}
}
/**
 * 重置按钮点击事件
 */
function clearAll(){
	var $oldpwd = $("#oldpwd").val("");
	var $newpwd = $("#newpwd").val("");
	var $repwd = $("#repwd").val("");
}
/**
 * 查看咨询详情
 */
function newsDetail(url,newsId){
	
	//使用load方法
	var ise = {"newsId":newsId};
	$(".m_right").load(contextPath+"/"+url+" .m_right>*",ise);
}

/**
 * 查看订单详情
 */
function getOrder(url,orderId){
	//加载订单详情
	$(".m_right").load(contextPath+"/"+url+"?orderId="+orderId+" .m_right>*");
}
/**
 * 确认订单
 */
function checkOrder(orderId,currentPage,isall,userId){
	mizhu.confirm('温馨提醒', '确认收货嘛？','确认', '取消', function(flag){
		//确认收货
		$.ajax({
			url			:		contextPath+"/spring/confirmOrder",
			method		:		"post",
			data		:		{
				orderId	: orderId,
			currentPage : currentPage
			},
			success     : 		function(data){
				var json = eval('('+data+')');
				//判断是否收货成功!
				if(json.status == 1){
					mizhu.toast(json.message,1200);
					//成功,跳回订单页面
					if(isall == "1"){ //操作的是我的订单
						$(".m_right").load(contextPath+"/spring/index?userId="+userId+"&currentPage="+currentPage+" .m_right>*");
					}else{//操作的是全部订单
						$(".m_right").load(contextPath+"/spring/queryAllOrder?currentPage="+currentPage+" .m_right>*");
					}
				}else{
					mizhu.toast(json.message,1200);
				}
			}
		});
	});
}	
	//权限页面下拉列表改变触发器
	function getPower(thisDom){
		//根据用户id查询该用户的对应权限
		var id = $(thisDom).val();
		//获得所有二级权限checkbox
		var checkboxAll2 = $("input:checkbox[name='power2']");
		//获得所有三级权限checkbox
		var checkboxAll3 = $("input:checkbox[name='power3']");
		
		//每次改变之前清除所有选中多选按钮
		$("input:checkbox").removeAttr("checked");
		
		//使用ajax去查询
		$.ajax({
			url		:		contextPath+"/spring/user/getPowerById",
			method	:		"post",
			dataType:		"json",
			data	:		{"id":id},
			success	:		function(data){
				//二级权限
				$.each(checkboxAll2,function(j,checkbox){
					//循环data
					for(var i=0;i<data.length;i++){
						var checkboxValue = $(checkbox).val();
						if(data[i].pId == checkboxValue){
							$(checkbox).prop("checked",true);
						}
					}
				});
				//三级权限
				$.each(checkboxAll3,function(j,checkbox){
					//循环data
					for(var i=0;i<data.length;i++){
						var checkboxValue = $(checkbox).val();
						if(data[i].pId == checkboxValue){
							$(checkbox).prop("checked",true);
						}
					}
				});
			}
		});
	}
	//保存权限修改
	function savePower(){
		//获得需要修改权限的用户
		var id = $("#userName").val();
		//获得所有二级权限
		var checkboxAll2 = $("input:checkbox[name='power2']:checked");
		//二级权限数组
		var array2 = new Array();
		//获得所有三级权限
		var checkboxAll3 = $("input:checkbox[name='power3']:checked");
		//三级权限数组
		var array3 = new Array();
		
		//向二级权限数组添加数据
		$.each(checkboxAll2,function(j,checkbox){
			var checkboxValue = $(checkbox).val();
			array2.push(checkboxValue);
		});
		//向三级权限数组添加数据
		$.each(checkboxAll3,function(j,checkbox){
			var checkboxValue = $(checkbox).val();
			array3.push(checkboxValue);
		});
		//使用ajax
		var ise = {
			id:id,
			array2:array2,
			array3:array3
		}
		$.ajax({
			url		:	 	contextPath+"/spring/user/savePower",
			method	:		"post",
			data	:		ise,
			traditional	:	true,
			success	:		function(data){
				var json = eval('('+data+')');
				alert(json.message);
			}
		});
	}
	
	//模态框下拉列表改变触发器
	function typeChange(thisDom){
		var typeId = $(thisDom).val();
		$("#parentId").removeAttr("disabled"); //先取消禁用
		$("#parentId").prop("disabled",true);
		if(typeId == "0" || typeId == "1"){ //未选择和选择目录时
			$("#parentId").append("<option value=\"0\">请选择</option>");
			//添加权限名称模板
			$("#pDom").val("<div class=\"left_m_t t_bg1\">自定义</div>");
			//禁用父类权限选择
			$("#parentId").prop("disabled",true);
		}else if(typeId == "2"){ //选择为菜单时
			//添加权限名称模板
			$("#pDom").val("<li><a href=\"javascript:;\" onclick=\"clickLeft(this,'url')\" class=\"now\">自定义</a></li>");
			$("#parentId").removeAttr("disabled");
			//清空所有option标签
			$("#parentId").empty();
			//使用ajax加载所有一级菜单
			$.ajax({
				url		:		contextPath+"/spring/user/showPower",
				method	:		"post",
				dataType:		"json",
				data	:		{"levelId":"1"},
				success	:		function(data){
					$("#parentId").append("<option value=\"0\">请选择</option>");
					//在父级权限下拉列表中追加数据
					for(var i=0;i<data.length;i++){
						$("#parentId").append("<option value=\""+data[i].pId+"\">"+data[i].pDesc+"</option>");
					}
				}
			});
		}else{ //选择为按钮时
			//添加权限名称模板
			$("#pDom").val("<a href=\"javascript:;\" onclick=\"fangfa\">自定义</a>");
			$("#parentId").removeAttr("disabled");
			$("#parentId").empty();
			//使用ajax加载所有一级菜单
			$.ajax({
				url		:		contextPath+"/spring/user/showPower",
				method	:		"post",
				dataType:		"json",
				data	:		{"levelId":"2"},
				success	:		function(data){
					//清空所有option标签
					$("#parentId").append("<option value=\"0\">请选择</option>");
					//在父级权限下拉列表中追加数据
					for(var i=0;i<data.length;i++){
						$("#parentId").append("<option value=\""+data[i].pId+"\">"+data[i].pDesc+"</option>");
					}
				}
			});
		}
	}
	//判断权限名是否存在
	function checkPowerName(){
		var pName = $("#pName").val();
		//使用ajax查询该权限名是否存在
		$.ajax({
			url		:		contextPath+"/spring/user/checkPowerName",
			method	:		"post",
			data	:		{"pName":pName},
			success	:		function(data){
				var json = eval('('+data+')');
				if(json.status == -1){
					//改变文本框颜色
					$("#pName").css("border-color","red");
					//提示div显示
					$("#tishi").show();
					$("#tishi").html(json.message);
					//锁定提交按钮
					$("#addPowerDom").attr("disabled",true);
				}else{
					$("#addPowerDom").removeAttr("disabled");
					$("#tishi").hide();
					$("#pName").css("border-color","#CCCCCC");
				}
			}
			
		});
	}
	//添加权限按钮
	function addPower(){
		//权限类型
		var typeId = $("#pType").val();
		//父级权限
		var parentId = $("#parentId").val();
		//权限名称
		var pName = $("#pName").val();
		//权限描述
		var pDesc = $("#pDesc").val();
		//权限dom
		var pDom = $("#pDom").val();
		//非空判断
		if(typeId == "0"){
			alert("请选择权限类型!");
			return;
		}
		if(typeId != "0" && typeId != "1" && parentId == "0"){
			alert("请选择父级权限!");
			return;
		}
		if(pName == ""){
			alert("请输入权限名称!");
			return;
		}
		if(pDom == ""){
			alert("请输入权限Dom!");
			return;
		}
		typeId = parseInt(typeId)-1;
		var ise = {
			typeId:typeId,
			parentId:parentId,
			pName:pName,
			pDesc:pDesc,
			pDom:pDom
		}
		//使用ajax添加
		$.ajax({
			url		:		contextPath+"/spring/user/addPower",
			method	:		"post",
			data	:		ise,
			success	:		function(data){
				var json = eval('('+data+')');
				if(json.status == 1){
					alert(json.message);
					//刷新权限显示界面
					//移除模态框背景
					$(".modal-backdrop").remove();
					$(".m_right").load(contextPath+ "/spring/product/index .m_right>*","currentPage=1");
				}else{
					alert(json.message);
				}
			}
		});
		
	}
	
	//关闭模态框
	function closeModal(){
		//权限类型
		$("#pType").val("0");
		//父级权限
		$("#parentId").val("0");
		//权限名称
		$("#pName").val("");
		//权限描述
		$("#pDesc").val("");
		//权限dom
		$("#pDom").val("");
		
		$("#parentId").prop("disabled",true);
		$("#addPowerDom").removeAttr("disabled");
		$("#tishi").hide();
		$("#pName").css("border-color","#CCCCCC");
	}
