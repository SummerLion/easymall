package cn.tedu.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Properties;

import cn.tedu.anno.Tran;
import cn.tedu.dao.Dao;
import cn.tedu.service.Service;
import cn.tedu.utils.TranManager;
/**
 * 通用的工厂类
 */
public class BasicFactory {
	private static BasicFactory factory = new BasicFactory();
	private static Properties prop = new Properties();
	public static BasicFactory getFactory() {
		return factory;
	}

	private BasicFactory() {
	}

	static {
		// 读取配置文件中配置的信息
		InputStream inputStream = BasicFactory
				.class
				.getClassLoader()
				.getResourceAsStream("conf.properties");
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*try {
			String confPath = BasicFactory.class.getClassLoader()
					.getResource("conf.properties").getPath();
			prop.load(new FileInputStream(confPath));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}*/

	}

	/*
	 * 根据conf.properties配置文件中的配置信息 创建UserDao的实例 UserDao=cn.tedu.dao.UserDaoImpl
	 */
public <T> T getInstance(Class<T> clazz) {
	try {
		// 1.读取配置文件中配置的信息(UserDao的实现类)
		// cn.tedu.dao.UserDaoImpl
		String className = prop.getProperty(clazz.getSimpleName());
		// 2.根据类的全限定名称获得该类的Class对象
		Class clz = Class.forName(className);
		// 3.利用反射技术根据该类Class对象创建该类的实例
		//T t = (T)clz.newInstance();
		//区分t是Service层对象还是Dao层对象
		if(Service.class.isAssignableFrom(clz)){
			final T t = (T)clz.newInstance();
			//service层
			//创建代理对象
			@SuppressWarnings("unchecked")
			T proxy = (T)Proxy.newProxyInstance(clz.getClassLoader(),
					clz.getInterfaces(),
					new InvocationHandler() {
						public Object invoke(Object proxy, Method method, Object[] args)
								throws Throwable {
							Object result = null;
							//判断method上是否使用事务的注解@Tran
							if(method.isAnnotationPresent(Tran.class)){
								//使用了事务
								try{//开启事务
									TranManager.startTran();
									//执行委托类对象（真实对象）的方法
									result = method.invoke(t, args);
									//提交事务
									TranManager.commitTran();
								}catch (InvocationTargetException e) {
									e.printStackTrace();
									//回滚事务
									TranManager.rollbackTran();
									throw e.getTargetException();
								}finally{//释放事务中使用数据库连接
									TranManager.releseTran();
								}
							}else{//未使用事务
								try{
									result = method.invoke(t, args);
								}catch (InvocationTargetException e) {
									e.printStackTrace();
									throw e.getTargetException();
								}finally{
									TranManager.releseTran();
								}
							}
							return result;
						}
					});
			/*Method[] mtds = proxy.getClass().getMethods();
			for (Method md:mtds) {
				System.out.println(md.toGenericString());
				System.out.println(md.toString());
				System.out.println("-------------------");
			}*/
			return proxy;
		}else if(Dao.class.isAssignableFrom(clz)){
			//dao层
			return (T)clz.newInstance(); 
		}else{//既不是service也不是dao层
			System.out.println("别捣乱....");
			return null;
		}
	} catch (Exception e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	}
}
}
