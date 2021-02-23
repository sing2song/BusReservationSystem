import java.util.*;

import db.ReservationDB;
public class ReservationSystem {
	ReservationDB db;
	List<Bus> buses;
	List<Person> people;
	
	public ReservationSystem() throws Exception {
		buses = new ArrayList<Bus>();
		people = new ArrayList<Person>();
		db = new ReservationDB();
	}
	/*아이디 유효성체크*/
	public boolean checkId(int id) {
		return id<=people.size();
	}
	
	/*file io*/
	public void insertBus(String name, int price, int size) {
		//name, price, size
		buses.add(new Bus(buses.size()+1, name, price, size));
	}
	/*1*/
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
	/*file io*/
	public void insertPerson(String name, int balance) {
		//name, balance, 
		people.add(new Person(people.size()+1, name, balance));
	}
	/*2*/
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
	/*3*/
	public void makeReservation(Scanner in) {
		Bus bus; Person person;
		System.out.print("id 를 입력해주세요 : >> ");
		person = getPerson(in);
		if(person == null) return;
		printBuses(buses);//버스 이름 출력
		person.MyTickets();//본인 티켓 상황 출력 
		System.out.print("버스를 선택해주세요 >> ");
		//bus id validity check
		bus = getBus(in);
		if(bus == null) return;
		//중복 
		if(person.hasBus(bus.getBusId())) {
			System.out.printf("중복 예약입니다.");
			return;
		}
		else if(bus.hasPerson(person)) {
			System.out.println("이미 대기 중 입니다. ");
			return;
		}
		//만석 
		if(bus.isFull()) {
			System.out.print("대기실에 입장하시겠습니까? y/n >> ");
			if(read(in).equals("y")) {
				bus.AddToQueue(person);
				person.addQueueBuses(bus);
			}
			return;
		}
		//잔액
		if(person.getBalance() < bus.getPrice()) {
			System.out.println("잔액이 부족합니다. ");
			return;
		}
		bus.PrintSeats();
		System.out.print("좌석 번호를 선택해주세요: >> ");
		try {
			int seat = Integer.parseInt(read(in));
			if(bus.Reserve(seat-1, person.getId())) {//bus.seats[i-1] --> i: 좌석번호 
				person.AddBus(bus, seat);
				System.out.printf("[%d] %s 버스, %d 좌석 예약 성공!\n", bus.getBusId(), bus.getName(), seat);
			}
			else throw new Exception();
		}
		catch (Exception e) {System.out.println("예약 오류!");}
	}
	/*4*/
	public void seeDetails(Scanner in) {
		System.out.print("id 를 입력해주세요 : >> ");
		Person person = getPerson(in);
		if(person == null) return;
		System.out.println("*******************************************");
		person.MyTickets();
		person.MyQueue();
		System.out.println("*******************************************");
	}
	/*5*/
	public void cancelReservation(Scanner in) {
		System.out.print("id 를 입력해주세요 : >> ");
		Person person = getPerson(in);
		if(person == null) return;
		if(person.getTickets().size() == 0) {
			System.out.println("구매한 티켓이 없습니다 ");
			return;
		}
		person.MyTickets();//예약된 티켓 출력 
		System.out.print("취소하실 버스를 선택해주세요 : >> ");
		try {
			Bus bus = getBus(in);
			Integer seat = person.CancelBus(bus);//key, value삭제, 잔액 더하기
			bus.Cancel(seat-1);//버스에 삭제 
			System.out.printf("[%d] %s, 좌석번호: %d 취소 성공했습다\n", bus.getBusId(), bus.getName(), seat);
		}
		catch(Exception e) {System.out.println("예약 취소 오류!");}
	}
	/*6*/
	public void cancelBusQueue(Scanner in) {
		System.out.print("id 를 입력해주세요 : >> ");
		Person person = getPerson(in);
		if(person == null) return;
		if(person.getQueuedBuses().size() == 0) {
			System.out.println("대기중인 버스가 없습니다 ");
			return;
		}
		person.MyQueue();
		System.out.print("대기 취소하실 버스를 선택해주세요 : >> ");
		try {
			Bus bus = getBus(in);
			person.removeQueueBuses(bus);
			bus.RemoveFromQueue(person);
		}
		catch(Exception e) { System.out.println("대기 취소 오류!");}
		
		
	}
	/*HELPER METHODS*/
	public Person getPerson(Scanner in) {
		try {
			int personid = Integer.parseInt(read(in));
			return this.people.get(personid-1);
		}
		catch(Exception e) {
			System.out.println("존재하지 않는 id입니다.");
			return null;
		}
	}
	public Bus getBus(Scanner in) {
		try {
			int busid = Integer.parseInt(read(in));
			return this.buses.get(busid-1);
		}
		catch(Exception e) {
			System.out.println("존재하지 않는 버스 입니다.");
			return null;
		}
	}
	public void printBuses(List<Bus> l) {
		for (Bus o : l) {
			o.PrintBus();
		}
	}
	public String read(Scanner in) {
		return in.nextLine().trim();
	}


}
