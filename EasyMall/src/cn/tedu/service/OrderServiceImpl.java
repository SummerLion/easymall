package cn.tedu.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tedu.bean.Order;
import cn.tedu.bean.OrderInfo;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.Product;
import cn.tedu.bean.SaleInfo;
import cn.tedu.dao.OrderDao;
import cn.tedu.dao.ProdDao;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.utils.JDBCUtils;
import cn.tedu.utils.TranManager;

public class OrderServiceImpl implements OrderService {
	private OrderDao order_dao = BasicFactory.getFactory().getInstance(OrderDao.class);
	private ProdDao prod_dao = BasicFactory.getFactory().getInstance(ProdDao.class);
	public void addOrder(Order order, List<OrderItem> list) throws MsgException {
		//����dao��ķ�����Ӷ�����Ϣ
		order_dao.addOrder(order);
		for(OrderItem orderItem : list){
			//��鹺������(orderItem.buyNum)�Ƿ�С�ڵ��ڿ������(Product.pnum)
			//��ȡ��������
			int buyNum = orderItem.getBuynum();
			//��ȡ�������
			//>>��ȡ��Ʒid
			String pid = orderItem.getProduct_id();
			//>>��ѯ��Ʒ��Ϣ
			Product prod = prod_dao.findProdById(pid);
			int pnum = prod.getPnum();
			if(buyNum>pnum){//��������������ڿ������
				throw new MsgException("�����������,id:"+pid+",name:"+prod.getName()+",pnum:"+prod.getPnum());
			}
			//����dao��ķ�����Ӷ�������Ϣ
			order_dao.addOrderItem(orderItem);
			//�����������ӿ�������п۳�
			prod_dao.updatePnum(pid, prod.getPnum()-buyNum);
		}
	}
	
	public List<OrderInfo> findOrderByUserId(int userId) {
		//1.ͨ��Userid��ѯ��ǰ�û������ж�����Ϣ
		List<Order> orderList = order_dao.findOrderByUserId(userId);
		if(orderList == null ){
			return null;
		}
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		for (Order order : orderList) {
			//2.����ÿһ������, ͨ������id��ѯ��ǰ�����а��������ж�������Ϣ
			List<OrderItem> orderItemList = order_dao
					.findOrderItemByOrderId(order.getId());
			//3.����ÿһ��������, ͨ���������ȡ��Ʒ��Ϣ����Ʒ�Ĺ�������
			Map<Product, Integer> map = new HashMap<Product, Integer>();
			if(orderItemList!=null){
				for(OrderItem orderItem : orderItemList){
					//3.1.��ȡ��Ʒid, ͨ����Ʒid��ѯ��Ʒ��Ϣ, ����Product����
					Product prod = prod_dao.findProdById(orderItem.getProduct_id());
					//3.2.��ȡ��������
					int buyNum = orderItem.getBuynum();
					//3.3.����Ʒ��Ϣ�͹�����������map��
					map.put(prod, buyNum);
				}
			}
			//4.��������Ϣ�����еĶ�������Ϣ����OrderInfo��
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder(order);
			orderInfo.setMap(map);
			
			//5.��һ�������Ķ�����Ϣ����List������
			orderInfoList.add(orderInfo);
		}
		return orderInfoList;
	}
	public void deleteOrderByOid(String id) throws MsgException {
		//1�����ݶ���id��ѯ������Ϣ
		Order order = order_dao.findOrderByOid(id);
		//2���ж϶����Ƿ����
		if(order==null){
			throw new MsgException("��ǰ���������ڣ�");
		}
		//3���жϵ�ǰ�����Ƿ�δ֧����ֻ��δ֧���Ķ�������ɾ��
		if(order.getPaystate()!=0){
			throw new MsgException("ֻ��δ֧���Ķ�������ɾ��!");
		}
		//4�����ݶ�����id����ѯ��ǰ���������еĶ�����Ŀ
		List<OrderItem> items = order_dao.findOrderItemByOrderId(id);
		//5������items
		if(items!=null){
			for (OrderItem item : items) {
				//6����ԭ��Ʒ�Ŀ��
				prod_dao.changePnum(item.getProduct_id(),
						item.getBuynum());
			}
		}
		//7��ɾ���ö��������еĶ�����Ŀ
		order_dao.deleteOrderItemsByOid(id);
		//8��ɾ���ö�����Ϣ
		order_dao.deleteOrderByOid(id);
	}

	public Order findOrderByOid(String oid) {
		return order_dao.findOrderByOid(oid);
	}

	public void updatePaystateByOid(String oid, int paystate) {
		order_dao.updatePaystateByOid(oid,paystate);
	}

	public List<SaleInfo> findSaleInfos() {
		return order_dao.findSaleInfos();
	}

}
