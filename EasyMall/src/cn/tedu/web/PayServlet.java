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
		//1接收订单id
		String oid = request.getParameter("orderid");
		//2、准备第三方支付平台需要的参数
		String p0_Cmd = "Buy";//业务类型
		String p1_MerId =PropUtils.getProperty("p1_MerId ");
		String p2_Order = oid;//商户的订单号
		//测试时使用
		String p3_Amt = "0.01";//订单金额
		//“正式发布”的时候使用
		/*OrderService os = BasicFactory.getFactory().getInstance(OrderService.class);
		Order order = os.findOrderByOid(oid);
		String p3_Amt = ""+order.getMoney();*/
		String p4_Cur = "CNY";//交易币种
		String p5_Pid = "";//商品名称
		String p6_Pcat = "";//商品分类
		String p7_Pdesc ="";//商品描述
		String p8_Url = PropUtils.getProperty("responseUrl");
		String p9_SAF = "";//送货地址
		String pa_MP = "";//商户的扩展信息
		//支付通道编码
		String pd_FrpId=request.getParameter("pd_FrpId");
		//应答机制
		String pr_NeedResponse = "1";
		//调用工具类生产数据签名
		String hmac = PaymentUtil.buildHmac(p0_Cmd, 
				p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, 
				p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, 
				pd_FrpId, pr_NeedResponse, PropUtils.getProperty("keyValue"));
		//3、将以上产生保存request
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
		//4、转发到confirm.jsp
		request.getRequestDispatcher("/confirm.jsp").
			forward(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
