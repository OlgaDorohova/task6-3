package my.home.programming6.archive.controller.command;

import my.home.programming6.archive.exception.LogicException;
import my.home.programming6.archive.logic.Logic;
import my.home.programming6.archive.logic.LogicProvider;

public class AddEntityCommand implements Command {

//request
//type=user,login=login,password=password
//type=admin,login=login,password=password
//type=deed,name=name,major=major,phone=phone,country=country,city=city,street=street
	@Override
	public String execute(String request) {
		LogicProvider provider = LogicProvider.getInstance();
		Logic access = provider.getLogic();

		String[] parameters = request.split(",");

		try {
			boolean isAdd = access.addEntity(EntityParser.getEntity(parameters));
			return (isAdd) ? "0" : "1";
		} catch (LogicException e) {
			return "error";
		}

	}

	
}
