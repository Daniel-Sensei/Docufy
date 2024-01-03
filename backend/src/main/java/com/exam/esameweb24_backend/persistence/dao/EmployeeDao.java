package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Employee;

public interface EmployeeDao {
    public Employee findById(Long id);
    public Employee findByAgency(String agency);
    public boolean insert(Employee employee);
    public boolean delete(Long id);
}
