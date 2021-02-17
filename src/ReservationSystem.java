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
		int personid = Integer.parseInt(in.nextLine().trim());
		Bus bus; Person person;
		person = people.get(personid-1);
		print(buses);//버스 이름 출력
		person.Mytickets();//본인 티켓 상황 출력 
		System.out.print("버스를 선택해주세요 >>");
		int busid = Integer.parseInt(in.nextLine().trim());
		bus = buses.get(busid-1);
		//만석 
		if(bus.getCount == bus.getSeats().length) {
			System.out.print("대기실에 입장하시겠습니까? y/n >>");
			if(in.nextLine().trim().equals("n")) bus.addtoqueue(person);
			else return;
		}
		//자리 선택으로 진
		else {
			if(person.getBalance() <= bus.getPrice()) {
				System.out.println("잔액이 부족합니다. ");
				return;
			}
			bus.print();
			System.out.print("좌석 번호를 선택해주세요: >> ");
			int seat = Integer.parseInt(in.nextLine().trim());
			if(bus.reserve(seat, person.getId())) person.AddBus(bus, seat);
			else System.out.println("예약 오류!");
		}
		
		
		
	}
	public void seeDetails(Scanner in) {
		int personid = Integer.parseInt(in.nextLine().trim());
		people.get(personid-1).MyTickets();
		
	}
	public void cancelReservation(Scanner in) {
		int personid = Integer.parseInt(in.nextLine().trim());
		Person person = people.get(personid-1);
		person.MyTickets();
		System.out.print("취소하실 버스를 선택해주세요 : >> ");
		int busid = Integer.parseInt(in.nextLine().trim());
		Integer seat = person.CancelBus(busId);
		if(seat != null) bus.get(busid-1).cancel(seat);
		else System.out.println("예약 취소 오류!");
	}
	/*HELPER METHODS*/
	public void print(List<Bus> l) {
		for (Bus o : l) {
			System.out.print(o.getId()+ ") "+o.getName()+ " ");
		}
	}

}
