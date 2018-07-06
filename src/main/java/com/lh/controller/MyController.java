package com.lh.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lh.service.MyService;

@Controller
public class MyController {
	
	@Resource
	private MyService myService;

	@RequestMapping(value = "/test")
	@ResponseBody
	public String list(HttpServletRequest request, HttpServletResponse respons) {
		
		System.out.println(myService.hello());
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("a", 100);
		jsonObject.put("b", "200");
		return jsonObject.toJSONString();
	}
}
