package cn.tedu.web.back;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.SaleInfo;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;

public class DownLoadServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1������ҵ������
		OrderService os = BasicFactory.getFactory().
				getInstance(OrderService.class);
		//2������ҵ��㷽����ѯȫ�������۰񵥼��϶���
		List<SaleInfo> list  = os.findSaleInfos();
		//3�����屣�����۰����ݵĶ���
		StringBuffer buf = new StringBuffer("��Ʒid,��Ʒ����,��������\n");
		//4������list���ϣ�ƴ������
		for(SaleInfo info:list){
			buf.append(info.getProd_id()).append(",")
			.append(info.getProd_name()).append(",")
			.append(info.getSale_num()).append("\n");
		}
		//5׼���ļ�����
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyyMMddHHmmssSSS");
		String fname = "SaleList"+sdf.format(date)+".csv";
		//6����֪������Ը������صķ�ʽ��
		response.setHeader("Content-Disposition", 
				"attachment;filename="+fname);
		//�����ļ����ݵ�����
		response.setContentType("text/html;charset=gbk");
		//7��ʹ��response��������ļ����������
		response.getWriter().write(buf.toString());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
