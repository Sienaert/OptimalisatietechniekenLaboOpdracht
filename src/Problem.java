import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.*;

public class Problem {

	public static List<Request> requestList;
	public static List<Zone> zoneList;
	public static List<Car> carList;
	public static int days;

	public static Random random;
	private Printer printer;
	private String solutionFileName;


	//"propere" constructor 
	//werkt waarschijnlijk niet meer correct-->moet nog aangepast worden
	public Problem(String inputFileName, String solutionFileName, int randomSeed, int timeLimitSeconds,int maxAmountOfThreads) throws IOException{
        printer = new Printer();
        this.solutionFileName=solutionFileName;
        
        requestList = new ArrayList<>();
        zoneList = new ArrayList<>();
        carList = new ArrayList<>();
        

        String line;
        String cvsSplitBy = ";";

        // Make map of requestID and possiblecars to fix after
        List<String> carMap = new ArrayList<>();

        // And for ZoneID..
        int[] zoneIDs;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {

            // Read Requests
            int numberOfRequests = Integer.parseInt(br.readLine().split(" ")[1]);

            zoneIDs = new int[numberOfRequests];

            for (int requestId = 0; requestId < numberOfRequests; requestId++) {
                line = br.readLine();
                String[] request = line.split(cvsSplitBy);

                // Fix zones
                int zoneId = Integer.parseInt(request[1].substring(1));
                zoneIDs[requestId] = zoneId;

                // Fix links
                String possibleCars = request[5];
                carMap.add(possibleCars);

                Request newRequest = new Request(request[0], Integer.parseInt(request[2]), Integer.parseInt(request[3]),
                        Integer.parseInt(request[4]), Integer.parseInt(request[request.length - 2]),
                        Integer.parseInt(request[request.length - 1]));
                requestList.add(newRequest);
            }

            // Read Zones
            int numberOfZones = Integer.parseInt(br.readLine().split(" ")[1]);

            for (int zoneId = 0; zoneId < numberOfZones; zoneId++) {
                line = br.readLine();
                String[] zoneStr = line.split(cvsSplitBy);


                zoneList.add(new Zone(zoneStr[0]));


                String[] adjacentString = zoneStr[1].split(",");

                for (String adjacentZone : adjacentString) {
                    zoneList.get(zoneId).addAdjacentZone(new Zone(adjacentZone));
                }
            }


            // Read Cars
            int numberOfCars = Integer.parseInt(br.readLine().split(" ")[1]);

            for (int i = 0; i < numberOfCars; i++) {
                line = br.readLine();
                carList.add(new Car(line));
            }

            // Read Days
            days = Integer.parseInt(br.readLine().split(" ")[1]);

            // Fix car links in requests
            for (Request request : requestList) {
                List<Car> localCarList = new ArrayList<>();
                String possibleCars = carMap.get(Integer.parseInt(request.getRequestId().substring(3)));
                String[] cars = possibleCars.split(",");
                for (String carString : cars) {
                    localCarList.add(carList.get(Integer.parseInt(carString.substring(carString.length() - 1))));
                }
                request.setPossibleVehicleTypes(localCarList);

                // Fix zoneID in request
                request.setZone(zoneList.get(zoneIDs[requestList.indexOf(request)]));
            }

        }

        //Place all requests in corresponding zone
        for(Zone zone : zoneList){
            for (Request request : requestList){
                if(request.getZone() == zone)
                    zone.addRequest(request);
            }
        }

    }
	
	
	
	
	
	
	//testConstructor

    public Problem(String csvFile) throws IOException {
        printer = new Printer();
        requestList = new ArrayList<>();
        zoneList = new ArrayList<>();
        carList = new ArrayList<>();

        String line;
        String cvsSplitBy = ";";

        // Make map of requestID and possiblecars to fix after
        Map<String,String> carMap = new HashMap<>();

        // And for ZoneID..
        int[] zoneIDs;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            // Read Requests
            int numberOfRequests = Integer.parseInt(br.readLine().split(" ")[1]);

            zoneIDs = new int[numberOfRequests];

            for (int requestId = 0; requestId < numberOfRequests; requestId++) {
                line = br.readLine();
                String[] request = line.split(cvsSplitBy);

                // Fix zones
                int zoneId = Integer.parseInt(request[1].substring(1));
                zoneIDs[requestId] = zoneId;

                // Fix links
   //==========================================================================================
                //fout hieronder
                String possibleCars = request[5];
                carMap.put(request[0],possibleCars);

                Request newRequest = new Request(request[0], Integer.parseInt(request[2]), Integer.parseInt(request[3]),
                        Integer.parseInt(request[4]), Integer.parseInt(request[request.length - 2]),
                        Integer.parseInt(request[request.length - 1]));
                requestList.add(newRequest);
            }

            // Read Zones
            int numberOfZones = Integer.parseInt(br.readLine().split(" ")[1]);

            for (int zoneId = 0; zoneId < numberOfZones; zoneId++) {
                line = br.readLine();
                String[] zoneStr = line.split(cvsSplitBy);


                zoneList.add(new Zone(zoneStr[0]));


                String[] adjacentString = zoneStr[1].split(",");

                for (String adjacentZone : adjacentString) {
                    zoneList.get(zoneId).addAdjacentZone(new Zone(adjacentZone));
                }
            }


            // Read Cars
            int numberOfCars = Integer.parseInt(br.readLine().split(" ")[1]);
            Map <String,Car>carListMap =new HashMap<>();
            for (int i = 0; i < numberOfCars; i++) {
                line = br.readLine();
                Car c=new Car(line);
                carList.add(c);
                carListMap.put(c.getCarId(), c);
            }

            // Read Days
            days = Integer.parseInt(br.readLine().split(" ")[1]);

            // Fix car links in requests
            //fout zit hierin
            for (Request request : requestList) {
                List<Car> localCarList = new ArrayList<>();
                String possibleCars = carMap.get(request.getRequestId());
                String[] cars = possibleCars.split(",");
                for (String carString : cars) {
                	//-->fout hieronder!
                    localCarList.add(carListMap.get(carString));
                }
                request.setPossibleVehicleTypes(localCarList);

                // Fix zoneID in request
                request.setZone(zoneList.get(zoneIDs[requestList.indexOf(request)]));
            }
            //===================================================
            //fout hierboven

        }

        //Place all requests in corresponding zone
        for(Zone zone : zoneList){
            for (Request request : requestList){
                if(request.getZone() == zone)
                    zone.addRequest(request);
            }
        }

    }

    
    
    public List<Request> getRequestList() {
        return requestList;
    }

    public List<Zone> getZoneList() {
        return zoneList;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public int getDays() {
        return days;

    }

    public void solve() throws IOException{
        List<Integer> allSolutions = new ArrayList<Integer>();
        List<Long> timeForSolution = new ArrayList<Long>();
        random = new Random(0);
        int counter = 0;

        // start optimising

        // --> simulated anealing

        // genereren eerste oplossing
        Solution currentSolution = new Solution();
        currentSolution.process();
        currentSolution.getCost();
        System.out.println("Initial solution costs: " + currentSolution.getCost());
        Solution bestSolution = currentSolution;
        // T=T_max willekeurig gekozen
        //int t=5000;
        int t = 5000;

        int iterations = 0;

        // willekeurig gekozen
        int maxIterations = 10;
        //int maxIterations = 1000;

        int delta;
        double passChance;
        double randomNumber;


        Long start = System.currentTimeMillis();

        // willekeurig gekozen
        //while (t > 1000) {
        while (t > 1000) {

            while (iterations < maxIterations) {

                // generate random neighbour
                Solution randomSolution = currentSolution.getNeighbour();
                
                
                randomSolution.process();
                delta = randomSolution.getCost() - currentSolution.getCost();
                //System.out.println("current:\n"+currentSolution);
                //System.out.println("random:\n"+randomSolution);
                //System.out.println("delta: "+delta);
                if (delta <= 0) {

                	//System.out.println("-better or equal cost");
                    currentSolution = randomSolution;
                    // doorgeven aan grafiek
                    allSolutions.add(currentSolution.getCost());
                    timeForSolution.add(System.currentTimeMillis());

                    // TODO oplossing doorgeven aan uitprintding
                    // code hier --> MOET blocking zijn anders problemen.

                    //Beste oplossing bijhouden
                    if (delta<0) {
                    	bestSolution = currentSolution;
                    	System.out.println("---Better solution cost: " + bestSolution.getCost());
                    }


                } else {
                    // acepteren met probabiliteit
                	//System.out.println("-worse cost");
                    passChance = Math.exp(-((float) delta) / ((float) t));
                    //System.out.println("-->"+passChance);
                    randomNumber = random.nextDouble();
                    
                    if (randomNumber <= passChance) {
                    	//System.out.println("passed");
                        currentSolution = randomSolution;

                        // doorgeven aan grafiek
                        allSolutions.add(currentSolution.getCost());
                        timeForSolution.add(System.currentTimeMillis());

                    }

                }


                iterations++;
            }

            // eventueel: t met functie laten dalen
            t--;
            iterations = 0;
            // eventueel: maxiterations kunnen we in functie van de temperatuur laten
            // dalen/stijgen

        }

        System.out.println("Best solution: " + bestSolution.toString());


        for (int i = 0; i < timeForSolution.size(); i++) {


            timeForSolution.set(i, timeForSolution.get(i) - start);


        }

        StringBuilder sb = new StringBuilder("");

        for (int i = 0; i < allSolutions.size(); i++) {

            sb.append(timeForSolution.get(i) + ";" + allSolutions.get(i) + "\n");


        }


       

        System.out.println("Best solution: " + bestSolution.toString());
        printer.GenerateOutput(bestSolution);
        
        
     
 System.out.println(allSolutions);
        System.out.println(timeForSolution);

        try (PrintWriter out = new PrintWriter("graph.csv")) {
            out.print(sb.toString());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

	@Override
	public String toString() {
		return "Problem{\n" + "requestList=" + requestList + ", \nzoneList=" + zoneList + ", \ncarList=" + carList
				+ ", \ndays=" + days + '}';

	}
}
