package my.home.programming6.archive.bean;

public enum UserRole{
	USER("user"), ADMIN("admin");
	
	private String role;
	
	UserRole(String role) {
		this.role = role;
	}
	
	public String getUserRole() {
		return this.role;
	}
}