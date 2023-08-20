package my.home.programming6.archive.bean;

import java.util.Objects;

import my.home.programming6.archive.exception.DaoException;

public class User extends Entity{
	private static final long serialVersionUID = -2572878380934197877L;
	private String login;
	private String password;
	protected UserRole role = UserRole.USER;
	
	public User() {}
	
	public User(String login, String password) throws DaoException {
		this.login = login;
		this.password = password;
//		this.role = UserRole.USER;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}
	
	public void setRole(UserRole role) {
		this.role = role;
	}
	
	public void setRole(String role) {
		this.role = roleParser(role);
	}
	
	private UserRole roleParser(String userRole) {
		return (userRole.equalsIgnoreCase("admin")) ? UserRole.ADMIN : UserRole.USER;
				
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(login, password, role);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(login, other.login) && Objects.equals(password, other.password) && role == other.role;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", password=" + password + ", role=" + role + "]";
	}
	
	
}
