package com.lh.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
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

	// 开发者在微信公众平台配置的token，只有微信端和商户端知道此token，请求参数中不会携带，用于验证签名
	private static String token = "dapengniaowechat";

	/**
	 * 服务器验证:微信调用此接口，商户进行验证请求确实是来自的微信的，验证成功直接返回原请求中的echostr参数值
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "security", method = RequestMethod.GET)
	@ResponseBody
	public void getWeiXinMethod(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String signature = request.getParameter("signature");// 微信端签名结果串
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数
		boolean validate = checkSignature(signature, timestamp, nonce);
		if (validate) {
			response.getWriter().write(request.getParameter("echostr"));// 参数中的随机字符串
			response.getWriter().close();
		}
	}

	/**
	 * 消息的接收和处理：post 方法用于接收微信服务端消息和事件
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "security", method = RequestMethod.POST)
	public void DoPost(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("这是 post 方法！");
		try {
			Map<String, String> map = MessageUtil.parseXml(request);
			System.out.println("=============================" + map.get("Content"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean checkSignature(String signature, String timestamp, String nonce) {
		String[] strs = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(strs);
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			content.append(strs[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 与参数中额签名signature对比，标识该请求来源于微信
		return signature != null ? signature.toUpperCase().equals(tmpStr.toUpperCase()) : false;
	}

	private static String byteToStr(byte[] bytes) {
		char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
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
