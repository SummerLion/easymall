package cn.tedu.service;

import java.util.List;

import cn.tedu.bean.Product;

public interface ProdService extends Service{
	/**
	 * ��ѯ������Ʒ, ����������Ʒ��ɵ�list����
	 * @return List<Product>
	 */
	List<Product> findAll();
	
	/**
	 * �޸�ָ����Ʒ�Ŀ������
	 * @param pid	��Ʒ��id
	 * @param pnum	��Ʒ�Ŀ������
	 * @return		boolean true��ʾ�޸ĳɹ�!
	 */
	boolean updatePnum(String pid, int pnum);
	
	/**
	 * ������Ʒ��idɾ��ָ������Ʒ
	 * @param pid	��Ʒ��id
	 * @return	boolean true��ʾɾ���ɹ�!
	 */
	boolean delProd(String pid);
	
	/**
	 * ����������ѯ���з�����������Ʒ����
	 * @param _name	��Ʒ������
	 * @param _category	��Ʒ�ķ���
	 * @param _minprice	��Ʒ����ͼ۸�
	 * @param _maxprice ��Ʒ����߼۸�
	 * @return List<Product>
	 */
	List<Product> findAllByCondition(String _name, String _category,
			double _minprice, double _maxprice);
	
	/**
	 * ����������ѯ�����з�����������Ʒ
	 * @param search ��ѯ����(��Ʒ����/��Ʒ����/��Ʒ����)
	 * @return List<Product>
	 */
	List<Product> findAllBySearch(String search);
	
	/**
	 * ������Ʒ��id��ѯָ������Ʒ
	 * @param pid	��Ʒid
	 * @return	Product
	 */
	Product findProdById(String pid);
}
