package my.home.programming6.archive.dao;

import java.util.Set;

import my.home.programming6.archive.bean.Entity;
import my.home.programming6.archive.exception.DaoException;

public interface StorageAccess

{
	public boolean add(Entity entity) throws DaoException;

	public boolean delete(Entity t) throws DaoException;

	public Set<? extends Entity> get(String parameter) throws DaoException;

	public Set<? extends Entity> getAll() throws DaoException;
	
	boolean change(Entity entity, String parameterName, String oldValue, String newValue) throws DaoException;

}
