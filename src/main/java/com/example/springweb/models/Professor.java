package com.example.springweb.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int professorID;

	private String professorName;

	private String subjects;

	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
	private Collection<Subject> subjectsTaught = new ArrayList();

	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
	private Collection<Lecture> lectures = new ArrayList();
	
	public Professor(String name, ArrayList <Subject> subjectsTaught){
		this.professorName = name;
		for(int i=0; i<subjectsTaught.size(); i++){
			Subject subject = subjectsTaught.get(i);
			subject.setProfessor(this);
		}
		this.subjectsTaught = subjectsTaught;
	}

	public Professor() {

	}

	public String getSubjects() {
		return subjects;
	}

	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}

	public Collection<Subject> getSubjectsTaught() {
		return subjectsTaught;
	}

	public void setSubjectsTaught(ArrayList<Subject> subjectsTaught) {
		for(int i=0; i < subjectsTaught.size(); i++){
			Subject subject = subjectsTaught.get(i);
			subject.setProfessor(this);
		}
		this.subjectsTaught = subjectsTaught;
	}

	public Collection<Lecture> getLectures() {
		return lectures;
	}

	public Professor setLectures(Collection<Lecture> lectures) {
		this.lectures = lectures;
		return this;
	}

	public int getProfessorID() {
		return professorID;
	}

	public void setProfessorID(int professorID) {
		this.professorID = professorID;
	}

	public String getProfessorName() {
		return professorName;
	}

	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}
	
}
