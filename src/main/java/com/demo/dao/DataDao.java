package com.demo.dao;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.demo.valueobject.NatTypeMst;

/***
 * Repository where will get basic CRUD API to perform Database Operation
 * 
 * @author Viral
 *
 */
@Repository
public interface DataDao extends CassandraRepository<NatTypeMst, Long> {
}
