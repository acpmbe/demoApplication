package com.demo.valueobject;

import java.io.Serializable;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/***
 * Model Table
 * 
 * @author Viral
 *
 */
@Table(value = "Nat_Type_Mst")
public class NatTypeMst implements Serializable {
	private static final long serialVersionUID = 1L;

	// This Auto Increment key should be from Sequence
	@PrimaryKey(value = "nat_type_mst_id")
	private Long natTypeMstId;

	@Column(value = "nat_type_id")
	private int natTypeId;

	@Column(value = "nat_type_desc")
	private String natTypeDesc;

	public Long getNatTypeMstId() {
		return natTypeMstId;
	}

	public void setNatTypeMstId(Long natTypeMstId) {
		this.natTypeMstId = natTypeMstId;
	}

	public int getNatTypeId() {
		return natTypeId;
	}

	public void setNatTypeId(int natTypeId) {
		this.natTypeId = natTypeId;
	}

	public String getNatTypeDesc() {
		return natTypeDesc;
	}

	public void setNatTypeDesc(String natTypeDesc) {
		this.natTypeDesc = natTypeDesc;
	}

}
