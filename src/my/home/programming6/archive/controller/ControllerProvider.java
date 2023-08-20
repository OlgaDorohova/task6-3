package my.home.programming6.archive.controller;

public class ControllerProvider {
	private static final ControllerProvider instance = new ControllerProvider();

	private ControllerProvider() {
	}

	public static ControllerProvider getInstance() {
		return instance;
	}

	private final Controller accessController = new ControllerImpl();

	public Controller getController() {
		return accessController;
	}
}
