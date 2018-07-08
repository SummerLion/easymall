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
		//1、接收参数
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur =request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		//商品订单号
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		//交易结果的返回类型:1表示重定向  2点对点通讯
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		//银行交易的流水号
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		//在第三方支付平台上完成支付的时间
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		//签名数据
		String hmac = request.getParameter("hmac");
		boolean result = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, 
				r7_Uid, r8_MP, r9_BType, PropUtils.getProperty("keyValue"));
		if(result){//数据未被修改
			if("1".equals(r9_BType)){//表示重定向过来的
				response.getWriter().write("支付操作已执行，支付结果需要等待进一步的通知");
				//测试使用，正式发布前要删除以下两行代码
				OrderService os = BasicFactory.getFactory().
						getInstance(OrderService.class);
				os.updatePaystateByOid(r6_Order,1);
			}else if("2".equals(r9_BType)){//点对点通知
				if("1".equals(r1_Code)){//点对点通知支付完成
					//修改订单的支付状态
					OrderService os = BasicFactory.getFactory().
							getInstance(OrderService.class);
					os.updatePaystateByOid(r6_Order,1);
					//响应给第三方支付平台 success
					response.getWriter().write("success");
				}
			}
			
		}else{
			System.out.println("数据被篡改了。。。");
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
