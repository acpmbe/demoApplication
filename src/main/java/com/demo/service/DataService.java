package com.demo.service;

import java.util.List;

import com.demo.valueobject.NatTypeMst;

/***
 * CRUD service
 * 
 * @author Viral
 *
 */
public interface DataService {

	public List<NatTypeMst> getAllNatTypeList();

	public NatTypeMst getNatTypeById(Long natTypeId);

	public void saveNatType(NatTypeMst natTypeMst);

	public void deleteNatType(Long natTypeId);

	public void updateNatType(NatTypeMst natTypeMst);
}
