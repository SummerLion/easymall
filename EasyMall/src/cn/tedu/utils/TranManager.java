package cn.tedu.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TranManager {
	//private static Connection conn = JDBCUtils.getConn();
	private static ThreadLocal<Connection> tl=
		new ThreadLocal<Connection>(){
			protected Connection initialValue() {
				return JDBCUtils.getConn();
			};
		};
	private TranManager(){}
	public static Connection getConn(){
		return tl.get();
	}
	//��������
	public static void startTran(){
		try {
			tl.get().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//�ύ����
	public static void commitTran(){
		try {
			tl.get().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//�ع�����
	public static void rollbackTran(){
		try {
			tl.get().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//�ͷ����񣺹ر����ݿ�����
	public static void releseTran(){
		try {
			//�ر����ݿ�����
			tl.get().close();
			//�ӵ�ǰ�̵߳�mapɾ����Ӧ�����ݿ����Ӷ���
			//ɾ����Ŀ���ǣ���ֹ��һ���û�ʹ�ø��߳�ʱ������
			//����ִ��һ���رյ����ݿ����ӡ�
			tl.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
