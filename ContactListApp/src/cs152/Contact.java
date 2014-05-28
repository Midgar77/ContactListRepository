package cs152;

import java.io.Serializable;

public class Contact implements Serializable, Comparable<Contact>{

	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	private String phoneNumber;
	private String notes;
	
	
	public Contact(String name, String email, String number, String notes){
		this.setName(name);
		this.setEmail(email);
		this.setPhoneNumber(number);
		this.setNotes(notes);
	}
	
	public Contact(Contact other){
		this.setName(other.getName());
		this.setEmail(other.getEmail());
		this.setPhoneNumber(other.getPhoneNumber());
		this.setNotes(other.getNotes());
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	@Override
	public String toString(){
		return name;
	}


	@Override
	public int compareTo(Contact otherContact) {
	    return this.getName().compareTo(otherContact.getName());
	}
	
	
}
