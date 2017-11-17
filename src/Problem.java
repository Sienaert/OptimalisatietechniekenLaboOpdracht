import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.*;

public class Problem {

    private List<Request> requestList;
    private List<Zone> zoneList;
    private List<Car> carList;
    private int days;

    private Problem(List<Request> requestList, List<Zone> zoneList, List<Car> carList, int days) {
        this.requestList = requestList;
        this.zoneList = zoneList;
        this.carList = carList;
        this.days = days;
    }

    public Problem loadCSV(String csvFile) throws IOException{
        String line;
        String cvsSplitBy = ";";

        //Make map of requestID and possiblecars to fix after
        Map<Integer, List<String>> carMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            //Read Requests
            int numberOfRequests = Integer.parseInt(br.readLine().split(":")[1]);

            for(int requestId = 0; requestId < numberOfRequests ; requestId++ ){
                line = br.readLine();
                String[] request = line.split(cvsSplitBy);

                //Fix links
                List<String> possibleCars = new ArrayList<>();
                for(int i = 5; i < request.length-3 ; i++) possibleCars.add(request[i]);
                carMap.put(requestId, possibleCars);

                requestList.add(new Request(requestId, Integer.parseInt(request[1]), Integer.parseInt(request[2]), Integer.parseInt(request[3]), Integer.parseInt(request[4]), Integer.parseInt(request[request.length-2]), Integer.parseInt(request[request.length-1])));
            }

            //Read Zones
            int numberOfZones = Integer.parseInt(br.readLine().split(":")[1]);

            for(int zoneId = 0; zoneId < numberOfZones ; zoneId++ ){
                zoneList.add(new Zone(zoneId));
            }

            for(Zone zone : zoneList){
                line = br.readLine();
                String[] zoneStr = line.split(cvsSplitBy);

                List<Zone> adjacentZones = new ArrayList<>();

                for(int i=1; i<zoneStr.length; i++){
                    adjacentZones.add(zoneList.get(i));
                }

                zone.setAdjacentZones(adjacentZones);
            }

            //Read Cars
            int numberOfCars = Integer.parseInt(br.readLine().split(":")[1]);

            for(int carId = 0 ; carId < numberOfCars ; carId++) carList.add(new Car(carId));

            //Read Days
            days = Integer.parseInt(br.readLine().split(":")[1]);

            //Fix car links in requests
            for(Request request : requestList){
                List<Car> localCarList = new ArrayList<>();
                List<String> possibleCars = carMap.get(request.getRequestId());
                for(String carString : possibleCars){
                    localCarList.add(carList.get(Integer.parseInt(carString.substring(carString.length()-1))));
                }
                request.setPossibleVehicleTypes(localCarList);
            }

        }


        return new Problem(requestList, zoneList, carList, days);

    }
}
