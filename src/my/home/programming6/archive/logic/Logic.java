package my.home.programming6.archive.logic;

import java.util.Set;

import my.home.programming6.archive.bean.Entity;
import my.home.programming6.archive.exception.LogicException;

public interface Logic {
	public boolean addEntity(Entity entity) throws LogicException;

	public boolean deleteEntity(Entity entity) throws LogicException;

	public boolean changeEntity(Entity entity, String parameter, String oldValue, String newValue) throws LogicException;

	public Set<? extends Entity> findEntity(String type, String parameter) throws LogicException;
	
	public Set<? extends Entity> getAllEntity(String type) throws LogicException;

}
