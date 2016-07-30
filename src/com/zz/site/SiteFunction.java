package com.zz.site;

import javax.servlet.jsp.PageContext;

import com.zz.backend.dao.ArticleDao;
import com.zz.backend.dao.ChannelDao;
import com.zz.backend.model.Article;
import com.zz.backend.model.Channel;
import com.zz.backend.view.InitBeanFactoryServlet;
import com.zz.utils.BeanFactory;



public class SiteFunction {
	
	/**
	 * ����Ƶ��ID�õ�Ƶ��
	 * @param pc
	 * @param channelId
	 * @return
	 */
	public static Channel findChannelById(PageContext pc,String channelId){
		BeanFactory factory = (BeanFactory)pc.getServletContext().getAttribute(InitBeanFactoryServlet.INIT_FACTORY_NAME);
		ChannelDao cd = (ChannelDao)factory.getBean("channelDao");
		return cd.findChannelById(Integer.parseInt(channelId));
	}
	
	/**
	 * ��������ID�õ�Article����
	 * @param pc
	 * @param articleId
	 * @return
	 */
	public static Article findArticleById(PageContext pc,String articleId){
		BeanFactory factory = (BeanFactory)pc.getServletContext().getAttribute(InitBeanFactoryServlet.INIT_FACTORY_NAME);
		ArticleDao articleDao = (ArticleDao)factory.getBean("articleDao");
		return articleDao.findArticleById(Integer.parseInt(articleId));
	}
}
