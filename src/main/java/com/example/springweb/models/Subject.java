package com.example.springweb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int subjectID;

	private String subjectName;
	private boolean isLaboratory;
	private int numberOfLecturesPerWeek;
	private String department;
	private String type;

	@JsonIgnore
	@ManyToOne
	private Professor professor;

	@JsonIgnore
	@ManyToOne
	private Course course;

	@JsonIgnore
	@ManyToOne
	private Combination combination;

	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
	private Collection<Lecture> lecture = new ArrayList();

	public Subject(String name, int lectures, boolean isLaboratory, String dept){
		this.subjectName = name;
		this.numberOfLecturesPerWeek = lectures;
		this.isLaboratory = isLaboratory;
		this.department = dept;
	}

	public Subject() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Collection<Lecture> getLecture() {
		return lecture;
	}

	public void setLecture(Collection<Lecture> lecture) {
		this.lecture = lecture;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getNumberOfLecturesPerWeek() {
		return numberOfLecturesPerWeek;
	}

	public void setNumberOfLecturesPerWeek(int numberOfLecturesPerWeek) {
		this.numberOfLecturesPerWeek = numberOfLecturesPerWeek;
	}

	public int getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(int subjectID) {
		this.subjectID = subjectID;
	}

	public boolean isLaboratory() {
		return isLaboratory;
	}

	public void setLaboratory(boolean laboratory) {
		isLaboratory = laboratory;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Combination getCombination() {
		return combination;
	}

	public void setCombination(Combination combination) {
		this.combination = combination;
	}
}
