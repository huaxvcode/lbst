package com.lbstspringboot3.controller;

import cn.dev33.satoken.util.SaResult;
import com.lbstspringboot3.service.IRoleService;
import com.lbstspringboot3.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  @Autowired private IUserService userService;

  @Autowired private IRoleService roleService;

  @GetMapping("/username/{uid}")
  public SaResult getUsername(@PathVariable Integer uid) {
    return SaResult.ok().setData(userService.getUsername(uid));
  }

  @GetMapping("/role/{uid}")
  public SaResult getRoleByUid(@PathVariable Integer uid) {
    return SaResult.ok().setData(roleService.getRoleByUid(uid));
  }
}
