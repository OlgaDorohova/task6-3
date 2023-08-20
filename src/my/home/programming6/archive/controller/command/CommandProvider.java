package my.home.programming6.archive.controller.command;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
private Map <String, Command> commands = new HashMap<>();

public CommandProvider() {
	commands.put("reg", new RegistrCommand());
	commands.put("log", new LoginationCommand());
	commands.put("find", new FindEntity());
	commands.put("add", new AddEntityCommand());
	commands.put("delete", new DeleteCommand());
	commands.put("change", new ChangeCommand());
	commands.put("getAll", new GetAllCommand());
}

public Command getCommand(String commandName) {
return	commands.get(commandName);
	
}
}
