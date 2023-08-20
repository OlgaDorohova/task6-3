package my.home.programming6.archive.dao;

public class DeedAccessProvider {
	private static final DeedAccessProvider instance = new DeedAccessProvider();

	private DeedAccessProvider() {
	}
	
	private final DeedAccess access = new DeedAccess();
	
	public static DeedAccessProvider getInstance() {
		return instance;
	}
	
	public DeedAccess getDeedAccess() {
		return access;
	}
}
