package my.home.programming6.archive.viewer;

public class ViewerImpl implements Viewer {

	@Override
	public void showAutorizMessage(String result) {
		switch (result) {
		case "0" -> System.out.println("User autorizated");
		case "1" -> System.out.println("Login is already used");
		case "2" -> System.out.println("Wrong login or password");
		case "error" -> System.out.println("Error!");
		}
	}

	@Override
	public void showDeeds(String result) {
		if (result.startsWith("0")) {
			for (String s : result.split("~")) {
				parseDeed(s);
			}
		} else if (result.startsWith("1")) {
			System.out.println("Deed is not found");
		} else if (result.startsWith("error")) {
			System.out.println("Something was wrong. Try again later");
		}
	}

	@Override
	public void showUsers(String result) {
		if(result.startsWith("0")) {
		for (String resLine : result.split("~")) {

			String parameters[] = resLine.split(",");
			String login = parameters[0].split("=")[1];
			String role = (parameters[2].split("=")[1]).replaceAll("]", "");
			System.out.printf("%-10s| %-10s\n", login, role);}}
		else if(result.startsWith("1")) {
			System.out.println("User is not found");}
		
		else if(result.startsWith("error")) {
			System.out.println("Something was wrong. Try again later");
		}
	}

	private void parseDeed(String deed) {
		String parameters[] = deed.split(",");
		String name = "";
		String major = "";
		String phone = "";
		String country = "";
		String city = "";
		String street = "";

		for (String s : parameters) {
			if (s.contains("name")) {
				name += s.split("=")[1];
			}
			if (s.contains("major")) {
				major += s.split("=")[1];
			}
			if (s.contains("phone")) {
				phone += s.split("=")[1];
			}
			if (s.contains("country")) {
				country += s.split("=")[1];
			}
			if (s.contains("city")) {
				city += s.split("=")[1];
			}
			if (s.contains("street")) {
				street += (s.split("=")[1]).replaceAll("]", "");
			}
		}

		System.out.printf("%-10s |%-10s |%-18s |%-10s |%-10s |%-10s\n", name, major, phone, country, city, street);
	}

	@Override
	public void showCommonRes(String result) {
		if (result.startsWith("0")) {
			System.out.println("Well done");
		} else if (result.startsWith("error")) {
			System.out.println("Something was wrong");
		} else if (result.startsWith("1")) {
			System.out.println("Content is not found");
		}

	}
}
