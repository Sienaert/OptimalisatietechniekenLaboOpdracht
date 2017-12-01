import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		Problem problem = null;

		String[] problemList = {"1_100_7_10_25.csv", "2_210_3_40_25.csv", "3_300_6_40_25.csv"};

		try {
			problem = new Problem("toy1.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}

		problem.solve();


	}

}
