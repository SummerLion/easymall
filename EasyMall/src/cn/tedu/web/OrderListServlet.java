package cn.tedu.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.OrderInfo;
import cn.tedu.bean.User;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;
/**
 * ��ѯ��ǰ�û������ж�����Ϣ
 */
public class OrderListServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.��ȡ��ǰ��½�û�
		User user = (User) request.getSession().getAttribute("user");
		
		//2.����service������û�id��ѯ��ǰ�û������ж�����Ϣ
		//�û�:�ŷ� 2������
		OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
		
		/*
		 * OrderInfo�б������һ�������Ķ�����Ϣ, һ������������Ӧ�ð���:
		 * Order
		 * Map<Product, Integer>(OrderItem(Product, buyNum)) 
		 */
		List<OrderInfo> list = service.findOrderByUserId(user.getId());
		
		//3.�����ж�����Ϣ����request��
		request.setAttribute("list", list);
		
		//4.����ת�������ж�����Ϣ���������б�ҳ�����չʾ!
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
