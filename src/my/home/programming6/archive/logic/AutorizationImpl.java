package my.home.programming6.archive.logic;

import java.util.Set;

import my.home.programming6.archive.bean.Entity;
import my.home.programming6.archive.bean.User;
import my.home.programming6.archive.dao.StorageAccess;
import my.home.programming6.archive.dao.UserAccessProvider;
import my.home.programming6.archive.exception.DaoException;
import my.home.programming6.archive.exception.LogicException;

public class AutorizationImpl implements Autorization {
	UserAccessProvider provider = UserAccessProvider.getInstance();
	StorageAccess access = provider.getAccess();

	@Override
	public boolean registration(String login, String password) throws LogicException {

		String getRequest = "/users/user[login='" + login + "']";
		Set<? extends Entity> users;
		User user;
		try {
			user = new User(login, password);
		} catch (DaoException e) {
			throw new LogicException(e);}

		try {
			users = access.get(getRequest);
			if (users.isEmpty()) {
				access.add(user);
				return true;
			} else {
				return false;

			}

		} catch (DaoException e) {
			throw new LogicException(e);
		}

	}

	@Override
	public User logination(String login, String password) throws LogicException {
		String getRequest = "/users/user[login='" + login + "']";

		try {
			Set<? extends Entity> users = access.get(getRequest);
			if (!users.isEmpty()) {
				
				for (Entity entity : users) {
					User user = (User) entity;

					return (login.equals(user.getLogin()) && password.equals(user.getPassword())) ? user
							: null;
				}
			}

		} catch (DaoException e) {
			throw new LogicException(e);
		}
		return null;
	}

}
