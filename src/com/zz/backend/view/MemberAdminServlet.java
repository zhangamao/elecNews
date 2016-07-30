package com.zz.backend.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz.backend.dao.MemberDao;

public class MemberAdminServlet extends BaseServlet{

	private MemberDao memberDao;
	
	//��ҳ�г����еĻ�Ա
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute("pv", memberDao.findAllMembers());
		
		request.getRequestDispatcher("/backend/member/member_list.jsp").forward(request, response);
	}
	
	//ɾ����Ա
	public void del(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		//�ӽ������ID����
		String[] ids = request.getParameterValues("id");
		
		if(ids == null){
			//��ʾ����
			request.setAttribute("error", "�޷�ɾ�����£�id������Ϊ��");
			request.getRequestDispatcher("/backend/common/error.jsp").forward(request, response);
		
			return;
		}
		
		memberDao.delMembers(ids);
		response.sendRedirect(request.getContextPath()+"/backend/MemberAdminServlet");
		
	}
	public void setMemberDao(MemberDao memberDao){
		this.memberDao = memberDao;
	}
}
