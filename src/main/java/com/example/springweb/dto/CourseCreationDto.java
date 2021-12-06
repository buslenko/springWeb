package com.example.springweb.dto;

import com.example.springweb.models.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseCreationDto {

    private List<Course> courses;

    public CourseCreationDto(List<Course> courses) {
        this.courses = courses;
    }

    public CourseCreationDto() {
        this.courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
