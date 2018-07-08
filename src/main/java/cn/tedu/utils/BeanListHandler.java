package cn.tedu.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class BeanListHandler<T> implements ResultSetHandler<List<T>>{
	private Class clazz;
	public BeanListHandler(Class clazz){
		/* ����T���͵�Class����, ��Ϊ�˱����������������, 
		 * �Ӷ��ó�������������Щ���Ժͷ��� */
		//Account.class
		this.clazz = clazz;//����������ڲ�, ��������ķ���ʹ��
		
	}
	
	/* ����ѯ������е�ÿһ�м�¼��װ��һ��Bean(T)����, �����Bean�������һ��List���ϲ����� */
	public List<T> handle(ResultSet rs) throws Exception {
		List<T> list = new ArrayList<T>();
		while(rs.next()){
			//1.ͨ�������͵�Class����, ���������͵�ʵ��
			//Account acc = new Account();
			T t = (T) clazz.newInstance();
			
			//2.����T�ó��������а�����Щ�����ķ���������
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			
			//2.1.����beanInfo��ȡT�����а�������������������
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			
			//2.2.������������������, ��ȡÿһ������������, 
				//�ٻ�ȡ�������������а��������Լ���ص�set��get����
			for (PropertyDescriptor pd : pds) {
				//pd: id setId() getId() --> PropertyDescriptor
				//2.3.��ȡ��ǰ�������е�������
				String name = pd.getName();//������: id	name money
				
				//2.4.��ȡ��ǰ��������������ص�set����
				Method method = pd.getWriteMethod();//setId() setName() setMoney()
				
				//2.5.��ȡ������е�һ�м�¼�е�ÿһ�е�ֵ
				//����name(������)��ȡrs��ָ���е�ֵ
				//acc.setId(rs.getObject("id"))
				try {
					//2.6.��������е����ݷ�װ��Bean����(t)��
					Object value =null;
					//����жϵ�ǰ���Ե�����������
					//if(pd.getPropertyType()==int.class){
					if(pd.getPropertyType()==Integer.TYPE){
						value = rs.getInt(name);
					}else{
						value = rs.getObject(name);
					}
					method.invoke(t,value);
				} catch (Exception e) {
					continue;
				}
			}
			list.add(t);
		}
		return list.size() == 0 ? null : list;
	}
	
}
