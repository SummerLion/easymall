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
	 * ���Ӷ���
	 * @param order ������Ϣ
	 * @param list	��������Ϣ
	 * @throws MsgException 
	 */
	@Tran
	void addOrder(Order order, List<OrderItem> list) throws MsgException;
	
	/**
	 * �����û�id��ѯ���еĶ�����Ϣ
	 * @param id
	 * @return
	 */
	List<OrderInfo> findOrderByUserId(int id);
	/**���ݶ���idɾ�������������Ϣ��orders��orderitem��
	 * ��ԭ��Ʒ���products��
	 * @param id:��Ʒid
	 * @throws MsgException:ɾ�����������ڻ򶩵���֧��
	 */
	@Tran
	void deleteOrderByOid(String id) throws MsgException;
	/**���ݶ�����id��ѯ��������ϸ��Ϣ
	 * @param oid������id
	 * @return ������Ϣ��ӦOrder��Ķ���
	 */
	Order findOrderByOid(String oid);
	/**�޸Ķ�����֧��״̬
	 * @param oid������id
	 * @param paystate���޸ĺ��֧��״̬
	 */
	void updatePaystateByOid(String oid, int paystate);
	/**��ѯȫ�������۰��б�
	 * @return ���۰��б��Ӧ����
	 */
	List<SaleInfo> findSaleInfos();

}
