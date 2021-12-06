package com.example.springweb.dao.repositories;

import com.example.springweb.models.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Integer> {

}
