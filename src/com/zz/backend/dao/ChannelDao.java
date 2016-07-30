package com.zz.backend.dao;

import com.zz.backend.model.Channel;
import com.zz.backend.vo.PagerVO;

public interface ChannelDao {

	public void addChannel(Channel c);
	public void delChannels(String[] ids);
	public Channel findChannelById(int channelId);
	public void updateChannel(Channel c);
	public PagerVO findChannels();
}
