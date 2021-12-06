package com.example.springweb.dao.repositories;

import com.example.springweb.models.Professor;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository extends CrudRepository<Professor, Integer> {

}
