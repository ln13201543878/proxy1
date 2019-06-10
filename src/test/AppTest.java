package test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.Test;

import bean.Users;
import dao.IDao;
import dao.IDaoImpl;




class myhand implements InvocationHandler{
	

	private Object target;


	public myhand(Object target) {
		this.target=target;
	
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		System.out.println(Arrays.toString(args));
		for(int i=0;i<args.length;i++) {
			Users u=(Users) args[0];
			if(u.getUname().length()<=5) {
				method.setAccessible(true);
				System.out.println("save成功");
				method.invoke(target, args);
				
			}else {
				throw new RuntimeException("名字小于5位");
			}
		}
	
		
		return null;
	}
	
}

class AppTest {

	
	@Test
	public void test() {
		IDao i=new IDaoImpl();
		ClassLoader cl=AppTest.class.getClassLoader();
		Class[] inter=new Class[] {IDao.class};
		Users u=new Users();
		u.setUname("asda");
		u.setUmoney(100.1d);
		u.setBirthday(new Date());
		myhand mh=new myhand(i);
		IDao proxy=(IDao) Proxy.newProxyInstance(cl, inter,mh);
		proxy.save(u);
//		proxy.delete(u.getUid());
		
	}

}
