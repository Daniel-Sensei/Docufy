package com.exam.esameweb24_backend.persistence.dao.postgres;

import com.exam.esameweb24_backend.persistence.DBManager;
import com.exam.esameweb24_backend.persistence.dao.CourseDao;
import com.exam.esameweb24_backend.persistence.model.Course;

import java.sql.Connection;
import java.util.List;

public class CourseDaoPostgres implements CourseDao {

    private static Connection conn = null;

    private static CourseDaoPostgres instance = null;

    public static CourseDaoPostgres getInstance() {
        if (instance == null) {
            instance = new CourseDaoPostgres();
            conn = DBManager.getInstance().getConnection();
        }
        return instance;
    }

    @Override
    public Course findById(Long id) {
        return null;
    }

    @Override
    public List<Course> findByConsultant(String consultant) {
        return null;
    }

    @Override
    public boolean insert(Course course) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
