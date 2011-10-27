package ch.hsr.votegame.beans;

import javax.faces.context.FacesContext;

import ch.hsr.votegame.domain.Game;
import ch.hsr.votegame.domain.HistoryEntry;
import ch.hsr.votegame.domain.User;

public class GameControllerBean {
	private GameModelBean modelBean;
	private int gameCounter = 0;
	
	public GameModelBean getModelBean() {
		return modelBean;
	}

	public void setModelBean(GameModelBean modelBean) {
		this.modelBean = modelBean;
	}
	
	public String addVote(){
		Game game = modelBean.getGame();
		game.addToHistory(new HistoryEntry<User, Integer>(modelBean.getUser(), modelBean.getUserVote()));
		return "ok";
	}

	public String check(){
		if (!gameExists()){
			Game joinableGame = getJoinableGame();
			
			if(joinableGame != null){
				modelBean.setGame(joinableGame);
			}else{
				Game game = new Game(gameCounter++);
				modelBean.setGame(game);
				storeToContext(game);
			}
		}
		return "index.xhtml";
	}

	private void storeToContext(Game game){
		FacesContext fc = FacesContext.getCurrentInstance();
		//TODO
		
	}

	private Game getJoinableGame() {
		//TODO
		return null;
	}

	private boolean gameExists() {
		return (modelBean.getGame() != null);
	}
}
