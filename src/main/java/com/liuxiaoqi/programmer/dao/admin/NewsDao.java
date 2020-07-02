package com.liuxiaoqi.programmer.dao.admin;

import com.liuxiaoqi.programmer.entity.admin.News;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ÐÂÎÅdao
 * @author lxq
 *
 */
@Repository
public interface NewsDao {
	public int add(News news);
	public int edit(News news);
	public int delete(Long id);
	public List<News> findList(Map<String,Object> queryMap);
	public int getTotal(Map<String,Object> queryMap);
	public News find(Long id);
	public int updateCommentNumber(Long id);
	public int updateViewNumber(Long id);
	public List<News> findLastCommentList(int pageSize);
}
