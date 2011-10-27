package ch.hsr.votegame.domain;

public class User {
	private String nickname;
	private int currentVote;

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getCurrentVote() {
		return currentVote;
	}
	public void setCurrentVote(int currentVote) {
		this.currentVote = currentVote;
	}
}
