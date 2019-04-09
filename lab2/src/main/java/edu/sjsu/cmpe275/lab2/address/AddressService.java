package edu.sjsu.cmpe275.lab2.address;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository addressRepository;
	
	public List<Address> getAllAddress() {
		//return EmployersList;
		List<Address> addressList = new ArrayList<Address>();
		addressRepository.findAll().forEach(addressList::add);
		return addressList;
	}
	
	public Address getAddress(String id) {
		//return EmployersList.stream().filter(e -> e.getId() == id).findFirst().get();
		return addressRepository.findOne(id);
	}

	public void addAddress(Address add) {
		//EmployersList.add(emp);
		addressRepository.save(add);
	}

	public void updateAddress(Address emp, long id) {
		addressRepository.save(emp);
	}

	public void deleteAddress(String id) {
		//EmployersList.removeIf(e -> e.getId() == id);
		addressRepository.delete(id);
	}

}
