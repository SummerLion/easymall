package cn.tedu.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;


public class BeanHandler<T> implements ResultSetHandler<T>{
	private Class clazz;
	public BeanHandler(Class clazz){
		/* ����T���͵�Class����, ��Ϊ�˱����������������, 
		 * �Ӷ��ó�������������Щ���Ժͷ��� */
		//Account.class
		this.clazz = clazz;//����������ڲ�, ��������ķ���ʹ��
		
	}
	
	/* ����ѯ������еĵ�һ�м�¼��װ��һ��Bean(T)���󲢷��� */
	public T handle(ResultSet rs) throws Exception {
		/* rs --> sqlִ�к�ó����
		 * id	name	money
		 * 1	a		999.0 
		 * 
		 * ���������װBean���󲢷���
		 * Bean������????	User	Account ....??
		 */
		//Account	acc.setId(rs.getInt("id"));
		//T			t.set???(rs.getObject("???"))
		//Account.class �������Class����, ��֪�������ϵ�set����������
		if(rs.next()){
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
				//2.6.��������е����ݷ�װ��Bean����(t)��
				/*
				 * account��: id name money
				 * Account acc: setId/getId--> id	
				 * 				setName/getName --> name
				 * 				setMoney/getMoney --> money
				 * 				getClass --> class
				 */
				try {
					//Column 'class' not found.
					method.invoke(t, rs.getObject(name));
				} catch (Exception e) {
					//�������class����ȥ�����Ҷ�Ӧ����, �Ҳ����ͻ��׳��쳣
					//��ʱ����Ӧ������������, ����ִ�к����ѭ��
					continue;
				}
			}
			return t;
		}
		return null;
	}
	
}
