package com.zz.backend.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.tags.ParagraphTag;

import com.zz.backend.model.Article;
import com.zz.backend.model.Attachment;
import com.zz.backend.service.Spider;
import com.zz.utils.HttpUtils;
import com.zz.utils.ParseUtils;


public class SpiderForIBM extends Spider{

	private Logger logger = Logger.getLogger(SpiderForIBM.class);
	
	public void execute() {
		
		try {
			//����URL��ַ����ȡ��ҳ����
			String html = HttpUtils.getHtml(httpclient, url);
			
			if(html == null){
				logger.error("�޷���ȡ��"+url+"����ַ������");
				throw new RuntimeException("�޷���ȡ��"+url+"����ַ������");
			}
			
			Article a = new Article();
			
			//�������µ���Դ
			a.setSource("www.ibm.com");
			
			//����ҳ���ݽ��з�������ȡ
			//�������µı���
			MetaTag titleTag = ParseUtils.parseTag(html, MetaTag.class, "name", "title");
			a.setTitle(titleTag.getMetaContent());

			//�������µĹؼ���
			MetaTag keywordTag = ParseUtils.parseTag(html, MetaTag.class, "name", "Keywords");
			if(keywordTag.getMetaContent().length() > 255){
				a.setKeyword(keywordTag.getMetaContent().substring(0, 255));
			}
			
			//�������µļ��
			MetaTag introTag = ParseUtils.parseTag(html, MetaTag.class, "name", "Abstract");
			a.setIntro(introTag.getMetaContent());
			
			//�������µ�����
			List<Div> authors = ParseUtils.parseTags(html, Div.class, "class", "author");
			String author = "";
			for(int i=0; i<authors.size(); i++){
				if(i != 0){
					author = author + ",";
				}
				Div div = authors.get(i);
				author = author + ParseUtils.parseTag(div.getStringText(), LinkTag.class).getStringText();
			}
			a.setAuthor(author);
			
			//�������µ�����
			String content = StringUtils.substringBetween(html, "<!-- MAIN_COLUMN_CONTENT_BEGIN -->", "<!-- CMA");
			
			//��ѯ���µ���������������ͼƬ�������ص�uploadĿ¼��Ȼ�󴴽�Attachment�������õ�Article������
			List<ImageTag> imageTags = ParseUtils.parseTags(content, ImageTag.class);
			if(imageTags != null){
				for(ImageTag it:imageTags){
					
					//�õ�ͼƬ���ڵ�·��Ŀ¼
					String baseUrl = url.substring(0, url.lastIndexOf("/")+1);
					
					//�����<img>��ǩ�е�src��ֵ
					String imageUrl = it.getImageURL();
					
					//ͼƬ�ľ���·��
					String absoluteUrl = baseUrl + imageUrl;

					//:   "���±���/xxx.jpg"
					String imageName = a.getTitle().replaceAll("/|\\\\|\\:|\\*|\\?|\\||\\<|>", "_")+"/" + imageUrl;
					
					//��ͼƬ���浽uploadĿ¼
					//����ȷ�������浽���ص�ͼƬ��·��
					String imageLocalFile = Attachment.ATTACHMENT_DIR + imageName;
					
					//���ͼƬ�Ѿ������ص����أ���������
					if(!new File(imageLocalFile).exists()){
						//����ͼƬ����Ϣ
						byte[] image = HttpUtils.getImage(httpclient, absoluteUrl);
						//ֱ��ʹ��new FileOutputStream(imageLocalFile)���ַ�ʽ������һ��
						//�ļ�����������ڵ�������ǣ��������ļ����ڵ�Ŀ¼�����ڣ��򴴽�����
						//����������׳��쳣��
						//���ԣ�ʹ�ø����Ĺ�����������һ���ļ������:FileUtils.openOutputStream(new File(imageLocalFile))
						//ͨ��������������ļ����ڵĸ�Ŀ¼�����ڵ�ʱ�򣬽��Զ����������еĸ�Ŀ¼
						IOUtils.write(image, FileUtils.openOutputStream(new File(imageLocalFile)));
						System.out.println("ͼƬ��"+absoluteUrl+"��������");
					}
					
					//���ÿ��ͼƬ������һ��Attachment����
					Attachment attachment = new Attachment();
					attachment.setContentType("image/jpeg");
					attachment.setName(imageName);
					attachment.setUploadTime(new Date());
					a.addAttachment(attachment);
				}
			}
			
			//�޸�content�е�����ͼƬ��src��ֵ
			//��src��ֵ������ǰ׺��upload_image/���±���/ͼƬ.jpg
			content = ParseUtils.modifyImageUrl(content, "upload_image/"+a.getTitle().replaceAll("/|\\\\|\\:|\\*|\\?|\\||\\<|>", "_")+"/");
			
			//ɾ��<hr>��"����ҳ"�����ӱ�ǩ
			content = ParseUtils.reomveTags(content, Div.class, "class", "ibm-alternate-rule");
			content = ParseUtils.reomveTags(content, ParagraphTag.class, "class", "ibm-ind-link ibm-back-to-top");
			
			a.setContent(content);
			
			//�����¶������HttpContext
			List<Article> articles = new ArrayList<Article>();
			articles.add(a);
			
			context.setAttribute("articles", articles);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
