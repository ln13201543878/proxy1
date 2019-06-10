package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class BaseDao<T> {
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;

	public void update(String sql, Object[] objs)  {
		try {
			conn = JdbcUtils.getCon();
			System.out.println(2222);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++) {
				ps.setObject(i + 1, objs[i]);
				System.out.println(333);
				System.out.println(objs[i].toString());
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(null, ps, conn);
		}
	}

	public List<T> select(String sql, Object[] objs, Class<T> c) {
		List<T> list = new ArrayList<T>();
		try {
			conn = JdbcUtils.getCon();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++) {
				ps.setObject(i + 1, objs[i]);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				T t = c.newInstance();
				Method[] methods = c.getMethods();
				for (int i = 0; i < methods.length; i++) {
					Method m = methods[i];
					String mName = m.getName();
					if (mName.startsWith("set")) {
						String filedName = mName.substring(3);

						Class[] cs = m.getParameterTypes();
						if (cs[0] == Integer.class) {
							m.invoke(t, rs.getInt(filedName));
						} else if (cs[0] == String.class) {
							// 调用自己 course.setCno(rs.getString("cno"));
							m.invoke(t, rs.getString(filedName));
						} else if (cs[0] == Date.class) {
							// 调用自己 course.setCno(rs.getString("cno"));
							m.invoke(t, rs.getDate(filedName));
						} else if (cs[0] == Double.class) {
							// 调用自己 course.setCno(rs.getString("cno"));
							m.invoke(t, rs.getDouble(filedName));
						} else if (cs[0] == Float.class) {
							// 调用自己 course.setCno(rs.getString("cno"));
							m.invoke(t, rs.getFloat(filedName));
						}
					}
				}
				list.add(t);
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.close(rs, ps, conn);
		}
		return list;

	}
}
