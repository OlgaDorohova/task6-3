package my.home.programming6.archive.controller.command;

import my.home.programming6.archive.bean.Archivist;
import my.home.programming6.archive.bean.Deed;
import my.home.programming6.archive.bean.Entity;
import my.home.programming6.archive.bean.User;
import my.home.programming6.archive.exception.DaoException;

public class EntityParser {
	
	public static Entity getEntity(String[] parameters) {
		Entity entity = null;
		switch (parameters[0].split("=")[1]) {
		case "user": {
			User user = new User();
			for (int i = 0; i < parameters.length; i++) {
				String[] tempParams = parameters[i].split("=!=");
				switch (tempParams[0]) {
				case "login" -> {
					user.setLogin(tempParams[1]);
				}
				case "password" -> {
					user.setPassword(tempParams[1]);
				}
				}
			}
			entity = user;
			break;
		}
		case "admin": {
			String login = "";
			String password = "";
			for (int i = 0; i < parameters.length; i++) {
				String[] tempParams = parameters[i].split("=!=");
				switch (tempParams[0]) {
				case "login" -> {
					login = tempParams[1];
				}
				case "password" -> {
					password = tempParams[1];
				}
				}
			}

			Archivist admin = null;
			try {
				admin = new Archivist(login, password);
			} catch (DaoException e) {

			}
			entity = admin;
			break;
		}
		case "deed": {
			Deed deed = new Deed();
			Deed.Address address = new Deed().getAddress();

			for (int i = 0; i < parameters.length; i++) {
				String[] tempParams = parameters[i].split("=");
				switch (tempParams[0]) {
				case "name" -> {
					deed.setName(tempParams[1]);
				}
				case "major" -> {
					deed.setMajor(tempParams[1]);
				}
				case "phone" -> {
					deed.setPhoneNumber(tempParams[1]);
				}

				case "country" -> {
					address.setCountry(tempParams[1]);
				}

				case "city" -> {
					address.setCity(tempParams[1]);
				}

				case "street" -> {
					address.setStreet(tempParams[1]);
				}

				}

			}

			deed.setAddress(address);
			entity = deed;
			break;
		}

		}
		return entity;

	}

}
