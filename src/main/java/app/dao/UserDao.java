package app.dao;

import java.sql.SQLException;
import java.util.List;

import app.model.User;

public interface UserDao {
	
	public void create(User user) throws SQLException;
	public void deleteById(Integer id) throws SQLException;
	public void update(User user) throws SQLException;
	
	public User find(Integer id) throws SQLException;
	public List<User> findAll() throws SQLException;
	
}
