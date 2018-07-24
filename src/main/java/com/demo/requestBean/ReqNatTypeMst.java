package com.demo.requestBean;

/***
 * Request Bean will get from UI
 * 
 * @author Viral
 *
 */
public class ReqNatTypeMst {
	private Long natTypeMstId;
	private int natTypeId;
	private String natTypeDesc;

	public Long getNatTypeMstId() {
		return natTypeMstId;
	}

	public int getNatTypeId() {
		return natTypeId;
	}

	public String getNatTypeDesc() {
		return natTypeDesc;
	}

	public void setNatTypeMstId(Long natTypeMstId) {
		this.natTypeMstId = natTypeMstId;
	}

	public void setNatTypeId(int natTypeId) {
		this.natTypeId = natTypeId;
	}

	public void setNatTypeDesc(String natTypeDesc) {
		this.natTypeDesc = natTypeDesc;
	}

	@Override
	public String toString() {
		return "ReqNatTypeMst [natTypeMstId=" + natTypeMstId + ", natTypeId=" + natTypeId + ", natTypeDesc=" + natTypeDesc + "]";
	}
}
