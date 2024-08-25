package com.lbstspringboot3.utils;

import jakarta.servlet.http.HttpServletRequest;

public class IpAddressUtil {

	/**
	 * 获取客户端真实IP地址的方法
	 * 支持处理多级代理的情况
	 *
	 * @param request HttpServletRequest对象
	 * @return 客户端IP地址
	 */
	public static String getClientIpAddress(HttpServletRequest request) {
		String ipAddress = null;

		try {
			// 尝试从X-Forwarded-For头获取IP地址
			ipAddress = request.getHeader("X-Forwarded-For");
			if (ipAddress != null && ipAddress.length() != 0 && !"unknown".equalsIgnoreCase(ipAddress)) {
				// X-Forwarded-For中可能有多个IP地址，取第一个
				ipAddress = ipAddress.split(",")[0].trim();
			}

			// 如果IP地址为空，尝试从X-Real-IP头获取
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("X-Real-IP");
			}

			// 如果还没有获取到IP地址，尝试从代理服务器的头获取
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}

			// 尝试从WL-Proxy-Client-IP头获取
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}

			// 最后尝试直接从请求对象获取IP地址
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 处理IPV6 localhost的情况
		if ("0:0:0:0:0:0:0:1".equals(ipAddress)) {
			ipAddress = "127.0.0.1";
		}

		return ipAddress;
	}
}