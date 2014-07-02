package app.dao;

import java.sql.SQLException;
import java.util.List;

import app.model.PostIt;
import app.model.User;

public interface PostItDao {
	
	public void create(PostIt postIt) throws SQLException;
	public void deleteById(Integer id) throws SQLException;
	public void update(PostIt postIt) throws SQLException;
	
	public PostIt find(Integer id) throws SQLException;
	public List<PostIt> findByUser(User user) throws SQLException;
	public List<PostIt> findAll() throws SQLException;
	
}
