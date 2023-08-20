package my.home.programming6.archive.controller.command;

import java.util.Set;

import my.home.programming6.archive.bean.Entity;
import my.home.programming6.archive.exception.LogicException;
import my.home.programming6.archive.logic.Logic;
import my.home.programming6.archive.logic.LogicProvider;

public class FindEntity implements Command {

//request
//returnType=type,searchParameter=parameter
	@Override
	public String execute(String request) {
		LogicProvider provider = LogicProvider.getInstance();
		Logic access = provider.getLogic();

		String[] requestParams = request.split(",");
		String type = requestParams[0].split("=")[1];
		String parameter = requestParams[1];
		
		Set<? extends Entity> entities;
		StringBuffer response = new StringBuffer("0");

		try {
		entities =	access.findEntity(type, parameter);
		
		if(entities.size() == 0) {
			return "1";
		}
		
		} catch (LogicException e) {
			return "error";
		}
		
		for(Entity entity: entities) {
			response.append(entity + "~");
		}
		
		return response.toString();
	}

}
