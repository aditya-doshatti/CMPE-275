package edu.sjsu.cmpe275.lab2.employer;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import edu.sjsu.cmpe275.lab2.address.Address;

@Entity
public class Employer {
	
	private static Long EmployerCounter = (long) 0.0;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    private String name;
    private String description;
    @ManyToOne
    private Address address;
	
	public Employer() {
	}
	
	public Employer(String name, String description, Address address) {
		super();
		this.name = name;
		this.description = description;
		this.address = address;
	}

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
