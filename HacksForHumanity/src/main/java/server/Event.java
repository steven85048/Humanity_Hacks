package server;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class Event {
	
	@Id
	private String id;
	
	private String eventName, eventDescription, location;
	private ClientProfile sender;
	private ArrayList<ClientProfile> listOfParticipants;
	
	public Event() {}
	
	public Event(String eventName, String eventDesc, String location, ClientProfile sender) {
		this.eventName = eventName;
		this.eventDescription = eventDesc;
		this.location = location;
		this.sender = sender;
		this.listOfParticipants = new ArrayList<ClientProfile>();
	}
	
	public Event(String eventName, String location, ClientProfile sender) {
		this.eventName = eventName;
		this.eventDescription = "";
		this.location = location;
		this.sender = sender;
		this.listOfParticipants = new ArrayList<ClientProfile>();
	}
	
	@Override
	public String toString() {
		return String.format("Event[id=%s, eventName='%s', eventDescription='%s'"
				+ ", location='%s', senderId = '%s']", 
				id, eventName, eventDescription, location, sender.toString());
	}
	
	public String getEventName() {
		return eventName;
	}
	
	public String getEventDescription() {
		return eventDescription;
	}
	
	public String getLocation() {
		return location;
	}
	
	public ClientProfile getSender() {
		return sender;
	}
	
	public ArrayList<ClientProfile> getListOfParticipants() {
		return listOfParticipants;
	}
	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setSender(ClientProfile sender) {
		this.sender = sender;
	}
	
	public void addToListOfParticipants(ClientProfile participant) {
		if(this.listOfParticipants.contains(participant))
			this.listOfParticipants.add(participant);
	}
}
