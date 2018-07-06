package cn.tedu.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.tedu.bean.User;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.UserService;
import cn.tedu.utils.WebUtils;

public class RegistServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.�������������������(POST�ύ)
		// request.setCharacterEncoding("utf-8");
		// >>������Ӧ��������
		// response.setContentType("text/html;charset=utf-8");

		// 2.У����֤��
		String valistr = request.getParameter("valistr");
		if (WebUtils.isNull(valistr)) {
			// ����ʾ��Ϣ����request����,ͨ��ת������Ϣ����regist.jsp������ʾ
			request.setAttribute("msg", "��֤�벻��Ϊ��!");
			request.getRequestDispatcher("/regist.jsp").forward(request,
					response);
			return;
		}
		String code = (String) request.getSession().getAttribute("code");
		if (!valistr.equalsIgnoreCase(code)) {
			request.setAttribute("msg", "��֤�벻��ȷ");
			request.getRequestDispatcher("/regist.jsp").forward(request,
					response);
			return;
		}

		// 3.��ȡ�������, �������ݷ�װ��JavaBean��
		User user = new User();
		/* ǧ��Ҫ�����!!! */
		try {
			BeanUtils.populate(user, request.getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			// 4.����JavaBean�е�checkData����У������
			user.checkData();
			
			//��password md5���ܺ��ٴ������ݿ�
			user.setPassword(WebUtils.md5(user.getPassword()));

			// 5.ʵ��ע��(���û���Ϣ��������ݿ�)
			UserService service = BasicFactory.getFactory().getInstance(
					UserService.class);
			service.registUser(user);
		} catch (MsgException e) {
			/* ��ȡ�׳��쳣��Ϣ, ����request��, ��ת��regist.jsp */
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/regist.jsp").forward(request,
					response);
			return;
		}

		// 5.��ʾ�û�ע��ɹ�, 3��֮����ת����ҳ!
		response.getWriter()
				.write("<h1 style='color:red;text-align:center'>��ϲ��ע��ɹ�, 3��֮�󽫻���ת����ҳ...</h1>");
		response.setHeader("refresh", "3;url=" + request.getContextPath()
				+ "/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
