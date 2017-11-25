import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.*;

public class Problem {

	public static List<Request> requestList;
	public static List<Zone> zoneList;
	public static List<Car> carList;
	public static int days;

	public static Random random;

	public Problem(String csvFile) throws IOException {
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

				Request newRequest = new Request(requestId, Integer.parseInt(request[2]), Integer.parseInt(request[3]),
						Integer.parseInt(request[4]), Integer.parseInt(request[request.length - 2]),
						Integer.parseInt(request[request.length - 1]));
				requestList.add(newRequest);
			}

			// Read Zones
			int numberOfZones = Integer.parseInt(br.readLine().split(" ")[1]);

			for (int zoneId = 0; zoneId < numberOfZones; zoneId++) {
				zoneList.add(new Zone(zoneId));
			}

			for (Zone zone : zoneList) {
				line = br.readLine();
				String[] zoneStr = line.split(cvsSplitBy);

				List<Zone> adjacentZones = new ArrayList<>();

				for (int i = 1; i < zoneStr.length; i++) {
					adjacentZones.add(zoneList.get(i));
				}

				zone.setAdjacentZones(adjacentZones);
			}

			// Read Cars
			int numberOfCars = Integer.parseInt(br.readLine().split(" ")[1]);

			for (int i = 0; i < numberOfCars; i++) {
				line = br.readLine();
				carList.add(new Car(Integer.parseInt(line.substring(line.length() - 1))));
			}

			// Read Days
			days = Integer.parseInt(br.readLine().split(" ")[1]);

			// Fix car links in requests
			for (Request request : requestList) {
				List<Car> localCarList = new ArrayList<>();
				String possibleCars = carMap.get(request.getRequestId());
				String[] cars = possibleCars.split(",");
				for (String carString : cars) {
					localCarList.add(carList.get(Integer.parseInt(carString.substring(carString.length() - 1))));
				}
				request.setPossibleVehicleTypes(localCarList);

				// Fix zoneID in request
				request.setZone(zoneList.get(zoneIDs[requestList.indexOf(request)]));
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

	public void solve() {

		// adding request to zones
		Map<Integer, Zone> zones = new <Integer, Zone>HashMap();
		for (Zone z : zoneList) {

			zones.put(z.getZoneId(), z);

		}

		Zone z;
		for (Request q : requestList) {

			z = q.getZone();

			z.addRequest(q);

		}
		System.out.println(zoneList);

		// start optimising

		// --> simulated anealing

		// genereren eerste oplossing
		Solution s = new Solution();
		
		Solution randomSolution;

		// T=T_max willekeurig gekozen
		int t = 5000;

		int iterations = 0;

		// willekeurig gekozen
		int maxIterations = 1000;

		int delta;
		double passChance;
		random = new Random(0);
		double randomNumber;
		List<Solution> allSolutions = new ArrayList();

		// willekeurig gekozen
		while (t > 1000) {

			while (iterations < maxIterations) {

				// generate random neighbour
				randomSolution = s.getNeighbour();

				delta = randomSolution.getCost() - s.getCost();
				if (delta < 0) {

					s = randomSolution;
					// doorgeven aan grafiek
					allSolutions.add(s);

					// TODO oplossing doorgeven aan uitprintding
					// code hier --> MOET blocking zijn anders problemen.
					
					
					

				} else {
					// acepteren met probabiliteit

					passChance = Math.exp(((float) delta) / ((float) t));
					randomNumber = random.nextDouble();

					if (randomNumber >= passChance) {

						s = randomSolution;

						// doorgeven aan grafiek
						allSolutions.add(s);

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

	}

	@Override
	public String toString() {
		return "Problem{\n" + "requestList=" + requestList + ", \nzoneList=" + zoneList + ", \ncarList=" + carList
				+ ", \ndays=" + days + '}';

	}
}
