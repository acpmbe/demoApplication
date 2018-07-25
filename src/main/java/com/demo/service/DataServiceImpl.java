package com.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.DataDao;
import com.demo.valueobject.NatTypeMst;

/***
 * services for CRUD operation
 * 
 * @author Viral
 *
 */
@Service
public class DataServiceImpl implements DataService {

	@Autowired
	private DataDao dataDao;

	@Override
	public List<NatTypeMst> getAllNatTypeList() {
		List<NatTypeMst> lstNatTypeMst = new ArrayList<NatTypeMst>();
		dataDao.findAll().forEach(lstNatTypeMst::add);
		return lstNatTypeMst;
	}

	@Override
	public void saveNatType(NatTypeMst natTypeMst) {
		dataDao.save(natTypeMst);
	}

	@Override
	public void deleteNatType(Long natTypeId) {
		dataDao.deleteById(natTypeId);
	}

	@Override
	public void updateNatType(NatTypeMst natTypeMst) {
		dataDao.save(natTypeMst);
	}

	@Override
	public NatTypeMst getNatTypeById(Long natTypeId) {
		NatTypeMst natTypeMst = null;
		Optional<NatTypeMst> opNatTypeMst = dataDao.findById(natTypeId);

		if (opNatTypeMst.isPresent()) {
			natTypeMst = opNatTypeMst.get();
		}
		return natTypeMst;
	}

}
