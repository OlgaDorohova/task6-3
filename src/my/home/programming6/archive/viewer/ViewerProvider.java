package my.home.programming6.archive.viewer;

public class ViewerProvider {
private static final ViewerProvider instance = new ViewerProvider();
private ViewerProvider() {}

public static ViewerProvider getInstance() {
	return instance;
}

private final Viewer viewer = new ViewerImpl();

public Viewer getViewer() {
	return viewer;
}
}
