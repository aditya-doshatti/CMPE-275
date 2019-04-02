package edu.sjsu.cmpe275.lab2.employer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class EmployerService {
	private List<Employer> EmployersList = new ArrayList<>(Arrays.asList(new Employer(123,"Veritas","Storage","Pune"),
			new Employer(124,"VMware","Storage","Palo Alto")));
	
	public List<Employer> getAllEmployers() {
		return EmployersList;
	}
	
	public Employer getEmployer(long id) {
		return EmployersList.stream().filter(e -> e.getId() == id).findFirst().get();
	}

	public void addEmployer(Employer emp) {
		EmployersList.add(emp);		
	}

	public void updateEmployer(Employer emp, long id) {
		for (int i = 0;i < EmployersList.size() ; i ++) {
			Employer e= EmployersList.get(i);
			if (e.getId() == id) {
				EmployersList.set(i, emp);
				return;
			}
		}
	}

	public void deleteEmployer(long id) {
		EmployersList.removeIf(e -> e.getId() == id);
	}

}
