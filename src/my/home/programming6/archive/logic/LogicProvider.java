package my.home.programming6.archive.logic;

public class LogicProvider {
private static final LogicProvider instance = new LogicProvider();
private LogicProvider(){}
public static LogicProvider getInstance() {
	return instance;
}

private final Logic access = new LogicImpl();
public Logic getLogic() {
	return access;
}

}
