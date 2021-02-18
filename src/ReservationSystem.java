import java.util.*;
public class ReservationSystem {
	List<Bus> buses;
	List<Person> people;
	public ReservationSystem() {
		buses = new ArrayList<Bus>();
		people = new ArrayList<Person>();
	}
	/*아이디 유효성체크*/
	public boolean checkId(int id) {
		return id<=people.size();
	}
	
	/*1*/
	public void insertBus(String name, int price, int size) {
		//name, price, size
		buses.add(new Bus(buses.size()+1, name, price, size));
	}
	/*2*/
	public void insertBus(Scanner in) {
		//name, price, size
		System.out.print("[버스이름 티켓값 좌석수]을 입력해주세요 : >> ");
		try {
			String[] arr = read(in).split(" ");
			buses.add(new Bus(buses.size()+1, arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2])));
		}
		catch(Exception e) {
			System.out.print("입력 오류가 있었습니다");
		}
		
	}
	/*3*/
	public void insertPerson(String name, int balance) {
		//name, balance, 
		people.add(new Person(people.size()+1, name, balance));
	}
	/*4*/
	public void insertPerson(Scanner in) {
		//name, balance, 
		System.out.print("[이름 잔액]을 입력해주세요 : >> ");
		try {
			String[] arr = read(in).split(" ");
			people.add( new Person(people.size()+1, arr[0], Integer.parseInt(arr[1]) ) );	
		}
		catch(Exception e) {
			System.out.print("입력 오류가 있었습니다");
		}
		
	}
	/*5*/
	public void makeReservation(Scanner in) {
		Bus bus; Person person;
		System.out.print("id 를 입력해주세요 : >> ");
		int personid = Integer.parseInt(read(in));
		if(checkId(personid)) {
			person = people.get(personid-1);
			printBuses(buses);//버스 이름 출력
			person.MyTickets();//본인 티켓 상황 출력 
			System.out.print("버스를 선택해주세요 >> ");
			//bus id validity check 
			int busid;
			try {
				busid = Integer.parseInt(read(in));
				if(busid > buses.size()) throw new Exception();
			} catch(Exception e) {
				System.out.println("없는 버스 id 입니다 ");
				return;
			}
			bus = buses.get(busid-1);
			//중복 
			if(person.hasBus(busid)) {
				System.out.println("중복 예약입니다. ");
				return;
			}
			if(bus.hasPerson(person)) {
				System.out.println("이미 대기 중 입니다. ");
				return;
			}
			//만석 
			if(bus.getCount() == bus.getSeats().length) {
				System.out.print("대기실에 입장하시겠습니까? y/n >> ");
				if(read(in).equals("y")) bus.AddToQueue(person);
			}
			//자리 선택으로 진
			else {
				if(person.getBalance() <= bus.getPrice()) {
					System.out.println("잔액이 부족합니다. ");
					return;
				}
				bus.PrintSeats();
				System.out.print("좌석 번호를 선택해주세요: >> ");
				int seat = Integer.parseInt(read(in));
				if(bus.Reserve(seat-1, person.getId())) {
					person.AddBus(bus, seat);//bus = indexing
					System.out.println(bus.getBusId()+"버스 "+seat+"좌석 예약 성공! ");
				}
				else System.out.println("예약 오류!");

			}
		}
	}
	
	public void seeDetails(Scanner in) {
		System.out.print("id 를 입력해주세요 : >> ");
		int personid = Integer.parseInt(read(in));
		if(checkId(personid))
			people.get(personid-1).MyTickets();
		else System.out.println("존재하지 않는 id입니다.");
	}
	public void cancelReservation(Scanner in) {
		System.out.print("id 를 입력해주세요 : >> ");
		int personid = Integer.parseInt(read(in));
		if(checkId(personid)) {
			Person person = people.get(personid-1);
			person.MyTickets();
			System.out.print("취소하실 버스를 선택해주세요 : >> ");
			int busid = Integer.parseInt(read(in));
			Integer seat = person.CancelBus(busid);
			if(seat != null) buses.get(busid-1).Cancel(seat-1);
			else System.out.println("예약 취소 오류!");
		}else System.out.println("존재하지 않는 id입니다.");
	}
	/*HELPER METHODS*/
	public void printBuses(List<Bus> l) {
		for (Bus o : l) {
			o.PrintBus();
		}
	}
	public String read(Scanner in) {
		return in.nextLine().trim();
	}
	public void cancelBusQueue(Scanner sc) {
		// TODO Auto-generated method stub
		
	}

}
