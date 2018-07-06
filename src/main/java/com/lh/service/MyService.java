package com.lh.service;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MyService {

	private static final Logger logger = LoggerFactory.getLogger(MyService.class);
	
	public String  hello() {
		return "hello";
	}

	public Long test(Long id) throws Exception {
		if (id == null)
			id = -1L;
		logger.info("muJob run...");
		List<String> lines = new ArrayList();
		lines.add("pid=" + getProcessId() + " id=" + id + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		FileUtils.writeLines(new File("D:\\test.txt"), lines, true);
		return (id + 10);
	}

	private static String getProcessId() {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println(name);
		String pid = name.split("@")[0];
		System.out.println("Pid is:" + pid);
		return pid;
	}
}
