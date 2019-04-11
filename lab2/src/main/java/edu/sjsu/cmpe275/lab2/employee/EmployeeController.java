package edu.sjsu.cmpe275.lab2.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.lab2.address.Address;
import edu.sjsu.cmpe275.lab2.address.AddressService;
import edu.sjsu.cmpe275.lab2.employer.Employer;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private AddressService addressService;
	
	
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
		Employee emp = new Employee();
		emp.setName(name);
		emp.setEmail(email);
		emp.setTitle(title);
		if (street != null) {
			emp.setAddress(addressService.getAddress(street));
		}
		emp.setEmployer(new Employer(employerId,"","",null));
		emp.setManager(employeeService.getEmployee(managerId));
		//emp.setEmployer();
		employeeService.addEmployee(emp);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/employers/{employerId}/employee/{id}")
	public ResponseEntity<Object> updateEmployee(@PathVariable long id, @RequestParam String name
            , @RequestParam String email
            , @RequestParam(required = false) String title
            , @RequestParam(required = false) String street
            , @RequestParam(required = false) String city
            , @RequestParam(required = false) String state
            , @RequestParam(required = false) String zip
            , @RequestParam Long employerId
            , @RequestParam(required = false) Long managerId
            ) {
		Employee emp = employeeService.getEmployee(id);
		if(emp ==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		if (name != null)
			emp.setName(name);
		if (email != null)
			emp.setEmail(email);
		if (title != null)
			emp.setTitle(title);
		if (street != null) {
			emp.setAddress(addressService.getAddress(street));
		}
		if (employerId != null)
			emp.setEmployer(new Employer(employerId,"","",null));
		if (managerId != null)
			emp.setManager(employeeService.getEmployee(managerId));
		employeeService.updateEmployee(emp);
		return ResponseEntity.ok(emp);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/employee/{id}")
	public ResponseEntity<Object> deleteEmployer(@PathVariable long id) {
		try {
			employeeService.deleteEmployee(id);
		}
		catch(Exception e) {
			if (e.getClass().equals(new org.springframework.dao.EmptyResultDataAccessException(0).getClass())) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			if (e.getClass().equals(new org.springframework.dao.DataIntegrityViolationException(null).getClass())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
		}
		return ResponseEntity.ok().build();
	}

}
