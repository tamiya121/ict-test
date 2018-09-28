package com.ict.test.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ict.test.vo.Dept;
import com.zaxxer.hikari.HikariDataSource;

@Repository
public class TestDAO {

	private static final Logger logger = LoggerFactory.getLogger(TestDAO.class);
	@Autowired
	private SqlSessionTemplate sst;
	@Autowired 
	private HikariDataSource hds;
	@Autowired
	private SqlSessionFactory ssf;
	
	public List<Dept> getList(){
		Dept d = new Dept();
		d.setDeptno(10); 
		return sst.selectList("DEPT.selectDept",d);
	}

	public List<Dept> getList2(){
		Dept d = new Dept();
		d.setDeptno(10); 
		SqlSession ss =  ssf.openSession();
		return ss.selectList("DEPT.selectDept",d);
	}
}
