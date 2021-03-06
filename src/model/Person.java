package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Person {
	private int id;//0이상이여야한다
	private String name;
	private int balance;
	private ArrayList<Object[]> tickets; //	 Object[] : ticketid, busid, name, seat
	private ArrayList<Object[]>queues; // Object[] : { queue id, busid, busname}
	//한사람이 한 자리 예약할 수 있다는 가정

	//생성자
	public Person(int id, String name, int balance) {
		this.id=id;
		this.name=name;
		this.balance=balance;
		tickets = new ArrayList<Object[]>();
		queues = new ArrayList<Object[]>();
	}
	
	//getter
	public int getId() {return id;}
	public String getName() {return name;}
	public int getBalance() {return balance;}
	public ArrayList<Object[]> getTickets() {return tickets;}
	public ArrayList<Object[]> getQueues() {return this.queues;}
	public void setQueues(ArrayList<Object[]> q) {this.queues = q;}
	public void setTickets(ArrayList<Object[]> t) { this.tickets = t;}
	public void print() {
		System.out.println("-----------------------------------------------------------------");
		System.out.println("ID: "+ this.id+"\t이름: " + this.name + "\t[잔액] : " + this.balance);
		MyTickets();
		MyQueues();
		System.out.println("-----------------------------------------------------------------");
	}
	//기능메서드	  
	//Object[] : ticketid, busid, name, seat, date
	public void MyTickets() {
		if(this.tickets.size() == 0) {
			System.out.println("-구매한 티켓이 없습니다-");
			return;
		}
		System.out.println("-구매한 티켓- ");
		for( Object[] o : this.tickets) System.out.printf("> [%d] 버스ID: %d, 버스이름: %s, 좌석번호: %d \t\t %s\n",
				o[0], (int) o[1], o[2], o[3], o[4]);
	}
	// Object[] : { queue id, busid, busname}
	public void MyQueues() {
		if(this.queues.size() == 0) {
			System.out.println("-대가중인 버스가 없습니다-");
			return;
		}
		System.out.println("-대기중인 버스-");
		for( Object[] o : this.queues) System.out.printf("> [%d], 버스ID: %d, 버스이름: %s\n",
				o[0], (int) o[1], o[2]);
		System.out.println();
	}
	
}
