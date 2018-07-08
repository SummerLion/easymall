package cn.tedu.service;

import cn.tedu.bean.User;
import cn.tedu.exception.MsgException;

public interface UserService extends Service{
	/**
	 * ʵ��ע��
	 * @param user
	 * @throws MsgException 
	 */
	public void registUser(User user) throws MsgException;
	
	/**
	 * ʵ�ֵ�½
	 * @param username �û���
	 * @param password ����
	 * @return User����
	 */
	
	public User loginUser(String username, String password);
	/**
	 * �����û�����ѯ�û��Ƿ����
	 * @param username �û���
	 * @return true��ʾ�û����Ѵ���, false��ʾ������
	 */
	public boolean hasUser(String username);
}
