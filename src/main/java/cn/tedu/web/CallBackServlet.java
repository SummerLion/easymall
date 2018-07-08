package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;
import cn.tedu.utils.PaymentUtil;
import cn.tedu.utils.PropUtils;

public class CallBackServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1�����ղ���
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur =request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		//��Ʒ������
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		//���׽���ķ�������:1��ʾ�ض���  2��Ե�ͨѶ
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		//���н��׵���ˮ��
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		//�ڵ�����֧��ƽ̨�����֧����ʱ��
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		//ǩ������
		String hmac = request.getParameter("hmac");
		boolean result = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, 
				r7_Uid, r8_MP, r9_BType, PropUtils.getProperty("keyValue"));
		if(result){//����δ���޸�
			if("1".equals(r9_BType)){//��ʾ�ض��������
				response.getWriter().write("֧��������ִ�У�֧�������Ҫ�ȴ���һ����֪ͨ");
				//����ʹ�ã���ʽ����ǰҪɾ���������д���
				OrderService os = BasicFactory.getFactory().
						getInstance(OrderService.class);
				os.updatePaystateByOid(r6_Order,1);
			}else if("2".equals(r9_BType)){//��Ե�֪ͨ
				if("1".equals(r1_Code)){//��Ե�֪֧ͨ�����
					//�޸Ķ�����֧��״̬
					OrderService os = BasicFactory.getFactory().
							getInstance(OrderService.class);
					os.updatePaystateByOid(r6_Order,1);
					//��Ӧ��������֧��ƽ̨ success
					response.getWriter().write("success");
				}
			}
			
		}else{
			System.out.println("���ݱ��۸��ˡ�����");
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}