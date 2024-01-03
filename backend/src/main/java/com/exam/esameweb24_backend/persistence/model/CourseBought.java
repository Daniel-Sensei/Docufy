package com.exam.esameweb24_backend.persistence.model;

import java.util.Date;

public class CourseBought {
    private Course course;
    private Agency agency;
    private Date date;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
