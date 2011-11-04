package ch.hsr.votegame.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
	private int gameId;
	private Integer minRangeNr = 1;
	private Integer maxRangeNr = 5;
	private Integer secretVote;
	private boolean gameOver = false;
	public static final int MAX_USERS = 3;
	private List<HistoryEntry> voteHistory = new ArrayList<HistoryEntry>();
	private List<User> users = new ArrayList<User>();
	private User winner;

	public Game(int id) {
		gameId = id;
		secretVote = new Integer(new Random().nextInt(maxRangeNr)+1);
		System.out.println("new Game created with secret Vote: " + secretVote);
	}

	public synchronized void addUser(User user) {
		users.add(user);
		user.setPlayerId(users.size());
	}

	public List<User> getUsers() {
		return users;
	}

	public boolean isWon(User user) {
		return (user.getCurrentVote() != null && user.getCurrentVote().equals(secretVote));
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getMinRangeNr() {
		return minRangeNr;
	}

	public void setMinRangeNr(int minRangeNr) {
		this.minRangeNr = minRangeNr;
	}

	public int getMaxRangeNr() {
		return maxRangeNr;
	}

	public void setMaxRangeNr(int maxRangeNr) {
		this.maxRangeNr = maxRangeNr;
	}

	public int getSecretVote() {
		return secretVote;
	}

	public void setSecretVote(int secretVote) {
		this.secretVote = secretVote;
	}

	public List<HistoryEntry> getHistory() {
		return voteHistory;
	}

	public void setHistory(List<HistoryEntry> history) {
		this.voteHistory = history;
	}

	public void addToHistory(User user, Integer vote) {
		voteHistory.add(new HistoryEntry(user, vote));
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public boolean isFull(){
		return getUsers().size() == MAX_USERS;
	}
	
	/**
	 * Workaround in order to get the amount of users in EL-Syntax
	 * @return
	 */
	public int getUserCount(){
		return getUsers().size();
	}

	public void setWinner(User winner) {
		this.winner = winner;
	}

	public User getWinner() {
		return winner;
	}
}
