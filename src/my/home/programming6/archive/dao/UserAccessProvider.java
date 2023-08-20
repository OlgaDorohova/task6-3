package my.home.programming6.archive.dao;

public class UserAccessProvider {
private static final UserAccessProvider instance = new UserAccessProvider();
private UserAccessProvider() {}
public static UserAccessProvider getInstance() {
	return instance;
}
private final UserAccess access = new UserAccess();
public UserAccess getAccess() {
	return access;
}

}
