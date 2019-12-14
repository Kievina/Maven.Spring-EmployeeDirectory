package io.zipcoder.persistenceapp.controllers;

import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.repositories.DepartmentRepository;
import io.zipcoder.persistenceapp.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
public class DepartmentController {
    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    //POST
    @PostMapping("/API")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        return new ResponseEntity<>(departmentService.createDepartment(department), HttpStatus.CREATED);
    }

    //GET
    @GetMapping("/API/{departmentNumber}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long departmentNumber) {
        return new ResponseEntity<>(departmentService.findDepartmentById(departmentNumber), HttpStatus.OK);
    }

    //GET ALL
    @GetMapping("/API")
    public ResponseEntity<Iterable<Department>> getAllDepartments() {
        return new ResponseEntity<>(departmentService.findAllDepartments(), HttpStatus.OK);
    }

    //TODO Set a new department manager (update department)
    //Update department manager field
    //PUT
    @PutMapping("/API/{departmentNumber}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long departmentNumber, @RequestBody Department newDepartment) {
        if (departmentService.updateDepartment(departmentNumber, newDepartment))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //DELETE
    @DeleteMapping("/API/{departmentNumber")
    public ResponseEntity<Boolean> deleteDepartment(@PathVariable Long departmentNumber) {
        if (departmentService.deleteDepartment(departmentNumber))

            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    //TODO Change department name


}