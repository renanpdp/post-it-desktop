package app.service.impl;

import java.sql.SQLException;
import java.util.List;

import app.dao.UserDao;
import app.dao.impl.UserDaoImpl;
import app.model.PostIt;
import app.model.User;
import app.security.Encrypter;
import app.service.PostItService;
import app.service.UserService;

public class UserServiceImpl implements UserService {
	
	private UserDao userDao = new UserDaoImpl();
	private PostItService postItService = new PostItServiceImpl();

	@Override
	public void create(final User user) {
		try {
			final String encryptedPassword = Encrypter.md5Hash(user.getPassword());
			user.setPassword(encryptedPassword);
			
			userDao.create(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(final User user) {
		try {
			final List<PostIt> postIts = postItService.findByUser(user);
			if (postIts.isEmpty()) {
				userDao.deleteById(user.getId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(final User user) {
		try {
			if (user.getPassword() != null) {
				final String encryptedPassword = Encrypter.md5Hash(user.getPassword());
				user.setPassword(encryptedPassword);
			}
			
			userDao.update(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User findById(final Integer id) {
		User user = null;
		
		try {
			user = userDao.find(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}

	@Override
	public List<User> findAll() {
		List<User> users = null;
		
		try {
			users = userDao.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}

}
