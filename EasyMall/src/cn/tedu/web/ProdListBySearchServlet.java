package cn.tedu.web;

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
 * ����������ѯ���з�����������Ʒ
 */
public class ProdListBySearchServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.��ȡ��������
		String search = request.getParameter("search");
		search = search == null ? "" : search;

		// 2.����service��ķ���, ����������ѯָ������Ʒ
		ProdService service = BasicFactory.getFactory().getInstance(
				ProdService.class);
		List<Product> list = service.findAllBySearch(search);

		// 3.��������Ʒ�ļ��ϴ���request��, ��ת������prod_list.jsp
		request.setAttribute("list", list);
		request.getRequestDispatcher("/prod_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
