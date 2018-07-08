package cn.tedu.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.Order;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.Product;
import cn.tedu.bean.User;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;
/**
 * ����һ������
 */
public class OrderAddServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* ��Ӷ���ֻ���ǵ�½֮����û��ſ��Խ��еĲ���
		 * ������Ҫ��һ����������������, ����û���½�˾Ϳ����¶���
		 * ���û�е�½����Ҫ��ת����½ҳ��, ��½֮�����¶���
		 */
		//1.��ȡ��ǰ��½�û�
		User user = (User) request.getSession().getAttribute("user");
		
		//2.��ȡ������Ϣ, ��װ��һ��Order Bean��
		Order order = new Order();
		order.setId(UUID.randomUUID().toString());
		
		order.setReceiverinfo(request.getParameter("receiverinfo"));
		order.setPaystate(0);//֧��״̬ 0��ʾδ֧��
		order.setUser_id(user.getId());
		
		
		//3.��ȡ��ǰ�����е����ж�������Ϣ
		/*
		 * һ�������п��԰��������Ʒ, ��һ����Ʒ��Ӧһ��������
		 * ����һ�������п��Զ�Ӧ���������, ��ǰ���������еĶ�����
		 * ������һ��list����������
		 * �ӹ���map��ȡ��Ҫ�������Ʒ��Ϣ, ����OrderItem��
		 * �ٽ����OrderItem����list������
		 */
		List<OrderItem> list = new ArrayList<OrderItem>();
		Map<Product, Integer> cartmap = (Map<Product, Integer>) 
				request.getSession().getAttribute("cartmap");
		//4.�ӹ���map��ȡ��Ҫ�������Ʒ��Ϣ, ����OrderItem��
		double totalMoney = 0;
		for(Map.Entry<Product, Integer> entry : cartmap.entrySet()){
			OrderItem item = new OrderItem();
			item.setOrder_id(order.getId());//�������
			item.setProduct_id(entry.getKey().getId());//��Ʒ���
			item.setBuynum(entry.getValue());//��������
			/*
			 * ������� = ������Ʒ�����ܺ� 
			 * ��Ʒ������� = ���� * ����
			 */
			totalMoney += entry.getKey().getPrice() * entry.getValue();
			order.setMoney(totalMoney);//�ֶ����㶩�����
			
			//5.�ٽ����OrderItem����list������
			list.add(item);
		}
		//6.����service��ķ���, ʵ����Ӷ�������
		OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
		try {
			service.addOrder(order, list);
			//7.�����ﳵ�е���Ʒ���
			cartmap.clear();//���map�е���������
			//8.������ӳɹ�, ��ת�������б�ҳ��
			response.sendRedirect(request.getContextPath()+"/servlet/OrderListServlet");
			//response.sendRedirect(request.getContextPath()+"/index.jsp");
		} catch (MsgException e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
