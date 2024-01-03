package com.exam.esameweb24_backend.persistence.dao;

import com.exam.esameweb24_backend.persistence.model.Course;

import java.util.List;

public interface CourseDao {
    public Course findById(Long id);
    public List<Course> findByConsultant(String consultant);
    public boolean insert(Course course);
    public boolean delete(Long id);
}
