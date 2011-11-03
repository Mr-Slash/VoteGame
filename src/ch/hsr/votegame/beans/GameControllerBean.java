package ch.hsr.votegame.beans;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.comet.CometContext;

import ch.hsr.votegame.domain.Game;
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
		// set the user locale
		FacesContext.getCurrentInstance().getViewRoot().setLocale(getModelBean().getLocale());
		
		initGamesList();

		if (!gameExists()) {
			Game joinableGame = getOrCreateJoinableGame();

			User user = new User("Player " + (joinableGame.getUsers().size() + 1));
			joinableGame.addUser(user);
			modelBean.setUser(user);
			modelBean.setGame(joinableGame);

			System.out.println("joinable game nr " + joinableGame.getGameId() + " found");
			System.out.println("added User [" + user.getNickname() + "] to game " + joinableGame.getGameId());
		}
	}

	public synchronized String addVote() {
		Game game = modelBean.getGame();
		game.addToHistory(modelBean.getUser(), modelBean.getUserVote());
		
		if (game.isWon(modelBean.getUser())) {
			game.setGameOver(true);
			game.setWinner(modelBean.getUser());
			System.out.println("User " + modelBean.getUser().getNickname() + " has won!!!");
		}
		System.out.println("User " + modelBean.getUser().getNickname() + " guessed wrong");
		
		CometContext.publish("update" + game.getGameId(), game.isGameOver());
		return "index.xhtml";
	}

	private void initGamesList() {
		if (getGames() == null) {
			System.out.println("controller creates new games list");
			getContext().getApplicationMap().put(GAMES_LIST, new ArrayList<Game>());
		}
	}

	private void storeToContext(Game game) {
		getGames().add(game);
		System.out.println("stored game " + game.getGameId() + " to context. nr of games in context = " + getGames().size());
	}

	private Game getOrCreateJoinableGame() {
		for (Game game : getGames()) {
			if (!game.isFull() && !game.isGameOver()) {
				return game;
			}
		}
		
		Game game = new Game(++gameCounter);
		storeToContext(game);
		
		return game;
	}

	private boolean gameExists() {
		return (modelBean.getGame() != null);
	}

	@SuppressWarnings("unchecked")
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
	
	public void changeLocale(ActionEvent event) {
		System.out.println("GameControllerBean.changeLocale()");
		
		FacesContext context = FacesContext.getCurrentInstance();
		String lang = event.getComponent().getId();

		if (lang.equals(Locale.GERMAN.getLanguage())) {
			Locale locale = new Locale(Locale.GERMAN.getLanguage(), Locale.GERMAN.getCountry());
			getModelBean().setLocale(locale);
		} else {
			Locale locale = new Locale(Locale.ENGLISH.getLanguage(), Locale.ENGLISH.getCountry());
			getModelBean().setLocale(locale);
		}
	}
	
	
	public String getLocale(){
		return FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
	}
	
	public String getWinner(){
		User winner = getModelBean().getGame().getWinner();
		return winner != null ? winner.getNickname() : "-";
	}
	
	public String getGameOverMessage(){
		return getMessage("gameover", getModelBean().getGame().getSecretVote(), getWinner());
	}
	
	public String getWelcomeMessage(){
		return getMessage("welcome", getModelBean().getUser().getNickname(), getModelBean().getGame().getGameId());
	}
	
	private void invalidateSession() {
		HttpSession session = (HttpSession) getContext().getSession(false);
		session.invalidate();
	}

	private ExternalContext getContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	private String getMessage(String key, Object... params){
		FacesContext context = FacesContext.getCurrentInstance();
	    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "props");
	    return MessageFormat.format(bundle.getString(key), params);
	}
}
