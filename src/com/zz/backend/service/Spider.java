package com.zz.backend.service;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.zz.backend.model.Article;
import com.zz.backend.service.impl.SpiderForIBM;

public abstract class Spider {

	private static Map<String,Class<? extends Spider>> spiders = new HashMap<String,Class<? extends Spider>>();

	static{
		spiders.put("www.ibm.com", SpiderForIBM.class);
		spiders.put("www.oracle.com", SpiderForIBM.class);
	}

	protected HttpClient httpclient;
	protected String url;
	//����ڽ��͹����е�״̬��Ϣ�����磺Article�����б�
	protected HttpContext context; 
	
	//����URL��ַ��������Ӧ��Spider����
	
	public static Spider getInstance(String url){
		try{
			URL u = new URL(url);
			String host = u.getHost();
			return spiders.get(host).newInstance();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("�޷��ҵ���"+url+"����Ӧ�����棡");
		}
	}
	
	//�ռ�����
	public List<Article> collect(String url){
		
		//����HttpClient
		this.httpclient = new DefaultHttpClient();
		this.context = new BasicHttpContext();
		this.url = url;
		
		//�����������
//		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
//				new HttpHost("192.168.1.1", 808));
		
		//ִ���ռ�����
		execute();  //�Ǹ����󷽷�
		httpclient.getConnectionManager().shutdown();
		
		//��ȡ�ռ���������
		List<Article> articles = (List<Article>)context.getAttribute("articles");
		
		return articles;
	}
	
	public abstract void execute();
	
}
