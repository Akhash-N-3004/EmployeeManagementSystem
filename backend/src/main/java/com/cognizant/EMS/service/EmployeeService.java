package com.cognizant.EMS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.EMS.Exception.ResourceNotFoundException;
import com.cognizant.EMS.entity.Employee;
import com.cognizant.EMS.repository.EmployeeRepository;
import com.cognizant.EMS.repository.TrainingSlotRepository;

@Service
public class EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;
  
  @Autowired
  private TrainingSlotRepository trainingRepository;

  public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
  }

  public Employee createEmployee(Employee employee) {
    //String bcryptHashString = BCrypt.withDefaults().hashToString(12, employee.getPassword().toCharArray());
    employee.setPassword(employee.getPassword());
    employee.setTotalLeaveCount(36);
    employee.setTrainingSlot(10);

    return employeeRepository.save(employee);
  }

  public Employee login(String email, String password) {
    Employee employee = employeeRepository.findByEmailId(email);

    if (employee == null || !password.equals(employee.getPassword())) {
      return null;
    }

    return employee;
  }

  public Employee getEmployee(Long id) {
    return employeeRepository.findById(id).orElse(null);
  }

  public Employee updateEmployee(Long id, Employee updatedEmployee) {
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

    if(updatedEmployee.hasFirstName()) {
    	employee.setFirstName(updatedEmployee.getFirstName());
    }
    if(updatedEmployee.hasLastName()) {
    	employee.setLastName(updatedEmployee.getLastName());
    }
    if(updatedEmployee.hasAge()) {
    	employee.setAge(updatedEmployee.getAge());
    }
    if(updatedEmployee.hasMobileNumber()) {
    	employee.setMobileNumber(updatedEmployee.getMobileNumber());
    }
    if(updatedEmployee.hasAddress()) {
    	employee.setAddress(updatedEmployee.getAddress());
    }
    if(updatedEmployee.hasPassword()) {
    	employee.setPassword(updatedEmployee.getPassword());
    }
    if(updatedEmployee.hasEmailId()) {
    	employee.setEmailId(updatedEmployee.getEmailId());
    }

    return employeeRepository.save(employee);
  }
  
  public Employee updatePassword(Long id, Employee updatepassword) {
	  Employee employee = employeeRepository.findById(id)
		        .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
	  
	  if(updatepassword.hasPassword()) {
	    	employee.setPassword(updatepassword.getPassword());
	    }
	    return employeeRepository.save(employee);
  }

  public void deleteEmployee(Long id) {
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
    employeeRepository.delete(employee);
  }

//  public void requestTrainingSlot(Long id) {
//    Employee employee = employeeRepository.findById(id)
//        .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
//
//    // Check if training slot is available
//    if (employee.getTrainingSlot() > 0) {
//      // Request training slot logic here
//      // ...
//    } else {
//      throw new IllegalArgumentException("No available training slots");
//    }
//  }

//  public void requestCertificate(Long id, String certificateType) {
//    employeeRepository.findById(id)
//        .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
//
//    // Request certificate logic here
//    // ...
//  }

  public Employee getEmployeeByEmailId(String emailId) {

    return employeeRepository.findByEmailId(emailId);
  }

  public void save(Employee employee) {
    employeeRepository.save(employee);

  }

public Employee updatePassword(String emailId, String password) throws ResourceNotFoundException {
	 Employee employee = employeeRepository.findByEmailId(emailId);

     if (employee != null) {
         
         employee.setPassword(password);
         
         return employeeRepository.save(employee);
     } else {
         throw new ResourceNotFoundException("Employee not found");
     }
	
}

}
