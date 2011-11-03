package ch.hsr.votegame.beans;

import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import ch.hsr.votegame.domain.Game;
import ch.hsr.votegame.domain.User;

public class GameModelBean {
	private Game game;
	private User user;
	private boolean gameJoined = false;
	private Locale locale = Locale.GERMAN;

	public GameModelBean() {
		System.out.println("model bean: created");
	}

	public Integer getUserVote() {
		return user != null ? user.getCurrentVote() : null;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		System.out.println("model bean added new user " + user.getNickname());
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
		if (game != null) {
			System.out.println("model bean added game " + game.getGameId());
			setGameJoined(true);
		}
	}

	public boolean isGameJoined() {
		return gameJoined;
	}

	public void setGameJoined(boolean gameJoined) {
		this.gameJoined = gameJoined;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}
}
