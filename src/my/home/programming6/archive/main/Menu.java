package my.home.programming6.archive.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import my.home.programming6.archive.viewer.Viewer;
import my.home.programming6.archive.viewer.ViewerProvider;

public class Menu {
	Viewer viewer = ViewerProvider.getInstance().getViewer();

	public void start() {
		System.out.println("Client run");

		try (Socket socket = new Socket(InetAddress.getLocalHost(), 2048);
				BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

			String stop = "q";
			String nextAction = "";

			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			String input = "";
			do {
				System.out.println("Choise action:\n" + "1 - sign up;\n" + "2 - sign in;\n" + "q - quite\n" + ">>");

				input = consoleReader.readLine();

				switch (input) {
				case "1" -> {
					String clientData = getClientUserData();
					String request = "reg;" + clientData;

					writer.write(request + "\n");
					writer.flush();
					Thread.sleep(1000);

					if (socketReader.ready()) {
						String result = socketReader.readLine();
						String resultParams[] = result.split(";");
						viewer.showAutorizMessage(resultParams[0]);
					}
					break;
				}
				case "2" -> {
					String clientData = getClientUserData();
					String request = "log;" + clientData;

					writer.write(request + "\n");
					writer.flush();
					Thread.sleep(2000);
					String result = socketReader.readLine();
					String resultParams[] = result.split(";");
					viewer.showAutorizMessage(resultParams[0]);
					nextAction = (resultParams.length > 1) ? resultParams[1] : null;
					break;
				}
				case "q" -> {
					writer.write(stop);
					writer.flush();
					return;
				}
				default -> {
					System.out.println("Unknown answer");
					break;
				}
				}

				if (nextAction != null) {

					String login = nextAction.split(",")[0].split("=")[1];
					String role = nextAction.split(",")[1].split("=")[1];

					switch (role) {

//user's actions
					case "user": {
						do {
							System.out.println("1 - find deed by;\n2 - change password;\n3 - log out;\nq - quite;\n>>");
							input = scanner.nextLine();
							switch (input) {
							case "1": {
								String request = findDeed();
								writer.write(request + "\n");
								writer.flush();
								Thread.sleep(2000);
								String result = socketReader.readLine();
								viewer.showDeeds(result);
								break;
							}
							case "2": {
								// type=user,login=!=login,password=!=password!~!parameter=!=value!~!oldValue=!=value!~!newValue=!=value
								String request = "change;type=user,login=!=" + login+",";
								request += getPasswordChangeData();
								writer.write(request + "\n");
								writer.flush();
								Thread.sleep(2000);
								String result = socketReader.readLine();
								viewer.showCommonRes(result);
								break;
							}
							case "3": {
								System.out.println("You log out");
								input = "returnToUpperLevel";
								break;
							}

							case "q": {
								writer.write(stop);
								writer.flush();
								return;
							}
							}

						} while (!input.equals("returnToUpperLevel"));
						break;
					}

//admin's actions					

					case "admin": {
						do {
							System.out.println(
									"1 - change password;\n2 - find user by login;\n3 - find deed by\n4 - change deed\n5 - add user\n6 - add deed\n7 - delete user\n8 - delete deed\n9 - get all users\n10 - get all deeds\n11 - log out;\nq - quite;\n>>");
							input = scanner.nextLine();
							switch (input) {

							case "1": {
								// type=user,login=!=login,password=!=password|~|parameter=!=value|~|oldValue=!=value|~|newValue=!=value
								String request = "change;type=user,login=!=" + login + ",";
								request += getPasswordChangeData();
								writer.write(request + "\n");
								writer.flush();
								Thread.sleep(2000);
								String result = socketReader.readLine();
								viewer.showCommonRes(result);
								break;
							}
							case "2": {
								System.out.println("Enter login\n>>");
								String inputLogin = scanner.nextLine();
								String request = "find;type=user,users/user[login='" + inputLogin + "']";
								writer.write(request + "\n");
								writer.flush();
								Thread.sleep(2000);
								String result = "";
								result += socketReader.readLine();
								viewer.showUsers(result);
								break;
							}

							case "3": {
								String request = findDeed();
								writer.write(request + "\n");
								writer.flush();
								Thread.sleep(2000);
								String result = socketReader.readLine();
								viewer.showDeeds(result);
								break;
							}

							case "4": {
								String request = "change;type=deed," + getDeedChangeData();
								writer.write(request + "\n");
								writer.flush();
								Thread.sleep(2000);
								String result = socketReader.readLine();
								viewer.showCommonRes(result);
								break;
							}
							case "5": {
								String request = "add;type=user," + getClientUserData();
								writer.write(request + "\n");
								writer.flush();
								Thread.sleep(2000);
								String result = socketReader.readLine();
								viewer.showCommonRes(result);
								break;
							}
							case "6": {
								String request = "add;type=deed," + getClientDeedData();
								writer.write(request + "\n");
								writer.flush();
								Thread.sleep(2000);
								String result = socketReader.readLine();
								viewer.showCommonRes(result);
								break;
							}

							case "7": {
								System.out.println("Enter login");
								String request = "delete;type=user,login=!=" + scanner.nextLine();
								writer.write(request + "\n");
								writer.flush();
								Thread.sleep(2000);
								String result = socketReader.readLine();
								viewer.showCommonRes(result);
								break;
							}
							case "8": {
								String request = "delete;type=deed," + getClientDeedData();
								writer.write(request + "\n");
								writer.flush();
								Thread.sleep(2000);
								String result = socketReader.readLine();
								viewer.showCommonRes(result);
								break;
							}

							case "9": {
								String request = "getAll;type=user";
								writer.write(request + "\n");
								writer.flush();
								Thread.sleep(2000);
								String result = socketReader.readLine();
								viewer.showUsers(result);
								break;
							}
							case "10": {
								String request = "getAll;type=deed";
								writer.write(request + "\n");
								writer.flush();
								Thread.sleep(2000);
								String result = socketReader.readLine();
								viewer.showDeeds(result);
								break;
							}
							case "11": {
								System.out.println("You log out");
								input = "returnToUpperLevel";
								break;
							}

							case "q": {
								writer.write(stop);
								writer.flush();
								return;
							}
							}

						} while (!input.equals("returnToUpperLevel"));
						break;
					}

					}

				}
			} while (!input.equals(stop));

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private String encrypt(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte hash[] = digest.digest(password.getBytes());

			String encode = Base64.getEncoder().encodeToString(hash);
			return encode;
		} catch (NoSuchAlgorithmException e) {
			return "error";
		}

	}

	private String getClientUserData() {
		StringBuffer data = new StringBuffer();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter login:\n>>");
		data.append("login=!=" + scanner.nextLine() + ",");
		System.out.println("Enter password:\n>>");
		String temp = scanner.nextLine();
		String password = encrypt(temp);
		data.append("password=!=" + password);

		return data.toString();

	}

	private String getClientDeedData() {
		StringBuffer data = new StringBuffer();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter name:\n>>");
		data.append("name=" + scanner.nextLine() + ",");
		System.out.println("Enter major:\n>>");
		data.append("major=" + scanner.nextLine() + ",");
		System.out.println("Enter phone number:\n>>");
		data.append("phone=" + scanner.nextLine() + ",");

		System.out.println("Create adrress");
		System.out.println("Enter country:\n>>");
		data.append("country=" + scanner.nextLine() + ",");
		System.out.println("Enter city:\n>>");
		data.append("city=" + scanner.nextLine() + ",");
		System.out.println("Enter street:\n>>");
		data.append("street=" + scanner.nextLine());
		System.out.println(data);
		return data.toString();
	}

	private String getFindData(String message) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println(message + "\n>>");
		return scanner.nextLine();
	}

	private String getPasswordChangeData() {
		// type=user,login=!=login,password=!=password!~!parameter=!=value!~!oldValue=!=value!~!newValue=!=value
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		StringBuffer data = new StringBuffer("password=!=");
		System.out.println("Enter your password\n>>");
		String pass = encrypt(scanner.nextLine());
		data.append(pass);
		data.append("!~!parameter=!=password!~!oldValue=!=" + pass + "!~!newValue=!=");
		System.out.println("Enter new password");
		String newPass = scanner.nextLine();
		data.append(encrypt(newPass));
		System.out.println(data);
		return data.toString();
	}

	private String getDeedChangeData() {
//		 type=deed,name=name,major=major,phone=phone,country=country,city=city,street=street
//		 !~!parameter=value!~!oldValue=value!~!newValue=value

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		// create deed for change
		StringBuffer data = new StringBuffer("name=");
		System.out.println("Enter name\n>>");
		data.append(scanner.nextLine());
		data.append(",major=");
		System.out.println("Enter major\n>>");
		data.append(scanner.nextLine());
		data.append(",phone=");
		System.out.println("Enter phone number\n>>");
		data.append(scanner.nextLine());
		data.append(",contry=");
		System.out.println("Enter country\n>>");
		data.append(scanner.nextLine());
		data.append(",city=");
		System.out.println("Enter city\n>>");
		data.append(scanner.nextLine());
		data.append(",street=");
		System.out.println("Enter country\n>>");
		data.append(scanner.nextLine());

		// add change parameters
		data.append("!~!parameter=");
		System.out.println(
				"Choose the parameter name\n + 1 - name\n2 - major\n3 - phone number\n4 - country\n5 - city\n6 -street\n>>");
		int dataLength = data.length();
		while (data.length() == dataLength) {
			String input = scanner.nextLine();
			switch (input) {
			case "1" -> {
				data.append("name");
			}
			case "2" -> {
				data.append("major");
			}
			case "3" -> {
				data.append("phone");
			}
			case "4" -> {
				data.append("country");
			}
			case "5" -> {
				data.append("city");
			}
			case "6" -> {
				data.append("street");
			}
			default -> {
				System.out.println("Wrong choice");
			}
			}

			data.append("!~!oldValue=");
			System.out.println("enter the old value\n>>");
			data.append(scanner.nextLine());
			data.append("!~!newValue=");
			System.out.println("Enter the new value\n>>");
			data.append(scanner.nextLine());

		}
		return data.toString();
	}

	private String findDeed() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		String request = "find;type=deed,";
		int requestLength = request.length();

		System.out.println("1 - name;\n2 - major;\n3 - phone number;\n4 - country;\n5 - city;\n6 - street;\n>>");

		String message;
		String userData = "";
		do {
			String input = scanner.nextLine();
			switch (input) {
			case "1": {
				request += "deeds/deed[name='";
				message = "Enter name";
				userData = getFindData(message) + "']";
				request += userData;
				break;
			}

			case "2": {
				request += "deeds/deed[major='";
				message = "Enter major";
				userData = getFindData(message) + "']";
				request += userData;
				break;
			}

			case "3": {
				request += "deeds/deed[phone='";
				message = "Enter phone number";
				userData = getFindData(message) + "']";
				request += userData;
				break;
			}

			case "4": {
				request += "address[country='";
				message = "Enter country";
				userData = getFindData(message) + "']";
				request += userData;
				break;
			}

			case "5": {
				request += "address[city='";
				message = "Enter city";
				userData = getFindData(message) + "']";
				request += userData;
				break;
			}

			case "6": {
				request += "address[street='";
				message = "Enter street";
				userData = getFindData(message) + "']";
				request += userData;
				break;
			}

			default:
				System.out.println("Wrong choise. Try again\n>>");
				break;
			}
		} while (requestLength == request.length());
		return request;
	}

}