package model;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Person {
	private int id;//0이상이여야한다
	private String name;
	private int balance;
	private Map<Integer, Integer> tickets; //버스ID, 좌석
	private Set<Bus> queuedBuses;
	//한사람이 한 자리 예약할 수 있다는 가정

	//생성자
	public Person(int id, String name, int balance) {
		this.id=id;
		this.name=name;
		this.balance=balance;
		tickets = new HashMap<Integer, Integer>();
		queuedBuses = new HashSet<Bus>();
	}
	
	//getter
	public int getId() {return id;}
	public String getName() {return name;}
	public int getBalance() {return balance;}
	public Map<Integer, Integer> getTickets() {return tickets;}
	public Set<Bus> getQueuedBuses() {return this.queuedBuses;}
	
	//기능메서드
	public boolean AddTicket(Bus bus, int seat) {
		tickets.put(bus.getBusId(), seat);
		this.balance -= bus.getPrice();
		return true;
	}
	public boolean hasTicket(int busid) {return this.tickets.containsKey(busid);}
	public int CancelTicket(Bus bus) {
		if(hasTicket(bus.getBusId())) this.balance += bus.getPrice();
		return tickets.remove(bus.getBusId());
	}
	
	public void addQueueBuses(Bus bus) {this.queuedBuses.add(bus);}
	public void removeQueueBuses(Bus bus) {this.queuedBuses.remove(bus);}
	
	public void MyTickets() {
		System.out.println("[잔액] : " + this.balance);
		if(!tickets.isEmpty()) {
			System.out.println("[구매한 티켓] ");
			for( int val :tickets.keySet()) System.out.printf("> [%d], 좌석번호: %d\n", val, tickets.get(val));
		}
	}
	public void MyQueue() {
		if(this.queuedBuses.size() == 0) return;
		System.out.print("[대기중인버스] : ");
		for (Bus b: this.queuedBuses) System.out.printf("[%d] %s\t", b.getBusId(), b.getName()); 
		System.out.println();
	}
	
}
