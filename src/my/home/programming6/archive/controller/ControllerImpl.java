package my.home.programming6.archive.controller;

import my.home.programming6.archive.controller.command.Command;
import my.home.programming6.archive.controller.command.CommandProvider;

public class ControllerImpl implements Controller {
//  request
//	commandName=commandName;parameters
	@Override
	public String action(String request) {
		CommandProvider provider = new CommandProvider();

		String [] params = request.split(";");
		Command command = provider.getCommand(params[0]);
	return	command.execute(params[1]);

	}

}
