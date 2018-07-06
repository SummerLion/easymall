package cn.tedu.listener;

import java.util.HashMap;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import cn.tedu.bean.Product;
/*
 * ��session���󴴽�֮��������cartmap(Map����)����session��
 */
public class MyHttpSessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent se) {
		se.getSession().setAttribute("cartmap", 
				new HashMap<Product, Integer>());
	}
	public void sessionDestroyed(HttpSessionEvent se) {
	}

}
