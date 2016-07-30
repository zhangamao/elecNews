package com.zz.site;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz.backend.dao.MemberDao;
import com.zz.backend.model.Member;
import com.zz.utils.RequestUtil;



public class MemberServlet extends com.zz.backend.view.BaseServlet {

	private MemberDao memberDao;
	
	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Member m = loginMember(request);
		if(m == null){
			request.setAttribute("error", "����δ��¼�޷�����");
			request.getRequestDispatcher("/backend/common/error.jsp").forward(request, response);
			return;
		}
		//TODO ���ݵ�¼��Ա����ѯ��Ա�ĸ�����ϸ����Ϣ�������ϴε�¼��ʱ��ȵ�
		
		request.setAttribute("member", m);
		
		request.getRequestDispatcher("/member/index.jsp").forward(request, response);
	}
	
	//�򿪻�Աע��ҳ��
	public void regInput(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		

		request.getRequestDispatcher("/member/reg_input.jsp").forward(request, response);
	}	
	
	//��Աע��
	public void reg(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Member member = (Member)RequestUtil.copyParam(Member.class, request);
		
		try{
			memberDao.addMember(member);
		}catch(Exception e){
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/member/reg_input.jsp").forward(request, response);
			return;
		}
		request.setAttribute("success", "ע���Ա�ɹ��������µ�¼��");
		request.getRequestDispatcher("/common/success.jsp").forward(request, response);
	}
	
	//�򿪸��¸�����Ϣ�Ľ���
	public void updateInput(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Member m = loginMember(request);
		if(m == null){
			request.setAttribute("error", "����δ��¼�޷�����");
			request.getRequestDispatcher("/backend/common/error.jsp").forward(request, response);
			return;
		}
		m = memberDao.findMemberById(m.getId());
		
		request.setAttribute("member", m);
		
		request.getRequestDispatcher("/member/update_input.jsp").forward(request, response);
	}
	
	//���¸�����Ϣ
	public void update(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Member m = loginMember(request);
		if(m == null){
			request.setAttribute("error", "����δ��¼�޷�����");
			request.getRequestDispatcher("/backend/common/error.jsp").forward(request, response);
			return;
		}
		
		Member member = (Member)RequestUtil.copyParam(Member.class, request);
		
		memberDao.updateMember(member);
		
		request.setAttribute("success", "���¸�����Ϣ�ɹ���");
		
		//����һ��session�е�member�����Ա���ҳ�����ܹ����Ͽ�����ǰ��¼�û���ʾ�����ĸı�
		m.setName(member.getName());
		
		request.getRequestDispatcher("/common/success.jsp").forward(request, response);
	}
	
	//�򿪸��¸����������
	public void updatePasswordInput(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Member m = loginMember(request);
		if(m == null){
			request.setAttribute("error", "����δ��¼�޷�����");
			request.getRequestDispatcher("/backend/common/error.jsp").forward(request, response);
			return;
		}
		m = memberDao.findMemberById(m.getId());
		
		request.setAttribute("member", m);
		
		request.getRequestDispatcher("/member/update_password_input.jsp").forward(request, response);
	}
	
	public void updatePassword(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Member m = loginMember(request);
		if(m == null){
			request.setAttribute("error", "����δ��¼�޷�����");
			request.getRequestDispatcher("/backend/common/error.jsp").forward(request, response);
			return;
		}
		
		String id = request.getParameter("id");
		String oldPass = request.getParameter("oldPassword");
		String newPass = request.getParameter("newPassword");
		
		try{
			memberDao.updatePassword(Integer.parseInt(id), oldPass, newPass);
		}catch(Exception e){
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/backend/common/error.jsp").forward(request, response);
			return;
		}
		request.setAttribute("success", "��������ɹ���");
		request.getRequestDispatcher("/common/success.jsp").forward(request, response);
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

}
