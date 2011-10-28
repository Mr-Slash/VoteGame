package ch.hsr.votegame.beans;

import ch.hsr.votegame.domain.Game;
import ch.hsr.votegame.domain.User;

public class GameModelBean {
	private Game game; //= new Game(1); //TODO remove initialization, only for testing
	private User user;
	private int userVote;
	
	public GameModelBean(){
		System.out.println("model bean: created");
	}
	
	public int getUserVote() {
		return userVote;
	}

	public void setUserVote(int userVote) {
		this.userVote = userVote;
		System.out.println("model bean: user voted "+ userVote);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		System.out.println("model bean added new user "+user.getNickname());
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
		System.out.println("model bean added game "+game.getGameId());
	}
}
