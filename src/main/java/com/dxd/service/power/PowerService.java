package com.dxd.service.power;

import java.util.List;
import java.util.Map;

import com.dxd.pojo.Powerone;
import com.dxd.pojo.Powerthree;
import com.dxd.pojo.Powertwo;

/**
 * 权限业务接口
 * @author 99266
 *
 */
public interface PowerService {
	/**
	 * 获得用户指定一级权限下的二级权限
	 * @return
	 */
	List<Powertwo> getPowerbytwo(Map<String,Object> map);
	/**
	 * 获得用户指定二级权限下的三级权限
	 * @param map
	 * @return
	 */
	List<Powerthree> getPowerbythree(Map<String,Object> map);
	/**
	 * 获得所有一级菜单
	 * @return
	 */
	List<Powerone> getPowerone();
	/**
	 * 获得所有二级菜单
	 * @return
	 */
	List<Powertwo> getPowertwo();
	/**
	 * 获得所有三级菜单
	 * @return
	 */
	List<Powerthree> getPowerthree();
	/**
	 * 得到指定用户所有二级权限
	 * @param id
	 * @return
	 */
	List<Powertwo> getUserAllPower2(Integer id);
	/**
	 * 得到指定用户所有三级权限
	 * @param id
	 * @return
	 */
	List<Powerthree> getUserAllPower3(Integer id);
	/**
	 * 移除指定用户所有权限
	 * @param id
	 * @return
	 */
	int delUserAllPower(Integer id);
	/**
	 * 添加指定用户指定权限
	 * @param map
	 * @return
	 */
	int addUserPower(Map<String,Object> map);
	/**
	 * 判断权限名是否存在
	 * @param pName
	 * @return
	 */
	int checkPowerName(String pName);
	/**
	 * 添加新的权限
	 * @param map
	 * @return
	 */
	int addPower(Map<String,Object> map);
	/**
	 * 得到该用户所有权限
	 * @param uId
	 * @return
	 */
	List<Powerone> getUserAllPower(Integer uId);
}
