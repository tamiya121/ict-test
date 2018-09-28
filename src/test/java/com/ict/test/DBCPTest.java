package com.ict.test;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StopWatch;

import com.zaxxer.hikari.HikariDataSource;

class Order{
	public static int end=0;
	public static final int MAX_NUM = 100;
	private StopWatch sw;
	public Order(StopWatch sw) {
		this.sw = sw;
	}
	private int syncIdx =0;
	
	public synchronized void addSyncIdx() {
		syncIdx++;
		if(syncIdx==MAX_NUM) {
			end++;
			sw.stop();
			System.out.println("running time : " +  sw.prettyPrint());
		}
	}
	public synchronized int getSyncIdx() {
		return syncIdx;
	}	
}
public class DBCPTest {
	private StopWatch sw1 = new StopWatch();
	private StopWatch sw2 = new StopWatch();
	private String sql = "select count(*) from emp";
	
	private class ThreadTest extends Thread {
		private DataSource ds;
		private int idx;
		private Order order;
		public ThreadTest(DataSource ds,int idx,Order order) {
			this.ds = ds;
			this.idx = idx;
			this.order = order;
		}
		@Override
		public void run() {
			try {
				try(Connection con = ds.getConnection()){
					try(ResultSet rs = con.createStatement().executeQuery(sql)){
						rs.next();
						//System.out.println(idx);
						rs.close();
					}
					Thread.sleep(new Random().nextInt(1000));
					order.addSyncIdx();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void runingTest(DataSource ds,StopWatch sw,Order o) {
		final DataSource ds2 = ds;
		final StopWatch sw2 = sw;
		final Order order = o;
		try {
			Thread tt = new Thread(new Runnable(){
				@Override
				public void run() {
					for(int i=1;i<=Order.MAX_NUM;i++) {
						Thread t = new ThreadTest(ds2,i,order);
						t.start();
					}
				}
			});
			tt.start();
			tt.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	@Test
	public void test() throws InterruptedException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:dbcp-test.xml");
		BasicDataSource bds = (BasicDataSource)ac.getBean("basicDataSource");
		sw1.start("dbcp2 test");
		runingTest(bds,sw1, new Order(sw1));
		HikariDataSource hds = (HikariDataSource)ac.getBean("hk");
		sw2.start("hikari test");
		runingTest(hds,sw2, new Order(sw2));
		while(Order.end<2) {
			//System.out.println(Order.end);
			Thread.sleep(1000);
		}
		fail("Not yet implemented");
	}

}
