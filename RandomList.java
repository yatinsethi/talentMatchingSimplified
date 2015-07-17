import java.util.*;

public class RandomList {

	public static void main(String[] argv) {
		String [] companies= {"Google", "McKinsey", "Volvo" , "H&M","Scania","Acando","L'Oreal","Nordea","Schibsted","Microsoft"};
		Random rand = new Random();
		for (int j = 0; j < 30; j++) {
			for (int i = 0; i < companies.length; i++) {
				int x = rand.nextInt(10);
				if (i < companies.length - 1) {
					System.out.print(companies[x] + " ");
				}
				else {
					System.out.println(companies[x]);
				}
			}
			System.out.println();
		}
	}
}