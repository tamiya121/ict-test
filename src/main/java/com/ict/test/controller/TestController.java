package com.ict.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ict.test.HomeController;
import com.ict.test.service.TestService;

@Controller
public class TestController {
	@Autowired
	private TestService ts;
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public String test(Model m) {
		m.addAttribute("list",ts.getList());
		return "test/test";
	}
	
	@RequestMapping(value="/test2",method=RequestMethod.GET)
	public String test2(Model m) {
		m.addAttribute("list",ts.getList());
		return "test/test";
	}
}
