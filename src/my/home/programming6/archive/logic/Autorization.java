package my.home.programming6.archive.logic;

import my.home.programming6.archive.bean.User;
import my.home.programming6.archive.exception.LogicException;

public interface Autorization {
public boolean registration(String login, String password) throws LogicException;
public User logination(String login, String password) throws LogicException;
}
