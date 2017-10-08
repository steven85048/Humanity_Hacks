package server;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class ClientProfile {
	
	@Id
	private String id;
	
	private String firstName, lastName, profession, location;
	private ArrayList<Event> pastEventList;
	private ArrayList<ClientProfile> pastPartnerList;
	
	public ClientProfile() {}
	
	public ClientProfile(String firstName, String lastName, String profession, String location) {
		this.firstName = firstName;
		this.lastName  = lastName;
		this.profession = profession;
		this.location = location;
		this.pastEventList = new ArrayList<Event>();
		this.pastPartnerList = new ArrayList<ClientProfile>();
	}
	public ClientProfile(String firstName, String lastName, String location) {
		this.firstName = firstName;
		this.lastName  = lastName;
		this.profession = "";
		this.location = location;
		this.pastEventList = new ArrayList<Event>();
		this.pastPartnerList = new ArrayList<ClientProfile>();
	}
	
	@Override
	public String toString() {
		return String.format("ClientProfile[id=%s, firstName='%s', lastName='%s', "
				+ "profession='%s', location='%s']", 
				id, firstName, lastName, profession, location);
	}
	
	public ArrayList<Event> getPastEventList() {
		return pastEventList;
	}
	
	public ArrayList<ClientProfile> getPastPartnerList() {
		return pastPartnerList;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getProfession() {
		return profession;
	}
	
	public String getId() {
		return id;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void addToPastEventList(Event pastEvent) {
		if(!this.pastEventList.contains(pastEvent))
			this.pastEventList.add(pastEvent);
	}
	
	public void addToPartnerList(ClientProfile partner) {
		if(!this.pastPartnerList.contains(partner)) 
			this.pastPartnerList.add(partner);
	}
}
