import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Printer {
	//vehicleAssignments
	//assignedRequests
	//unassignedRequests

	String name;
	public Printer(String solutionFileName){
		name=solutionFileName;
	}
	
	public void GenerateOutput(Solution solution) throws IOException{
		solution.process();

		FileWriter fw = new FileWriter(name);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(solution.getCost());
		pw.println("+Vehicle assignments");
		for(Zone zone: solution.getZones()){
			for(Car car : zone.getCarList()){
				pw.println(car.getCarId()+";"+zone.getZoneId());
			}
		}
		pw.println("+Assigned requests");
		for(Zone zone: solution.getZones()){
			for(Request request : zone.getRequestList()){
				if(request.isAssigned() && !request.isRedirected()){
					pw.println(request.getRequestId()+";"+request.getCarID());
				}
			}
			for(Request request : zone.getRedirectedRequests()){
				pw.println(request.getRequestId()+";"+request.getCarID());
			}
		}
		pw.println("+Unassigned requests");
		for(Zone zone: solution.getZones()){
			for(Request request : zone.getRequestList()){
				if(!request.isAssigned() && !request.isRedirected()){
					pw.println(request.getRequestId());
				}
			}
		}
			
		pw.close();	
		}


		

	}

