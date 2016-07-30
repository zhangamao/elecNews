package com.zz.utils;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.Converter;
import org.apache.log4j.Logger;

import com.zz.backend.model.Channel;



public class ChannelsSetConverter implements Converter {

	private Logger logger = Logger.getLogger(ChannelsSetConverter.class);
	
	public Object convert(Class targetClass, Object value) {
		
		String[] channelIds = null;
		
		if(value == null){
			logger.warn("�޷����յ�Ƶ��IDת��ΪƵ���б�");
			return null;
		}
		
		if(value instanceof String){
			channelIds = new String[]{(String)value};
		}
		if(value instanceof String[]){
			channelIds = (String[])value;
		}
		
		if(channelIds != null){
			Set channels = new HashSet();
			for(String channelId:channelIds){
				Channel c = new Channel();
				c.setId(Integer.parseInt(channelId));
				channels.add(c);
			}
			return channels;
		}
		
		return null;
	}

}
