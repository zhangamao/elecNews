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
	
	// 在这个方法中执行查询工作
	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//从界面中获取title参数
		String title = request.getParameter("title");
		PagerVO pv = articleDao.findArticles(title);
		request.setAttribute("pv", pv);
		
		// forward到article_list.jsp
		request.getRequestDispatcher("/backend/article/article_list.jsp")
				.forward(request, response);
	}
	
	//添加文章
	public void addInput(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException{
		//查出所有的频道列表
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
		
		// 从request中获得上传文件的有关信息
		Attachment[] attachments = (Attachment[]) request.getParameterMap()
				.get("attachs");
		if (attachments != null) {
			for (Attachment atta : attachments) {
				System.out.println(atta.getName() + ",已经被上传，文件类型是："
						+ atta.getContentType());
				a.addAttachment(atta);// GRASP模式中专家模式的运用
			}
		}

		articleDao.addArticle(a);

		// forward到成功页面
		request.getRequestDispatcher(
						"/backend/article/add_article_success.jsp").forward(
						request, response);
	}
			
	// 打开更新界面
	public void updateInput(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 接收从界面传递过来的ID
		String id = request.getParameter("id");

		Article a = articleDao.findArticleById(Integer.parseInt(id));

		request.setAttribute("article", a);

		// 查出所有的频道列表
		SystemContext.setOffset(0);
		SystemContext.setPagesize(Integer.MAX_VALUE);
		PagerVO pv = channelDao.findChannels();
		request.setAttribute("channels", pv.getDatas());

		// forward到更新页面
		request.getRequestDispatcher("/backend/article/update_article.jsp")
				.forward(request, response);
	}

	// 执行更新操作
	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Article a = (Article) RequestUtil.copyParam(Article.class, request);

		// 从request中获得上传文件的有关信息
		Attachment[] attachments = (Attachment[]) request.getParameterMap()
				.get("attachs");
		if (attachments != null) {
			for (Attachment atta : attachments) {
				System.out.println(atta.getName() + ",已经被上传，文件类型是："
						+ atta.getContentType());
				a.addAttachment(atta);// GRASP模式中专家模式的运用
			}
		}

		articleDao.updateArticle(a);

		// forward到更新成功的页面
		request.getRequestDispatcher(
				"/backend/article/update_article_success.jsp").forward(request,
				response);
	}

	//删除文章的附件
	public void delAttachment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 从界面接收ID参数
		String attachmentId = request.getParameter("attachmentId");
		
		articleDao.delAttachment(Integer.parseInt(attachmentId));
		
		//返回刚才的更新界面
		response.sendRedirect(request.getHeader("referer"));
	}

	// 执行删除操作
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 从界面接收ID参数
		String[] ids = request.getParameterValues("id");

		if (ids == null) {
			// 提示错误(forward到错误页面)
			request.setAttribute("error", "无法删除文章，ID不允许为空");
			request.getRequestDispatcher("/backend/common/error.jsp").forward(
					request, response);
			return;
		}
		articleDao.delArticles(ids);

		// 转向列表页面
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
