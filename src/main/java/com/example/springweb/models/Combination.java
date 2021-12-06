package com.example.springweb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Combination {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int combinationID;

	@OneToMany(mappedBy = "combination", cascade = CascadeType.ALL)
	private Collection<Subject> subjectCombination = new ArrayList<>();

	@JsonIgnore
	@ManyToOne
	private Course course;

	@JsonIgnore
	@ManyToOne
	private StudentGroup studentGroup;

	private int sizeOfClass;

	public Combination(ArrayList<Subject> subjects, int size) {
		for(int i = 0; i < subjects.size();i++){
			Subject subject = subjects.get(i);
			subject.setCombination(this);
			subjectCombination.add(subject);
		}
	}

	public Combination() {

	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getCombinationID() {
		return combinationID;
	}

	public void setCombinationID(int combinationID) {
		this.combinationID = combinationID;
	}

	public Collection<Subject> getSubjects() {
		return subjectCombination;
	}

	public void setSubjects(ArrayList<Subject> subjects) {
		this.subjectCombination = subjects;
	}

	public StudentGroup getStudentGroup() {
		return studentGroup;
	}

	public void setStudentGroup(StudentGroup studentGroup) {
		this.studentGroup = studentGroup;
	}

	public int getSizeOfClass() {
		return sizeOfClass;
	}

	public void setSizeOfClass(int sizeOfClass) {
		this.sizeOfClass = sizeOfClass;
	}
}
