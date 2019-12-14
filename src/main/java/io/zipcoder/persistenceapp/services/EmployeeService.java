package io.zipcoder.persistenceapp.services;


import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findEmployeeById(Long employeeNumber) {
        return employeeRepository.findOne(employeeNumber);
    }


    public Iterable<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    //Update employee to set new manager and other fields
    public Boolean updateEmployeeManager(Long employeeNumber, Employee newEmployeeInfo) {
        try {
            verifyEmployee(employeeNumber);
            Employee employeeToUpdate = employeeRepository.findOne(employeeNumber);
            employeeToUpdate.setManager(newEmployeeInfo.getManager());
            employeeToUpdate.setFirstName(newEmployeeInfo.getFirstName());
            employeeToUpdate.setLastName(newEmployeeInfo.getLastName());
            employeeToUpdate.setTitle(newEmployeeInfo.getTitle());
            employeeToUpdate.setPhoneNumber(newEmployeeInfo.getPhoneNumber());
            employeeToUpdate.setEmail(newEmployeeInfo.getEmail());
            employeeToUpdate.setHireDate(newEmployeeInfo.getHireDate());
            employeeRepository.save(employeeToUpdate);
            return true;
        } catch (ResourceNotFoundException ex) {
            return false;
        }
    }

    //Delete a specific employee
    public Boolean deleteEmployee(Long employeeNumber) {
        try {
            verifyEmployee(employeeNumber);
            employeeRepository.delete(employeeNumber);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public void verifyEmployee(Long employeeNumber) {
        if (!employeeRepository.exists(employeeNumber))
            throw new IllegalArgumentException("Employee not found");
    }

    //TODO Get list of employees under a particular manager

}
