package com.dxd.service.power.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxd.dao.PowerMapper;
import com.dxd.pojo.Powerone;
import com.dxd.pojo.Powerthree;
import com.dxd.pojo.Powertwo;
import com.dxd.service.power.PowerService;

/**
 * 权限业务层实现类
 * @author 99266
 *
 */
@Service("powerService")
public class PowerServiceImpl implements PowerService{
	
	@Autowired
	private PowerMapper mapper;
	
	/**
	 * 获得用户一级权限下的二级权限
	 */
	public List<Powertwo> getPowerbytwo(Map<String, Object> map) {
		return mapper.getPowerbytwo(map);
	}

	/**
	 * 获得用户二级权限下的三级权限
	 */
	public List<Powerthree> getPowerbythree(Map<String, Object> map) {
		return mapper.getPowerbythree(map);
	}

	/**
	 * 获得一级菜单
	 */
	public List<Powerone> getPowerone() {
		return mapper.getPowerone();
	}

	/**
	 * 获得二级菜单
	 */
	public List<Powertwo> getPowertwo() {
		return mapper.getPowertwo();
	}

	/**
	 * 获得三级菜单
	 */
	public List<Powerthree> getPowerthree() {
		return mapper.getPowerthree();
	}

	/**
	 * 获得指定用户的二级权限
	 */
	public List<Powertwo> getUserAllPower2(Integer id) {
		return mapper.getUserAllPower2(id);
	}

	/**
	 * 获得指定用户的三级权限
	 */
	public List<Powerthree> getUserAllPower3(Integer id) {
		return mapper.getUserAllPower3(id);
	}

	/**
	 * 删除指定用户所有权限
	 */
	public int delUserAllPower(Integer id) {
		return mapper.delUserAllPower(id);
	}

	/**
	 * 添加指定用户权限
	 */
	public int addUserPower(Map<String, Object> map) {
		return mapper.addUserPower(map);
	}

	/**
	 * 判断权限名是否存在
	 */
	public int checkPowerName(String pName) {
		return mapper.checkPowerName(pName);
	}

	/**
	 * 添加新的权限
	 */
	public int addPower(Map<String, Object> map) {
		return mapper.addPower(map);
	}

	/**
	 * 得到用户所有权限
	 */
	public List<Powerone> getUserAllPower(Integer uId) {
		return mapper.getUserAllPower(uId);
	}
	
}
