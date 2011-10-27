package ch.hsr.votegame.beans;

import ch.hsr.votegame.domain.Game;
import ch.hsr.votegame.domain.User;

public class GameModelBean {
	private Game game;
	private User user;
	private int userVote;
	
	public int getUserVote() {
		return userVote;
	}

	public void setUserVote(int userVote) {
		this.userVote = userVote;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
