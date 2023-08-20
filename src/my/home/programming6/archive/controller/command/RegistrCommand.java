package my.home.programming6.archive.controller.command;

import my.home.programming6.archive.exception.LogicException;
import my.home.programming6.archive.logic.AutoProvider;
import my.home.programming6.archive.logic.Autorization;

public class RegistrCommand implements Command {

//	login=!=userLogin,password=!=userPassword

	@Override
	public String execute(String request) {
		AutoProvider provider = AutoProvider.getInstance();
		Autorization auto = provider.getAutorization();

		String params[] = request.split(",");
		String login = params[0].split("=!=")[1];
		String password = params[1].split("=!=")[1];

		try {
			return (auto.registration(login, password)) ? "0" : "1";

		} catch (LogicException e) {
			return "error";
		}
	}

}
