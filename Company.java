import java.util.*;

public class Company {

	int numSeats;
	String companyName;
	HashMap<String, Integer> talentRankings;
	HashMap<Talent, Integer> markedTalents;

	Company(int numSeats) {
		this.numSeats = numSeats;
		companyName = "";
		talentRankings = new HashMap<String, Integer>();
		markedTalents = new HashMap<Talent, Integer>();
	}

	public void resetMarkedTalents() {
		this.markedTalents = new HashMap<Talent, Integer>();
	}

	public int getNumSeats() {
		return this.numSeats;
	}

	public void decrementNumSeats() {
		this.numSeats--;
	}

	public void incrementNumSeats() {
		this.numSeats++;
	}

	public void setNumSeats(int x) {
		this.numSeats = x;
	}

	public String getName() {
		return this.companyName;
	}

	public HashMap<String, Integer> getTalentRankings() {
		return this.talentRankings;
	}

	public HashMap<Talent, Integer> getMarkedTalents() {
		return this.markedTalents;
	}




}
