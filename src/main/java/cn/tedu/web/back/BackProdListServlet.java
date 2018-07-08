package cn.tedu.web.back;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.Product;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;
/**
 * �����ѯ���е���Ʒ��Ϣ����ʾ����ҳ��
 */
public class BackProdListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 1.����Service��ķ���(findAll)��ѯ������Ʒ, 
		 * ����������Ʒ�������list���� */
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		List<Product> list =  service.findAll();
		
		/* 2.��������Ʒ��list���ϴ���request�� */
		request.setAttribute("list", list);
		
		/* 3.��������Ʒ��list����ͨ��ת������prod_list.jsp����չʾ */
		request.getRequestDispatcher("/backend/prod_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
