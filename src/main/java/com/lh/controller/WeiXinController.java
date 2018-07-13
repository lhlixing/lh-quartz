package com.lh.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lh.weixin.MessageUtil;

@Controller
@RequestMapping(value = "weixin")
public class WeiXinController {

	// 与接口配置信息中的 Token 要一致
	private static String token = "dapengniaowechat";

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 服务器验证
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "security", method = RequestMethod.GET)
	@ResponseBody
	public void getWeiXinMethod(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean validate = validate(request);
		if (validate) {
			response.getWriter().write(request.getParameter("echostr"));// 参数中的随机字符串
			response.getWriter().close();
		}

	}

	/**
	 * 消息的接收和处理
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "security", method = RequestMethod.POST)
	// post 方法用于接收微信服务端消息
	public void DoPost(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("这是 post 方法！");
		try {
			Map<String, String> map = MessageUtil.parseXml(request);
			System.out.println("=============================" + map.get("Content"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean validate(HttpServletRequest req) throws IOException {
		String signature = req.getParameter("signature");// 微信加密签名
		String timestamp = req.getParameter("timestamp");// 时间戳
		String nonce = req.getParameter("nonce");// 随机数
		List<String> list = new ArrayList<String>();
		list.add(token);// token
		list.add(timestamp);
		list.add(nonce);
		Collections.sort(list);// 字典排序
		String s = "";
		for (int i = 0; i < list.size(); i++) {
			s += (String) list.get(i);
		}
		if (encode("SHA1", s).equalsIgnoreCase(signature)) {
			return true;
		} else {
			return false;
		}
	}

	public static String encode(String algorithm, String str) {
		if (str == null) {
			return null;
		}
		try {
			// Java自带的加密类
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			// 转为byte
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}
}
