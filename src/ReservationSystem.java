import java.util.*;
public class ReservationSystem {
	List<Bus> buses;
	List<Person> people;
	public ReservationSystem() {
		buses = new ArrayList<Bus>();
		people = new ArrayList<Person>();
	}
	public void insertBus(Scanner in) {
		//name, price, size
		String[] arr = in.nextLine().trim().split(" ");
		buses.add(new Bus(buses.size()+1, arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2])));
	}
	public void insertPerson(Scanner in) {
		//name, balannce, 
		String[] arr = in.nextLine().trim().split(" ");
		people.add(people.size()+1, arr[0], Integer.parseInt(arr[1]));
	}
	public void makeReservation(Scanner in) {
		
	}
	public void seeDetails(Scanner in) {
		
	}
	public void cancelReservation(Scanner in) {
		
	}

}
