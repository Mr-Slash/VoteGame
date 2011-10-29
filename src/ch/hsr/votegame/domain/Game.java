package ch.hsr.votegame.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
	private int gameId;
	private int minRangeNr = 1;
	private int maxRangeNr = 5;
	private int secretVote;
	private boolean gameOver = false;

	public static final int MAX_USERS = 3;
	public static final String PLAYER_1 = "Spieler 1";
	public static final String PLAYER_2 = "Spieler 2";
	public static final String PLAYER_3 = "Spieler 3";

	private List<HistoryEntry<User, Integer>> voteHistory;
	private ArrayList<User> users;

	public Game(int id) {
		gameId = id;
		users = new ArrayList<User>(MAX_USERS);
		voteHistory = new ArrayList<HistoryEntry<User, Integer>>();
		secretVote = new Integer(new Random().nextInt(maxRangeNr)+1);
		System.out.println("new Game created with secret Vote: " + secretVote);
	}

	public void addUser(User user) {
		users.add(user);
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public boolean isWon(User user) {
		return (user.getCurrentVote() == secretVote) ? true : false;
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

	public List<HistoryEntry<User, Integer>> getHistory() {
		return voteHistory;
	}

	public void setHistory(List<HistoryEntry<User, Integer>> history) {
		this.voteHistory = history;
	}

	public void addToHistory(HistoryEntry<User, Integer> historyEntry) {
		voteHistory.add(historyEntry);
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
}
