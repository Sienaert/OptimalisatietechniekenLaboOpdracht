import java.util.ArrayList;

public class test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Zone z=new Zone("0"); 
		
		Car car1=new Car("1");
		Car car2=new Car("2");
		Car car3=new Car("3");
		
		//String requestId, int dayIndex, int startTime, int duration, int penalty1, int penalty2
		//req0;z0;0;1072;127;car2,car3,car0,car1,car4;100;20
		Request request1=new Request("1", 0, 1072, 127, 100, 20);
		
		ArrayList <Car> cars=new ArrayList<Car>();
		cars.add(car1);
		cars.add(car2);
		cars.add(car3);
		request1.setPossibleVehicleTypes(cars);
		
		
		//req5;z0;1;763;265;car2,car3,car0,car1,car4;100;20
		
		Request request3=new Request("3", 0, 763, 265, 100, 20);
		
		request3.setPossibleVehicleTypes(cars);
		
		//req3;z0;0;885;314;car1;100;20
		
		Request request2=new Request("2", 0, 885, 314, 100, 20);
		cars=new ArrayList<Car>();
		cars.add(car2);
		request2.setPossibleVehicleTypes(cars);
		
		//add to zone
		z.addCar(car1);
		z.addCar(car2);
		z.addCar(car3);
		z.addRequest(request1);
		z.addRequest(request2);
		z.addRequest(request3);
		
		System.out.println(z);
		System.out.println("----------------------------");
		z.handleRequests();
		System.out.println(z.getLatestCost());
		System.out.println("----------------------------");
		System.out.println(z);
		
	}

}
