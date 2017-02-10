package application;


import java.util.ArrayList;

public class Building {

	private String name; 
	private String id; 
	private ArrayList<String> rooms = new ArrayList<String>();
	
	public Building(String name, String id, ArrayList<String> rooms){
		this.name = name; 
		this.id = id; 
		this.rooms = rooms; 
	}
	@Override 
	public String toString(){
		return name; 
	}
	public void setName(String name){
		this.name = name; 
	}
	public void addRoom(String roomnr){
		rooms.add(roomnr);
	}
	public String getName()
	{
		return name;
	}
	public void setID(String id){
		this.id = id; 
	}
	public String getID(){
		return id; 
	}
	public ArrayList<String> getRooms()
	{
		return rooms; 
	}
}

