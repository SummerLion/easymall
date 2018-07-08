package cn.tedu.dao;

import java.sql.Connection;
import java.util.List;

import cn.tedu.bean.Order;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.SaleInfo;

public interface OrderDao extends Dao{
	
	/**
	 * ��Ӷ�����Ϣ(orders��)
	 * @param order ������Ϣ
	 */
	void addOrder(Order order);
	
	/**
	 * ��Ӷ�������Ϣ(orderitem)
	 * @param orderItem
	 */
	void addOrderItem(OrderItem orderItem);
	
	/**
	 * �����û�id��ѯ��ǰ�û������ж���
	 * @param userId �û�id
	 * @return	List<Order>
	 */
	List<Order> findOrderByUserId(int userId);
	/**
	 * ���ݶ���id��ѯ���еĶ�������Ϣ
	 * @param id
	 * @return
	 */
	List<OrderItem> findOrderItemByOrderId(String id);
	/**���ݶ���id��ѯ�����������Ϣ��orders��
	 * @param id:����id
	 * @return ��װ�˶�Ӧ�����������Ϣ��Order��Ķ���
	 */
	Order findOrderByOid(String id);
	/**���ݶ���idɾ���ö����µ����ж�����Ŀ��orderitem��
	 * @param id:����id
	 */
	void deleteOrderItemsByOid(String id);
	/**���ݶ���idɾ����Ӧ�Ķ�����Ϣ��orders)
	 * @param id:����id
	 */
	void deleteOrderByOid(String id);
	/**�޸Ķ�����֧��״̬
	 * @param oid������id
	 * @param paystate�޸ĺ��֧��״̬
	 */
	void updatePaystateByOid(String oid, int paystate);
	/**��ѯȫ�������� ���б�
	 * @return ȫ�������۰��б��Ӧ�ļ��϶���
	 */
	List<SaleInfo> findSaleInfos();
}
