package cn.tedu.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.Product;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

/**
 * ����Ʒ�������������빺�ﳵ ɾ�����ﳵ��ָ������Ʒ
 */
public class CartUpdateServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.��ȡ��Ҫ���빺�ﳵ����Ʒid����������
		String pid = request.getParameter("pid");
		int buyNum = Integer.parseInt(request.getParameter("buyNum"));

		// 2.����service��ķ�������id��ѯ��Ʒ��Ϣ
		ProdService service = BasicFactory.getFactory().getInstance(
				ProdService.class);
		Product prod = service.findProdById(pid);

		// 3.��ȡsession�б�����Ʒ�Ĺ��ﳵmap
		Map<Product, Integer> map = (Map<Product, Integer>) request
				.getSession().getAttribute("cartmap");

		// 4.����Ʒ�������������빺�ﳵ
		/*
		 * �ڽ���Ʒ���빺�ﳵʱ, �������Ʒ�ڹ��ﳵ���Ѿ�������, ��ô��������Ӧ���� "֮ǰ�Ĺ�������"+"����Ҫ����Ĺ�������"
		 * ����ǵ�һ�ν���Ʒ���빺�ﳵ, ֱ�ӽ���Ʒ�Ͷ�Ӧ�Ĺ����������뼴��!
		 */
		//�����Ʒ�Ĺ�������С��0 ���ڹ��ﳵ��ɾ������Ʒ
		if(buyNum < 0){
			map.remove(prod);
		}else{
			map.put(prod, map.containsKey(prod) ? 
					map.get(prod) + buyNum : buyNum);
		}

		// 5.�ض��򵽹��ﳵ�б�ҳ��
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
