package com.example.springweb.dto;

import com.example.springweb.models.Professor;

import java.util.ArrayList;
import java.util.List;

public class ProfessorCreationDto {

    private List<Professor> professors;

    public ProfessorCreationDto(List<Professor> professors) {
        this.professors = professors;
    }

    public ProfessorCreationDto() {
        this.professors = new ArrayList<>();
    }

    public void addProfessor(Professor professor) {
        this.professors.add(professor);
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }
}
