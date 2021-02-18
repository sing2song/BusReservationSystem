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
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getBalance() {
		return balance;
	}

	public Map<Integer, Integer> getTickets() {
		return tickets;
	}
	
	//기능메서드
	public boolean AddBus(Bus bus, int seat) {
			tickets.put(bus.getBusId(), seat);
			PayTicket(bus.getPrice());
			return true;
	}
	public boolean hasBus(int busid) {
		return this.tickets.containsKey(busid);
	}
	
	public int CancelBus(int busId) {
		return tickets.remove(busId);
	}
	
	public void PayTicket(int amount ) {
		balance-=amount;
	}
	public void addQueueBuses(Bus bus) {
		this.queuedBuses.add(bus);
	}
	public void removeQueueBuses(Bus bus) {
		this.queuedBuses.remove(bus);
	}
	
	public void MyTickets() {
		System.out.println("[잔액] : " + this.balance);
		if(!tickets.isEmpty()) {
			System.out.print("[구매한 티켓] ");
			for( int val :tickets.keySet()) {
				System.out.println("버스번호 : "+val+" 좌석번호 : "+tickets.get(val));
			}
		}
		System.out.println();
	}
	public void MyQueue() {
		if(this.queuedBuses.size() == 0) {
			System.out.print("대기중인 버스가 없습니다 ");
			return;
		}
		System.out.print("[대기중인버스] ");
		for (Bus b: this.queuedBuses) System.out.printf("[%d] %s\t", b.getBusId(), b.getName()); 
		System.out.println();
	}
	
}
