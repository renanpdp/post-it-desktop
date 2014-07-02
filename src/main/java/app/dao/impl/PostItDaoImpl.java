package app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.dao.ConnectionDao;
import app.dao.PostItDao;
import app.model.Color;
import app.model.PostIt;
import app.model.User;
import app.util.Util;

public class PostItDaoImpl implements PostItDao {

	@Override
	public void create(final PostIt postIt) throws SQLException {
		
		final String sql = "insert into PostIt (userId, dateCreated, color, text) values (?,?,?,?)";
		
		final Connection connection = ConnectionDao.getConnection();
		
		final PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, postIt.getUser().getId());
		preparedStatement.setTimestamp(2, Util.getTimestamp(postIt.getDateCreated()));
		preparedStatement.setString(3, postIt.getColor().getString());
		preparedStatement.setString(4, postIt.getText());
		preparedStatement.executeUpdate();
		
		final ResultSet keys = preparedStatement.getGeneratedKeys();
		keys.next();
		
		postIt.setId(keys.getInt(1));
		
		if (preparedStatement != null)
			preparedStatement.close();
		if (connection != null)
			connection.close();
		
	}

	@Override
	public void deleteById(final Integer id) throws SQLException {
		
		final String sql = "delete from PostIt where id = ?";

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
	public void update(final PostIt postIt) throws SQLException {
		
		final String sql = "update PostIt set color = ?, text = ? where id = ?";
		
		final Connection connection = ConnectionDao.getConnection();
		
		final PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, postIt.getColor().getString());
		preparedStatement.setString(2, postIt.getText());
		preparedStatement.setInt(3, postIt.getId());
		preparedStatement.executeUpdate();
		
		if (preparedStatement != null)
			preparedStatement.close();
		if (connection != null)
			connection.close();
		
	}

	@Override
	public PostIt find(final Integer id) throws SQLException {
		
		final String sql = "select * from PostIt where id = ? order by dateCreated";
		
		final Connection connection = ConnectionDao.getConnection();
		
		final PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		final ResultSet rs = preparedStatement.executeQuery();
		
		PostIt postIt = null;
		
		if (rs.next()) {
			postIt = new PostIt();
			postIt.setId(id);
			postIt.setColor(Color.getColor(rs.getString("color")));
			postIt.setDateCreated(rs.getTimestamp("dateCreated"));
			postIt.setText(rs.getString("text"));
			
			final User user = new User();
			user.setId(rs.getInt("userId"));
			postIt.setUser(user);
		}
		
		if (preparedStatement != null)
			preparedStatement.close();
		if (connection != null)
			connection.close();
		
		return postIt;
	}

	@Override
	public List<PostIt> findByUser(final User user) throws SQLException {
		
		final String sql = "select * from PostIt where userId = ? order by dateCreated";
		
		final Connection connection = ConnectionDao.getConnection();
		
		final PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, user.getId());
		final ResultSet rs = preparedStatement.executeQuery();
		
		final List<PostIt> postIts = new ArrayList<PostIt>();
		
		while (rs.next()) {
			final PostIt postIt = new PostIt();
			postIt.setId(rs.getInt("id"));
			postIt.setColor(Color.getColor(rs.getString("color")));
			postIt.setDateCreated(rs.getTimestamp("dateCreated"));
			postIt.setText(rs.getString("text"));
			postIt.setUser(user);
			
			postIts.add(postIt);
		}
		
		if (preparedStatement != null)
			preparedStatement.close();
		if (connection != null)
			connection.close();
		
		return postIts;
		
	}

	@Override
	public List<PostIt> findAll() throws SQLException {
		
		final String sql = "select * from PostIt order by dateCreated";
		
		final Connection connection = ConnectionDao.getConnection();
		
		final PreparedStatement preparedStatement = connection.prepareStatement(sql);
		final ResultSet rs = preparedStatement.executeQuery();
		
		final List<PostIt> postIts = new ArrayList<PostIt>();
		
		while (rs.next()) {
			final PostIt postIt = new PostIt();
			postIt.setId(rs.getInt("id"));
			postIt.setColor(Color.getColor(rs.getString("color")));
			postIt.setDateCreated(rs.getTimestamp("dateCreated"));
			postIt.setText(rs.getString("text"));
			
			final User user = new User();
			user.setId(rs.getInt("userId"));
			postIt.setUser(user);
			
			postIts.add(postIt);
		}
		
		if (preparedStatement != null)
			preparedStatement.close();
		if (connection != null)
			connection.close();
		
		return postIts;
		
	}

}
