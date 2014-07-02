package app.model;

import java.util.Date;

public class PostIt {
	
	private Integer id;
	private Color color;
	private Date dateCreated;
	private String text;
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public void setColor(final Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(final Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}
	
}
