package cn.tedu.web.back;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;
/**
 * ����ɾ����Ʒ����
 */
public class BackProdDelServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.��ȡ��Ʒ��id
		String pid = request.getParameter("pid");
		
		//2.����service��ķ���������Ʒidɾ��ָ������Ʒ
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		boolean result = service.delProd(pid);
		
		//3.������Ӧ
		response.getWriter().write(result+"");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
