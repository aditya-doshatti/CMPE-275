package edu.sjsu.cmpe275.lab2.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	
	@RequestMapping("/address")
	public List<Address> getAllEmployers() {
		return addressService.getAllAddress();
	}
	
	@RequestMapping("/address/{street}")
	public Address getEmployer(@PathVariable String street) {
		return addressService.getAddress(street);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/address")
	public void addEmployer(@RequestParam String street
            , @RequestParam(required = false) String city
            , @RequestParam(required = false) String state
            , @RequestParam(required = false) String zip) {
		addressService.addAddress(new Address(street, city, state, zip));
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/address/{id}")
	public void updateEmployer(@RequestBody Address emp, @PathVariable long id) {
		addressService.updateAddress(emp, id);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/address/{street}")
	public void deleteEmployer(@PathVariable String street) {
		addressService.deleteAddress(street);
	}

}
