package ch.hsr.votegame.domain;

public class User {
	private String nickname;
	private Integer currentVote = null;

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
}
