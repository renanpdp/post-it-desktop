package app.model;


public class User {
	
	private Integer id;
	private String username;
	private String password;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(final Integer id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(final String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(final String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return username;
	}
	
	@Override
	public boolean equals(final Object object) {
		if (object instanceof User) {
			final User user = (User) object;
			if (this.id == user.getId()) {
				return true;
			}
		}
		return false;
	}
	
}
