package cn.tedu.utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;


import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	private static ComboPooledDataSource pool = new ComboPooledDataSource();
	private JDBCUtils(){}
	
	/**
	 * ��ȡһ�����ӳ�ʵ��
	 * @return DataSource
	 */
	public static DataSource getPool(){
		return pool;
	}
	/**
	 * �����ӳ��л�ȡһ������
	 * @return Connection
	 */
	public static Connection getConn(){
		try {
			return pool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/* query��ѯ���� */
	public static <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = TranManager.getConn();
			ps = conn.prepareStatement(sql);
			if(params != null){//����sql����
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i+1, params[i]);//ע��i+1
				}
			}
			rs = ps.executeQuery();
			return rsh.handle(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{//ǧǧ����Ҫ�ر�conn
			close(null, ps, rs);
		}
	}
	
	/* update��ɾ�ķ��� */
	public static int update(String sql, Object... params) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn =TranManager.getConn();
			ps = conn.prepareStatement(sql);
			if(params != null){
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i+1, params[i]);
				}
			}
			return ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{//ǧ��Ҫ�ر�conn
			close(null, ps, rs);
		}
	}
	
	
	/**
	 * �ͷ���Դ
	 * @param conn ���Ӷ���
	 * @param stat ����������
	 * @param rs ���������
	 */
	public static void close(Connection conn, Statement stat, ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				rs = null;
			}
		}
		if(stat != null){
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				stat = null;
			}
		}
		if(conn != null){
			try {
				/*
				 * ���������ͨ��c3p0���ӳػ�ȡ��, conn.close
				 * ֻ�ǽ����ӻ������ӳ���, �����ر�����!! 
				 */
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				conn = null;
			}
		}
	}
}
