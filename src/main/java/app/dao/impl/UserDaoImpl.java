package app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.dao.ConnectionDao;
import app.dao.UserDao;
import app.model.User;

public class UserDaoImpl implements UserDao {

	@Override
	public void create(final User user) throws SQLException {
		
		final String sql = "insert into User (username, password) values (?,?)";
		
		final Connection connection = ConnectionDao.getConnection();
		
		final PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, user.getUsername());
		preparedStatement.setString(2, user.getPassword());
		preparedStatement.executeUpdate();
		
		final ResultSet keys = preparedStatement.getGeneratedKeys();
		keys.next();
		
		user.setId(keys.getInt(1));
		
		if (preparedStatement != null)
			preparedStatement.close();
		if (connection != null)
			connection.close();
		
	}

	@Override
	public void deleteById(final Integer id) throws SQLException {
		
		final String sql = "delete from User where id = ?";

		final Connection connection = ConnectionDao.getConnection();

		final PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();

		if (preparedStatement != null)
			preparedStatement.close();
		if (connection != null)
			connection.close();
		
	}

	@Override
	public void update(final User user) throws SQLException {
		
		final Connection connection = ConnectionDao.getConnection();
		final StringBuilder sql = new StringBuilder("update User set ");
		boolean param = false;

		if (user.getUsername() != null && !user.getUsername().equals("")) {
			sql.append("username = '" + user.getUsername() + "'");
			param = true;
		}
		if (user.getPassword() != null && !user.getPassword().equals("")) {
			if (param) sql.append(", ");
			sql.append("password = '" + user.getPassword() + "'");
		}
		sql.append(" where id = " + user.getId());

		final Statement statement = connection.createStatement();
		statement.executeUpdate(sql.toString());

		if (statement != null) 
			statement.close();
		if (connection != null) 
			connection.close();
		
	}

	@Override
	public User find(final Integer id) throws SQLException {
		
		final String sql = "select * from User where id = ?";
		
		final Connection connection = ConnectionDao.getConnection();
		
		final PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		final ResultSet rs = preparedStatement.executeQuery();
		
		User user = null;
		
		if (rs.next()) {
			user = new User();
			user.setId(id);
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
		}
		
		if (preparedStatement != null)
			preparedStatement.close();
		if (connection != null)
			connection.close();
		
		return user;
		
	}

	@Override
	public List<User> findAll() throws SQLException {
		
		final String sql = "select * from User";
		
		final Connection connection = ConnectionDao.getConnection();
		
		final PreparedStatement preparedStatement = connection.prepareStatement(sql);
		final ResultSet rs = preparedStatement.executeQuery();
		
		final List<User> users = new ArrayList<User>();
		
		while (rs.next()) {
			final User user = new User();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			
			users.add(user);
		}
		
		if (preparedStatement != null)
			preparedStatement.close();
		if (connection != null)
			connection.close();
		
		return users;
		
	}

}
