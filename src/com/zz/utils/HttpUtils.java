package com.zz.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Parser;

public class HttpUtils {
	public static String getHtml(HttpClient httpclient,String url){
		try {

			// ����HTTP GET���������������
			HttpGet get = new HttpGet(url);
			
			// ��÷�������Ӧ�ĵ�������Ϣ
			HttpResponse response = httpclient.execute(get);
			// ��÷�������Ӧ��������Ϣ�壨������HTTP HEAD��
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// �����Ӧ���ַ���������Ϣ
				// ����ȡHTTP HEAD�ģ�Content-Type:text/html;charset=UTF-8�е��ַ�����Ϣ
				String charset = EntityUtils.getContentCharSet(entity);
				InputStream is = entity.getContent();
				byte[] content = IOUtils.toByteArray(is);
				
				String html = null;
				//����HTTP HEAD�в�����charset����Ϣ�������ҳ���ݵ�<meta>��ǩ����ȡcharset��Ϣ
				if(charset == null){
					//������ISO-8859-1�������������HTML
					html = new String(content,"ISO-8859-1");
					Parser parser = new Parser();
					parser.setInputHTML(html);
					//���Խ��ͱ���ҳ��HTMLParser�ڽ�����ҳ�Ĺ����У����Զ���ȡ
					//<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>��
					//�������ı�����Ϣ
					parser.parse(null);
					
					//�����ҳ�в�����������Ϣ�������ֵ���ؿ�
					charset = parser.getEncoding();
				}
				
				if(charset != null && !charset.equals("ISO-8859-1")){ //���Բ��ò²��㷨�����ں��ԣ�
					html = new String(content,charset);
				}

				return html;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] getImage(HttpClient httpclient,String url){
		try {

			// ����HTTP GET���������������
			HttpGet get = new HttpGet(url);
			
			// ��÷�������Ӧ�ĵ�������Ϣ
			HttpResponse response = httpclient.execute(get);
			// ��÷�������Ӧ��������Ϣ�壨������HTTP HEAD��
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream is = entity.getContent();
				return IOUtils.toByteArray(is);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
