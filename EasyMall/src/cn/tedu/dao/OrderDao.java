package cn.tedu.dao;

import java.sql.Connection;
import java.util.List;

import cn.tedu.bean.Order;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.SaleInfo;

public interface OrderDao extends Dao{
	
	/**
	 * 添加订单信息(orders表)
	 * @param order 订单信息
	 */
	void addOrder(Order order);
	
	/**
	 * 添加订单项信息(orderitem)
	 * @param orderItem
	 */
	void addOrderItem(OrderItem orderItem);
	
	/**
	 * 根据用户id查询当前用户的所有订单
	 * @param userId 用户id
	 * @return	List<Order>
	 */
	List<Order> findOrderByUserId(int userId);
	/**
	 * 根据订单id查询所有的订单项信息
	 * @param id
	 * @return
	 */
	List<OrderItem> findOrderItemByOrderId(String id);
	/**根据订单id查询订单的相关信息（orders）
	 * @param id:订单id
	 * @return 封装了对应订单的相关信息的Order类的对象
	 */
	Order findOrderByOid(String id);
	/**根据订单id删除该订单下的所有订单项目（orderitem）
	 * @param id:订单id
	 */
	void deleteOrderItemsByOid(String id);
	/**根据订单id删除对应的订单信息（orders)
	 * @param id:订单id
	 */
	void deleteOrderByOid(String id);
	/**修改订单的支付状态
	 * @param oid：订单id
	 * @param paystate修改后的支付状态
	 */
	void updatePaystateByOid(String oid, int paystate);
	/**查询全部的销售 榜单列表
	 * @return 全部的销售榜单列表对应的集合对象
	 */
	List<SaleInfo> findSaleInfos();
}
