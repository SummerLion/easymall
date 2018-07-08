package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.factory.BasicFactory;
import cn.tedu.service.UserService;

/**
 * ����ajax����û����Ƿ���ڵ�����
 */
public class AjaxCheckUsernameServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.��������(POST)
		// response.setContentType("text/html;charset=utf-8");
		// request.setCharacterEncoding("utf-8");

		// 2.��ȡ�������(�û���)
		String username = request.getParameter("username");

		// 3.����service��ķ�������û����Ƿ����
		UserService service = BasicFactory.getFactory().getInstance(
				UserService.class);

		boolean result = service.hasUser(username);
		/*
		 * if(result){ response.getWriter().write("�û����Ѵ���!"); }else{
		 * response.getWriter().write("��ϲ,�û�������ʹ��!"); }
		 */
		response.getWriter().write(result ? "�û����Ѵ���!" : "��ϲ,�û�������ʹ��!");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
