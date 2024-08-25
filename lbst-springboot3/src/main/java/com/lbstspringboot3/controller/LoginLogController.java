package com.lbstspringboot3.controller;

import cn.dev33.satoken.util.SaResult;
import com.lbstspringboot3.service.ILoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginLogController {
	@Autowired
	private ILoginLogService loginLogService;

	@GetMapping("/loginLogList/{pageNum}/{pageSize}")
	public SaResult getLoginLogList(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
		if (pageNum <= 0 || pageSize <= 0) {
			return SaResult.error();
		}
		return SaResult.ok().setData(loginLogService.getLoginLogList(pageNum, pageSize));
	}
}
