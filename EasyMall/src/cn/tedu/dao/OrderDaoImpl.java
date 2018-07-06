package cn.tedu.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.tedu.bean.Order;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.SaleInfo;
import cn.tedu.utils.BeanHandler;
import cn.tedu.utils.BeanListHandler;
import cn.tedu.utils.JDBCUtils;

public class OrderDaoImpl implements OrderDao {

	public void addOrder(Order order) {
		String sql = "insert into orders values(?,?,?,?,null,?)";
		try {
			JDBCUtils.update(sql, order.getId(), order.getMoney(),
					order.getReceiverinfo(), order.getPaystate(),
					order.getUser_id());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void addOrderItem(OrderItem orderItem) {
		String sql = "insert into orderitem values(?,?,?)";
		try {
			JDBCUtils.update(sql, orderItem.getOrder_id(),
					orderItem.getProduct_id(),
					orderItem.getBuynum());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	public List<Order> findOrderByUserId(int userId) {
		String sql = "select * from orders where user_id=?";
		try {
			return JDBCUtils.query(sql, new BeanListHandler<Order>(Order.class), userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<OrderItem> findOrderItemByOrderId(String orderId) {
		try {
		String sql = "select * from orderitem where order_id=?";
		return JDBCUtils.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), orderId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Order findOrderByOid(String id) {
		//1、编写sql语句
		String sql = "select * from orders where id=?";
		try {
			//2、调用query进行查询
			return JDBCUtils.query(sql, new BeanHandler<Order>
				(Order.class), id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void deleteOrderItemsByOid(String id) {
		//1、编写sql语句
		String sql = "delete from orderitem where order_id=?";
		//2、调用update方法执行删除
		try {
			JDBCUtils.update(sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void deleteOrderByOid(String id) {
		//1、编写sql
		String sql = "delete from orders where id=?";
		//2、调用update方法执行删除
		try {
			JDBCUtils.update(sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updatePaystateByOid(String oid, int paystate) {
		String sql = "update orders set paystate=? where id=?";
		try {
			JDBCUtils.update(sql, paystate,oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<SaleInfo> findSaleInfos() {
		String sql = "SELECT pd.id prod_id,pd.name prod_name,"+
					"SUM(oi.buynum) sale_num " +
				" FROM products pd,orderitem oi,orders od"+
				" WHERE pd.id = oi.product_id"+
				" AND oi.order_id = od.id"+
				" AND od.paystate = 1"+
				" GROUP BY prod_id"+
				" ORDER BY sale_num DESC";
				//+"LIMIT 0,5";
		try {
			return JDBCUtils.query(sql, new BeanListHandler
					<SaleInfo>(SaleInfo.class));
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<SaleInfo>();
		}
	}
}
