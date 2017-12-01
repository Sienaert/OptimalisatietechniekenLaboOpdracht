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

    public Problem(String csvFile) throws IOException {
        printer = new Printer();
        requestList = new ArrayList<>();
        zoneList = new ArrayList<>();
        carList = new ArrayList<>();

        String line;
        String cvsSplitBy = ";";

        // Make map of requestID and possiblecars to fix after
        List<String> carMap = new ArrayList<>();

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

        Zone z;
        for (Request q : requestList) {

            z = q.getZone();

            z.addRequest(q);

        }
        System.out.println(zoneList);

        // start optimising

        // --> simulated anealing

        // genereren eerste oplossing
        Solution currentSolution = new Solution();

        Solution randomSolution;
        Solution bestSolution = currentSolution;

        // T=T_max willekeurig gekozen
        int t = 5000;

        int iterations = 0;

        // willekeurig gekozen
        int maxIterations = 1000;

        int delta;
        double passChance;
        random = new Random(0);
        double randomNumber;


        Long start = System.currentTimeMillis();

        // willekeurig gekozen
        while (t > 1000) {

            while (iterations < maxIterations) {

                // generate random neighbour
                randomSolution = currentSolution.getNeighbour();

                delta = randomSolution.getCost() - currentSolution.getCost();
                if (delta > 0) {

                    currentSolution = randomSolution;
                    // doorgeven aan grafiek
                    allSolutions.add(currentSolution.getCost());
                    timeForSolution.add(System.currentTimeMillis());

                    // TODO oplossing doorgeven aan uitprintding
                    // code hier --> MOET blocking zijn anders problemen.

                    //Beste oplossing bijhouden
                    bestSolution = currentSolution;

                    System.out.println("New best solution: " + bestSolution.getCost());


                } else {
                    // acepteren met probabiliteit

                    passChance = Math.exp(((float) delta) / ((float) t));
                    randomNumber = random.nextDouble();

                    if (randomNumber >= passChance) {

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


        System.out.println(allSolutions);
        System.out.println(timeForSolution);

        try (PrintWriter out = new PrintWriter("graph.csv")) {
            out.println(sb.toString());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Best solution: " + bestSolution.toString());
        printer.GenerateOutput(bestSolution);



    }

	@Override
	public String toString() {
		return "Problem{\n" + "requestList=" + requestList + ", \nzoneList=" + zoneList + ", \ncarList=" + carList
				+ ", \ndays=" + days + '}';

	}
}
