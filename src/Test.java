import java.io.IOException;

public class Test {

	public static void main(String[] args) {
		Problem problem = null;

		try {
			problem = new Problem("toy1.csv");
			
			problem.solve();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(problem.toString());

	}

}
