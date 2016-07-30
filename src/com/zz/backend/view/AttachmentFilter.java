package com.zz.backend.view;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.zz.backend.model.Attachment;

public class AttachmentFilter implements Filter{

	private Logger logger = Logger.getLogger(AttachmentFilter.class);
	
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//�ж������URI���Ƿ����"upload_image"
		//�������"upload_image"�����d:/temp/uploadĿ¼�ж�ȡ��Ӧ���ļ������Ұ��ļ���������д��response
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		String requestURI = req.getRequestURI();
		
		//��Ϊ·���п��ܰ����о����������Ϣ��������Ҫ����ת��Ϊ��ȷ���ַ���
		requestURI = URLDecoder.decode(requestURI, request.getCharacterEncoding());
		//System.out.println(requestURI);
		logger.debug(requestURI);
		
		//indexOf����ֵ��
		//-1��ʾ����������ַ���
		//ĳ��ֵ���ͱ�ʾ����ַ�����ʼ�ַ�������
		//requestURI��ֵ�������ǣ�/cms/backend/upload_image/logo.gif
		int index = requestURI.indexOf("/upload_image/");
		if(index != -1){ 
			String imageName = requestURI.substring(index+"/upload_image/".length());
			byte[] image = FileUtils.readFileToByteArray(new File(Attachment.ATTACHMENT_DIR+imageName));
			response.setContentType("image/jpeg");
			response.getOutputStream().write(image);
			return;
		}
		
		index = requestURI.indexOf("/upload_file/");
		if(index != -1){
			String fileName = requestURI.substring(index+"/upload_file/".length());
			byte[] file = FileUtils.readFileToByteArray(new File(Attachment.ATTACHMENT_DIR+fileName));
			response.setContentType("application/x-msdownload");
			response.getOutputStream().write(file);
			return;
		}
		
		//������������ͨ�У�
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
