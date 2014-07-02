package app.service;

import java.util.List;

import app.model.PostIt;
import app.model.User;

public interface PostItService {
	
	public void create(PostIt postIt);
	public void delete(PostIt postIt);
	public void update(PostIt postIt);
	
	public PostIt findById(Integer id);
	public List<PostIt> findByUser(User user);
	public List<PostIt> findAll();
	
}
