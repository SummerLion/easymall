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
	//定义两个集合，分别user.txt和admin.txt
	private List<String> userList;
	private List<String> adminList;
	public void init(FilterConfig fc) throws ServletException {
		userList = new ArrayList<String>();
		adminList = new ArrayList<String>();
		//获取user.txt文件的绝对路径
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
		//1强制类型转换
		HttpServletRequest req = (HttpServletRequest)request;
		//2获取当前请求的uri
		String uri = req.getRequestURI();
		//3判断uri是否需要权限
		if(userList.contains(uri)||adminList.contains(uri)){
			//4需要权限，判断当前用户是否具体对应的权限
			//4.1判断是否登录
			Object userObj = req.getSession().getAttribute("user");
			if(userObj==null){//未登录
				req.getRequestDispatcher("/login.jsp").
					forward(request, response);
			}else{//已登录
				//4.2获取用户信息role
				String role = ((User)userObj).getRole();
				//4.3判断当前用户role,是否具有访问当前uri的权限
				if("user".equals(role)&&userList.contains(uri)){
					chain.doFilter(request, response);
				}else if("admin".equals(role)&&adminList.contains(uri)){
					chain.doFilter(request, response);
				}else{//提示：您无权访问该资源
					response.getWriter().write("您无权访问该资源");
				}
			}
		}else{//4不需要权限，直接放行
			chain.doFilter(req, response);
		}
	}
	public void destroy() {
		
	}

}
