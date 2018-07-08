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
	//开启事务
	public static void startTran(){
		try {
			tl.get().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//提交事务
	public static void commitTran(){
		try {
			tl.get().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//回滚事务
	public static void rollbackTran(){
		try {
			tl.get().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//释放事务：关闭数据库连接
	public static void releseTran(){
		try {
			//关闭数据库连接
			tl.get().close();
			//从当前线程的map删除对应的数据库连接对象
			//删除的目的是：防止下一个用户使用该线程时，将会
			//遇到执行一个关闭的数据库连接。
			tl.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
