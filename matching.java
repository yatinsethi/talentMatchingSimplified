import java.util.*;

public class SMP {

	HashMap<Talent, ArrayList<Company>> matching;
	int totalSeatsTaken;

	public Talent(int numCompanies) {
		int numCompaniesMatched = numCompanies;
		String talentName = "";
		HashMap<Company, Integer> companyRankings = new HashMap<Company, Integer>();
		HashMap<Company, Integer> markedCompanies = new HashMap<Company, Integer>();
	}
	public Company(int numSeats) {
		int numSeats = numSeats;
		String companyName = "";
		HashMap<Talent, Integer> talentRankings = new HashMap<Talent, Integer>();
		HashMap<Talent, Integer> markedTalents = new HashMap<Talent, Integer>();
	}

	// Generates fake data files for testing
	private void generateTalentData(String fileName) {

	}
	private void generateCompanyData(String fileName) {

	}

	// Return true if T is already matched to C
	private boolean matched(Talent t, Company c) {
		if (matching.contains(t, c)) {
			return true;
		}
		return false;
	}

	// Takes Talent t and Company c and matches them by adding them to matching
	// Marks the talents and companies
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
		int tRank = companyRankings.get(t);
		t.markedCompanies.add(c, cRank);
		c.markedTalents.addtT, tRank);
	}

	// Remove matching between C and T 
	private void freeTalent(Company c, Talent t) {
		// Finds and removes proper matching
		ArrayList<Talent> companyList = matching.get(t);
		for (Company C : companyList) {
			if (c.equals(C)) {
				companyList.remove(C);
			}
		}
		matchings.put(t, companyList);

		// Increment Counters
		t.numCompanies++;
		c.numSeats++;

		// Unmark both company and talent
		t.markedCompanies.remove(c);
		c.markedTalents.remove(t);
	}

	// returns the Company C's ranking of Talent T
	private int talentWeight(Company c, Talent t) {
		if (c.talentRankings.contains(t)) {
			for (Talent T : c.talentRankings) {
				if (T.equals(t)) {
					return c.talentRankings.get(t);
				}
			}
		}
		return -1;
	}

	// Returns the top-ranked company not yet matched
	private Company topCompany(Talent t) {
		Company topCompany;
		int max = -1;

		for (Company C : t.companyRankings) {
			if (!t.markedCompanies.containsKey(C)) {
				int val = t.companyRankings.get(C);
				if (val > max) {
					topCompany = C; 
					max = val;
				}
			}
		}

		return topCompany;
	} 

	// Returns the top-ranked student not yet matched
	private Talent topTalent(Company c) {
		Company topTalent;
		int max = -1;

		for (Talent T : c.talentRankings) {
			if (!c.markedTalents.containsKey(C)) {
				int val = c.talentRankings.get(C);
				if (val > max) {
					topTalent = T; 
					max = val;
				}
			}
		}

		return topTalent;
	}

	// Returns the minimum weighted Talent in the list of marked Talents
	private Talent minTalent(Company c) {
		Talent minTalent;
		int min = 1000;

		for (Talent T : c.markedTalents) {
			int val = c.markedTalents.get(T);
			if (val < min) {
				min = val;
				minTalent = T; 
			}
		}

		return minTalent;
	}


	public static void main(String[] argv) {
		// Initialize Global Variables
		totalSeatsTaken = 0;
		matching = new HashMap<Talent, ArrayList<Company>>();

		// Create ArrayList of Talents and Companies
		ArrayList<Talent> talents = new ArrayList<Talent>();
		ArrayList<Companies> companies = new ArrayList<Companies>();
		
		// Initialize talents and companies from file

		// Computes the maximum number of seats available
		int maxSeating = 0;
		for (Talent T: talents) {
			maxSeating += T.numCompaniesMatched;
		}

		// While there are still seats available
		while (totalSeatsTaken != maxSeating) {
			for (Talent T : talents) { // For each talent, see if valid matching
				if (T.markedCompanies.size() != T.companyRankings.size()) { // Checks if all companies are marked
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