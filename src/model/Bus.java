package model;
import java.util.LinkedList;
import java.util.Queue;

public class Bus {

	private int busId;
	private int price;
	private String name;
	private int count;
	private int size;
	private String seats;
	
	//생성자
	public Bus(int busId, String name, int price,int size) {
		this.busId = busId;
		this.price = price;
		this.name = name;
		this.size = size;
		//seats = new int[size];
		count = 0;
	}


//getter setter
	public String getSeats() {return seats;}
	public void setSeats(String seats) {this.seats = seats;}
	public int getBusId() {return busId;}
	public void setBusId(int busId) {this.busId = busId;}
	public int getPrice() {return price;}
	public void setPrice(int price) {this.price = price;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public int getCount() {return count;}
	public void setCount(int count) {this.count = count;}

	//메소드
	public boolean isFull() { return this.count >= this.size;}

	public void PrintSeats() {
		char[] chars = seats.toCharArray();
		for (int i = 0 ; i < chars.length; i ++) {
			System.out.printf("[%d] ", i+1);
			if(chars[i] == 0) System.out.println("O");
			else System.out.println("X");
		}
	}
	public void PrintBus() {
		System.out.printf("[%d] ", busId);
		System.out.printf("%s ", name);
		System.out.print( count == size ? "만석!\n": "\n" );		
	}

}
