package com.zz.backend.service;

import java.util.List;

import com.zz.backend.model.Article;

public interface SpiderService {

	public List<Article> collect(String url, String[] channelIds);
}
