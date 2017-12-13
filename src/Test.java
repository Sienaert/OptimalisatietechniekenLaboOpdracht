import java.io.IOException;
import java.util.List;

public class Test {

	public static void main(String[] args) throws IOException {
		Problem problem = null;

		String[] problemList = {"1_100_7_10_25.csv", "2_210_3_40_25.csv", "3_300_6_40_25.csv",
				"gent9zones648requestsDif.csv","Hasselt10zones716requestsDif.csv","gent9zones1108requestsDif.csv","gent34zones1108requestsDif.csv"};

		try {
			//problem = new Problem("toy1.csv");
			problem = new Problem(problemList[6], "solution.csv", 0, 10, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		problem.solve();


	}

}
