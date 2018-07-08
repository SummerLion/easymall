package cn.tedu.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import cn.tedu.bean.User;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.UserService;

public class AutoLoginFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		/* �Զ���½�ı�Ҫ����: 
		 * 1.�û�������δ��½״̬
		 * 2.�û����빴ѡ��30���Զ���½, ��Cookie�а����Զ���½Cookie
		 * 3.�Զ���½Cookie�е��û������������Ҫ��ȷ
		 * 4.�����Ƿ��Զ���½������Ҫ���й�����
		 */
		
		//1.�ж��û��Ƿ�Ϊδ��¼״̬
		HttpServletRequest req = (HttpServletRequest) request;
		if(req.getSession().getAttribute("user") == null){
			//�����û�Ϊδ��¼״̬
			//2.�ж�Cookie���Ƿ�����Զ���½Cookie
			Cookie[] cs = req.getCookies();
			if( cs != null){
				for (Cookie c : cs) {
					if("autologin".equals(c.getName())){
						//3.�ж��Զ���½Cookie�е��û����������Ƿ���ȷ
						String value = c.getValue();//"admin:123"
						String username = value.split(":")[0];
						//���û�������url����
						username = URLDecoder.decode(username,"utf-8");
						String password = value.split(":")[1];
						UserService service = BasicFactory.getFactory().getInstance(UserService.class);
						User user = service.loginUser(username, password);
						if(user != null){//�û�����������ȷ
							//���û�����½����
							req.getSession().setAttribute("user", user);
						}
					}
					
				}
			}
		}
		//4.�����Ƿ��Զ���½, ����Ҫ���й�����ִ�к���Ĳ���!
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

}
