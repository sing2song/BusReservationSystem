package main;
import java.util.*;

import db.ReservationDB;
import model.Bus;
import model.Person;
public class ReservationSystem {
	ReservationDB db;
	List<Bus> buses;
	List<Person> people;
	
	public ReservationSystem() throws Exception {
		buses = new ArrayList<Bus>();
		people = new ArrayList<Person>();
		db = new ReservationDB();
	}
	
	/*file io*/
	public void insertBus(String name, int price, int size) {
		try {
			String seats;
			char[] chars = new char[size];
			seats = new String(chars);
			Arrays.fill(chars, '0');
			if(!db.busDB.insert(name, price, seats, size)) throw new Exception();
		}
		catch(Exception e) {
			System.out.print("입력 오류가 있었습니다");
		}
	}
	/*1*/
	public void insertBus(Scanner in) {
		System.out.print("[버스이름 티켓값 좌석수]을 입력해주세요 : >> ");
		try {
			String[] arr = read(in).split(" ");
			int price, size;
			String name, seats;
			price = Integer.parseInt(arr[1]);
			size = Integer.parseInt(arr[2]);
			char[] chars = new char[size];
			seats = new String(chars);
			name = arr[0];
			Arrays.fill(chars, '0');
			if(!db.busDB.insert(name, price, seats, size)) throw new Exception();
		}
		catch(Exception e) {
			System.out.print("입력 오류가 있었습니다");
		}
		
	}
	/*file io*/
	public void insertPerson(String name, int balance) {
		try {
			if(!db.personDB.insert(name, balance)) throw new Exception();
		}
		catch(Exception e) {
			System.out.print("입력 오류가 있었습니다");
		}
	}
	/*2*/
	public void insertPerson(Scanner in) {
		//name, balance, 
		System.out.print("[이름 잔액]을 입력해주세요 : >> ");
		try {
			String[] arr = read(in).split(" ");
			String name = arr[0];
			int balance = Integer.parseInt(arr[1]);
			if(!db.personDB.insert(name, balance)) throw new Exception();
		}
		catch(Exception e) {
			System.out.print("입력 오류가 있었습니다");
		}
		
	}
	/*3*/
	public void makeReservation(Scanner in) {
		Bus bus; Person person;
		try {
			person = getPerson(in);//db에서 person 객체로 불러오기
			printBuses();//db 에서 select all buses + 출력
			person.print();//본인 티켓/대기 상황 출력 
			bus = getBus(in);
			//중복 예약/대 허락....
			if(bus.isFull()) {//대기하기 
				System.out.print("대기실에 입장하시겠습니까? y/n >> ");
				if(read(in).equals("y")) db.queueDB.insert(person.getId(), bus.getBusId());
				else System.out.println("메뉴로 돌아갑니다.");
				return;
			}
			//예약 진행 
			//잔액
			if(person.getBalance() < bus.getPrice()) {
				System.out.println("잔액이 부족합니다. ");
				return;
			} 
			bus.PrintSeats();//좌석 출력
			//좌석 선택 
			System.out.print("좌석 번호를 선택해주세요: >> ");
			try {
				int seat = Integer.parseInt(read(in));
				if(db.ticketDB.insert(person.getId(), bus, seat-1)) {
					System.out.printf("[%d] %s 버스, %d 좌석 예약 성공!\n", bus.getBusId(), bus.getName(), seat);
				}
				else throw new Exception("");
			}
			catch (Exception e) {throw new Exception("잘못된 자리 선택입니다.");}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		/********************************************************/
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
			Integer seat = person.CancelTicket(bus);//key, value삭제, 잔액 더하기
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
	public Person getPerson(Scanner in) throws Exception {
		try {
			System.out.print("id 를 입력해주세요 : >> ");
			int personid = Integer.parseInt(read(in));
			if(!this.db.personDB.exists(personid)) throw new Exception();
			Person p = this.db.personDB.select(personid);
			p.setQueues(this.db.queueDB.selectByPerson(personid));//get my queues
			p.setTickets(this.db.ticketDB.select(personid));//get my tickets
			return p;
		}
		catch(Exception e) {
			throw new Exception("존재하지 않는 id입니다.");
		}
	}
	public Bus getBus(Scanner in) throws Exception {
		try {
			System.out.print("버스 id 를 입력해주세요 : >> ");
			int busid = Integer.parseInt(read(in));
			if(!this.db.busDB.exists(busid)) throw new Exception();
			return this.db.busDB.select(busid);
		}
		catch(Exception e) {
			throw new Exception("존재하지 않는 버스 id 입니다.");
		}
	}
	
	public void printBuses() {
		ArrayList<Bus> buses = db.busDB.selectAll();
		for (Bus b : buses) {
			b.PrintBus();
		}
	}
	public String read(Scanner in) {
		return in.nextLine().trim();
	}


}
