package app.service;

import java.util.List;

import app.model.User;

public interface UserService {
	
	public void create(User user);
	public void delete(User user);
	public void update(User user);
	
	public User findById(Integer id);
	public List<User> findAll();
	
}
