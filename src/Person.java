import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Person {
	private int id;//0이상이여야한다
	private String name;
	private int balance;
	private Map<Integer, Integer> tickets; //버스ID, 좌석
	private Set<Integer> queuedBuses;
	//한사람이 한 자리 예약할 수 있다는 가정

	//생성자
	public Person(int id, String name, int balance) {
		this.id=id;
		this.name=name;
		this.balance=balance;
		tickets = new HashMap<Integer, Integer>();
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
	public void addQueueBuses(int busid) {
		this.queuedBuses.add(busid);
	}
	public void removeQueueBuses(int busid) {
		this.queuedBuses.remove(busid);
	}
	
	public void MyTickets() {
		System.out.println("[잔액] : " + this.balance);
		if(!tickets.isEmpty()) {
			System.out.print("[구매한 티켓] ");
			for( int val :tickets.keySet()) {
				System.out.println("버스번호 : "+val+" 좌석번호 : "+tickets.get(val));
			}
		}
	}
	
}
