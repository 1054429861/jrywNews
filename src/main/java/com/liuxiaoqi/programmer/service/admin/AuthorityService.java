package com.liuxiaoqi.programmer.service.admin;

import com.liuxiaoqi.programmer.entity.admin.Authority;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限service接口
 * @author lxq
 *
 */
@Service
public interface AuthorityService {
	public int add(Authority authority);
	public int deleteByRoleId(Long roleId);
	public List<Authority> findListByRoleId(Long roleId);
}
