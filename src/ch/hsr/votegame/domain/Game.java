package ch.hsr.votegame.domain;

import java.util.List;
import java.util.Map.Entry;

public class Game {
	private int gameId;
	private int minRange;
	private int maxRange;
	private int secretVote;
	private List<Entry<User, Integer>> history;
	
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public int getMinRange() {
		return minRange;
	}
	public void setMinRange(int minRange) {
		this.minRange = minRange;
	}
	public int getMaxRange() {
		return maxRange;
	}
	public void setMaxRange(int maxRange) {
		this.maxRange = maxRange;
	}
	public int getSecretVote() {
		return secretVote;
	}
	public void setSecretVote(int secretVote) {
		this.secretVote = secretVote;
	}
	public List<Entry<User, Integer>> getHistory() {
		return history;
	}
	public void setHistory(List<Entry<User, Integer>> history) {
		this.history = history;
	}
}
