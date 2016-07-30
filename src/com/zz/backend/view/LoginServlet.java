package com.zz.backend.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.zz.backend.dao.AdminDao;
import com.zz.backend.model.Admin;

public class LoginServlet extends BaseServlet{

	private AdminDao adminDao;

	private int width;
	private int height;
	private int number; // ��ʾ���ٸ��ַ�
	private String codes; // ����Щ�ַ��ɹ�ѡ��

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		width = Integer.parseInt(config.getInitParameter("width"));
		height = Integer.parseInt(config.getInitParameter("height"));
		number = Integer.parseInt(config.getInitParameter("number"));
		codes = config.getInitParameter("codes");
	}

	//������֤��
	public void checkcode(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("image/jpeg");

		// ����һ��ͼƬ
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();

		// ������ɫ����
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);

		// ���ڱ߿�
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width - 1, height - 1);

		Random random = new Random();

		// ÿ���ַ�ռ�ݵĿ��
		int x = (width - 1) / number;
		int y = height - 4;

		StringBuffer sb = new StringBuffer();

		// ��������ַ�
		for (int i = 0; i < number; i++) {
			String code = String.valueOf(codes.charAt(random.nextInt(codes
					.length())));
			int red = random.nextInt(255);
			int green = random.nextInt(255);
			int blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue));

			Font font = new Font("Arial", Font.PLAIN,
					random(height / 2, height));
			g.setFont(font);

			g.drawString(code, i * x + 1, y);

			sb.append(code);
		}

		// ����֤�봮�ŵ�HTTP SESSION��
		request.getSession().setAttribute("codes", sb.toString());

		// �������һЩ��
		for (int i = 0; i < 50; i++) {
			int red = random.nextInt(255);
			int green = random.nextInt(255);
			int blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue));
			g.drawOval(random.nextInt(width), random.nextInt(height), 1, 1);
		}

		OutputStream out = response.getOutputStream();
		// ��ͼƬת��ΪJPEG����
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image);

		out.flush();
		out.close();

	}

	// ����ִ�е�¼��֤
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String checkcode = request.getParameter("checkcode"); // �û��������֤��

		// ϵͳ�ж���֤���Ƿ���ȷ
		// �ո����ɵ���֤�봮
		String sessionCodes = (String) request.getSession().getAttribute(
				"codes");

		 if(!sessionCodes.equalsIgnoreCase(checkcode)){
		 //�ض���[forward]����¼ҳ��
		 request.setAttribute("error", "��֤�����");
		 request.getRequestDispatcher("/backend/login.jsp").forward(request,
		 response);
		 return;
		 }

		// ϵͳ�ж��û����Ƿ���ڣ��ж������Ƿ���ȷ
		Admin admin = adminDao.findAdminByUsername(username);

		if (admin == null) {
			// forward��long.jsp��������ʾ���û��������ڡ�
			request.setAttribute("error", "�û���" + username + "��������");
			request.getRequestDispatcher("/backend/login.jsp").forward(request,
					response);
			return;
		}

		if (!admin.getPassword().equals(password)) {
			// forward��long.jsp��������ʾ���û����벻��ȷ��
			request.setAttribute("error", "�û���" + username + "�������벻��ȷ��������");
			request.getRequestDispatcher("/backend/login.jsp").forward(request,
					response);
			return;
		}

		// ��Ҫ�ѵ�¼�û�����Ϣ����HTTP SESSION
		request.getSession().setAttribute("LOGIN_ADMIN", username);

		// �ж϶�ͨ���ˣ�ת���̨������ҳ��
		response.sendRedirect(request.getContextPath() + "/backend/main.jsp");

	}
	
	//�˳���̨����ϵͳ
	public void quit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ���Http Session�е��������ݣ�����HTTP SESSION����
		// �����Ự
		request.getSession().invalidate();

		// ת���¼ҳ��
		response.sendRedirect(request.getContextPath() + "/backend/login.jsp");
	}	

	/**
	 * ����һ����min��max֮��������
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	private int random(int min, int max) {
		int m = new Random().nextInt(999999) % (max - min);
		return m + min;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
}
