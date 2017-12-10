import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Main {

	//jar moet algorithm.jar heten
    public static void main(String[] args){
    	Problem problem = null;

    	//<input_file> <solution_file> <random_seed> <time_limit> <num_threads>

    	String inputFileName=args[0];
    	String solutionFileName=args[1];
    	int randomSeed=Integer.parseInt(args[2]);
    	int timeLimitSeconds=Integer.parseInt(args[3]);
    	
    	//moet niet gebruikt worden
    	int maxAmountOfThreads=Integer.parseInt(args[4]);
    	
    	try {
		problem = new Problem(inputFileName,solutionFileName,randomSeed,timeLimitSeconds,maxAmountOfThreads);
		

		problem.solve();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

}
