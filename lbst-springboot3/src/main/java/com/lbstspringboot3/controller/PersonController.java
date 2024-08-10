package com.lbstspringboot3.controller;

import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.util.SaResult;
import com.lbstspringboot3.common.LocalStorage;
import com.lbstspringboot3.service.IUserInfoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import java.io.OutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
  @Autowired private IUserInfoService userInfoService;

  @GetMapping("/head-img/{name}")
  public void getMethodName(@PathVariable String name, HttpServletResponse resp)
      throws Exception {
    String type = userInfoService.getHeadImgType(name);
    if (type == null) {
      return;
    }
    resp.setContentType(type);
    byte[] data = userInfoService.getHeadImgBytes(name);
    resp.setContentLength(data.length);
    try (OutputStream out = resp.getOutputStream()) {
      out.write(data);
      out.flush();
    }
  }

  @GetMapping("/user-info/{uid}")
  public SaResult getUserInfo(@PathVariable Integer uid) {
    return SaResult.ok().setData(userInfoService.getUserInfoByUid(uid));
  }
}
