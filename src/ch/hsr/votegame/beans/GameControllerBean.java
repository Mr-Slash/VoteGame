package ch.hsr.votegame.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import ch.hsr.votegame.domain.Game;
import ch.hsr.votegame.domain.HistoryEntry;
import ch.hsr.votegame.domain.User;

public class GameControllerBean {
	private GameModelBean modelBean;
	private static int gameCounter = 1;
	private static final String GAMES_LIST = "gamesList";

	public GameControllerBean(){
		getApplicationMap().put(GAMES_LIST, new ArrayList<Game>());
	}
	
	public GameModelBean getModelBean() {
		return modelBean;
	}

	public void setModelBean(GameModelBean modelBean) {
		this.modelBean = modelBean;
	}

	public String addVote() {
		System.out.println("Method addVote() in Controller started");
		Game game = modelBean.getGame();
		game.addToHistory(new HistoryEntry<User, Integer>(modelBean.getUser(), modelBean.getUserVote()));
		if (game.isWon(modelBean.getUser())){
			game.setGameOver(true);
			return "rerender page show winner, secret vote, vote history and new game link";
		}
		return "show old vote and vote history";
	}

	public String check() {
		if (!gameExists()) {
			Game joinableGame = getJoinableGame();

			if (joinableGame != null) {
				System.out.println("joinable game found");
				addUser(joinableGame);
				modelBean.setGame(joinableGame);
			} else {
				System.out.println("no joinable games found");
				Game game = new Game(gameCounter++);
				addUser(game);
				modelBean.setGame(game);
				storeToContext(game);
			}
			invalidateSession();
			return "start page";
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
		System.out.println("added User ["+game.getUsers().get(game.getUsers().size()-1).getNickname()+"] to game "+ game.getGameId());
	}

	private void storeToContext(Game game) {
		getApplicationMap().put(Integer.toString(game.getGameId()), game);
	}

	private Game getJoinableGame() {
		Iterator<Game> it = getGames().iterator();
		while(it.hasNext()){
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
		return (ArrayList<Game>) getApplicationMap().get(GAMES_LIST);
	}

	private Map<String, Object> getApplicationMap() {
		return getContext().getApplicationMap();
	}
	
	private void invalidateSession() {
		HttpSession session = (HttpSession) getContext().getSession(false);
		session.invalidate();
		System.out.println("Session invalidiert");
	}
	
	private ExternalContext getContext(){
		return FacesContext.getCurrentInstance().getExternalContext();
	}
}
