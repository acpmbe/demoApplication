package com.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.requestBean.ReqNatTypeMst;
import com.demo.service.DataService;
import com.demo.valueobject.NatTypeMst;

/***
 * Rest API to perform CRUD operation.
 * 
 * @author Viral
 *
 */
@RestController
@RequestMapping("/api")
public class RestControllerTest {
	public static final Logger logger = LoggerFactory.getLogger(RestControllerTest.class);
	@Autowired
	DataService dataService;

	public DataService getDataService() {
		return dataService;
	}

	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}

	/**
	 * Retrive All Nature Type with their details
	 * 
	 * @return
	 */
	@GetMapping("/nature")
	public ResponseEntity<List<ReqNatTypeMst>> retrieveAllNature() {
		List<ReqNatTypeMst> lstReqNatTypeMst = new ArrayList<ReqNatTypeMst>();

		List<NatTypeMst> lstNatTypeMsts = dataService.getAllNatTypeList();
		if (lstNatTypeMsts.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		convertVoToBeanLst(lstNatTypeMsts, lstReqNatTypeMst);
		return new ResponseEntity<List<ReqNatTypeMst>>(lstReqNatTypeMst, HttpStatus.OK);
	}

	/**
	 * Retrive Single Nature by Id
	 * 
	 * @return
	 */
	@GetMapping("/nature/{id}")
	public ResponseEntity<?> retrieveNature(@PathVariable long id) {
		NatTypeMst natTypeMst = dataService.getNatTypeById(id);
		if (natTypeMst == null) {
			logger.error("nature id {} not found.", id);
			return new ResponseEntity("Nature id " + id + " not found", HttpStatus.NOT_FOUND);
		}
		ReqNatTypeMst reqNatTypeMst = new ReqNatTypeMst();
		convertVoToBean(natTypeMst, reqNatTypeMst);
		return new ResponseEntity<ReqNatTypeMst>(reqNatTypeMst, HttpStatus.OK);

	}

	/***
	 * Delete Nature by Nature Id
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/nature/{id}")
	public ResponseEntity<Object> deleteNature(@PathVariable long id) {
		dataService.deleteNatType(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	/***
	 * Create New Nature
	 * 
	 * @param reqNatTypeMst
	 * @param ucBuilder
	 * @return
	 */
	@PostMapping("/nature")
	public ResponseEntity<String> createNature(@RequestBody ReqNatTypeMst reqNatTypeMst) {

		logger.info("Creating nature : {}", reqNatTypeMst);
		NatTypeMst natTypeMst = new NatTypeMst();
		this.convertBeanToVo(reqNatTypeMst, natTypeMst);
		dataService.saveNatType(natTypeMst);
		return ResponseEntity.status(HttpStatus.CREATED).build();

	}

	/***
	 * Update Existing Nature By Id
	 * 
	 * @param reqNatTypeMst
	 * @param id
	 * @return
	 */
	@PutMapping("/nature/{id}")
	public ResponseEntity<?> updateNature(@RequestBody ReqNatTypeMst reqNatTypeMst, @PathVariable long id) {
		NatTypeMst natTypeMst = new NatTypeMst();
		reqNatTypeMst.setNatTypeMstId(id);
		this.convertBeanToVo(reqNatTypeMst, natTypeMst);
		dataService.updateNatType(natTypeMst);
		return new ResponseEntity<ReqNatTypeMst>(reqNatTypeMst, HttpStatus.OK);
	}

	/***
	 * Copy property from Request Bean to VO Bean
	 * 
	 * @param reqNatTypeMst
	 * @param natTypeMst
	 */
	private void convertBeanToVo(ReqNatTypeMst reqNatTypeMst, NatTypeMst natTypeMst) {
		BeanUtils.copyProperties(reqNatTypeMst, natTypeMst);
	}

	/***
	 * Copy property from VO Bean to Request Bean
	 * 
	 * @param natTypeMst
	 * @param reqNatTypeMst
	 */
	private void convertVoToBean(NatTypeMst natTypeMst, ReqNatTypeMst reqNatTypeMst) {
		BeanUtils.copyProperties(natTypeMst, reqNatTypeMst);
	}

	/***
	 * Copy List of property from VO Bean to Request Bean
	 * 
	 * @param lstNatTypeMsts
	 * @param lstReqNatTypeMst
	 */
	private void convertVoToBeanLst(List<NatTypeMst> lstNatTypeMsts, List<ReqNatTypeMst> lstReqNatTypeMst) {
		ReqNatTypeMst reqNatTypeMst;
		for (NatTypeMst natTypeMst : lstNatTypeMsts) {
			reqNatTypeMst = new ReqNatTypeMst();
			BeanUtils.copyProperties(natTypeMst, reqNatTypeMst);
			lstReqNatTypeMst.add(reqNatTypeMst);
		}
	}
}
