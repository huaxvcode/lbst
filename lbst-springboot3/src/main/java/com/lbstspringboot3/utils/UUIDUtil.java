package com.lbstspringboot3.utils;

import java.util.UUID;

public class UUIDUtil {
	public static UUID getUUID() {
		return UUID.randomUUID();
	}

	public static String getUUIDString() {
		return UUID.randomUUID().toString();
	}

	public static String getUUIDStringWithoutLine() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
