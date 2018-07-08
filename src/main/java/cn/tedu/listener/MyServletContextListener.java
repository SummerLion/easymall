package cn.tedu.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * ServletContext�������ڼ���
 * ServletContext���󴴽�ʱ����ִ��contextInitialized
 * ServletContext��������ʱ����ִ��contextDestroyed
 * 
 */
public class MyServletContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		//����ͨ���¼�����(sce)��ȡ��ǰ�¼�Դ
		ServletContext context = sce.getServletContext();
		//context.getContextPath() �൱�� request.getContextPath()
		context.setAttribute("app", context.getContextPath());
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

}
