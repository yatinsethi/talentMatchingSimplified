import java.util.*;

public class SMP {

	HashMap<Talent, ArrayList<Company>> matching;
	int totalSeatsTaken;

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
		if (!matching.containsKey(t)) {
			matching.put(t, new ArrayList<Company>());
		}
		matching.get(t).add(c);

		// Decrement Counters
		t.numCompanies--;
		c.numSeats--;

		// Mark both company and talent
		int cRank = talentRankings.get(c);
		int tRank = companyRankings.get(T);
		t.markedCompanies.add(c, cRank);
		c.markedTalents.add(T, tRank);
	}

	// Remove matching between C and T 
	private void freeTalent(Company c, Talent t) {
		ArrayList<Talent> companyList = matching.get(t);
		for (Company C: companyList) {
			if (c.equals(C) {
				companyList.remove(C);
			}
		}
		matchings.put(t, companyList);
		t.numCompanies++;
		c.numSeats++;
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
		// Initialize Global Variables
		totalSeatsTaken = 0;
		matching = new HashMap<Talent, ArrayList<Company>>();

		// Create ArrayList of Talents and Companies
		ArrayList<Talent> talents = new ArrayList<Talent>();
		ArrayList<Companies> companies = new ArrayList<Companies>();
		
		// Computes the maximum number of seats available
		int maxSeating = 0;
		for (Company C: companies) {
			maxSeating += C.numSeats;
		}

		// While there are still seats available
		while (totalSeatsTaken != maxSeating) {
			for (Talent T : talents) { // For each talent, see if valid matching
				if (T.markedCompanies.size() != talentRankings.size()) {
					// Find highest ranked company of talent t
					c = topCompany(t);
					// if Company is free
					if (c.numSeats > 0 && T.numCompanies > 0) {
						matchPair(T, c);
						totalSeatsTaken++;
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
}