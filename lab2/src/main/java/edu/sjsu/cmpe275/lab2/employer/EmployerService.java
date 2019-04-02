package edu.sjsu.cmpe275.lab2.employer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployerService {
	
	@Autowired
	private EmployerRepository employerRepository;
	
	private List<Employer> EmployersList = new ArrayList<>(Arrays.asList(new Employer(123,"Veritas","Storage","Pune"),
			new Employer(124,"VMware","Storage","Palo Alto")));
	
	public List<Employer> getAllEmployers() {
		//return EmployersList;
		List<Employer> empList = new ArrayList<Employer>();
		employerRepository.findAll().forEach(empList::add);
		return empList;
	}
	
	public Employer getEmployer(long id) {
		//return EmployersList.stream().filter(e -> e.getId() == id).findFirst().get();
		return employerRepository.findOne(id);
	}

	public void addEmployer(Employer emp) {
		//EmployersList.add(emp);
		employerRepository.save(emp);
	}

	public void updateEmployer(Employer emp, long id) {
		employerRepository.save(emp);
	}

	public void deleteEmployer(long id) {
		//EmployersList.removeIf(e -> e.getId() == id);
		employerRepository.delete(id);
	}

}
