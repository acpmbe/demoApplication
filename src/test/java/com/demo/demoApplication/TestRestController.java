package com.demo.demoApplication;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.dao.DataDao;
import com.demo.valueobject.NatTypeMst;

/***
 * Test cases for CRUD nature
 * 
 * @author Viral
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
@EnableAutoConfiguration
public class TestRestController extends CassandraEmbeddedServerTestConfig {
	public static final Logger logger = LoggerFactory.getLogger(TestRestController.class);
	@Autowired
	DataDao dataDao;

	/***
	 * Nature Save Test Case. on Successfully insert Nature into table, auto
	 * generate non negative primary key will be generated. Test Cases 1) Insert
	 * Success.
	 */
	@Test
	public void t01_saveNature() {
		logger.info("Start Testing t01_saveNature test case");
		NatTypeMst natTypeMst = new NatTypeMst();
		natTypeMst.setNatTypeMstId(9L);
		natTypeMst.setNatTypeDesc("N8");
		natTypeMst.setNatTypeId(8);
		dataDao.save(natTypeMst);
		Assert.assertEquals("Successfully inserted nature in the table", Boolean.TRUE, natTypeMst.getNatTypeMstId() != 0);
	}

	/***
	 * fetch Nature by id Test Case. Test Cases 1) No Nature found. 2) Valid
	 * Nature found.
	 * 
	 */
	@Test
	public void t02_getNatureByIdTest() {
		logger.info("Start Testing t02_getNatureByIdTest test case");
		Optional<NatTypeMst> opNatTypeMst = dataDao.findById(15L);
		Assert.assertFalse("No data found in table", !opNatTypeMst.isPresent());
		if (opNatTypeMst.isPresent()) {
			Assert.assertTrue("Successfully fetched data from table", ("ab244".equals(opNatTypeMst.get().getNatTypeDesc()) && opNatTypeMst.get()
					.getNatTypeId() == 3));
		}
	}

	/***
	 * Fetch all Nature Test Case. Test Cases 1) No Nature found.
	 */
	@Test
	public void t03_getAllNature() {
		logger.info("Start Testing t03_getAllNature test case");
		List<NatTypeMst> lstNatTypeMst = dataDao.findAll();
		Assert.assertFalse("Successfully fetched all data from table. No data found in table", lstNatTypeMst.isEmpty());
	}

	/***
	 * Update Nature Test Case. Test Cases 1) No Nature found. 2) Update Nature
	 * Success.
	 */
	@Test
	public void t04_updateNature() {
		logger.info("Start Testing t04_updateNature test case");
		NatTypeMst natTypeMst = new NatTypeMst();
		natTypeMst.setNatTypeMstId(9L);
		natTypeMst.setNatTypeDesc("N9");
		dataDao.save(natTypeMst);
		Optional<NatTypeMst> opNatTypeMst = dataDao.findById(8L);
		Assert.assertFalse("No data found in table", !opNatTypeMst.isPresent());
		if (opNatTypeMst.isPresent()) {
			Assert.assertTrue("Successfully updated nature in the table", ("N9".equals(opNatTypeMst.get().getNatTypeDesc())));
		}
	}

	/***
	 * Delete Nature Test Case. Test Cases 1) Delete Nature Success.
	 */
	@Test
	public void t05_deleteNature() {
		logger.info("Start Testing t05_deleteNature test case");
		NatTypeMst natTypeMst = new NatTypeMst();
		natTypeMst.setNatTypeMstId(8L);
		dataDao.deleteById(3L);
		Optional<NatTypeMst> opNatTypeMst = dataDao.findById(8L);
		Assert.assertFalse("No data found in table. Delete Successfully Done", !opNatTypeMst.isPresent());
	}

}
