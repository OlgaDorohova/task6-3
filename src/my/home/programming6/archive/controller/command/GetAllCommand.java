package my.home.programming6.archive.controller.command;

import java.util.Set;

import my.home.programming6.archive.bean.Entity;
import my.home.programming6.archive.exception.LogicException;
import my.home.programming6.archive.logic.Logic;
import my.home.programming6.archive.logic.LogicProvider;

public class GetAllCommand implements Command {

	// request
	// type=entityType
	@Override
	public String execute(String request) {
		LogicProvider provider = LogicProvider.getInstance();
		Logic accessLogic = provider.getLogic();

		String entityType = request.split("=")[1];
		StringBuffer result = new StringBuffer();

		try {
			Set<? extends Entity> entities = accessLogic.getAllEntity(entityType);
if(entities.size() != 0) {
	result.append("0");
} else {
	result.append("1");
}
			for (Entity entity : entities) {
				result.append(entity.toString() + "~");
			}

			return result.toString();

		} catch (LogicException e) {
			return "error";
		}
	}

}
