package edu.sjsu.cmpe275.lab2.employer;

public class Employer {
	
	public Employer() {
	}
	
	public Employer(long id, String name, String description, String address) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
	}
	private long id;
    private String name;
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
	private String description;
    private String address;
}
