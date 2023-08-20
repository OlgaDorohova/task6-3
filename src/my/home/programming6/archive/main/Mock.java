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

import my.home.programming6.archive.bean.Archivist;
import my.home.programming6.archive.bean.User;
import my.home.programming6.archive.dao.DeedAccessProvider;
import my.home.programming6.archive.dao.StorageAccess;
import my.home.programming6.archive.dao.UserAccessProvider;
import my.home.programming6.archive.bean.Deed;
import my.home.programming6.archive.viewer.Viewer;
import my.home.programming6.archive.viewer.ViewerProvider;

public class Mock {
	public void present() {
		
		try(Socket socket = new Socket(InetAddress.getLocalHost(), 2048);
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))){
		Deed deed1 = new Deed("Olha", "economist", "1232423", new Deed().new Address("Ukrain", "Kiev", "Sumska"));
		Deed deed2 = new Deed("Helen", "math", "123242345", new Deed().new Address("Russia", "Moscow", "Arbat"));
		Deed deed3 = new Deed("Andrew", "fisics", "12376724868", new Deed().new Address("Ukrain", "Lvov", "Kiivska"));
		Deed deed4 = new Deed("Mary", "math", "1237574567348", new Deed().new Address("Russia", "Perm", "Lenina"));
		Deed deed5 = new Deed("Helen", "engineer", "1236534265", new Deed().new Address("Belarus", "Minsk", "Mira"));

		DeedAccessProvider accessProvider = DeedAccessProvider.getInstance();
		StorageAccess deedAccess = accessProvider.getDeedAccess();

		UserAccessProvider userProvider = UserAccessProvider.getInstance();
		StorageAccess userAccess = userProvider.getAccess();

		try {
			User user1 = new User("a", encrypt("1"));
			User user2 = new User("b", encrypt("2"));
			User admin = new Archivist("admin", encrypt("1234"));
			User user3 = new User("c", encrypt("3"));
			User user4 = new User("aa", encrypt("4"));

		} catch (Exception e) {
			System.out.println(e);
		}

		Viewer viewer = ViewerProvider.getInstance().getViewer();

		String getAllUsersRequest = "getAll;type=user";
		String getAllDeedsRequest = "getAll;type=deed";
		
		String request;
		String result;

		// admin authorization
		System.out.println("Authorizate admin");
		request = "log;login=!=admin,password=!="+encrypt("1234");
		writer.write(request + "\n");
		writer.flush();
		Thread.sleep(2000);
		result = reader.readLine();
		viewer.showAutorizMessage(result);
		System.out.println();

		// creating request for FindEntityCommand
		String userSearchType2 = "major";
		String userReadParameter2 = "math";
		String userSearchType1 = "country";
		String userReadParameter1 = "Russia";
		// String userSearchType1 = "city";
		// String userReadParameter1 = "Moscow";
		// String userSearchType1 = "street";
		// String userReadParameter1 = "Kiivska";
		String searchTypeUser = "type=user,users/user[";
		String searchTypeDeed = "type=deed,deeds/deed[";
		String searchTypeDeedAddress = "type=deed,address[";
		String findRequest1 = searchTypeDeedAddress + userSearchType1 + "='" + userReadParameter1 + "']";
		String findRequest2 = searchTypeDeed + userSearchType2 + "='" + userReadParameter2 + "']";

		String findCommandRequest;

		System.out.println("find deed by country = Russia");
		findCommandRequest = "find;" + findRequest1;
		writer.write(findCommandRequest + "\n");
		writer.flush();
		Thread.sleep(2000);
		result = reader.readLine();
		viewer.showDeeds(result);
		System.out.println();

		System.out.println("find deed by major = math");
		findCommandRequest = "find;" + findRequest2;
		writer.write(findCommandRequest + "\n");
		writer.flush();
		Thread.sleep(2000);
		result = reader.readLine();
		viewer.showDeeds(result);
		System.out.println();

		System.out.println("add user login=w2, password=doublew");
		request ="add;type=user,login=!=w2,password=!="+encrypt("doublew");
		writer.write(request + "\n");
		writer.flush();
		Thread.sleep(2000);
		result = reader.readLine();
		viewer.showCommonRes(result);
		
		request = "find;type=user,users/user[login='w2']";
		writer.write(request + "\n");
		writer.flush();
		Thread.sleep(2000);
		result = reader.readLine();
		viewer.showUsers(result);
		System.out.println();

		System.out.println("change password login=w2, old password=doublew, new password=doubleW");
		String EntityRequest = "type=user,login=!=w2";
		String parameter = "password";
		String oldValue = "doublew";
		String newValue = "doubleW";
		String changeDelimeter = "!~!";
		String changePassCommandReuest = "change;" + EntityRequest + changeDelimeter  + "parameter=!=" + parameter
				+ changeDelimeter + "oldValue=!=" +encrypt(oldValue)+ changeDelimeter + "newValue=!=" + encrypt(newValue);
		System.out.println(changePassCommandReuest);
		writer.write(changePassCommandReuest + "\n");
		writer.flush();
		Thread.sleep(2000);
		result = reader.readLine();
		viewer.showCommonRes(result);
		
		System.out.println("delete user login = w2");
		request = "delete;type=user,login=!=w2";
		writer.write(request + "\n");
		writer.flush();
		Thread.sleep(2000);
		result = reader.readLine();
		viewer.showCommonRes(result);
		System.out.println();

		System.out.println("add deed");
		request = "add;type=deed,name=testDeed,major=tests,phone=0,country=test,city=test,street=test";
		writer.write(request + "\n");
		writer.flush();
		Thread.sleep(2000);
		result = reader.readLine();
		viewer.showCommonRes(result);
		
		writer.write(getAllDeedsRequest + "\n");
		writer.flush();
		Thread.sleep(2000);
		result = reader.readLine();
		viewer.showDeeds(result);
		System.out.println();

//	 request
//		 type=user,login=!=login,password=!=password!~!parameter=!=value!~!oldValue=!=value!~!newValue=!=value
//		 type=admin,login=!=login,password=!=password!~!parameter=!=value!~!oldValue=!=value!~!newValue=!=value
//	 type=deed,name=name,major=major,phone=phone,country=country,city=city,street=street
//	 !~!parameter=value!~!oldValue=value!~!newValue=value
		System.out.println("change major from math to teacher in Helems's deed");
		request = "find;type=deed,deeds/deed[name='Helen']";
		writer.write(request + "\n");
		writer.flush();
		Thread.sleep(2000);
		result = reader.readLine();
		viewer.showDeeds(result);

		String EntityRequest2 = "type=deed,name=Helen";

		String parameter2 = "major";
		String oldValue2 = "math";
		String newValue2 = "teacher";
		String changeCommandReuest = "change;" + EntityRequest2 + changeDelimeter + "parameter=!=" + parameter2
				+ changeDelimeter + "oldValue=!=" + oldValue2 + changeDelimeter + "newValue=!=" + newValue2;

		writer.write(changeCommandReuest + "\n");
		writer.flush();
		Thread.sleep(2000);
		result = reader.readLine();
		viewer.showCommonRes(result);
		
		request = "find;type=deed,deeds/deed[name='Helen']";
		writer.write(request + "\n");
		writer.flush();
		Thread.sleep(2000);
		result = reader.readLine();
		viewer.showDeeds(result);

		System.out.println("get all users");
		writer.write(getAllUsersRequest + "\n");
		writer.flush();
		Thread.sleep(2000);
		result = reader.readLine();
		viewer.showUsers(result);
		
		
		writer.write("q" + "\n");
		writer.flush();
		Thread.sleep(2000);
		}catch (Exception e) {
		System.out.println(e);	}
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

}
