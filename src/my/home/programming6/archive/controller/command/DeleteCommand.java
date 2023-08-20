package my.home.programming6.archive.controller.command;

import my.home.programming6.archive.bean.Entity;
import my.home.programming6.archive.exception.LogicException;
import my.home.programming6.archive.logic.Logic;
import my.home.programming6.archive.logic.LogicProvider;

public class DeleteCommand implements Command {

	// request
	// type=user,login=login,password=password
	// type=admin,login=login,password=password
	// type=deed,name=name,major=major,phone=phone,country=country,city=city,street=street

	@Override
	public String execute(String request) {
		Logic access = LogicProvider.getInstance().getLogic();

		String[] parameters = request.split(",");
		Entity entity = EntityParser.getEntity(parameters);

		try {
			boolean isDelete = access.deleteEntity(entity);
			return (isDelete) ? "0" : "1";
		} catch (LogicException e) {
			return "error";
		}

	}

}
