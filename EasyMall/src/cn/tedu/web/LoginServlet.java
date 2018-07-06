package cn.tedu.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.User;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.UserService;
import cn.tedu.utils.WebUtils;

/**
 * �����½����
 */
public class LoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.���������������
		// request.setCharacterEncoding("utf-8");

		// 2.��ȡ�������(��ȡ�û��ĵ�½��Ϣ)
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remname = request.getParameter("remname");

		// 3.����service��ķ������е�½
		UserService service = BasicFactory.getFactory().getInstance(
				UserService.class);
		User user = service.loginUser(username, WebUtils.md5(password));

		if (user != null) {// �û���������ȷ
			// 4.ʵ�ּ�ס�û�������
			if ("true".equals(remname)) {
				Cookie cookie = new Cookie("remname", URLEncoder.encode(
						username, "utf-8"));
				cookie.setPath(request.getContextPath() + "/");
				cookie.setMaxAge(3600 * 24 * 30);
				response.addCookie(cookie);
			} else {// ȡ����ס�û���(ɾ��Cookie)
				Cookie cookie = new Cookie("remname", "");
				cookie.setPath(request.getContextPath() + "/");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}

			// 5.�ж��Ƿ���Ҫ30�����Զ���½
			String autologin = request.getParameter("autologin");
			if ("true".equals(autologin)) {// ʵ��30���Զ���½
				// ���û��������뱣���Cookie��
				Cookie c = new Cookie("autologin", URLEncoder.encode(username, "utf-8") + ":" + WebUtils.md5(password));
				c.setMaxAge(60 * 60 * 24 * 30);// ����Cookie30��
				c.setPath(request.getContextPath() + "/");
				// ��Cookie���͸������
				response.addCookie(c);
			} else {// ȡ��30���Զ���½
				Cookie c = new Cookie("autologin", "");
				c.setMaxAge(0);// ����Ϊ0����ɾ��
				c.setPath(request.getContextPath() + "/");
				response.addCookie(c);
			}

			// 6.���е�½
			request.getSession().setAttribute("user", user);

			// 7.��½�ɹ��ض�����ҳ
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		} else {// �û������벻��ȷ
			request.setAttribute("msg", "�û��������벻��ȷ!");
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
