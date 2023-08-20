package my.home.programming6.archive.logic;

import java.util.Set;

import my.home.programming6.archive.bean.Deed;
import my.home.programming6.archive.bean.Entity;
import my.home.programming6.archive.bean.User;
import my.home.programming6.archive.dao.DeedAccessProvider;
import my.home.programming6.archive.dao.StorageAccess;
import my.home.programming6.archive.dao.UserAccessProvider;
import my.home.programming6.archive.exception.DaoException;
import my.home.programming6.archive.exception.LogicException;

public class LogicImpl implements Logic {
	UserAccessProvider userProvider = UserAccessProvider.getInstance();
	DeedAccessProvider deedProvider = DeedAccessProvider.getInstance();
	StorageAccess access;

	@Override
	public boolean addEntity(Entity entity) throws LogicException {
		access = getStorageAccess(entity);
		try {
			return access.add(entity);
		} catch (DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public boolean deleteEntity(Entity entity) throws LogicException {
		access = getStorageAccess(entity);
		try {
			return access.delete(entity);
		} catch (DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public boolean changeEntity(Entity entity, String parameter, String oldValue, String newValue)
			throws LogicException {
		access = getStorageAccess(entity);

		try {
			return access.change(entity, parameter, oldValue, newValue);
		} catch (DaoException e) {
			throw new LogicException(e);
		}

	}

	@Override
	public Set<? extends Entity> findEntity(String returnType, String parameter) throws LogicException {
		access = getStorageAccess(returnType);
		try {
			return access.get(parameter);
		} catch (DaoException e) {
			throw new LogicException(e);
		}

	}

	@Override
	public Set<? extends Entity> getAllEntity(String type) throws LogicException {
		access = getStorageAccess(type);
		try {
			return access.getAll();
		} catch (DaoException e) {
			throw new LogicException(e);
		}
	}

	private StorageAccess getStorageAccess(Entity entity) throws LogicException {
		StorageAccess access = null;
		if (entity instanceof User) {
			access = userProvider.getAccess();
		} else if (entity instanceof Deed) {
			access = deedProvider.getDeedAccess();
		} else {
			throw new LogicException("Wrong enterance type");
		}
		return access;
	}

	private StorageAccess getStorageAccess(String type) throws LogicException {
		return (type.equalsIgnoreCase("user")) ? userProvider.getAccess() : deedProvider.getDeedAccess();
	}

}
