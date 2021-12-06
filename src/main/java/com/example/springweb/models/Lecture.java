package com.example.springweb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Lecture {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int lectureID;

	@JsonIgnore
	@ManyToOne
	private Professor professor;

	@JsonIgnore
	@ManyToOne
	private Subject subject;

	@JsonIgnore
	@ManyToOne
	private StudentGroup studentGroup;

	@JsonIgnore
	@ManyToOne
	private TimeTable timeTable;

	@JsonIgnore
	@OneToOne(mappedBy = "lecture")
	private TimeSlot timeSlot;

	public Lecture(Professor prof, Subject sub){
		this.professor=prof;
		this.subject=sub;
	}

	public Lecture() {

	}

	public TimeTable getTimeTable() {
		return timeTable;
	}

	public void setTimeTable(TimeTable timeTable) {
		this.timeTable = timeTable;
	}

	public int getLectureID() {
		return lectureID;
	}

	public void setLectureID(int lectureID) {
		this.lectureID = lectureID;
	}

	public TimeSlot getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public StudentGroup getStudentGroup() {
		return studentGroup;
	}

	public void setStudentGroup(StudentGroup studentGroup) {
		this.studentGroup = studentGroup;
	}
}
