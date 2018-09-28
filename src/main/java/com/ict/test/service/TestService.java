package com.ict.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.test.dao.TestDAO;
import com.ict.test.vo.Dept;

@Service
public class TestService {
	@Autowired
	private TestDAO tdao;
	
	public List<Dept> getList(){
		return tdao.getList();
	}
	
	public List<Dept> getList2(){
		return tdao.getList2();
	}
}
