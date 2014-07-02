package app.service.impl;

import java.sql.SQLException;
import java.util.List;

import app.dao.PostItDao;
import app.dao.impl.PostItDaoImpl;
import app.model.PostIt;
import app.model.User;
import app.service.PostItService;
import app.util.Util;

public class PostItServiceImpl implements PostItService {
	
	private PostItDao postItDao = new PostItDaoImpl();
	
	@Override
	public void create(final PostIt postIt) {
		try {
			postIt.setDateCreated(Util.getCurrentTime());
			postItDao.create(postIt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(final PostIt postIt) {
		try {
			postItDao.deleteById(postIt.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(final PostIt postIt) {
		try {
			postItDao.update(postIt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public PostIt findById(final Integer id) {
		PostIt postIt = null;
		
		try {
			postIt = postItDao.find(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return postIt;
	}

	@Override
	public List<PostIt> findByUser(final User user) {
		List<PostIt> postIts = null;
		
		try {
			postIts = postItDao.findByUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return postIts;
	}

	@Override
	public List<PostIt> findAll() {
		List<PostIt> postIts = null;
		
		try {
			postIts = postItDao.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return postIts;
	}
	
}
