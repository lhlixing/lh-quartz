package com.lh.job.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core.xml")
// @Transactional(transactionManager="transactionManager")
public class WebStartTest {
	@Test
	public void test() {
		System.out.println("");
	}
}
