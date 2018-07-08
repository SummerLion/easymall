package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.Order;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;
import cn.tedu.utils.PaymentUtil;
import cn.tedu.utils.PropUtils;

public class PayServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1���ն���id
		String oid = request.getParameter("orderid");
		//2��׼��������֧��ƽ̨��Ҫ�Ĳ���
		String p0_Cmd = "Buy";//ҵ������
		String p1_MerId =PropUtils.getProperty("p1_MerId ");
		String p2_Order = oid;//�̻��Ķ�����
		//����ʱʹ��
		String p3_Amt = "0.01";//�������
		//����ʽ��������ʱ��ʹ��
		/*OrderService os = BasicFactory.getFactory().getInstance(OrderService.class);
		Order order = os.findOrderByOid(oid);
		String p3_Amt = ""+order.getMoney();*/
		String p4_Cur = "CNY";//���ױ���
		String p5_Pid = "";//��Ʒ����
		String p6_Pcat = "";//��Ʒ����
		String p7_Pdesc ="";//��Ʒ����
		String p8_Url = PropUtils.getProperty("responseUrl");
		String p9_SAF = "";//�ͻ���ַ
		String pa_MP = "";//�̻�����չ��Ϣ
		//֧��ͨ������
		String pd_FrpId=request.getParameter("pd_FrpId");
		//Ӧ�����
		String pr_NeedResponse = "1";
		//���ù�������������ǩ��
		String hmac = PaymentUtil.buildHmac(p0_Cmd, 
				p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, 
				p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, 
				pd_FrpId, pr_NeedResponse, PropUtils.getProperty("keyValue"));
		//3�������ϲ�������request
		request.setAttribute("pd_FrpId", pd_FrpId);
		request.setAttribute("p0_Cmd", p0_Cmd);
		request.setAttribute("p1_MerId", p1_MerId);
		request.setAttribute("p2_Order", p2_Order);
		request.setAttribute("p3_Amt", p3_Amt);
		request.setAttribute("p4_Cur", p4_Cur);
		request.setAttribute("p5_Pid", p5_Pid);
		request.setAttribute("p6_Pcat", p6_Pcat);
		request.setAttribute("p7_Pdesc", p7_Pdesc);
		request.setAttribute("p8_Url", p8_Url);
		request.setAttribute("p9_SAF", p9_SAF);
		request.setAttribute("pa_MP", pa_MP);
		request.setAttribute("pr_NeedResponse", pr_NeedResponse);
		request.setAttribute("hmac", hmac);
		//4��ת����confirm.jsp
		request.getRequestDispatcher("/confirm.jsp").
			forward(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
