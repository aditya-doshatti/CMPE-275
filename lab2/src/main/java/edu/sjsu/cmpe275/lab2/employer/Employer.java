package edu.sjsu.cmpe275.lab2.employer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import edu.sjsu.cmpe275.lab2.address.Address;

@Entity
public class Employer {
	
	private static Long EmployerCounter = (long) 0.0;
	
	@Id
	private Long id;
    private String name;
    private String description;
    @ManyToOne
    private Address address;
	
	public Employer() {
	}
	
	public Employer(Long id, String name, String description, Address address) {
		super();
		if (id == null) {
			this.id = EmployerCounter + 1;
			EmployerCounter += 1;
		}
		else {
			this.id = id;
		}
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
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

}
