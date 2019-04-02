package edu.sjsu.cmpe275.lab2.employer;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employer {
	
	@Id
	private long id;
    private String name;
    private String description;
    private String address;
	
	public Employer() {
	}
	
	public Employer(long id, String name, String description, String address) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
	}

    public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
