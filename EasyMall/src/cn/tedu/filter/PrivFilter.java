package cn.tedu.filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.tedu.bean.User;

public class PrivFilter implements Filter{
	//�����������ϣ��ֱ�user.txt��admin.txt
	private List<String> userList;
	private List<String> adminList;
	public void init(FilterConfig fc) throws ServletException {
		userList = new ArrayList<String>();
		adminList = new ArrayList<String>();
		//��ȡuser.txt�ļ��ľ���·��
		String path = fc.getServletContext().
				getRealPath("/WEB-INF/user.txt");
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(path));
			String line = null;
			while((line=reader.readLine())!=null){
				userList.add(line);
			}
			path = fc.getServletContext().
					getRealPath("/WEB-INF/admin.txt");
			reader = new BufferedReader(
					new FileReader(path));
			while((line=reader.readLine())!=null){
				adminList.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//1ǿ������ת��
		HttpServletRequest req = (HttpServletRequest)request;
		//2��ȡ��ǰ�����uri
		String uri = req.getRequestURI();
		//3�ж�uri�Ƿ���ҪȨ��
		if(userList.contains(uri)||adminList.contains(uri)){
			//4��ҪȨ�ޣ��жϵ�ǰ�û��Ƿ�����Ӧ��Ȩ��
			//4.1�ж��Ƿ��¼
			Object userObj = req.getSession().getAttribute("user");
			if(userObj==null){//δ��¼
				req.getRequestDispatcher("/login.jsp").
					forward(request, response);
			}else{//�ѵ�¼
				//4.2��ȡ�û���Ϣrole
				String role = ((User)userObj).getRole();
				//4.3�жϵ�ǰ�û�role,�Ƿ���з��ʵ�ǰuri��Ȩ��
				if("user".equals(role)&&userList.contains(uri)){
					chain.doFilter(request, response);
				}else if("admin".equals(role)&&adminList.contains(uri)){
					chain.doFilter(request, response);
				}else{//��ʾ������Ȩ���ʸ���Դ
					response.getWriter().write("����Ȩ���ʸ���Դ");
				}
			}
		}else{//4����ҪȨ�ޣ�ֱ�ӷ���
			chain.doFilter(req, response);
		}
	}
	public void destroy() {
		
	}

}
