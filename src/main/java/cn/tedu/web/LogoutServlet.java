package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * �����û���ע������
 */
public class LogoutServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ɱ��session
		request.getSession().invalidate();
		
		//����û�����˳���ť, Ĭ����ȡ���Զ���½, ��ɾ���Զ���½Cookie
		Cookie c = new Cookie("autologin", "");
		c.setMaxAge(0);// ����Ϊ0����ɾ��
		c.setPath(request.getContextPath() + "/");
		response.addCookie(c);
		
		//�˳�֮����ת��ҳ
		response.sendRedirect(request.getContextPath()+"/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
