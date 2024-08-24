package com.lbstspringboot3.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbstspringboot3.entity.Role;
import com.lbstspringboot3.mapper.RoleMapper;
import com.lbstspringboot3.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 林日清
 * @since 2024-08-18
 */
@Service
public class RoleServiceImpl
    extends ServiceImpl<RoleMapper, Role> implements IRoleService {

  @Override
  public Integer getRoleByUid(Integer uid) {
    LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Role::getUid, uid);
    Role role = this.getOne(wrapper);
    if (role == null)
      return 2;
    return role.getType();
  }
}
