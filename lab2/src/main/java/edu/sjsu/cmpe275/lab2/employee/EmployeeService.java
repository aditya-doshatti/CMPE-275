package edu.sjsu.cmpe275.lab2.employee;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
//	private List<Employee> EmployersList = new ArrayList<>(Arrays.asList(new Employee(123,"Veritas","Storage","Pune"),
//			new Employee(124,"VMware","Storage","Palo Alto")));
	
	public List<Employee> getAllEmployees() {
		//return EmployersList;
		List<Employee> empList = new ArrayList<Employee>();
		employeeRepository.findAll().forEach(empList::add);
		return empList;
	}
	
	public Employee getEmployee(Long id) {
		//return EmployersList.stream().filter(e -> e.getId() == id).findFirst().get();
		if(id != null)
			return employeeRepository.findOne(id);
		else
			return null;
	}

	public void addEmployee(Employee emp) {
		//EmployersList.add(emp);
		employeeRepository.save(emp);
	}

	public void updateEmployee(Employee emp) {
		employeeRepository.save(emp);
	}

	public void deleteEmployee(long id) {
		//EmployersList.removeIf(e -> e.getId() == id);
		employeeRepository.delete(id);
	}

	public void addCollaboration(Employee emp1, Employee emp2) {
		employeeRepository.save(emp1);
		employeeRepository.save(emp2);
		
	}

}
