package my.home.programming6.archive.logic;

public class AutoProvider {
private static final AutoProvider instance = new AutoProvider();
private AutoProvider() {}
public final Autorization access = new AutorizationImpl();

public static AutoProvider getInstance() {
	return instance;
}

public Autorization getAutorization() {
	return access;
}
}
