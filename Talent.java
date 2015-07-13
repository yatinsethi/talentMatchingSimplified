import java.util.*;

public class Talent {

	// Talent Variables
	HashMap<String, Integer> companyRankings;
	HashMap<Company, Integer> markedCompanies;
	String talentName;
	int numCompanies; 

	public Talent(int numCompanies) {
		this.numCompanies = numCompanies;
		talentName = "";
		companyRankings = new HashMap<String, Integer>();
		markedCompanies = new HashMap<Company, Integer>();
	}

	public String getName() {
		return this.talentName;
	}

	public HashMap<String, Integer> getCompanyRankings() {
		return this.companyRankings;
	}

	public HashMap<Company, Integer> getMarkedCompanies() {
		return this.markedCompanies;
	}

	public int getNumCompanies() {
		return this.numCompanies;
	}
}

