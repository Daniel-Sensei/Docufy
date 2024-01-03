package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Course;
import com.exam.esameweb24_backend.persistence.model.CourseAttended;
import com.exam.esameweb24_backend.persistence.model.CourseBought;

import java.util.List;

public interface CourseDao {
    public List<Course> findByConsultant(String consultant);
    public List<CourseBought> findByAgency(String agency);
    public List<CourseAttended> findByEmployee(Long employee);
    public Course findById(Long id);
    public boolean insert(Course course);
    public boolean delete(Long id);
}
