package com.dxd.controller.pre;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dxd.pojo.EasybuyUser;
import com.dxd.pojo.Powerone;
import com.dxd.pojo.Powerthree;
import com.dxd.pojo.Powertwo;
import com.dxd.service.order.EasybuyOrderService;
import com.dxd.service.power.PowerService;
import com.dxd.service.user.EasybuyUserService;
import com.dxd.utils.Pager;
import com.dxd.utils.PrintUtils;
import com.dxd.utils.ReturnResult;

/**
 * 用户信息控制层！ Servlet implementation class EasybuyUserServlet
 */
@Controller
@RequestMapping("user/")
public class EasybuyUserServlet{
	@Autowired
	private EasybuyUserService eusi;
	@Autowired
	private EasybuyOrderService eosi;
	@Autowired
	private PowerService ps;
	
	/**
	 * 加载所有用户列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="user")
	public String user(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int currentPage = 1; //当前页
		//判断参数currentPage是否为空
		if(null != request.getParameter("currentPage")){
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		//获得所用用户条数
		int count = eusi.getTotalCount();
		//创建分页对象
		Pager pager = new Pager(count,10,currentPage);
		//设置pager  URL
		pager.setUrl("spring/user/user");
		//得到所有用户信息
		List<EasybuyUser> userList = eusi.getEasybuyUserAll(pager);
		//存到request中
		request.setAttribute("list", userList);
		request.setAttribute("pager", pager);
		return "/backend/user/Member_Packet";
	}

	/**
	 * 添加用户成功后调用方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="adduser")
	public String adduser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/backend/user/Member_Packet";
	}

	/**
	 * 回显数据 到修改页面(添加页面)
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="toUpdateUser")
	public String toUpdateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//回显数据
		int id = Integer.parseInt(request.getParameter("id"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage")); //在哪一页进行修改
		EasybuyUser user = eusi.getEasybuyUserById(id);
		//存储到作用域中
		request.setAttribute("user", user);
		request.setAttribute("currentPage", currentPage);
		//存储当前修改用户的登录名
		request.setAttribute("oldloginName", user.getLoginName());
		return "/backend/user/toUpdateUser";
	}

	/**
	 * 修改用户信息/添加用户信息！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="modify",method=RequestMethod.POST)
	public void modify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id"); //0添加  否则修改
		//添加的参数
		String loginName = request.getParameter("loginName");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String identityCode = request.getParameter("identityCode");
		String sex = request.getParameter("sex");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String type = request.getParameter("type");
		
		//创建对象
		EasybuyUser user = new EasybuyUser();
		user.setUserName(userName);
		user.setLoginName(loginName);
		user.setPassword(password);
		user.setIdentityCode(identityCode);
		user.setSex(Integer.parseInt(sex));
		user.setEmail(email);
		user.setMobile(mobile);
		user.setType(Integer.parseInt(type));
		ReturnResult result = new ReturnResult();
		//判断是执行新增还是修改
		if(id.equals("0")){  //新增
			int count = eusi.addRegisterInfo(user);
			if(count > 0){
				result.returnSuccess("添加成功!");  //添加成功
			}else{
				result.returnFail("添加失败!");
			}
		}else{  //执行修改
			//在哪一页修改
			int currentPage = Integer.parseInt(request.getParameter("currentPage"));
			user.setId(Integer.parseInt(id)); //存储需要修改的id
			int count2 = eusi.updateEasybuyUserById(user);
			if(count2 > 0){
				request.setAttribute("currentPage", currentPage);
				result.returnSuccess("更新成功!");
			}else{
				result.returnFail("更新失败!");
			}
		}
		PrintUtils.getJsonString(response, result);
	}

	/**
	 * 点击新增用户！   跳转至新增用户页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="add")
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/backend/user/toUpdateUser";

	}

	/**
	 * 用户详情！
	 * 点击后台管理时
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/backend/user/Member_User";
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="loginBtn",method=RequestMethod.POST)
	public void loginBtn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnResult result = new ReturnResult();
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		EasybuyUser user = eusi.getEasybuyUserInfo(name); //得到该用户名查询到的用户信息
		//判断是否为空
		if(null == user){
			result.returnFail("用户名不存在!");
		}else{
			//用户名存在,判断密码是否正确
			if(user.getPassword().equals(pwd)){
				//得到当前登录用户的权限
					//获得所有菜单信息
					List<Powerone> level1 = ps.getPowerone();
					Map<String,Object> map = new HashMap();
					map.put("uId", user.getId());
					//循环得到该用户所有二级权限
					for(Powerone level : level1) {
						//存储当前父菜单id
						map.put("parentId", level.getpId());
						//存储该父菜单对应的二级菜单
						level.setPowerTwo(ps.getPowerbytwo(map));
						//得到该二级菜单下的三级菜单
						for(Powertwo level2 : level.getPowerTwo()) {
							//重新存储二级菜单id到map中
							map.put("parentId", level2.getpId());
							//存储
							level2.setPowerThree(ps.getPowerbythree(map));
						}
					}
					//存储进用户对象中
					user.setPowerList(level1);
				request.getSession().setAttribute("easybuyUserLogin", user);
				result.returnSuccess("登录成功!");
			}else{
				result.returnFail("密码错误!请核对后输入!");
			}
		}
		PrintUtils.getJsonString(response, result);
	}

	/**
	 * 注册
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="addUser",method=RequestMethod.POST)
	public void addUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		EasybuyUser ebu = new EasybuyUser();
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String numName = request.getParameter("numName");
		int sex = Integer.parseInt(request.getParameter("sex"));
		String mem = request.getParameter("mem");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		int type = 0;
		ebu.setLoginName(name);
		ebu.setPassword(password);
		ebu.setUserName(numName);
		ebu.setSex(sex);
		ebu.setIdentityCode(mem);
		ebu.setEmail(email);
		ebu.setMobile(phone);
		ebu.setType(type);
		ReturnResult result = new ReturnResult();
		int count = eusi.addRegisterInfo(ebu);
		if(count > 0){
			result.returnSuccess();
		}else{
			result.returnFail("注册失败!");
		}
		PrintUtils.getJsonString(response, result);
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="del",method=RequestMethod.POST)
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));  //用户id
		int currentPage = Integer.parseInt(request.getParameter("currentPage")); //在哪一页操作
		ReturnResult result = new ReturnResult();
		//判断该用户是否有订单
		int count = eusi.checkOrderByid(id);
		if(count > 0){  //有订单 无法删除
			result.returnFail("该用户下有未收货订单,无法删除!");
		}else{  //无订单/可以删除
			//先删除该用户所有收货地址
			eusi.delAddressByid(id);
			//删除该用户所有订单中的所有商品
			   //查询所有订单号
			List<Integer> list = eusi.getAllOrderIdByuserId(id);
			//循环遍历list
			for(int i=0;i<list.size();i++){
				//删除所有商品
				eosi.delOrderProductByOrderId(list.get(i));
			}
			//删除该用户所有已收货订单
			eosi.delOrderByUserId(id);
			//删除用户
			int count2 = eusi.removeEasybuyUserById(id);
			if(count2 > 0){
				result.returnSuccess(); //删除成功!
			}else{
				result.returnFail("删除失败!");
			}
		}
		PrintUtils.getJsonString(response, result);
	}

	/**
	 * 查询是否相同用户名！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="loginNameCount",method=RequestMethod.POST)
	public void loginNameCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("name"); //用户名
		ReturnResult result = new ReturnResult();
		int count = eusi.getLoginNameByName(name);
		//判断是否存在
		if(count > 0){  //存在
			 result.returnFail("该用户名已存在");
		}else{//不存在
			result.returnSuccess("用户名可用!");
		}
		PrintUtils.getJsonString(response, result);
	}

	/// *************************
	/**
	 * 跳转至验证身份！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="findLoginNamePassword")
	public String findLoginNamePassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/backend/userPassWord/forgetPwdTwo";
	}

	/**
	 * 查询邮箱或者手机输入是否输入正确！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ReturnResult loginNameBy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}

	/**
	 * 跳转至设置新密码！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("modifyPwd")
	public String modifyPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/backend/userPassWord/forgetPwdThree";
	}
	/**
	 * 跳转至修改密码页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toUpdatePwd2")
	public String toUpdatePwd2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/backend/userPassWord/toUpdatePwd2";
	}
	/**
	 * 修改密码！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="modifyPassWord",method=RequestMethod.POST)
	public void modifyPassWord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String loginName = request.getParameter("loginName"); //需要修改密码的用户名
		//System.out.println(loginName);
		String password = request.getParameter("password");  //新密码
		ReturnResult result = new ReturnResult();
		int count = eusi.modifyUserPasswordBy(loginName, password);
		if(count > 0){
			result.returnSuccess("修改密码成功!");
		}else{
			result.returnFail("修改密码失败!");
		}
		PrintUtils.getJsonString(response, result);
	}

	/**
	 * 跳转至设置修改成功页面！！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("forgetPwd")
	public String forgetPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//移除session中的登录对象,让用户重新登录
		request.getSession().removeAttribute("easybuyUserLogin");
		return "/backend/userPassWord/forgetPwdLast";
	}

	// ***********************

	/**
	 * 注销
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("remove")
	public String remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//移除session中的登录对象
		request.getSession().removeAttribute("easybuyUserLogin");
		return "/pre/Login";
	}
	/**
	 * 分配权限界面
	 * @param model
	 * @return
	 */
	@RequestMapping("allPower")
	public String getPower(Model model) {
		//所有用户
		List<EasybuyUser> userList = eusi.getAllEasybuyUser();
		//所有二级权限
		List<Powertwo> level2 = ps.getPowertwo();
		List<Powerthree> level3 = ps.getPowerthree();
		//存储
		model.addAttribute("userList",userList);
		model.addAttribute("level2",level2);
		model.addAttribute("level3",level3);
		return "/backend/givePower/powerChange";
	}
	/**
	 * 获得指定用户的所有权限
	 */
	@RequestMapping("getPowerById")
	public void getPowerById(String id,HttpServletResponse response) {
		StringBuffer sb = new StringBuffer("[");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;
		try {
			out =  response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//获得该用户所有二级权限
		List<Powertwo> level2 = ps.getUserAllPower2(Integer.parseInt(id));
		//获得该用户所有三级权限
		List<Powerthree> level3 = ps.getUserAllPower3(Integer.parseInt(id));
		//循环拼接成json格式
		for(int i=0;i<level2.size();i++) {
			Powertwo power2 = level2.get(i);
			sb.append("{\"pId\":\""+power2.getpId()+"\"}");
			if((i+1) != level2.size()) {
				sb.append(",");
			}
		}
		if(level3.size() != 0) {
			sb.append(",");
		}
		//拼接三级权限
		for(int i=0;i<level3.size();i++) {
			Powerthree power3 = level3.get(i);
			sb.append("{\"pId\":\""+power3.getpId()+"\"}");
			if((i+1) != level3.size()) {
				sb.append(",");
			}
		}
		sb.append("]");
		System.out.println(sb.toString());
		out.print(sb);
	}
	/**
	 * 保存修改后的权限
	 * @param array2
	 * @param array3
	 * @param id
	 * @param response
	 */
	@RequestMapping("savePower")
	public void savePower(String[] array2,String[] array3,String id,HttpServletResponse response) {
		//删除该用户所有权限
		ps.delUserAllPower(Integer.parseInt(id));
		Map<String,Object> map = new HashMap<>();
		map.put("uId", id);
		int count = 0;
		//循环添加该用户的二级权限
		if( array2 != null && array2.length != 0 ) {
			for(String pId2 : array2) {
				map.put("pId", pId2);
				//添加
				count = ps.addUserPower(map);
			}
		}
		//循环添加该用户三级权限
		if(array3 != null && array3.length != 0 ) {
			for(String pId3 : array3) {
				map.put("pId", pId3);
				//添加
				count = ps.addUserPower(map);
			}
		}
		ReturnResult result = new ReturnResult();
		if(count > 0) {
			result.returnSuccess("修改权限成功!");
		}else {
			result.returnFail("系统出现异常!请联系管理员!");
		}
		PrintUtils.getJsonString(response, result);
	}
	/**
	 * 根据条件 查询不同等级权限
	 * @param <T>
	 * @param levelId
	 * @param response
	 */
	@RequestMapping("showPower")
	public <T> void showPowerLevel(String levelId,HttpServletResponse response) {
		StringBuffer sb = new StringBuffer("[");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<T> powerList = null;
		//根据levelId 查询不同等级权限
		if(levelId.equals("1")) { //一级目录
			powerList = (List<T>) ps.getPowerone();
			for(int i=0;i<powerList.size();i++) {
				Powerone level1 = (Powerone)powerList.get(i);
				//拼接json数据
				sb.append("{\"pId\":\""+level1.getpId()+"\",\"pDesc\":\""+level1.getpDesc()+"\"}");
				if((i+1) != powerList.size()) {
					sb.append(",");
				}
			}
		}else if(levelId.equals("2")) {//二级菜单
			powerList = (List<T>) ps.getPowertwo();
			for(int i=0;i<powerList.size();i++) {
				Powertwo level2 = (Powertwo)powerList.get(i);
				//拼接json数据
				sb.append("{\"pId\":\""+level2.getpId()+"\",\"pDesc\":\""+level2.getpDesc()+"\"}");
				if((i+1) != powerList.size()) {
					sb.append(",");
				}
			}
		}
		sb.append("]");
		//发送给jsp页面
		//System.out.println(sb.toString());
		out.print(sb);
		out.flush();
		out.close();
	}
	/**
	 * 判断权限名是否存在
	 * @param pName
	 * @param response
	 */
	@RequestMapping("checkPowerName")
	public void checkPowerName(String pName,HttpServletResponse response) {
		ReturnResult result = new ReturnResult();
		//判断是否已存在
		int count = ps.checkPowerName(pName);
		if(count > 0) {
			result.returnFail("权限名已存在!");
		}else {
			result.returnSuccess();
		}
		PrintUtils.getJsonString(response, result);
	}
	/**
	 * 添加新的权限
	 * @param power
	 * @param response
	 * @param pDom
	 */
	@RequestMapping("addPower")
	public void addPower(Powerone power,String pDom,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();
		ReturnResult result = new ReturnResult();
		map.put("pName",pDom);
		map.put("pDesc", power.getpName());
		map.put("parentId", power.getParentId());
		map.put("typeId", power.getTypeId());
		
		//添加
		int count = ps.addPower(map);
		if(count > 0) {
			result.returnSuccess("添加权限成功!");
		}else {
			result.returnFail("系统出现异常!请联系管理员!");
		}
		PrintUtils.getJsonString(response, result);
	}
	/**
	 * 测试树状菜单
	 * @param model
	 * @param request
	 */
	@RequestMapping("menuTree")
	public String menuTree(Model model,HttpServletRequest request) {
		//加载三级权限
		StringBuffer sb1 = new StringBuffer("[");  //一级目录
		StringBuffer sb2 = new StringBuffer("[");	//二级菜单
		StringBuffer sb3 = new StringBuffer("[");  //三级按钮
		
		//一级目录
		List<Powerone> power1 = ps.getPowerone();
		//二级菜单
		List<Powertwo> power2 = ps.getPowertwo();
		//三级按钮
		List<Powerthree> power3 = ps.getPowerthree();
		
		//转换成json格式
		for(int i=0;i<power1.size();i++) {
			Powerone po = power1.get(i);
			sb1.append("{\"pId\":\""+po.getpId()+"\",\"pName\":\""+po.getpDesc()+"\",\"parentId\":\""+po.getParentId()+"\"}");
			if((i+1) != power1.size()) {
				sb1.append(",");
			}
		}
		for(int i=0;i<power2.size();i++) {
			Powertwo po = power2.get(i);
			sb2.append("{\"pId\":\""+po.getpId()+"\",\"pName\":\""+po.getpDesc()+"\",\"parentId\":\""+po.getParentId()+"\"}");
			if((i+1) != power2.size()) {
				sb2.append(",");
			}
		}
		for(int i=0;i<power3.size();i++) {
			Powerthree po = power3.get(i);
			sb3.append("{\"pId\":\""+po.getpId()+"\",\"pName\":\""+po.getpDesc()+"\",\"parentId\":\""+po.getParentId()+"\"}");
			if((i+1) != power3.size()) {
				sb3.append(",");
			}
		}
		sb1.append("]");
		sb2.append("]");
		sb3.append("]");
		//得到当前用户所有权限
		EasybuyUser user = (EasybuyUser)request.getSession().getAttribute("easybuyUserLogin");
		List<Powerone> userPower = ps.getUserAllPower(user.getId());
		StringBuffer sb4 = new StringBuffer("[");
		for(int i=0;i<userPower.size();i++) {
			Powerone po = userPower.get(i);
			sb4.append("{\"pId\":\""+po.getpId()+"\",\"pName\":\""+po.getpDesc()+"\",\"parentId\":\""+po.getParentId()+"\"}");
			if((i+1) != userPower.size()) {
				sb4.append(",");
			}
		}
		sb4.append("]");
		/*
		 * request.getSession().setAttribute("level1", sb1);
		 * request.getSession().setAttribute("level2", sb2);
		 * request.getSession().setAttribute("level3", sb3);
		 * request.getSession().setAttribute("userPower", sb4);
		 */
		model.addAttribute("level1", sb1);
		model.addAttribute("level2", sb2);
		model.addAttribute("level3", sb3);
		model.addAttribute("userPower",sb4);
		return "/backend/givePower/powerTree";
	}
}
