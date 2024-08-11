package com.lbstspringboot3.controller;

import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.lbstspringboot3.common.LocalStorage;
import com.lbstspringboot3.service.IUserInfoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import java.io.OutputStream;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

  @PostMapping("/upload/head-img")
  public SaResult postMethodName(MultipartFile file) {
    if (file.isEmpty())
      return SaResult.error("图片为空");
    String type = file.getContentType();
    if (!Set.of("image/jpeg", "image/png", "image/gif").contains(type)) {
      return SaResult.error("文件不是图片类型");
    }
    if (userInfoService.saveHeadImg((int)StpUtil.getLoginIdAsLong(), file)) {
      return SaResult.ok("图片上传成功");
    }
    return SaResult.error("图片上传失败");
  }

  @GetMapping("/user-info/{uid}")
  public SaResult getUserInfo(@PathVariable Integer uid) {
    return SaResult.ok().setData(userInfoService.getUserInfoByUid(uid));
  }
}
