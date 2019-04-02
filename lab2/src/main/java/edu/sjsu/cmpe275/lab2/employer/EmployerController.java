package edu.sjsu.cmpe275.lab2.employer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployerController {

	@Autowired
	private EmployerService employerService;
	
	
	@RequestMapping("/employers")
	public List<Employer> getAllEmployers() {
		return employerService.getAllEmployers();
	}
	
	@RequestMapping("/employers/{id}")
	public Employer getEmployer(@PathVariable long id) {
		return employerService.getEmployer(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/employers")
	public void addEmployer(@RequestBody Employer emp) {
		employerService.addEmployer(emp);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/employers/{id}")
	public void updateEmployer(@RequestBody Employer emp, @PathVariable long id) {
		employerService.updateEmployer(emp, id);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/employers/{id}")
	public void deleteEmployer(@PathVariable long id) {
		employerService.deleteEmployer(id);
	}

}
