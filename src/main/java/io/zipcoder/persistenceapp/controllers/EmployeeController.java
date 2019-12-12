package io.zipcoder.persistenceapp.controllers;

import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    //POST
    @PostMapping("/API")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.CREATED);
    }

    //GET
    @GetMapping("/API/{employeeNumber}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long employeeNumber) {
        return new ResponseEntity<>(employeeRepository.findOne(employeeNumber), HttpStatus.OK);
    }

    //GET ALL
    @GetMapping("/API")
    public ResponseEntity<Iterable<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
    }

    //PUT
    @PutMapping("/API/{employeeNumber}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long employeeNumber, @RequestBody Employee newEmployee) {
        if(newEmployee.getEmployeeNumber() != null)
            return new ResponseEntity<>(employeeRepository.save(newEmployee), HttpStatus.OK);
        else
            return createEmployee(newEmployee);
    }

    //DELETE
    @DeleteMapping("/API/{employeeNumber")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable Long employeeNumber) {
//        employeeRepository.delete(employeeNumber);
//        return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);

        try {
            verifyEmployee(employeeNumber);
            employeeRepository.delete(employeeNumber);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    public void verifyEmployee(Long employeeNumber) {
        if (!employeeRepository.exists(employeeNumber))
            throw new IllegalArgumentException();
    }

}
