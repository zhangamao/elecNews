package com.zz.backend.dao.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import com.zz.SystemContext;
import com.zz.backend.dao.ArticleDao;
import com.zz.backend.model.Article;
import com.zz.backend.model.Attachment;
import com.zz.backend.model.Channel;
import com.zz.backend.model.Comment;
import com.zz.backend.vo.PagerVO;
import com.zz.utils.MyBatisUtil;


public class ArticleDaoForMyBatisImpl extends BaseDao implements ArticleDao {

	public void addArticle(Article a) {
		a.setCreateTime(new Date());
		//��һ��session
		SqlSession session = MyBatisUtil.getSession();
		
		try {
			//����
			session.insert(Article.class.getName()+".add", a);
			
			//����channel
			Set<Channel> channels = a.getChannels();
			if(channels != null){
				for(Channel c:channels){
					Map params = new HashMap();
					params.put("a", a);
					params.put("c", c);
					session.insert(Article.class.getName()+".insert_channel_article", params);
				}
			}
			
			//�������º͹ؼ��ֵĹ�����
			if(a.getKeyword() != null && !a.getKeyword().trim().equals("")){
				String keyword = a.getKeyword();
				String[] keywords = keyword.split(",| ");//���տո�򶺺Ÿ���
				for(String kw:keywords){
					Map params = new HashMap();
					params.put("articleId", a.getId());
					params.put("keyword", kw);
					session.insert(Article.class.getName()+".insert_article_keyword", params);
				}
			}
			
			//�������µĸ�����Ϣ
			if(a.getAttachments() != null){
				List attachments = a.getAttachments();
				for (Iterator iterator = attachments.iterator(); iterator
						.hasNext();) {
					Attachment attachment = (Attachment) iterator.next();
					attachment.setArticleId(a.getId());
					session.insert(Article.class.getName()+".insert_attachment", attachment);
				}
			}
			
			//�ύ����
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally{
			//�ر�session
			session.close();
		}
	}

	public void delArticles(String[] ids) {
		SqlSession session = MyBatisUtil.getSession();
		
		try {
			for(String id:ids){
				int articleId = Integer.parseInt(id);

				//ɾ�����º�Ƶ��֮��Ĺ�����Ϣ
				session.delete(Article.class.getName()+".del_channel_article", articleId);
				
				//ɾ�����º͹ؼ���֮��Ĺ�����Ϣ
				session.delete(Article.class.getName()+".del_article_keyword", articleId);
				
				//ɾ����ƪ���µ����ж�Ӧ������
				session.delete(Comment.class.getName()+".delCommentByArticleId", articleId);
				
				//ɾ����ƪ���¶�Ӧ�����и���
				Article a = (Article)session.selectOne(Article.class.getName()+".findById", articleId);
				List attachments = a.getAttachments();
				System.out.println(attachments);
				for (Iterator iterator = attachments.iterator(); iterator
						.hasNext();) {
					Attachment attachment = (Attachment) iterator.next();
					String realPath = Attachment.ATTACHMENT_DIR+attachment.getName();
					new File(realPath).delete(); //ɾ�������ϵ��ļ�
				}
				//ɾ�����ݿ��е���ؼ�¼
				session.delete(Article.class.getName()+".del_attachments_by_articleId", articleId);
				
				//ɾ������
				session.delete(Article.class.getName()+".del", articleId);
				
			}
			//�ύ����
			session.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally{
			//�ر�session
			session.close();
		}
	}

	public void delAttachment(int attachmentId) {
		SqlSession session = MyBatisUtil.getSession();
		
		try {
			
			//���Ȳ��Attachment����
			Attachment attachment = (Attachment)session.selectOne(Article.class.getName()+".findAttachmentById", attachmentId);
			
			//ɾ�������ϵ��ļ�
			String realPath = Attachment.ATTACHMENT_DIR+attachment.getName();
			new File(realPath).delete(); //ɾ�������ϵ��ļ�
			
			//ɾ��Attachemnt��¼
			session.delete(Article.class.getName()+".del_attachment_by_Id", attachmentId);
			
			//�ύ����
			session.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally{
			//�ر�session
			session.close();
		}
	}

	public Article findArticleById(int id) {
		return (Article)findById(Article.class,id);
	}

	public PagerVO findArticles(String title) {

		Map params = new HashMap();
		if(title != null){
			params.put("title", "%"+title+"%");
		}
		return findPaginated(Article.class.getName()+".findArticleByTitle", params);
	}
	
	public PagerVO findArticles(Channel c) {
		Map params = new HashMap();
		params.put("c", c);
		return findPaginated(Article.class.getName()+".findArticleByChannel", params);
	}

	public List findArticles(Channel c, int max) {
		
		Map params = new HashMap();
		params.put("c", c);
		SystemContext.setOffset(0);
		SystemContext.setPagesize(max);
		PagerVO vo = findPaginated(Article.class.getName()+".findArticleByChannel", params);
		return vo.getDatas();
	}

	public List findHeadLine(int max) {
		SqlSession session = MyBatisUtil.getSession();
		
		try {
			return session.selectList(Article.class.getName()+".findHeadline", max);
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally{
			//�ر�session
			session.close();
		}
		return null;
	}

	public List findRecommend(int max) {
		SqlSession session = MyBatisUtil.getSession();
		
		try {
			return session.selectList(Article.class.getName()+".findRecommend", max);
			
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally{
			//�ر�session
			session.close();
		}
		return null;
	}

	public PagerVO findRecommend() {
		Map params = new HashMap();
		return findPaginated(Article.class.getName()+".findAllRecommend", params);
	}

	public PagerVO findArticlesByKeyword(String keyword) {
		SqlSession session = MyBatisUtil.getSession();
		
		try {
			
			if(keyword == null || keyword.trim().equals("")){
				return null;
			}
			
			String[] keywords = keyword.split(",| ");
			
			//���ҳ�������µ�ID�б�
			if(keywords != null && keywords.length > 0){
				StringBuffer sb = new StringBuffer();
				for(int i=0; i<keywords.length; i++){
					if(i != 0){
						sb.append(",");
					}
					sb.append("'"+keywords[i]+"'");
				}
				Map params = new HashMap();
				params.put("keywords", sb.toString());
				List articleIds = session.selectList(Article.class.getName()+".findArticlesIdByKeyword", 
						params);
				
				StringBuffer ids = new StringBuffer();
				for(int i=0; i<articleIds.size(); i++){
					if(i != 0){
						ids.append(",");
					}
					ids.append(articleIds.get(i));
				}
				
				params = new HashMap();
				params.put("ids", ids.toString());
				params.put("offset", 0);
				params.put("pagesize", Integer.MAX_VALUE);
				//�����������б�
				List articles = session.selectList(Article.class.getName()+".findArticlesByIds", 
						params);
				
				PagerVO pv = new PagerVO();
				pv.setDatas(articles);
				pv.setTotal(articleIds.size());
				return pv;
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally{
			//�ر�session
			session.close();
		}
		return null;
	}

	public void updateArticle(Article a) {
		a.setUpdateTime(new Date());
		SqlSession session = MyBatisUtil.getSession();
		try {
			//�������µĻ�����Ϣ
			session.update(Article.class.getName()+".update", a);
			
			//ɾ�����º�Ƶ��֮��Ĺ���
			session.delete(Article.class.getName()+".del_channel_article", a.getId());
			
			//�������º�Ƶ��֮��Ĺ���
			Set<Channel> channels = a.getChannels();
			if(channels != null){
				for(Channel c:channels){
					Map params = new HashMap();
					params.put("a", a);
					params.put("c", c);
					session.insert(Article.class.getName()+".insert_channel_article", params);
				}
			}
			
			//ɾ�����º͹ؼ���֮��Ĺ���
			session.delete(Article.class.getName()+".del_article_keyword", a.getId());
			
			//�����µ����º͹ؼ���֮��Ĺ���
			//�������º͹ؼ��ֵĹ�����
			if(a.getKeyword() != null && !a.getKeyword().trim().equals("")){
				String keyword = a.getKeyword();
				String[] keywords = keyword.split(",| ");//���տո�򶺺Ÿ���
				for(String kw:keywords){
					Map params = new HashMap();
					params.put("articleId", a.getId());
					params.put("keyword", kw);
					session.insert(Article.class.getName()+".insert_article_keyword", params);
				}
			}
			
			//�������µĸ�����Ϣ
			if(a.getAttachments() != null){
				List attachments = a.getAttachments();
				for (Iterator iterator = attachments.iterator(); iterator
						.hasNext();) {
					Attachment attachment = (Attachment) iterator.next();
					attachment.setArticleId(a.getId());
					session.insert(Article.class.getName()+".insert_attachment", attachment);
				}
			}
			
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally{
			session.close();
		}
	}

	public int updateClickNumber(int articleId) {
		SqlSession session = MyBatisUtil.getSession();
		int clickNumber = 0;
		try {
			
			clickNumber = (Integer)session.selectOne(Article.class.getName()+".selectClickNumber", articleId);
			clickNumber = clickNumber + 1;
			Article a = new Article();
			a.setId(articleId);
			a.setClickNumber(clickNumber);
			session.update(Article.class.getName()+".updateClickNumber", a);
			
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally{
			session.close();
		}
		return clickNumber;
	}

}
