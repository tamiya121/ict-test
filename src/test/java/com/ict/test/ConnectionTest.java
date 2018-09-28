package com.ict.test;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConnectionTest {

	@Test
	public void test() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:root-context.xml");
		DataSource ds = (DataSource)ac.getBean("hkds");
		String sql = "select count(1) from emp";
		try(Connection con = ds.getConnection()){
			try(ResultSet rs = con.createStatement().executeQuery(sql)){
				rs.next();
				assertEquals(14,rs.getInt(1));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
