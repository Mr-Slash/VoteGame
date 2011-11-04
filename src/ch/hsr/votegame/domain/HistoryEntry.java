package ch.hsr.votegame.domain;

public class HistoryEntry {

	private final User user;
	private final Integer vote;

	public HistoryEntry(User user, Integer vote) {
		this.user = user;
		this.vote = vote;
	}

	public User getUser() {
		return user;
	}

	public Integer getVote() {
		return vote;
	}
}
