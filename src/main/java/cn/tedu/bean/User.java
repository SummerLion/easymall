package cn.tedu.bean;

import java.io.Serializable;

import cn.tedu.exception.MsgException;
import cn.tedu.utils.WebUtils;

public class User implements Serializable {
	private int id;
	private String username;
	private String password;
	private String password2;
	private String nickname;
	private String email;
	private String role;

	public User(){}
	
	public User(int id, String username, String password, String nickname,
			String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public void checkData() throws MsgException{
		// >>�ǿ�У��
		if (WebUtils.isNull(username)) {
			// ����ʾ��Ϣ����request����,ͨ��ת������Ϣ����regist.jsp������ʾ
			throw new MsgException("�û�������Ϊ!");
		}
		if (WebUtils.isNull(password)) {
			// ����ʾ��Ϣ����request����,ͨ��ת������Ϣ����regist.jsp������ʾ
			throw new MsgException("���벻��Ϊ��!");
		}
		if (WebUtils.isNull(password2)) {
			// ����ʾ��Ϣ����request����,ͨ��ת������Ϣ����regist.jsp������ʾ
			throw new MsgException("ȷ�����벻��Ϊ��!");
		}
		// >>���������Ƿ�һ��У��
		if (!password.equals(password2)) {
			throw new MsgException("�������벻һ��!");
		}
		
		if (WebUtils.isNull(nickname)) {
			// ����ʾ��Ϣ����request����,ͨ��ת������Ϣ����regist.jsp������ʾ
			throw new MsgException("�ǳƲ���Ϊ��!");
		}
		if (WebUtils.isNull(email)) {
			// ����ʾ��Ϣ����request����,ͨ��ת������Ϣ����regist.jsp������ʾ
			throw new MsgException("���䲻��Ϊ��!");
		}
		// >>�����ʽУ��
		if (!email.matches("^\\w+@\\w+(\\.\\w+)+$")) {
			throw new MsgException("�����ʽ����ȷ!");
		}
	}
	
}
