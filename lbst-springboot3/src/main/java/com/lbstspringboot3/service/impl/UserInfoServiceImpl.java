package com.lbstspringboot3.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbstspringboot3.common.LocalStorage;
import com.lbstspringboot3.entity.UserInfo;
import com.lbstspringboot3.mapper.UserInfoMapper;
import com.lbstspringboot3.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 林日清
 * @since 2024-08-10
 */
@Service
public class UserInfoServiceImpl
    extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

  @Autowired UserInfoMapper userInfoMapper;

  @Autowired LocalStorage localStorage;

  @Override
  public UserInfo getByUid(Integer uid) {
    LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(UserInfo::getUid, uid);
    return this.getOne(wrapper);
  }

  @Override
  public boolean saveHeadImg(Integer uid, MultipartFile file) {
    UserInfo ui = this.getByUid(uid);
    if (ui == null)
      ui = new UserInfo();
    ui.setUid(uid);
    String name = localStorage.saveImg(file);
    if (name == null) {
      return false;
    }
    if (localStorage.delHeadImg(ui.getHeadImg())) {
      System.out.println("=========================================");
      System.out.println("文件删除成功");
      System.out.println("=========================================");
    }
    ui.setHeadImg(name);
    this.saveOrUpdate(ui);
    return true;
  }

  @Override
  public byte[] getHeadImgBytes(String name) {
    return localStorage.getHeadImgBytes(name);
  }

  @Override
  public String getHeadImgType(String name) {
    return localStorage.getHeadImgType(name);
  }

  @Override
  public UserInfo getUserInfoByUid(Integer uid) {
    LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(UserInfo::getUid, uid);
    UserInfo res = this.getOne(wrapper);
    if (res == null) {
      res = new UserInfo();
      res.setUid(uid);
    }
    return res;
  }

  @Override
  public boolean saveOrUpdateByUid(Integer uid, UserInfo userInfo) {
    UserInfo ui = getUserInfoByUid(uid);
    if (userInfo.getNickName() != null) {
      ui.setNickName(userInfo.getNickName());
    }
    if (userInfo.getYearOfBirth() != null) {
      ui.setYearOfBirth(userInfo.getYearOfBirth());
    }
    if (userInfo.getEducation() != null) {
      ui.setEducation(userInfo.getEducation());
    }
    return this.saveOrUpdate(ui);
  }
}
