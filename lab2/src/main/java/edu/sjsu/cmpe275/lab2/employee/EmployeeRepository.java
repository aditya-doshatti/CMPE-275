package edu.sjsu.cmpe275.lab2.employee;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	
	 public List<Employee> findByEmployerId(long id);

}
