package application;

public class Room {
	
	Building building; 
	String room; 
	
	public Room(Building building,String room){
		this.building = building; 
		this.room = room; 
	}
	public String getRoom(){
		return room; 
	}
	public void setRoom(String room){
		this.room = room; 
	}
	public void setBuilding(Building building){
		this.building = building; 
	}
	public Building getBuilding(){
		return building; 
	}
}
