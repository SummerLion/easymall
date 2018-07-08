package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;

public class OrderDeleteServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1�����ն���id
		String id = request.getParameter("id");
		//2������ҵ������
		OrderService orderService = BasicFactory.getFactory().
					getInstance(OrderService.class);
		try{
			//3������ҵ���ɾ���ķ���
			orderService.deleteOrderByOid(id);
			//4����ʾɾ���ɹ�
			response.getWriter().write("����ɾ���ɹ���2����Զ���ת...");
		}catch (MsgException e) {
			//4����ʾɾ��ʧ��
			response.getWriter().write("����ɾ��ʧ��,2����Զ���ת...");
		}
		//5�������Զ���ת
		response.setHeader("refresh", "2;url="+
		   request.getContextPath()+
		   "/servlet/OrderListServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
