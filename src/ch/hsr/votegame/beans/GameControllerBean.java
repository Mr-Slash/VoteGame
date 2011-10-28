package ch.hsr.votegame.beans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.context.FacesContext;

import ch.hsr.votegame.domain.Game;
import ch.hsr.votegame.domain.HistoryEntry;
import ch.hsr.votegame.domain.User;

public class GameControllerBean {
	private GameModelBean modelBean;
	private static int gameCounter = 0;
	private static final String GAMES_LIST = "gamesList";

	public GameControllerBean() {
		System.out.println("controller bean created");
	}

	public GameModelBean getModelBean() {
		return modelBean;
	}

	public void setModelBean(GameModelBean modelBean) {
		this.modelBean = modelBean;
	}

	public String addVote() {
		Game game = modelBean.getGame();
		game.addToHistory(new HistoryEntry<User, Integer>(modelBean.getUser(), modelBean.getUserVote()));
		return "ok";
	}

	public String check() {
		if (!gameExists()) {
			Game joinableGame = getJoinableGame();

			if (joinableGame != null) {
				addUser(joinableGame);
				modelBean.setGame(joinableGame);
			} else {
				Game game = new Game(gameCounter++);
				addUser(game);
				modelBean.setGame(game);
				storeToContext(game);
			}
		}
		return "index.xhtml";
	}

	private void addUser(Game game) {
		int count = game.getUsers().size();
		if (count == 1)
			game.addUser(new User(Game.PLAYER_2, modelBean.getUserVote()));
		if (count == 2) {
			game.addUser(new User(Game.PLAYER_3, modelBean.getUserVote()));
		} else {
			game.addUser(new User(Game.PLAYER_1, modelBean.getUserVote()));
		}
	}

	private void storeToContext(Game game) {
		getApplicationMap().put(Integer.toString(game.getGameId()), game);
	}

	private Game getJoinableGame() {
		for (Game game : getGames()) {
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
		return (ArrayList<Game>) getApplicationMap().get(GAMES_LIST);
	}

	private Map<String, Object> getApplicationMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
	}
}
