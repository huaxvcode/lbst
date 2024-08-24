package com.lbstspringboot3.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormat {
  public static LocalDateTime parseLocalDateTime(String s) {
    if (s == null)
      return null;

    // 自定义时间格式
    DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    return LocalDateTime.parse(s, formatter);
  }
}
