package com.dxd.pojo;

import java.util.List;

/**
 * 权限控制
 * 大权限
 * @author 99266
 *
 */
public class Powerone {
	private Integer pId;
	private String pName;
	private String pDesc;
	private Integer parentId;
	private Integer typeId;
	private List<Powertwo> powerTwo;
	
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getpDesc() {
		return pDesc;
	}
	public void setpDesc(String pDesc) {
		this.pDesc = pDesc;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public List<Powertwo> getPowerTwo() {
		return powerTwo;
	}
	public void setPowerTwo(List<Powertwo> powerTwo) {
		this.powerTwo = powerTwo;
	}
	
}
