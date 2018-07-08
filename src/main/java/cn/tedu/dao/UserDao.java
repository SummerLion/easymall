package cn.tedu.dao;
import cn.tedu.bean.User;
public interface UserDao extends Dao{
	
	/**
	 * �����û�����ѯ�û��Ƿ����
	 * @param username �û���
	 * @return true ��ʾ�û����Ѵ��� false��ʾ�û���������
	 */
	public boolean findUserByUsername(String username);
	/**
	 * ���û�ע����Ϣ��������ݿ���
	 * @param user User����
	 */
	public void addUser(User user);
	
	/**
	 * �����û����������ѯ�û�
	 * @param username �û���
	 * @param password ����
	 * @return User����
	 */
	public User findUserByUsernameAndPassword(String username, String password);
}
