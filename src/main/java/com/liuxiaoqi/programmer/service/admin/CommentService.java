package com.liuxiaoqi.programmer.service.admin;

import com.liuxiaoqi.programmer.entity.admin.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 新闻评论接口
 * @author lxq
 *
 */
@Service
public interface CommentService {
	public int add(Comment comment);
	public int edit(Comment comment);
	public int delete(String ids);
	public List<Comment> findList(Map<String,Object> queryMap);
	public List<Comment> findAll();
	public int getTotal(Map<String,Object> queryMap);
}
