package edu.sjsu.cmpe275.lab2.employee;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.*;

import edu.sjsu.cmpe275.lab2.address.Address;
import edu.sjsu.cmpe275.lab2.employer.Employer;

@Entity
public class Employee {
	
	private static long idCounter = 0;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="EMP_ID")
	private Long id;
    private String name;
    private String email;
    private String title;
    @ManyToOne
    private Address address;

	@ManyToOne
    private Employer employer;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID")
    private Employee manager;
	
//	@OneToMany(mappedBy="manager")
//    private List<Employee> reports;
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="Collaboration",
	joinColumns={@JoinColumn(name="EMP_ID")},
	inverseJoinColumns={@JoinColumn(name="COLLAB_ID")})
	private Set<Employee> persons = new HashSet<Employee>();
	
	@ManyToMany(mappedBy="persons")
    private List<Employee> collaborators;
	
	public Employee(String name, String email, String title, Address address, Employer employer, Employee managerId) {
		super();
//		this.id = Employee.idCounter + 1;
//		Employee.idCounter += 1;
		this.name = name;
		this.email = email;
		this.title = title;
		this.address = address;
		this.employer = employer;
		if (managerId != null)
			this.setManager(managerId);
	}

	public Employee() {
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
	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

//	public List<Employee> getReports() {
//		return reports;
//	}
//
//	public void setReports(Employee reports) {
//		this.reports.add(reports);
//	}
//
	public List<Employee> getCollaborators() {
		return collaborators;
	}
	
	public void setCollaborators(Employee collaborators) {
		this.collaborators.add(collaborators);
	}

	public Set<Employee> getPersons() {
		return persons;
	}

	public void setPersons(Set<Employee> persons) {
		this.persons = persons;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
