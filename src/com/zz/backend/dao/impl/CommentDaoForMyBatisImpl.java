package com.zz.backend.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.zz.backend.dao.CommentDao;
import com.zz.backend.model.Article;
import com.zz.backend.model.Comment;
import com.zz.backend.vo.PagerVO;
import com.zz.utils.MyBatisUtil;

public class CommentDaoForMyBatisImpl extends BaseDao implements CommentDao{

	public void addComment(Comment c) {
		// TODO Auto-generated method stub
		SqlSession session = MyBatisUtil.getSession();
		
		try{
			c.setCommentTime(new Date());
			session.insert(Comment.class.getName()+".add", c);
			
			int levelNumber = (Integer)session.selectOne(Article.class.getName()
					+".selectLeaveNumber", c.getArticleId());
			Article a = new Article();
			a.setId(c.getArticleId());
			a.setLeaveNumber(levelNumber+1);
			session.update(Article.class.getName()+".updateLeaveNumber",a );
			session.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.rollback();
		}finally{
			session.close();
		}
	}

	/**
	 * ɾ�����ԣ�Ҳ��Ҫ���´����Զ�Ӧ�����µ�������
	 */
	public void delComments(String[] ids) {
		// TODO Auto-generated method stub
		SqlSession session = MyBatisUtil.getSession();
		
		try{
			for(String id : ids){
				//���Ȳ�������Ե���Ϣ
				Comment c = (Comment)session.selectOne(Comment.class.getName()+".findById", Integer.parseInt(id));
				
				//���¶�Ӧ���µ�������
				int leaveNumber = (Integer)session.selectOne(Article.class.getName()+".selectLeaveNumber", 
						c.getArticleId());
				Article a = new Article();
				a.setId(c.getArticleId());
				a.setLeaveNumber(leaveNumber - 1);
				session.update(Article.class.getName()+".updateLeaveNumber", a);
				
				//ɾ�����Լ�¼
				session.delete(Comment.class.getName()+".del", Integer.parseInt(id));
			}
			session.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.rollback();
		}finally{
			session.close();
		}
	}

	public PagerVO findAllComments() {
		// TODO Auto-generated method stub
		Map params = new HashMap();
		return findPaginated(Comment.class.getName()+".findPaginated", params);
	}

	public Comment findCommentById(int id) {
		// TODO Auto-generated method stub
		return (Comment)findById(Comment.class, id);
	}

	public List findCommentsByArticleId(int articleId) {
		// TODO Auto-generated method stub
		SqlSession session = MyBatisUtil.getSession();
		try {
			
			return session.selectList(Comment.class.getName()+".findByArticleId", articleId);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			session.close();
		}
		return null;
	}

	public void updateComment(Comment c) {
		// TODO Auto-generated method stub
		update(c);
	}

}
