package dao;

import bean.Users;
import util.BaseDao;

public class IDaoImpl implements IDao {
	BaseDao<Users> bd=new BaseDao<Users>();
	@Override
	public void save(Users u) {
		String sql="insert into users values(null,?,?,?)";
		System.out.println(11111);
		Object[] objs= {u.getUname(),u.getBirthday(),u.getUmoney()};
		bd.update(sql, objs);
	}

	@Override
	public void delete(Integer uid) {
		String sql="delete from users where uid=?";
		Object[] objs= {uid};
		bd.update(sql, objs);

	}

}
