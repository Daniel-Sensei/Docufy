package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Employee;

import java.util.List;

public interface EmployeeDao {
    public List<Employee> findByAgency(String agency);
    public Employee findById(Long id);
    public boolean insert(Employee employee);
    public boolean delete(Long id);
}
