package ch.hsr.votegame.domain;

public class User {

	/** The username */
	private String nickname;

	/** The current vote of the user */
	private Integer currentVote = null;

	/** Indicates the game join order */
	private int playerId;

	public User(String name) {
		this.nickname = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getCurrentVote() {
		return currentVote;
	}

	public void setCurrentVote(Integer currentVote) {
		this.currentVote = currentVote;
		System.out.println("user voted " + currentVote);
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getPlayerId() {
		return playerId;
	}
}
