package com.zz.backend.model;

import java.util.Date;

public class Attachment {
	
	public static final String ATTACHMENT_DIR = "d:/temp/upload/";
	
	private int id;
	
	//��ƪ���µĸ���
	private int articleId;
	
	//�ļ�������(·��)
	private String name;
	
	//�ļ�������
	private String contentType;
	
	//�ļ��ϴ���ʱ��
	private Date uploadTime;

	public boolean isImage(){
		return contentType.startsWith("image");
	}	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
}
