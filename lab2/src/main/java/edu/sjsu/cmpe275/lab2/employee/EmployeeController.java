package edu.sjsu.cmpe275.lab2.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.lab2.address.Address;
import edu.sjsu.cmpe275.lab2.employer.Employer;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	
	@RequestMapping("/employee")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}
	
	@RequestMapping("/employers/{employerId}/employee/{id}")
	public Employee getEmployee(@PathVariable long id) {
		return employeeService.getEmployee(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/employee")
	public void addEmployee(@RequestParam String name
            , @RequestParam String email
            , @RequestParam(required = false) String title
            , @RequestParam(required = false) String street
            , @RequestParam(required = false) String city
            , @RequestParam(required = false) String state
            , @RequestParam(required = false) String zip
            , @RequestParam Long employerId
            , @RequestParam(required = false) Long managerId
            ) {
		Employee emp = new Employee(name,email,title, new Address(street, city, state, zip), 
				new Employer(employerId,"","",null), employeeService.getEmployee(managerId));
		//emp.setEmployer();
		employeeService.addEmployee(emp);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/employers/{employerId}/employee/{id}")
	public void updateEmployee(@RequestBody Employee emp, @PathVariable long employerId, @PathVariable long id) {
		emp.setEmployer(new Employer(employerId,"","",null));
		employeeService.updateEmployee(emp);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/employers/{employerId}/employee/{id}")
	public void deleteEmployer(@PathVariable long id) {
		employeeService.deleteEmployee(id);
	}

}
