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
 * �޸Ĺ��ﳵ����Ʒ�Ĺ�������
 */
public class AjaxUpdateBuyNumServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.��ȡ��Ʒid�͹�������
		String pid = request.getParameter("pid");
		int buyNum = Integer.parseInt(request.getParameter("buyNum"));
	
		//2.����service��ķ�����ѯָ��id����Ʒ
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		Product prod = service.findProdById(pid);
		
		//3.��cartmap�и���Ʒ�Ĺ��������޸�ΪbuyNum
		Map<Product, Integer> map = (Map<Product, Integer>) 
				request.getSession().getAttribute("cartmap");
		map.put(prod, buyNum);
		
		//4.������Ӧ
		response.getWriter().write("�޸ĳɹ�!");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
