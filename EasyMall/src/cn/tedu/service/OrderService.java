package cn.tedu.service;

import java.util.List;

import cn.tedu.anno.Tran;
import cn.tedu.bean.Order;
import cn.tedu.bean.OrderInfo;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.SaleInfo;
import cn.tedu.exception.MsgException;

public interface OrderService extends Service{
	
	/**
	 * 增加订单
	 * @param order 订单信息
	 * @param list	订单项信息
	 * @throws MsgException 
	 */
	@Tran
	void addOrder(Order order, List<OrderItem> list) throws MsgException;
	
	/**
	 * 根据用户id查询所有的订单信息
	 * @param id
	 * @return
	 */
	List<OrderInfo> findOrderByUserId(int id);
	/**根据订单id删除订单的相关信息（orders，orderitem，
	 * 还原商品库存products）
	 * @param id:商品id
	 * @throws MsgException:删除订单不存在或订单已支付
	 */
	@Tran
	void deleteOrderByOid(String id) throws MsgException;
	/**根据订单的id查询订单的详细信息
	 * @param oid：订单id
	 * @return 订单信息对应Order类的对象
	 */
	Order findOrderByOid(String oid);
	/**修改订单的支付状态
	 * @param oid：订单id
	 * @param paystate：修改后的支付状态
	 */
	void updatePaystateByOid(String oid, int paystate);
	/**查询全部的销售榜单列表
	 * @return 销售榜单列表对应集合
	 */
	List<SaleInfo> findSaleInfos();

}
