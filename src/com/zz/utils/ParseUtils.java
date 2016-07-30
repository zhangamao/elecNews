package com.zz.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.nodes.RemarkNode;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

public class ParseUtils {
	
	/**
	 * ��ȡ����ĳ������ֵ�ı�ǩ�б�
	 * @param <T>
	 * @param html ����ȡ��HTML�ı�
	 * @param tagType ��ǩ����
	 * @param attributeName ĳ�����Ե�����
	 * @param attributeValue ����Ӧȡ��ֵ
	 * @return
	 */
	public static <T extends TagNode> List<T> parseTags(String html,final Class<T> tagType,final String attributeName,final String attributeValue){
		try {
			//����һ��HTML������
			Parser parser = new Parser();
			parser.setInputHTML(html);

			NodeList tagList = parser.parse(
				new NodeFilter(){
					public boolean accept(Node node) {
						
						if(node.getClass() == tagType){
							T tn = (T)node;
							if(attributeName == null){
								return true;
							}
							
							String attrValue = tn.getAttribute(attributeName);
							if(attrValue != null && attrValue.equals(attributeValue)){
								return true;
							}
						}

						return false;
					}
				}
			);
			
			List<T> tags = new ArrayList<T>();
			for(int i=0; i<tagList.size(); i++){
				T t = (T)tagList.elementAt(i);
				tags.add(t);
			}
			
			return tags;
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T extends TagNode> List<T> parseTags(String html,final Class<T> tagType){
		return parseTags(html, tagType,null,null);
	}
	
	public static <T extends TagNode> T parseTag(String html,final Class<T> tagType,final String attributeName,final String attributeValue){
		List<T> tags = parseTags(html, tagType, attributeName, attributeValue);
		if(tags != null && tags.size() > 0){
			return tags.get(0);
		}
		return null;
	}
	
	public static <T extends TagNode> T parseTag(String html,final Class<T> tagType){
		return parseTag(html, tagType,null,null);
	}
	
	/**
	 * �޸�HTML������������������ͼƬ�����ӵ�ַ������ָ����ǰ׺
	 * @param html Ҫ���޸ĵ�HTML����
	 * @param prefix Ҫ���ӵ�ǰ׺
	 * @return ���޸�֮���HTML����
	 */
	public static String modifyImageUrl(String html,String prefix){
		try {
			
			StringBuffer sb = new StringBuffer();
			
			//����һ��HTML������
			Parser parser = new Parser();
			parser.setInputHTML(html);
			
			//nodeList�У���������ҳ�е���������
			NodeList nodeList = parser.parse(
				new NodeFilter(){
					public boolean accept(Node node) {
						return true;
					}
				}
			);
			
			for(int i=0; i<nodeList.size(); i++){
				Node node = nodeList.elementAt(i);
				if(node instanceof ImageTag){
					//�����<img>��ǩ
					ImageTag it = (ImageTag)node;
					it.setImageURL(prefix+it.getImageURL());
					sb.append(it.toHtml());
				}else if(node instanceof TextNode){ //�ı���ǩ��ԭ�����
					TextNode text = (TextNode)node;
					sb.append(text.getText());
				}else{ //�������б�ǩ��ԭ�����
					sb.append("<");
					sb.append(node.getText());
					sb.append(">");
				}
			}
			
			return sb.toString();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	public static <T extends TagNode> String reomveTags(String html,final Class<T> tagType,String attributeName,String attributeValue){
		try {
			StringBuffer sb = new StringBuffer();
			Parser parser = new Parser();
			parser.setInputHTML(html);
			NodeList allNodes = parser.parse(
				new NodeFilter(){
					public boolean accept(Node node) {
						return true;
					}
				}
			);
			
			for(int i=0; i<allNodes.size(); i++){
				Node node = allNodes.elementAt(i);
				if(node.getClass() == tagType){
					TagNode tn = (TagNode)node;
					//����Ƿ���Ҫ���tag�ڵ��ǩ
					if(StringUtils.equals(tn.getAttribute(attributeName),attributeValue)){
						if(!tn.isEndTag()){ //����һ����ʼ��ǩ
							allNodes.remove(tn); //�Ƴ����ڵ�
							allNodes.remove(tn.getEndTag()); //�Ƴ����Ӧ�Ľ����ڵ�
							NodeList nl = tn.getChildren(); //�Ƴ�������������ӽڵ�
							SimpleNodeIterator sni = nl.elements();
							while(sni.hasMoreNodes()){
								Node n = sni.nextNode();
								allNodes.remove(n);
							}
							i = i -1;
						}
					}else{ //���������Ҫ��ԭ�����
						sb.append("<");
						sb.append(node.getText());
						sb.append(">");
					}
				}else if(node instanceof TextNode){
					TextNode text = (TextNode)node;
					sb.append(text.getText());
				}else if(node instanceof RemarkNode){
					RemarkNode rn = (RemarkNode)node;
					sb.append(rn.toHtml());
				}
				else{
					sb.append("<");
					sb.append(node.getText());
					sb.append(">");
				}
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}		
}
