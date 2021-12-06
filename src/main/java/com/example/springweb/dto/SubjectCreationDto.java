package com.example.springweb.dto;

import com.example.springweb.models.Subject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SubjectCreationDto {

    private List<Subject> subjects;

    public SubjectCreationDto(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public SubjectCreationDto() {
        this.subjects = new ArrayList<>();
    }

    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void refactor(){
        Iterator<Subject> subjectIterator = subjects.iterator();
        while (subjectIterator.hasNext()){
            Subject subject = subjectIterator.next();
            if (subject.getType().equals("true")){
                subject.setLaboratory(true);
            } else {
                subject.setLaboratory(false);
            }
        }
    }
}