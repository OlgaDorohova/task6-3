package my.home.programming6.archive.bean;

import my.home.programming6.archive.exception.DaoException;

public class Archivist extends User{

	private static final long serialVersionUID = 3087738889411825140L;

public Archivist(String login, String password) throws DaoException {
	super(login, password);
	this.role = UserRole.ADMIN;
}

}
