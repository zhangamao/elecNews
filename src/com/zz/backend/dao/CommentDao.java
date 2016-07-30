package com.zz.backend.dao;

import java.util.List;

import com.zz.backend.model.Comment;
import com.zz.backend.vo.PagerVO;

public interface CommentDao {
	public void addComment(Comment c);
	public void updateComment(Comment c);
	public void delComments(String[] ids);
	public Comment findCommentById(int id);
	public List findCommentsByArticleId(int articleId);
	public PagerVO findAllComments();
}

