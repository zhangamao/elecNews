package com.zz.backend.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz.SystemContext;
import com.zz.backend.dao.ArticleDao;
import com.zz.backend.dao.ChannelDao;
import com.zz.backend.model.Article;
import com.zz.backend.model.Attachment;
import com.zz.backend.vo.PagerVO;
import com.zz.utils.RequestUtil;

public class ArticleServlet extends BaseServlet{

	private ArticleDao articleDao;
	private ChannelDao channelDao;
	
	// �����������ִ�в�ѯ����
	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//�ӽ����л�ȡtitle����
		String title = request.getParameter("title");
		PagerVO pv = articleDao.findArticles(title);
		request.setAttribute("pv", pv);
		
		// forward��article_list.jsp
		request.getRequestDispatcher("/backend/article/article_list.jsp")
				.forward(request, response);
	}
	
	//�������
	public void addInput(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException{
		//������е�Ƶ���б�
		SystemContext.setOffset(0);
		SystemContext.setPagesize(Integer.MAX_VALUE);
		PagerVO pv = channelDao.findChannels();
		request.setAttribute("channels", pv.getDatas());
		
		request.getRequestDispatcher("/backend/article/add_article.jsp")
				.forward(request, response);
	}
	
	public void add(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException{
		Article a = (Article)RequestUtil.copyParam(Article.class, request);
		
		// ��request�л���ϴ��ļ����й���Ϣ
		Attachment[] attachments = (Attachment[]) request.getParameterMap()
				.get("attachs");
		if (attachments != null) {
			for (Attachment atta : attachments) {
				System.out.println(atta.getName() + ",�Ѿ����ϴ����ļ������ǣ�"
						+ atta.getContentType());
				a.addAttachment(atta);// GRASPģʽ��ר��ģʽ������
			}
		}

		articleDao.addArticle(a);

		// forward���ɹ�ҳ��
		request.getRequestDispatcher(
						"/backend/article/add_article_success.jsp").forward(
						request, response);
	}
			
	// �򿪸��½���
	public void updateInput(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// ���մӽ��洫�ݹ�����ID
		String id = request.getParameter("id");

		Article a = articleDao.findArticleById(Integer.parseInt(id));

		request.setAttribute("article", a);

		// ������е�Ƶ���б�
		SystemContext.setOffset(0);
		SystemContext.setPagesize(Integer.MAX_VALUE);
		PagerVO pv = channelDao.findChannels();
		request.setAttribute("channels", pv.getDatas());

		// forward������ҳ��
		request.getRequestDispatcher("/backend/article/update_article.jsp")
				.forward(request, response);
	}

	// ִ�и��²���
	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Article a = (Article) RequestUtil.copyParam(Article.class, request);

		// ��request�л���ϴ��ļ����й���Ϣ
		Attachment[] attachments = (Attachment[]) request.getParameterMap()
				.get("attachs");
		if (attachments != null) {
			for (Attachment atta : attachments) {
				System.out.println(atta.getName() + ",�Ѿ����ϴ����ļ������ǣ�"
						+ atta.getContentType());
				a.addAttachment(atta);// GRASPģʽ��ר��ģʽ������
			}
		}

		articleDao.updateArticle(a);

		// forward�����³ɹ���ҳ��
		request.getRequestDispatcher(
				"/backend/article/update_article_success.jsp").forward(request,
				response);
	}

	//ɾ�����µĸ���
	public void delAttachment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// �ӽ������ID����
		String attachmentId = request.getParameter("attachmentId");
		
		articleDao.delAttachment(Integer.parseInt(attachmentId));
		
		//���ظղŵĸ��½���
		response.sendRedirect(request.getHeader("referer"));
	}

	// ִ��ɾ������
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// �ӽ������ID����
		String[] ids = request.getParameterValues("id");

		if (ids == null) {
			// ��ʾ����(forward������ҳ��)
			request.setAttribute("error", "�޷�ɾ�����£�ID������Ϊ��");
			request.getRequestDispatcher("/backend/common/error.jsp").forward(
					request, response);
			return;
		}
		articleDao.delArticles(ids);

		// ת���б�ҳ��
		// request.getRequestDispatcher("/backend/ArticleServlet").forward(request,
		// response);
		response.sendRedirect(request.getContextPath()
				+ "/backend/ArticleServlet");
	}

	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	public void setChannelDao(ChannelDao channelDao) {
		this.channelDao = channelDao;
	}
}
