package com.example.springweb.dto;

import com.example.springweb.models.ClassRoom;
import com.example.springweb.models.Course;
import com.example.springweb.models.Professor;
import com.example.springweb.models.Subject;

public class FormCreationDto {

    private ClassRoomCreationDto classRooms;
    private SubjectCreationDto subjects;
    private ProfessorCreationDto professors;
    private CourseCreationDto courses;

    public FormCreationDto(){

        ClassRoomCreationDto classRooms = new ClassRoomCreationDto();
        SubjectCreationDto subjects = new SubjectCreationDto();
        ProfessorCreationDto professors = new ProfessorCreationDto();
        CourseCreationDto courses = new CourseCreationDto();

        for (int i = 0; i < 50; i++){
            subjects.addSubject(new Subject());
            classRooms.addClassRoom(new ClassRoom());
            professors.addProfessor(new Professor());
            courses.addCourse(new Course());
        }
    }

    public ClassRoomCreationDto getClassRooms() {
        return classRooms;
    }

    public SubjectCreationDto getSubjects() {
        return subjects;
    }

    public ProfessorCreationDto getProfessors() {
        return professors;
    }

    public CourseCreationDto getCourses() {
        return courses;
    }
}
