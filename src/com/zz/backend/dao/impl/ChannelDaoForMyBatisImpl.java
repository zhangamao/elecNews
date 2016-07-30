package com.zz.backend.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.zz.backend.dao.ChannelDao;
import com.zz.backend.model.Channel;
import com.zz.backend.vo.PagerVO;

public class ChannelDaoForMyBatisImpl extends BaseDao implements ChannelDao{

	public void addChannel(Channel c) {
		// TODO Auto-generated method stub
		add(c);
	}

	public void delChannels(String[] ids) {
		// TODO Auto-generated method stub
		del(Channel.class, ids);
	}

	public Channel findChannelById(int channelId) {
		// TODO Auto-generated method stub
		return (Channel)findById(Channel.class, channelId);
	}

	public PagerVO findChannels() {
		// TODO Auto-generated method stub
		Map params = new HashMap();
		
		return findPaginated(Channel.class.getName()+".findPaginated", params);
	}

	public void updateChannel(Channel c) {
		// TODO Auto-generated method stub
		update(c);
	}

	
}
