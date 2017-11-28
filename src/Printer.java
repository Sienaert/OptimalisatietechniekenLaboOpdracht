import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Printer {
	//vehicleAssignments
	//assignedRequests
	//unassignedRequests

	public Printer(){
		
	}
	
	public void GenerateOutput() throws IOException{
		FileWriter fw = new FileWriter("solution.csv");
		PrintWriter pw = new PrintWriter(fw);
		pw.println("total_objective_value");
		pw.println("+Vehicle assignments");

		
		pw.println("+Assigned requests");

		
		
		pw.println("+Unassigned requests");

			
		pw.close();	
		}


		

	}

