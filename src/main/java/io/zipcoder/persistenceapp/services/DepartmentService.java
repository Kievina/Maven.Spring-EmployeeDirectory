package io.zipcoder.persistenceapp.services;

import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;

public class DepartmentService {

    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }


    public Department findDepartmentById(Long departmentNumber) {
        return departmentRepository.findOne(departmentNumber);
    }

    public Iterable<Department> findAllDepartments() {
        return departmentRepository.findAll();
    }

    public Boolean updateDepartment(Long departmentNumber, Department newDepartmentInfo) {
        try {
            verifyDepartment(departmentNumber);
            Department departmentToUpdate = departmentRepository.findOne(departmentNumber);
            departmentToUpdate.setDepartmentManager(newDepartmentInfo.getDepartmentManager());
            departmentToUpdate.setDepartmentName(newDepartmentInfo.getDepartmentName());
            departmentRepository.save(departmentToUpdate);
            return true;
        } catch (ResourceNotFoundException ex) {
            return false;
        }
    }

    public Boolean deleteDepartment(Long departmentNumber) {
        try {
            verifyDepartment(departmentNumber);
            departmentRepository.delete(departmentNumber);
            return true;
        } catch (ResourceNotFoundException ex) {
            return false;
        }
    }

    public void verifyDepartment(Long departmentNumber) {
        if (!departmentRepository.exists(departmentNumber))
            throw new IllegalArgumentException();
    }

    //TODO Set a new department manager (update department)
}
