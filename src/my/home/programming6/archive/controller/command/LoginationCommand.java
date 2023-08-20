package my.home.programming6.archive.controller.command;

import my.home.programming6.archive.bean.User;
import my.home.programming6.archive.exception.LogicException;
import my.home.programming6.archive.logic.AutoProvider;
import my.home.programming6.archive.logic.Autorization;

public class LoginationCommand implements Command {
//	login=!=userLogin,password=!=userPassword

	@Override
	public String execute(String request) {

		AutoProvider provider = AutoProvider.getInstance();
		Autorization access = provider.getAutorization();

		String[] params = request.split(",");
		String login = params[0].split("=!=")[1];
		String password = params[1].split("=!=")[1];

		try {
			User user = access.logination(login, password);
			return (user == null) ? "2" : "0;" + "login=" + user.getLogin() + ",role=" + user.getRole().getUserRole();
		} catch (LogicException e) {
			return "error";
		}
	}

}
