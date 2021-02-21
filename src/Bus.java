import java.util.LinkedList;
import java.util.Queue;

public class Bus {

	private int busId;
	private int price;
	private String name;
	private Queue<Person> queue;
	private int count;
	private int[] seats;
	
	//생성자
	public Bus(int busId, String name, int price,int size) {
		this.busId = busId;
		this.price = price;
		this.name = name;
		queue = new LinkedList<Person>();
		seats = new int[size];
		count = 0;
	}


//getter setter
	public int[] getSeats() {return seats;}
	public void setSeats(int[] seats) {this.seats = seats;}
	public int getBusId() {return busId;}
	public void setBusId(int busId) {this.busId = busId;}
	public int getPrice() {return price;}
	public void setPrice(int price) {this.price = price;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public Queue<Person> getQueue() {return queue;}
	public void setQueue(Queue<Person> queue) {this.queue = queue;}
	public int getCount() {return count;}
	public void setCount(int count) {this.count = count;}

	//메소드
	public void AddToQueue(Person p) {queue.add(p);}
	public void RemoveFromQueue(Person p) {queue.remove(p);}
	public boolean hasPerson(Person p) {return queue.contains(p);}
	public boolean isFull() { return this.count >= this.seats.length;}
	public boolean Reserve(int seat, int PersonID) {
		if(seat>=seats.length) return false;
		if(seats[seat] == 0) {
			count++;
			seats[seat] = PersonID;
			return true;
		}
		else return false;
	}
	
	public int Cancel(int seat) {
		seats[seat] =0;
		if(queue.size() >0) {
			Person person = queue.remove();
			seats[seat] = person.getId();
			 person.AddBus(this, seat+1);
		}
		else count --;
		
		return seats[seat];
	}
	public void PrintSeats() {
		for (int i = 0 ; i < seats.length; i ++) {
			System.out.printf("[%d] ", i+1);
			if(seats[i] == 0) System.out.println("O");
			else System.out.println("X");
		}
	}
	public void PrintBus() {
		System.out.printf("[%d] ", busId);
		System.out.printf("%s ", name);
		System.out.print( count == seats.length ? "만석!": "" );
		System.out.print( queue.size() > 0 ? " 대기자들: " + this.queue.size() + " 명\n": "\n" );
		
	}

}
