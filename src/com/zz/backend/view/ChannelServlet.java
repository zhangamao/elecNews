package com.zz.backend.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz.backend.dao.ChannelDao;
import com.zz.backend.model.Channel;
import com.zz.backend.vo.PagerVO;

public class ChannelServlet extends BaseServlet {

	private ChannelDao channelDao;

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PagerVO pv = channelDao.findChannels();

		request.setAttribute("pv", pv);
		request.getRequestDispatcher("/backend/channel/channel_list.jsp")
				.forward(request, response);
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��request�л�ȡ����
		String name = request.getParameter("name");
		String description = request.getParameter("description");

		Channel c = new Channel();
		c.setName(name);
		c.setDescription(description);

		channelDao.addChannel(c);

		// forward���ɹ�ҳ��
		request
				.getRequestDispatcher(
						"/backend/channel/add_channel_success.jsp").forward(
						request, response);
	}
	
	//�򿪸��½���
	public void updateInput(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		
		//���մӽ��洫�ݹ�����ID
		String id = request.getParameter("id");
		Channel c = channelDao.findChannelById(Integer.parseInt(id));
		request.setAttribute("channel", c);
		//forward������ҳ��
		request.getRequestDispatcher("/backend/channel/update_channel.jsp").forward(request, response);

	}
	//ִ�и��½���
	public void update(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		
		//���ȣ��ӽ������Ƶ���Ļ�����Ϣ��������ID�����ơ�������
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		Channel c = new Channel();
		c.setId(Integer.parseInt(id));
		c.setName(name);
		c.setDescription(description);
		channelDao.updateChannel(c);
		
		//forward�����³ɹ���ҳ��
		request.getRequestDispatcher("/backend/channel/update_channel_success.jsp").forward(request, response);
	}
	
	//ִ��ɾ������
	public void del(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException{
		
		//�ӽ������ID����
		String[] ids = request.getParameterValues("id");
		
		if(ids == null){
			//��ʾ������Ϣ
			request.setAttribute("error", "�޷�ɾ��Ƶ����ID������Ϊ��");
			request.getRequestDispatcher("/backend/common/error.jsp").forward(request, response);
			return;
		}
		
		channelDao.delChannels(ids);
		

		//ת���б�ҳ��
		response.sendRedirect(request.getContextPath()+"/backend/ChannelServlet");
		//request.getRequestDispatcher("/backend/ChannelServlet").forward(request, response);
	}
	
	public void setChannelDao(ChannelDao channelDao){
		this.channelDao = channelDao;
	}
}
