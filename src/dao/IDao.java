package dao;

import bean.Users;

public interface IDao {
void save(Users u);
void delete(Integer uid);
}
