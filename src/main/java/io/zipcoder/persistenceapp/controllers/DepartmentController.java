package io.zipcoder.persistenceapp.controllers;

import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DepartmentController{
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    //POST
    @PostMapping("/API")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        return new ResponseEntity<>(departmentRepository.save(department), HttpStatus.CREATED);
    }

    //GET
    @GetMapping("/API/{departmentNumber}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long departmentNumber) {
        return new ResponseEntity<>(departmentRepository.findOne(departmentNumber), HttpStatus.OK);
    }

    //GET ALL
    @GetMapping("/API")
    public ResponseEntity<Iterable<Department>> getAllDepartments() {
        return new ResponseEntity<>(departmentRepository.findAll(), HttpStatus.OK);
    }

    //PUT
    @PutMapping("/API/{departmentNumber}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long departmentNumber, @RequestBody Department newDepartment) {
        if(newDepartment.getDepartmentNumber() != null)
            return new ResponseEntity<>(departmentRepository.save(newDepartment), HttpStatus.OK);
        else
            return createDepartment(newDepartment);
    }

    //DELETE
    @DeleteMapping("/API/{departmentNumber")
    public ResponseEntity<Boolean> deleteDepartment(@PathVariable Long departmentNumber) {
//        departmentRepository.delete(employeeNumber);
//        return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);

        try {
            verifyDepartment(departmentNumber);
            departmentRepository.delete(departmentNumber);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    public void verifyDepartment(Long departmentNumber) {
        if (!departmentRepository.exists(departmentNumber))
            throw new IllegalArgumentException();
    }

    //TODO Set a new department manager (update department)
    //TODO Change department name


}