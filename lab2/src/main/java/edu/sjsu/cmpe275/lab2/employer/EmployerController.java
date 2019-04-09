package edu.sjsu.cmpe275.lab2.employer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.lab2.address.Address;

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
	public void addEmployer(@RequestParam String name
            , @RequestParam String description
            , @RequestParam(required = false) String title
            , @RequestParam(required = false) String street
            , @RequestParam(required = false) String city
            , @RequestParam(required = false) String state
            , @RequestParam(required = false) String zip) {
		employerService.addEmployer(new Employer((long)1, name, description, new Address(street, city, state, zip)));;
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
