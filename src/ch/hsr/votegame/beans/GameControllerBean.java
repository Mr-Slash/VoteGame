package ch.hsr.votegame.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import ch.hsr.votegame.domain.Game;
import ch.hsr.votegame.domain.HistoryEntry;
import ch.hsr.votegame.domain.User;

public class GameControllerBean {
	private GameModelBean modelBean;
	private static int gameCounter;
	private static final String GAMES_LIST = "gamesList";

	public GameModelBean getModelBean() {
		return modelBean;
	}

	public void setModelBean(GameModelBean modelBean) {
		this.modelBean = modelBean;
	}

	public void check() {
		initGamesList();

		if (!gameExists()) {
			Game joinableGame = getJoinableGame();

			if (joinableGame != null) {
				System.out.println("joinable game nr " + joinableGame.getGameId() + " found");
				addUser(joinableGame);
				modelBean.setGame(joinableGame);
			} else {
				System.out.println("no joinable games found");
				Game game = new Game(++gameCounter);
				addUser(game);
				modelBean.setGame(game);
				storeToContext(game);
			}
		}
	}

	public String addVote() {
		Game game = modelBean.getGame();
		game.addToHistory(new HistoryEntry<User, Integer>(modelBean.getUser(), modelBean.getUserVote()));
		if (game.isWon(modelBean.getUser())) {
			game.setGameOver(true);
			System.out.println("User " + modelBean.getUser().getNickname() + " has won!!!");
			return "index.xhtml";
		}
		System.out.println("User " + modelBean.getUser().getNickname() + " guessed wrong");
		return "index.xhtml";
	}

	private void initGamesList() {
		if (getGames() == null) {
			System.out.println("controller creates new games list");
			getContext().getApplicationMap().put(GAMES_LIST, new ArrayList<Game>());
		}
	}

	private void addUser(Game game) {
		User user = createUser(game);
		game.addUser(user);
		modelBean.setUser(user);
		System.out.println("added User [" + user.getNickname() + "] to game " + game.getGameId());
	}

	private User createUser(Game game) {
		switch (game.getUsers().size()) {
		case 1:
			return new User(Game.PLAYER_2);
		case 2:
			return new User(Game.PLAYER_3);
		default:
			return new User(Game.PLAYER_1);
		}
	}

	private void storeToContext(Game game) {
		getGames().add(game);
		System.out.println("stored game " + game.getGameId() + " to context. nr of games in context = "
				+ getGames().size());
	}

	private Game getJoinableGame() {
		Iterator<Game> it = getGames().iterator();
		while (it.hasNext()) {
			Game game = it.next();
			if (game.getUsers().size() < Game.MAX_USERS) {
				return game;
			}
		}
		return null;
	}

	private boolean gameExists() {
		return (modelBean.getGame() != null);
	}

	private ArrayList<Game> getGames() {
		return (ArrayList<Game>) getContext().getApplicationMap().get(GAMES_LIST);
	}

	public String startNewGame() {
		getGames().remove(modelBean.getGame());
		invalidateSession();
		try {
			getContext().redirect("index.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "index.xhtml";
	}

	private void invalidateSession() {
		HttpSession session = (HttpSession) getContext().getSession(false);
		session.invalidate();
	}

	private ExternalContext getContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
}
