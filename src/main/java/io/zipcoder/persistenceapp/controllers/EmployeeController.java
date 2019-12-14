package io.zipcoder.persistenceapp.controllers;

import io.zipcoder.persistenceapp.models.Employee;
import io.zipcoder.persistenceapp.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //POST
    @PostMapping("/API")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.createEmployee(employee), HttpStatus.CREATED);
    }

    //GET
    @GetMapping("/API/{employeeNumber}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long employeeNumber) {
        return new ResponseEntity<>(employeeService.findEmployeeById(employeeNumber), HttpStatus.OK);
    }

    //GET ALL
    @GetMapping("/API")
    public ResponseEntity<Iterable<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    //Update employee to set new manager
    //PUT
    @PutMapping("/API/{employeeNumber}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long employeeNumber, @RequestBody Employee updatedEmployee) {
        if (employeeService.updateEmployeeManager(employeeNumber, updatedEmployee))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //DELETE
    @DeleteMapping("/API/{employeeNumber")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable Long employeeNumber) {
        if (employeeService.deleteEmployee(employeeNumber))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }


    //TODO Get list of employees under a particular manager

}
