package my.home.programming6.archive.controller.command;

import my.home.programming6.archive.bean.Entity;
import my.home.programming6.archive.exception.LogicException;
import my.home.programming6.archive.logic.Logic;
import my.home.programming6.archive.logic.LogicProvider;

public class ChangeCommand implements Command {

	// request
	// type=user,login=!=login,password=!=password!~!parameter=!=value!~!oldValue=!=value!~!newValue=!=value
	// type=admin,login=!=login,password=!=password!~!parameter=!=value!~!oldValue=!=value!~!newValue=!=value
	// type=deed,name=name,major=major,phone=phone,country=country,city=city,street=street
	// |~|parameter=value|~|oldValue=value|~|newValue=value

	@Override
	public String execute(String request) {
		Logic access = LogicProvider.getInstance().getLogic();
		String parameters[] = request.split("!~!");
		String[] entityParams = parameters[0].split(",");

		Entity entity = EntityParser.getEntity(entityParams);
		String parameter = parameters[1].split("=!=")[1];
		String oldValue = parameters[2].split("=!=")[1];
		String newValue = parameters[3].split("=!=")[1];
		try {
			boolean isChanged = access.changeEntity(entity, parameter, oldValue, newValue);

			return (isChanged) ? "0" : "1";
		} catch (LogicException e) {
			return "error";
		}

	}

}
