package com.lbstspringboot3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbstspringboot3.entity.UserInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 林日清
 * @since 2024-08-10
 */
public interface IUserInfoService extends IService<UserInfo> {

  /**
   * 通过 uid 获取个人信息
   *
   * @param uid 用户id
   */
  UserInfo getByUid(Integer uid);

  /**
   * 通过 uid 将头像保存到本地
   *
   * @param uid  用户id
   * @param file 表单提交的图片
   */
  boolean saveHeadImg(Integer uid, MultipartFile file);

  /**
   * 通过图片名称获取图片的二进制数据
   *
   * @param name 图片名称
   */
  byte[] getHeadImgBytes(String name);

  /**
   * 获取图片的类型
   *
   * @param name 图片名称
   */
  String getHeadImgType(String name);

  /**
   * 通过 uid 获取用户信息
   *
   * @param uid 用户id
   */
  UserInfo getUserInfoByUid(Integer uid);

  /**
   * 通过 uid 保存用户信息
   *
   * @param uid 用户id
   */
  boolean saveOrUpdateByUid(Integer uid, UserInfo userInfo);
}
