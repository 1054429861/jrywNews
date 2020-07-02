package com.liuxiaoqi.programmer.service.admin;

import com.liuxiaoqi.programmer.entity.admin.News;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 新闻接口
 * @author lxq
 *
 */
@Service
public interface NewsService {
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
