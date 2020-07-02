package com.liuxiaoqi.programmer.dao.admin;

import com.liuxiaoqi.programmer.entity.admin.Authority;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Ȩ��ʵ����dao
 * @author lxq
 *
 */
@Repository
public interface AuthorityDao {
	public int add(Authority authority);
	public int deleteByRoleId(Long roleId);
	public List<Authority> findListByRoleId(Long roleId);
}
