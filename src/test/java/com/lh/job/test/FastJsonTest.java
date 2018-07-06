package com.lh.job.test;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

public class FastJsonTest {

	@Test
	public void test() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("a", 100);
		jsonObject.put("b", "200");
		System.out.println(jsonObject.toJSONString());
	}
}
