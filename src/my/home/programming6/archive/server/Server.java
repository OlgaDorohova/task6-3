package my.home.programming6.archive.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import my.home.programming6.archive.controller.Controller;
import my.home.programming6.archive.controller.ControllerProvider;

public class Server {
	public static void run() {
//	public static void main(String[] args) {
		System.out.println("Server starts");
		Controller controller = ControllerProvider.getInstance().getController();

		try (ServerSocket serverSocket = new ServerSocket(2048);
				Socket socket = serverSocket.accept();
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
			String request = "";
			String answer = "";
			 while (!request.equalsIgnoreCase("q")) {
				if (reader.ready()) {
					request = reader.readLine();
					if(request.equals("q")) {
						break;
					}
					answer = controller.action(request);
					
				}
					if (!socket.isClosed() && !answer.isBlank()) {
//						System.out.println(answer);
						writer.write(answer + "\n");
						writer.flush();
						Thread.sleep(300);
						answer = "";

					
				
			}}

			System.out.println("server closes");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
