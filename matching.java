import java.util.*;

public class SMP {

	HashMap<Talent, Company> matching;
	public Talent(int numCompanies) {
		int numCompaniesMatched = numCompanies;
		HashMap<Company, Integer> companyRankings = new HashMap<Company, Integer>();
		HashMap<Company, Integer> markedCompanies = new ArrayList<Company>();
	}
	public Company(int numSeats) {
		int numSeats = numSeats;
		HashMap<Talent, Integer> talentRankings = new HashMap<Talent, Integer>();
		HashMap<Talent, Integer> markedTalents = new ArrayList<Talent>();
	}

	// Generates fake data files for testing
	private void generateTalentData(String fileName) {

	}
	private void generateCompanyData(String fileName) {

	}

	// Return true if T is already matched to C
	private boolean matched(Talent t, Company c) {
		if matching.contains(t, c) {
			return true;
		}
		return false;
	}

	private void matchPair(Talent t, Company c) {
		// Create matching
		matching.add(T, c); 

		// Decrement Counters
		T.numCompanies--;
		c.numSeats--;

		// Mark both company and talent
		int cRank = talentRankings.get(c);
		int tRank = companyRankings.get(T);
		T.markedCompanies.add(c, cRank);
		c.markedCompanies.add(T, tRank);
	}

	// Remove matching between C and T 
	private void freeTalent(Company c, Talent t) {
		
	}

	// returns the Company C's ranking of Talent T
	private int talentWeight(Company c, Talent t) {
		return -1; // so it compiles
	}

	// Returns the top-ranked company not yet matched
	private Company topCompany(Talent t) {
		return null; // so it compiles
	} 

	// Returns the top-ranked student not yet matched
	private Talent topTalent(Company c) {
		return null; // so it compiles
	}

	// Returns the minimum weighted Talent in the list of marked Talents
	private Talent minTalent(Company c) {
		return null; // so it compiles
	}


	public static void main(String[] argv) {
		matching = new HashMap<Talent, Company>();
		ArrayList<Talent> talents = new ArrayList<Talent>();
		ArrayList<Companies> companies = new ArrayList<Companies>();
		for (Talent T : talents) { // Broken will fix later
			if (T.markedCompanies.size() != talentRankings.size()) {
				// Find highest ranked company of talent t
				c = topCompany(t);
				// if Company is free
				if (c.numSeats > 0) {
					matchPair(T, c);
				} 
				else {
					Talent minRanking = minTalent(c);
					if (talentWeight(c, T) > talentWeight(c, minRanking))
					{
						matchPair(c, T);
						freeTalent(c, minRanking);
					}
				}
			}
		}
	}
}