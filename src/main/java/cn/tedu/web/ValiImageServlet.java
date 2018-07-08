package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.utils.VerifyCode;
/**
 * ����������֤��ͼƬ����Ӧ�������
 */
public class ValiImageServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//�����������Ҫ������֤��ͼƬ
		//Expires Cache-Control
		response.setDateHeader("Expires", -1);
		response.setHeader("Cache-Control", "no-cache");
		
		VerifyCode vc = new VerifyCode();
		//��ͼƬ���浽response��������, ����Ӧ�������
		vc.drawImage(response.getOutputStream());
		
		//��ȡͼƬ�ϵ���֤��
		String code = vc.getCode();
		//����֤���ı����浽session��, ���ں��ڵ�У��
		request.getSession().setAttribute("code", code);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
