package ch.hsr.votegame.beans;

import ch.hsr.votegame.domain.Game;
import ch.hsr.votegame.domain.HistoryEntry;
import ch.hsr.votegame.domain.User;

public class GameControllerBean {
	private GameModelBean modelBean;

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
			if(!joinableGameExists()){
				updateComponents(new Game());
			}else{
				storeGame();
			}
		}
		return "index.xhtml";
	}

	private void updateComponents(Game game) {
		modelBean.setGame(game);
		//Store game in Context
	}

	private boolean gameExists() {
		return (modelBean.getGame() != null);
	}
	
	private boolean joinableGameExists() {
		return false;
	}
	
	private void storeGame() {
		
	}
}
