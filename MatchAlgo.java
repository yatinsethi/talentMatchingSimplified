import java.util.*;
import java.io.*;

public class MatchAlgo {
	
	ArrayList<Talent> talents;
	ArrayList<Company> companies;

	public MatchAlgo(String talentFileName, String companyFileName, int numCompanies, int numSeats, int companyCapacity) {
		BufferedReader br1 = null;
		BufferedReader br2 = null;
		String line = "";
		String csvSplitBy = ",";
		int lineIndex = 0;

		HashMap<String, ArrayList<String>> talentDict = new HashMap<String, ArrayList<String>>();
		try {
			br1 = new BufferedReader(new FileReader(talentFileName));
			while ((line = br1.readLine()) != null) {
				if (lineIndex > 0) {
					String[] talentRankings = line.split(csvSplitBy);
					if (!talentDict.containsKey(talentRankings[0])) {
						talentDict.put(talentRankings[0], new ArrayList<String>());
						for (int i = 1; i < talentRankings.length; i++)
						{
							talentDict.get(talentRankings[0]).add(talentRankings[i]);
						}
					}
				}
				lineIndex++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br1 != null) {
				try {
					br1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		lineIndex = 0;

		ArrayList<String> companyNames = new ArrayList<String>();
		HashMap<String, ArrayList<Integer>> weightsOfTalent = new HashMap<String, ArrayList<Integer>>();
		try {
			br2 = new BufferedReader(new FileReader(companyFileName));
			while ((line = br2.readLine()) != null) {
				if (lineIndex == 0) {
					String[] companyOrdering = line.split(csvSplitBy);
					for (int i = 1; i < companyOrdering.length; i++) {
						companyNames.add(companyOrdering[i]);
					}
				}
				else {
					String[] talentAndWeights = line.split(csvSplitBy);
					weightsOfTalent.put(talentAndWeights[0], new ArrayList<Integer>());
					for (int i = 1; i < talentAndWeights.length; i++) {
						weightsOfTalent.get(talentAndWeights[0]).add(Integer.parseInt(talentAndWeights[i]));
					}
				}
				lineIndex++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br2 != null) {
				try {
					br2.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		talents = generateTalentList(talentDict, companyNames, weightsOfTalent, numCompanies, companyCapacity);
		companies = generateCompanyList(talentDict, companyNames, weightsOfTalent, numSeats);

	}
	
	// Returns an ArrayList of Talent objects
	private ArrayList<Talent> generateTalentList(HashMap<String, ArrayList<String>> talentDict, ArrayList<String> companyNames, HashMap<String, ArrayList<Integer>> weightsOfTalent, int numCompanies, int companyCapacity) {
		ArrayList<Talent> talentList = new ArrayList<Talent>();
		int weightCounter = numCompanies;
		for (String s : talentDict.keySet()) {
			Talent newTalent = new Talent(companyCapacity);
			newTalent.talentName = s;
			for (String companyName : talentDict.get(s)) {
				if (!newTalent.companyRankings.containsKey(companyName)) {
					newTalent.companyRankings.put(companyName, weightCounter);
				}
				weightCounter--;
			}
			talentList.add(newTalent);
			weightCounter = numCompanies;
		}

		return talentList;
	}

	// Returns an ArrayList of Company objects
	private ArrayList<Company> generateCompanyList(HashMap<String, ArrayList<String>> talentDict, ArrayList<String> companyNames, HashMap<String, ArrayList<Integer>> weightsOfTalent, int numSeats) {
		ArrayList<Company> companyList = new ArrayList<Company>();
		for (String s : companyNames) {
			Company newCompany = new Company(numSeats);
			int indexOfCompany = companyNames.indexOf(s);
			newCompany.companyName = s;
			for (String j : weightsOfTalent.keySet()) {
				int talentWeight = weightsOfTalent.get(j).get(indexOfCompany);
				if (!newCompany.talentRankings.containsKey(j)) {
					newCompany.talentRankings.put(j, talentWeight);
				}
			}
			companyList.add(newCompany);
		}

		return companyList;
	}

	// Return true if T is already matched to C
	private boolean matched(Talent t, Company c, HashMap<Talent, ArrayList<Company>> talentMatching) { // checked
		if (talentMatching.containsKey(t))
			if (talentMatching.get(t).contains(c)) 
				return true;
		return false;
	}

	/* Takes Talent t and Company c and matches them by adding them to matching
	   After matching t to c, marks both the talent and company */
	private void matchPair(Talent t, Company c, HashMap<Talent, ArrayList<Company>> talentMatching, HashMap<Company, ArrayList<Talent>> companyMatching) { // checked
		// Create Talent-Company Matching
		if (!talentMatching.containsKey(t)) {
			talentMatching.put(t, new ArrayList<Company>());
		}
		talentMatching.get(t).add(c);

		// Create Company-Talent Matching
		if (!companyMatching.containsKey(c)) {
			companyMatching.put(c, new ArrayList<Talent>());
		}
		companyMatching.get(c).add(t);

		// Decrement Counters
		t.decrementNumCompanies();
		c.decrementNumSeats();

		// Mark both company and talent
		int cRank = c.getTalentRankings().get(t.getName());
		int tRank = t.getCompanyRankings().get(c.getName());
		t.markedCompanies.put(c, cRank);
		c.markedTalents.put(t, tRank);
	}

	// Remove matching between C and T 
	private void freeTalent(Company c, Talent t, HashMap<Talent, ArrayList<Company>> talentMatching, HashMap<Company, ArrayList<Talent>> companyMatching) { // checked
		// Finds and removes proper matching for Talent-Company matching
		ArrayList<Company> companyList = talentMatching.get(t);
		companyList.remove(c);
		talentMatching.put(t, companyList);

		// Removes the key from the list if the talent has no matchings
		if (talentMatching.get(t).isEmpty()) {
			talentMatching.remove(t);
		}

		// Finds and removes proper matching for Company-Talent matching
		ArrayList<Talent> talentList = companyMatching.get(c);
		talentList.remove(t);
		companyMatching.put(c, talentList);

		// Removes the key from the list if the talent has no matchings
		if (companyMatching.get(c).isEmpty()) {
			companyMatching.remove(c);
		}

		// Increment Counters
		t.incrementNumCompanies();
		c.incrementNumSeats();

		// Unmark both company and talent
		t.markedCompanies.remove(c);
		c.markedTalents.remove(t);
	}

	// Returns the top-ranked company not yet matched
	private Company topCompany(Talent t, HashMap<Talent, ArrayList<Company>> talentMatching, ArrayList<Company> companies) { // checked
		Company topCompany = null;
		int max = -1;

		for (String S : t.getCompanyRankings().keySet()) {

			Company C = null;
			for (Company temp : companies) {
				if (S.equals(temp.getName())) 
					C = temp;
			}
			if (!matched(t, C, talentMatching)) {
				int val = t.getCompanyRankings().get(C.getName());
				if (val > max) {
					topCompany = C; 
					max = val;
				}
			}
		}	
		return topCompany;
	} 

	// returns the Company C's ranking of Talent T
	private int talentWeight(Company c, Talent t) { // checked
		if (c.getTalentRankings().containsKey(t.getName())) {
			for (String T : c.getTalentRankings().keySet()) {
				if (T.equals(t.getName())) {
					return c.getTalentRankings().get(t.getName());
				}
			}
		}
		return -1;
	}

	// Returns the minimum weighted Talent in the list of marked Talents
	private Talent minMarkedTalent(Company c) { // checked
		Talent minTalent = null;
		int min = 1000;

		for (Talent T : c.markedTalents.keySet()) {
			int val = c.markedTalents.get(T);
			if (val < min) {
				min = val;
				minTalent = T; 
			}
		}

		return minTalent;
	}

	public static void main(String[] argv) {
		String talentFileName = argv[0];
		String companyFileName = argv[1];
		int numCompanies = Integer.parseInt(argv[2]);
		int numAttendees = Integer.parseInt(argv[3]);
		int numSessions = Integer.parseInt(argv[4]);

		int aveSeatPerSession = numAttendees/numCompanies;

		MatchAlgo match = new MatchAlgo(talentFileName, companyFileName, numCompanies, aveSeatPerSession, numSessions);
		
		// Reads in CSV files and generates list of Companies and Talents
		ArrayList<Company> companies = match.companies;
		ArrayList<Talent> talents = match.talents;

		// Initialize Global Variables
		int totalSeatsTaken = 0;

		// Matching results for Talent-Company and Company-Talent
		HashMap<Talent, ArrayList<Company>> talentMatching = new HashMap<Talent, ArrayList<Company>>();
		HashMap<Company, ArrayList<Talent>> companyMatching = new HashMap<Company, ArrayList<Talent>>();
		
		int seatsPerSession = numAttendees;
		int maxSeats = numAttendees * numSessions;

		for (Talent T : talents) {
			T.setNumCompanies(1);
		}

		for (int i = 0; i < numSessions; i++) {
			while (totalSeatsTaken != seatsPerSession) {
				for (Talent T : talents) { 

					// Find highest ranked company of talent t
					Company c = match.topCompany(T, talentMatching, companies);

					// if Company is free 
					if (c.getNumSeats() > 0) {
						match.matchPair(T, c, talentMatching, companyMatching);
						totalSeatsTaken++;
					} 
					else {
						// if minimum talent in marked list has weight less than current talent
						Talent minTalent = match.minMarkedTalent(c);
						if (match.talentWeight(c, T) > match.talentWeight(c, minTalent))
						{
							match.freeTalent(c, minTalent, talentMatching, companyMatching);
							match.matchPair(T, c, talentMatching, companyMatching);
						}
					}
				}
			}
			for (Company C : companies) {
				C.setNumSeats(aveSeatPerSession);
			}
			for (Talent T : talents) {
				T.setNumCompanies(1);
			}
			totalSeatsTaken = 0;
			// System.out.println(talentMatching);
			// System.out.println(companyMatching);

		}
	}
}

