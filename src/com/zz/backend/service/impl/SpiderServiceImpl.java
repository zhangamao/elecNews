package com.zz.backend.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zz.backend.dao.ArticleDao;
import com.zz.backend.model.Article;
import com.zz.backend.model.Channel;
import com.zz.backend.service.Spider;
import com.zz.backend.service.SpiderService;

public class SpiderServiceImpl implements SpiderService{

	private ArticleDao articleDao;
	
	public List<Article> collect(String url, String[] channelIds) {
		// TODO Auto-generated method stub
		Spider spider = Spider.getInstance(url);
		
		List<Article> articles = spider.collect(url);
		
		if(articles != null){
			Set channels = new HashSet();
			if(channelIds != null){
				for(String channelId: channelIds){
					Channel c = new Channel();
					c.setId(Integer.parseInt(channelId));
					channels.add(c);
				}
			}
			
			for(Article a : articles){
				a.setChannels(channels);
				a.setCreateTime(new Date());
				a.setType("зЊди");
				articleDao.addArticle(a);
			}
		}
		
		return articles;
	}

	public void setArticleDao(ArticleDao articleDao){
		this.articleDao = articleDao;
	}
	
}
