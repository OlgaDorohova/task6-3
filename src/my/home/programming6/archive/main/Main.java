package my.home.programming6.archive.main;

//import my.home.programming6.archive.server.Client;
import my.home.programming6.archive.server.Server;

public class Main {
	public static void main(String[] args) throws InterruptedException {

		ServerThread thread1 = new ServerThread("server");
		thread1.start();
		Thread.sleep(2000);

//		use mock
		
		Mock mock = new Mock();
		mock.present();

//		use console menu	
		
//		Menu menu = new Menu();
//		menu.start();

	}
}

class ServerThread extends Thread {
	ServerThread(String name) {
		super(name);
	}

	public void run() {
		Server.run();
	}
}
